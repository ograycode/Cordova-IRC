package org.ogray.android.irc;

import java.util.HashMap;

import org.json.JSONObject;

public interface ThreadBridge {
	
	public abstract void message(JSONObject args);

}
