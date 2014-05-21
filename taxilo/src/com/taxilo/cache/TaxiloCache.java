package com.taxilo.cache;

import java.util.HashMap;
import java.util.Map;

public class TaxiloCache {
	public static final Map<String,Map<String,String>>  edgeCache = new HashMap<String,Map<String,String>>();
	public static final Map<String,String> cityCache = new HashMap<String,String>();
	public static final Map<String,Map<String,String>> localityCache = new HashMap<String,Map<String,String>>();
	
}
