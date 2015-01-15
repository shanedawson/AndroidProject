package com.example.android_project;

import org.json.JSONException;
import org.json.JSONObject;

interface TaskCallback {
	public void onTaskDone(JSONObject result) throws JSONException;

}
