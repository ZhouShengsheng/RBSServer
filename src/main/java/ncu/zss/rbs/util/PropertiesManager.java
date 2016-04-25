package ncu.zss.rbs.util;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 该类管理所有与properties文件相关的操作
 *
 */
public class PropertiesManager {
	
	//private constructor
	private PropertiesManager() {
		
	}
	
	//contain all properties
	private static Map<String, Properties> properties = new HashMap<String, Properties>();
	
	public static PropertiesManager getInstance() {
		return Instance.instance;
	}
	
	//static inner class 
	public static class Instance {
		private static PropertiesManager  instance = new PropertiesManager();
	}
	
	/**
	 * get value by key 
	 * @param filename
	 * @param key
	 * @return
	 */
	public synchronized String getValue(String filename, String key) {
		Properties p = getProperties(filename);
		if(p == null)
			return null;
		return p.getProperty(key);
	}
	
	/**
	 * get properties by filename
	 * @param filename
	 * @return
	 */
	private Properties getProperties(String filename) {
		Properties p = properties.get(filename);
		if(p == null) {
			try {
				initProperties(filename);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties.get(filename);
	}
	
	/**
	 * init properties file by filename
	 * @param filename
	 * @throws AiException 
	 */
	private void initProperties(String filename) {
		InputStream in = null;
		Properties p = null;
		try {
			p = new Properties();
			in = this.getClass().getResourceAsStream("/" + filename);
			p.load(in);
			properties.put(filename, p);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
