package org.tamacat.dao.util;

import java.util.Collection;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.tamacat.dao.meta.Column;
import org.tamacat.dao.orm.MapBasedORMappingBean;

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

	public static JsonArrayBuilder json(Collection<? extends MapBasedORMappingBean> list, Column... columns) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (MapBasedORMappingBean bean : list) {
			builder.add(json(bean, columns));
		}
		return builder;
	}
}
