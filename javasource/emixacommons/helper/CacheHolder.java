package emixacommons.helper;

import java.util.concurrent.ConcurrentHashMap;

import com.mendix.systemwideinterfaces.core.IMendixObject;

public class CacheHolder {
	private static ConcurrentHashMap<String, IMendixObject> objects;
	
	public static void setObject(String Entity, IMendixObject object) {
		if (objects == null) {
			objects = new ConcurrentHashMap<String, IMendixObject>();
		}
		if (Entity != null && object != null) {
			objects.put(Entity, object);
		}
	}
	
	public static IMendixObject getObject(String Entity) {
		if (objects != null  && Entity != null) {
			return objects.get(Entity);
		}
		return null;
	}
	
	public static IMendixObject removeObject(String Entity) {
		if (objects != null && Entity != null) {
			return objects.remove(Entity);
		}
		return null;
	}
}
