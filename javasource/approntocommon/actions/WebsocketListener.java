// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package approntocommon.actions;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IDataType;
import com.mendix.webui.CustomJavaAction;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

/**
 * To receive realtime messages from a websocket connection provide a url, subscribe message and the microflow that is processing that message.
 */
public class WebsocketListener extends CustomJavaAction<java.lang.Void>
{
	private java.lang.String URL;
	private java.lang.String onMessageMicroflow;
	private java.lang.String subscribeMessage;

	public WebsocketListener(IContext context, java.lang.String URL, java.lang.String onMessageMicroflow, java.lang.String subscribeMessage)
	{
		super(context);
		this.URL = URL;
		this.onMessageMicroflow = onMessageMicroflow;
		this.subscribeMessage = subscribeMessage;
	}

	@java.lang.Override
	public java.lang.Void executeAction() throws Exception
	{
		// BEGIN USER CODE
		
		try {
			 final CountDownLatch latch = new CountDownLatch(1);
			 
			 WebSocket ws = HttpClient
	                    .newHttpClient()
	                    .newWebSocketBuilder()
	                    .buildAsync(URI.create(URL), new WebSocketClient(latch, onMessageMicroflow))
	                    .join();
	            ws.sendText(subscribeMessage, true);

	            try {
	    		    latch.await(); // block here until message received, and latch will flip
	    		} catch (InterruptedException e) {
	    			Core.getLogger("Websocket").error("I was awoken while waiting");
	    		}
	            
	        } catch (Exception e) {
	        	Core.getLogger("Websocket").error("Error when receiving message, session close. Error: "+e.getMessage());
	            throw new RuntimeException(e);
	        }
		return null;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "WebsocketListener";
	}

	// BEGIN EXTRA CODE

	 
	 private static class WebSocketClient implements WebSocket.Listener {
		
		 
         private final CountDownLatch latch;
         private final String onMessage;

         public WebSocketClient(CountDownLatch latch, String onMessage) 
         { 
        	 this.latch = latch; 
        	 this.onMessage = onMessage;
         }

         @Override
         public void onOpen(WebSocket webSocket) {
        	 Core.getLogger("Websocket").debug("onOpen using subprotocol " + webSocket.getSubprotocol());
             WebSocket.Listener.super.onOpen(webSocket);
         }

         @Override
         public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        	 Core.getLogger("Websocket").debug("onText received " + data);
        	 HashMap<String, Object> parameters = new HashMap<String, Object>();

 			for(Entry<String,IDataType> entry : Core.getInputParameters(this.onMessage).entrySet())
 			{
 				String inputParameterName = entry.getKey();
 				IDataType inputParameterType = entry.getValue();
 				if (inputParameterType.getType() == IDataType.DataTypeEnum.String) {
 					parameters.put(inputParameterName, data.toString());
 				}
 			}
 			Core.microflowCall(this.onMessage)
 			.inTransaction(true)
 			.withParams(parameters)
 			.execute(Core.createSystemContext());
             latch.countDown();
             return WebSocket.Listener.super.onText(webSocket, data, last);
         }

         @Override
         public void onError(WebSocket webSocket, Throwable error) {
        	 Core.getLogger("Websocket").debug("Error: " + webSocket.toString());
             WebSocket.Listener.super.onError(webSocket, error);
         }
         
         @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        	 Core.getLogger("Websocket").debug("Stopped: " + webSocket.toString() + " statusCode: "+statusCode+ " reason: "+reason);
        	return Listener.super.onClose(webSocket, statusCode, reason);
        }
     }

	// END EXTRA CODE
}
