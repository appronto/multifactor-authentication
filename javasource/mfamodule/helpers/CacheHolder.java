package mfamodule.helpers;

import java.util.concurrent.ConcurrentHashMap;

public class CacheHolder {
	
	public final static String CreateCodeMicroflow = "createcode";
	public final static String ValidateCodeMicroflow = "validatecode";
	public final static String UserDisabledCheckMicroflow = "userdisabledcheck";
	
	private static ConcurrentHashMap<String, String> objects;
	
	public static void setMicroflow(String process, String microflowname) {
		if (objects == null) {
			objects = new ConcurrentHashMap<String, String>();
		}
		if (process != null && microflowname != null) {
			objects.put(process, microflowname);
		}
	}
	
	public static String getMicroflow(String process) {
		if (objects != null  && process != null) {
			return objects.get(process);
		}
		return null;
	}
	
	public static String removeMicroflow(String process) {
		if (objects != null && process != null) {
			return objects.remove(process);
		}
		return null;
	}
}
