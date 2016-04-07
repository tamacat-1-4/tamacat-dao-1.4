/*
 * Copyright (c) 2007 tamacat.org
 * All rights reserved.
 */
package org.tamacat.dao.meta;

import java.io.Serializable;

import org.tamacat.dao.validation.Validator;

public class DefaultColumn implements Column, Serializable {

	private static final long serialVersionUID = 4721350387373477367L;
	
	protected String columnName;
	protected String defaultValue;
	protected String name;
	protected String functionName;
	
	protected DataType type;
	
	protected boolean isAutoGenerateId;
	protected boolean isAutoTimestamp;
	protected boolean isNotNull;
	protected boolean isPrimaryKey;
	protected boolean isFunction;
	
	protected int maxLength = -1;
	protected Validator validator;
	protected Table table;

	public DefaultColumn() {
	}

	public DefaultColumn(String columnName) {
		this.columnName = columnName;
		this.name = columnName;
	}

	public DefaultColumn(Table table, String columnName, DataType type,
			String name, ColumnDefine... defines) {
		this.columnName = columnName;
		this.type = type;
		this.name = name;
		this.table = table;
		table.registerColumn(this);
		if (defines != null) {
			for (ColumnDefine def : defines) {
				if (PRIMARY_KEY.equals(def))
					this.isPrimaryKey = true;
				if (AUTO_GENERATE_ID.equals(def))
					this.isAutoGenerateId = true;
				if (AUTO_TIMESTAMP.equals(def))
					this.isAutoTimestamp = true;
				if (NOT_NULL.equals(def))
					this.isNotNull = true;
				if (FUNCTION.equals(def))
					this.isFunction = true;
			}
		}
	}

	public String getColumnName() {
		return columnName;
	}

	public DefaultColumn setColumnName(String columnName) {
		this.columnName = columnName;
		return this;
	}

	public DefaultColumn column(String columnName) {
		this.columnName = columnName;
		return this;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public DefaultColumn setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	@Override
	public boolean isAutoGenerateId() {
		return isAutoGenerateId;
	}

	@Override
	public DefaultColumn autoGenerateId(boolean isAutoGenerateId) {
		this.isAutoGenerateId = isAutoGenerateId;
		return this;
	}
	
	public DefaultColumn setAutoGenerateId(boolean isAutoGenerateId) {
		return autoGenerateId(isAutoGenerateId);
	}
	
	@Override
	public boolean isAutoTimestamp() {
		return isAutoTimestamp;
	}

	@Override
	public DefaultColumn autoTimestamp(boolean isAutoTimestamp) {
		this.isAutoTimestamp = isAutoTimestamp;
		return this;
	}
	
	public DefaultColumn setAutoTimestamp(boolean isAutoTimestamp) {
		return autoTimestamp(isAutoTimestamp);
	}

	@Override
	public boolean isNotNull() {
		return isNotNull;
	}

	@Override
	public DefaultColumn notNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
		return this;
	}
	
	public DefaultColumn setNotNull(boolean isNotNull) {
		return notNull(isNotNull);
	}

	@Override
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	
	@Override
	public DefaultColumn primaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
		return this;
	}
	
	public DefaultColumn setPrimaryKey(boolean isPrimaryKey) {
		return primaryKey(isPrimaryKey);
	}
	
	@Override
	public String getName() {
		return name;
	}

	public DefaultColumn setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public boolean isFunction() {
		return isFunction;
	}
	
	@Override
	public String getFunctionName() {
		return functionName;
	}

	public DefaultColumn setFunctionName(String functionName) {
		this.functionName = functionName;
		this.isFunction = true;
		return this;
	}
	
	@Override
	public DataType getType() {
		return type;
	}

	public DefaultColumn setType(DataType type) {
		this.type = type;
		return this;
	}

	@Override
	public DefaultColumn type(DataType type) {
		this.type = type;
		return this;
	}

	public Column setTable(Table table) {
		this.table = table;
		return this;
	}

	@Override
	public Table getTable() {
		return table;
	}
	
	@Override
	public Column maxLength(int maxLength) {
		this.maxLength = maxLength;
		return this;
	}
	
	@Override
	public int getMaxLength() {
		return maxLength;
	}

	@Override
	public Validator getValidator() {
		return validator;
	}
	
	@Override
	public Column set(Validator validator) {
		this.validator = validator;
		return this;
	}
}
