/*
 * Copyright (c) 2008 tamacat.org
 * All rights reserved.
 */
package org.tamacat.dao.orm;

import java.util.Map;

import org.tamacat.dao.meta.Column;

public interface ORMappingSupport<T extends ORMappingSupport<T>> extends Map<String, Object> {

	T mapping(Object name, Object value);

	String getValue(Column column);

	T setValue(Column column, String value);

	boolean isUpdate(Object name);
}
