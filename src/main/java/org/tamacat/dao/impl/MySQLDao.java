/*
 * Copyright (c) 2007 tamacat.org
 * All rights reserved.
 */
package org.tamacat.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.tamacat.dao.Dao;
import org.tamacat.dao.Query;
import org.tamacat.dao.Search;
import org.tamacat.dao.event.DaoEvent;
import org.tamacat.dao.meta.Column;
import org.tamacat.dao.orm.ORMappingSupport;
import org.tamacat.sql.SQLParser;

public class MySQLDao<T extends ORMappingSupport> extends Dao<T> {

	public MySQLDao() {
		parser = new SQLParser(new MySQLSearch.MySQLValueConvertFilter());
	}

	@Override
	public Search createSearch() {
		return new MySQLSearch();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Query<T> createQuery() {
		return new QueryImpl(new MySQLSearch.MySQLValueConvertFilter());
	}

	@Override
	public Collection<T> searchList(Query<T> query, int start, int max) {
		Collection<Column> columns = query.getSelectColumns();
		String sql = query.getSelectSQL();
		if (useHitCount) {
			sql = sql.replaceFirst("SELECT ", "SELECT SQL_CALC_FOUND_ROWS ");
		}
		if (start > 0 && max > 0) {
			sql = sql + " limit " + (start - 1) + "," + max;
		}
		DaoEvent event = createDaoEvent(sql);
		getExecuteHandler().handleBeforeExecuteQuery(event);
		Statement stmt = dbm.createStatement();
		getExecuteHandler().handleAfterExecuteQuery(event);
		ArrayList<T> list = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(sql);
			int add = 0;
			while (rs.next()) {
				T o = mapping(columns, rs);
				list.add(o);
				add++;
				if (max > 0 && add >= max)
					break;
			}
			if (useHitCount) {
				rs = stmt.executeQuery("SELECT FOUND_ROWS()");
				if (rs.next()) {
					long hit = rs.getLong(1);
					setHitCount(hit);
				}
			}
			dbm.close(rs);
		} catch (SQLException e) {
			handleException(e);
		} finally {
			dbm.close(stmt);
		}
		return list;
	}
}
