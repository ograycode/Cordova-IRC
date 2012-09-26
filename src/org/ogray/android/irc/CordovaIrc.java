package org.ogray.android.irc;

import java.util.HashMap;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CordovaIrc extends Plugin implements ThreadBridge {
	
	private IrcClient client;
	private static final String CONNECT = "connect";
	private static final String SEND = "send";
	private static final String MESSAGE = "message";
	private String callBack = null; 
	
	public CordovaIrc () {}

	public IrcClient getIrcClient(){
		if (client == null){
			client = IrcClient.getInstance();
		}
		return client;
	}
	
	@Override
	public PluginResult execute(String action, JSONArray arg1, String arg2) {
		if (this.callBack == null){
			this.callBack = arg2;	
		}
		if (action.contentEquals(CONNECT)){
			if (!this.getIrcClient().isInit()){
				try {
					JSONObject obj = arg1.getJSONObject(0);
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put(constants.USER_NAME, obj.getString(constants.USER_NAME));
					map.put(constants.PASSWORD, obj.getString(constants.PASSWORD));
					map.put(constants.PORT, Integer.valueOf(obj.getString(constants.PORT)));
					map.put(constants.CHANNEL, obj.getString(constants.CHANNEL));
					map.put(constants.SERVER, obj.getString(constants.SERVER));
					this.getIrcClient().init(map, this);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (!this.getIrcClient().isConnected()) {
				new IrcThread().start();
			}
		} else if (action.contentEquals(SEND)) {
			JSONObject obj;
			try {
				obj = arg1.getJSONObject(0);
				obj.put("type", "send_message");
				this.getIrcClient().message(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        PluginResult r = new PluginResult(PluginResult.Status.NO_RESULT);
        r.setKeepCallback(true);
        return r;
	}

	public void message(JSONObject args) {
		// TODO Auto-generated method stub
		PluginResult res = new PluginResult(PluginResult.Status.OK, args);
		res.setKeepCallback(true);
		this.success(res, this.callBack);
	}
}
