package ncu.zss.rbs.util;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Json util for converting among pojo(object), hash map and json string.
 *
 * @author Zhou Shengsheng
 * 
 */
public class JsonUtil {
	private final static ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
		// set the name format to lower_case_with_underscores (eg. show_name, product_category_index)
		mapper.setPropertyNamingStrategy(
			    PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		// convert date as string format
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
	/**
	 * Convert object to json string.
	 * @param object POJO.
	 * @return JSON string.
	 * @throws JsonProcessingException
	 */
	public static String objectToJsonString(Object object) {		
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Convert object to hash map.
	 * @param object POJO.
	 * @return Hash map.
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> objectToMap(Object object) {
		return mapper.convertValue(object, HashMap.class);
	}
	
	/**
	 * Convert json string to hash map.
	 * @param json Json string.
	 * @return Hash map.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> jsonStringToMap(String json) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(json, HashMap.class);
	}
	
	/**
	 * Convert map to json string.
	 * @param map Map<String, Object> instance.
	 * @return Json string.
	 */
	public static String mapToJsonString(Map<String, Object> map) {
		return JSONObject.toJSONString(map);
	}
}