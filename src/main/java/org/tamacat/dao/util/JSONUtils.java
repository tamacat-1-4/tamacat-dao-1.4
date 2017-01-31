package org.tamacat.dao.util;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.tamacat.dao.meta.Column;
import org.tamacat.dao.meta.DataType;
import org.tamacat.dao.orm.MapBasedORMappingBean;
import org.tamacat.util.CollectionUtils;
import org.tamacat.util.DateUtils;
import org.tamacat.util.StringUtils;

public class JSONUtils {

	public static String toString(MapBasedORMappingBean<?> bean, Column... columns) {
		return json(bean, columns).build().toString();
	}
	
	public static String toString(Collection<? extends MapBasedORMappingBean<?>> list, Column... columns) {
		return json(list, columns).build().toString();
	}
	
	public static JsonObjectBuilder json(MapBasedORMappingBean<?> bean, Column... columns) {
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
	public static JsonObjectBuilder toJson(MapBasedORMappingBean<?> bean, Column... columns) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		for (Column col : columns) {
			String value = bean.val(col);
			if (value != null && value.length()>0) {
				if (col.getType()==DataType.NUMERIC) {
					builder.add(col.getColumnName(), StringUtils.parse(value, 0L));
				} else if (col.getType()==DataType.FLOAT) {
					builder.add(col.getColumnName(), StringUtils.parse(value, 0d));
				} else if (col.getType()==DataType.TIME) {
					String format = col.getFormat();
					if (StringUtils.isNotEmpty(format)) {
						Date d = DateUtils.parse(value, format);
						if (d != null) {
							builder.add(col.getColumnName(), DateUtils.getTime(d, format));
						}
					} else {
						if (value.indexOf('.')>0) {
							builder.add(col.getColumnName(), DateUtils.parse(value, "yyyy-MM-dd HH:mm:ss.SSS").getTime());
						} else {
							builder.add(col.getColumnName(), DateUtils.parse(value, "yyyy-MM-dd HH:mm:ss").getTime());
						}
					}
				} else if (col.getType()==DataType.DATE) {
					builder.add(col.getColumnName(), DateUtils.parse(value, "yyyy-MM-dd").getTime());
				} else {
					builder.add(col.getColumnName(), value);
				}
			}
		}
		return builder;
	}
	
	public static JsonArrayBuilder json(Collection<? extends MapBasedORMappingBean<?>> list, Column... columns) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (MapBasedORMappingBean<?> bean : list) {
			builder.add(json(bean, columns));
		}
		return builder;
	}
	
	public static MapBasedORMappingBean<?> parse(MapBasedORMappingBean<?> bean, JsonParser parser, Column... columns) {
		Map<String, Column> colmaps = CollectionUtils.newLinkedHashMap();
		for (Column col : columns) {
			colmaps.put(col.getColumnName(), col);
		}
		Column col = null;
		while (parser.hasNext()) {
			Event event = parser.next();
			switch (event) {
				case KEY_NAME:
					String key = parser.getString();
					if (StringUtils.isNotEmpty(key)) {
						col = colmaps.get(key);
					}
					break;
				case VALUE_STRING:
					if (col != null) {
						bean.val(col, parser.getString());
						col = null;
					}
					break;
				case VALUE_TRUE:
					if (col != null) {
						bean.val(col, true);
						col = null;
					}
					break;
				case VALUE_FALSE:
					if (col != null) {
						bean.val(col, false);
						col = null;
					}
					break;
				case VALUE_NUMBER:
					if (col != null) {
						bean.val(col, parser.getLong());
						col = null;
					}
					break;
				case VALUE_NULL:
					if (col != null) {
						bean.val(col, "");
						col = null;
					}
					break;
				default:
					col = null;
					break;
			}
		}
		return bean;
	}
}
