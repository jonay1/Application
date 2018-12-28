package com.wolf.app.tools;

import java.util.HashMap;
import java.util.Map;

public class MapCache {
	private static Map<Object, MapCache> cache = new HashMap<>();
	private Map<String, Object> innerMap = new HashMap<>();

	private MapCache() {
	}

	public static MapCache getInstance(Object group) {
		MapCache map = cache.get(group);
		if (map == null) {
			map = new MapCache();
			cache.put(group, map);
		}
		return map;
	}

	public static void clear(Object group) {
		MapCache cache = getInstance(group);
		cache.innerMap.clear();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) innerMap.get(key);
	}

	public MapCache put(String key, Object value) {
		this.innerMap.put(key, value);
		return this;
	}

	public <T> T get(String key, T defaultValue) {
		T v = get(key);
		if (v == null) {
			v = defaultValue;
		}
		return v;
	}

	public void clear() {
		this.innerMap.clear();
	}
}
