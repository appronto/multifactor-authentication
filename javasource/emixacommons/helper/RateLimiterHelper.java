package emixacommons.helper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.util.concurrent.RateLimiter;
import com.mendix.core.Core;
import com.mendix.logging.ILogNode;

public class RateLimiterHelper {
	
	//to support multiple ratelimiters
	public static RateLimiter RateLimiter (java.math.BigDecimal limitSize, String ratelimitQueueName) {
		RateLimiterHelper.log.trace("Current RateLimiterMap:"+RateLimiterMap);
		
		//default if nothing is provided
		if(limitSize == null && ratelimitQueueName == null) {
			log.debug("Default limit: 1");
			return rateLimiter;
		}
		
		//if limit + queue available? combi as key
		if(ratelimitQueueName != null && limitSize != null) {
			String key = ratelimitQueueName+limitSize.toString();
			log.debug("Custom limit:"+limitSize + " for queue"+ratelimitQueueName);
			if ( RateLimiterMap.containsKey(key)) {
				return RateLimiterMap.get(key);
			}
			else {
				RateLimiterMap.put(key, RateLimiter.create(limitSize.doubleValue()));
				return RateLimiterMap.get(key);
			}
		}
		
		//limitSize as key
		if(ratelimitQueueName == null && limitSize != null) {
			log.debug("Custom limit:"+limitSize + " without queue");
			String key = limitSize.toString();
			if ( RateLimiterMap.containsKey(key)) {
				return RateLimiterMap.get(key);
			}
			else {
				RateLimiterMap.put(key, RateLimiter.create(limitSize.doubleValue()));
				return RateLimiterMap.get(key);
			}
		}
		
		//ratelimitQueueName as key
		if(ratelimitQueueName != null && limitSize == null) {
			log.debug("Default limit: 1 for queue"+ratelimitQueueName);
			String key = ratelimitQueueName;
			if ( RateLimiterMap.containsKey(key)) {
				return RateLimiterMap.get(key);
			}
			else {
				RateLimiterMap.put(key, rateLimiter);
				return RateLimiterMap.get(key);
			}
		}
		
		
		return rateLimiter;
	}

	public static ILogNode log = Core.getLogger("Ratelimiter");
	private static RateLimiter rateLimiter = RateLimiter.create(1);
	private static Map<String, RateLimiter> RateLimiterMap = new ConcurrentHashMap<>();

}
