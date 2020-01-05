package net.kitty.lesson.helloapi.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertyUtil {
  
	private static Map<String, Properties> propFileMap = new ConcurrentHashMap<String, Properties>();

	public static void main(String[] args) {
	
		Properties prop2 = PropertyUtil.getProperties("/db.properties");
		System.out.println("jdbc.url: " + prop2.getProperty("jdbc.url"));
		Properties prop3 = PropertyUtil.getProperties("/http.properties");
		System.out.println("jdbc.url: " + prop3.getProperty("http.cookie"));
	}

	public static Properties getProperties(String fileName) {
		Properties prop = propFileMap.get(fileName);
		if (prop == null) {
			prop = new Properties();
		}
		InputStream is = null;
		try {
			is = PropertyUtil.class.getResourceAsStream(fileName);
			prop.load(is);
			propFileMap.put(fileName, prop);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return prop;
	}
}
