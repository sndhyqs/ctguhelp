package com.ctgu.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 自己Json处理用的一个类
 * 
 * @author 晏青山
 * 
 */
public class Json {
	/**
	 * map转换一个一维的json数据
	 * 
	 * @param map
	 * @return String
	 */
	public static String mapTojson(Map<String, String> map) {
		JSONObject json = new JSONObject();
		for (String key : map.keySet()) {
			try {
				json.put(key, map.get(key));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return json.toString();
	}
}
