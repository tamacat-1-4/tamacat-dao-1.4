package org.tamacat.dao.util;

import java.util.Collection;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.tamacat.dao.meta.Column;
import org.tamacat.dao.meta.DataType;
import org.tamacat.dao.orm.MapBasedORMappingBean;
import org.tamacat.util.DateUtils;
import org.tamacat.util.StringUtils;

public class JSONUtils {

	public static String toString(MapBasedORMappingBean bean, Column... columns) {
		return json(bean, columns).build().toString();
	}
	
	public static String toString(Collection<? extends MapBasedORMappingBean> list, Column... columns) {
		return json(list, columns).build().toString();
	}
	
	public static JsonObjectBuilder json(MapBasedORMappingBean bean, Column... columns) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		for (Column col : columns) {
			String value = bean.val(col);
			if (value == null) value = "";
			builder.add(col.getColumnName(), value);
		}
		return builder;
	}

	/**
	 * Not included empty value.
	 * @since 1.4
	 * @param bean
	 * @param columns
	 */
	public static JsonObjectBuilder toJson(MapBasedORMappingBean bean, Column... columns) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		for (Column col : columns) {
			String value = bean.val(col);
			if (value != null && value.length()>0) {
				if (col.getType()==DataType.NUMERIC) {
					builder.add(col.getColumnName(), StringUtils.parse(value, 0L));
				} else if (col.getType()==DataType.FLOAT) {
					builder.add(col.getColumnName(), StringUtils.parse(value, 0d));
				} else if (col.getType()==DataType.TIME) {
					builder.add(col.getColumnName(), DateUtils.parse(value, "yyyy-MM-dd H:mm:ss.S").getTime());
				} else if (col.getType()==DataType.DATE) {
					builder.add(col.getColumnName(), DateUtils.parse(value, "yyyy-MM-dd").getTime());
				} else {
					builder.add(col.getColumnName(), value);
				}
			}
		}
		return builder;
	}
	
	public static JsonArrayBuilder json(Collection<? extends MapBasedORMappingBean> list, Column... columns) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (MapBasedORMappingBean bean : list) {
			builder.add(json(bean, columns));
		}
		return builder;
	}
}
