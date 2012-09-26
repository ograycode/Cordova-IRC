package org.ogray.android.irc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import org.jibble.pircbot.*;
import org.json.JSONException;
import org.json.JSONObject;

public class IrcClient extends PircBot implements ThreadBridge {
	
	private static IrcClient client = null;
	private Boolean isInit = false;
	private String channel;
	private String server;
	private int port;
	private String password;
	private ThreadBridge bridge;
	
	private IrcClient () {
		super();
	}
	
	public static IrcClient getInstance () {
		if (client == null) {
			client = new IrcClient();
		}
		return client;
	}
	
	public Boolean isInit () {
		return this.isInit;
	}
	
	public void init (HashMap<String, Object> map, ThreadBridge callback) {
		this.setName((String) map.get("userName"));
		this.server = (String) map.get("server");
		this.port = (Integer) map.get("port");
		this.password = (String) map.get("password");
		this.channel = (String) map.get("channel");
		this.bridge = callback;
		this.isInit = true;
	}
	
	public void Connect () {
		try {
			this.connect(this.server);
			this.joinChannel(this.channel);
			JSONObject obj = new JSONObject();
			obj.put("type", "connected");
			obj.put("connected", "true");
			bridge.message(obj);
		} catch (NickAlreadyInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IrcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage (String message) {
		if (this.isConnected()){
			this.sendMessage(this.channel, message);
		}
	}
	
	public void onMessage(String channel,
            String sender,
            String login,
            String hostname,
            String message) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("type", "message");
			obj.put("channel", channel);
			obj.put("login", login);
			obj.put("hostname", hostname);
			obj.put("message", message);
			bridge.message(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void message(JSONObject args) {
		try {
			if (args.getString("type").equalsIgnoreCase("send_message")){
				this.sendMessage(args.getString("contents"));
			}
		} catch (Exception ex){
			//TODO
			ex.printStackTrace();
		}
	}
}
