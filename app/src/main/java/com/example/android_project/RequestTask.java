package com.example.android_project;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

class RequestTask extends AsyncTask<Object, Void, JSONObject> {

	private final TaskCallback listener;
	private final String token = Api.getToken();

	public RequestTask(TaskCallback callback) {
		this.listener = callback;
	}


	@Override
	protected JSONObject doInBackground(Object[] objects) {

		JSONObject json = (JSONObject) objects[0];
		URL url = (URL) objects[1];
		String type = (String) objects[2];

		HttpClient httpClient = new DefaultHttpClient();
		HttpMessage httpRequest = new HttpGet(url.toString());
		JSONObject jsonResponse;
		int timeout = 1000;

		httpClient.getParams().setParameter("http.socket.timeout", timeout * 1000);
		httpClient.getParams().setParameter("http.connection.timeout", timeout * 1000);
		try {
			HttpEntity params = new StringEntity(json.toString());

		/*	params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));*/

			if (type.contentEquals("GET")) {
				httpRequest = new HttpGet(url.toString());

			} else if (type.contentEquals("POST")) {
				httpRequest = new HttpPost(url.toString());
				((HttpEntityEnclosingRequest) httpRequest).setEntity(params);
			}
		} catch (UnsupportedEncodingException e) {
			Log.e("JSONRequest", e.toString());
		}

		httpRequest.addHeader("content-type", "application/json");

		if (token != null) {
			if (token.length() > 0) {
				httpRequest.addHeader("Authorization", "Token " + token);
			}
		}
		for (Header h : httpRequest.getAllHeaders()) {
			Log.d(h.getName(), h.getValue());
		}
		try {
			HttpResponse response = httpClient
					.execute((HttpUriRequest) httpRequest);
			Log.d("HTTPResponse", response.toString());

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String input = reader.readLine();

			JSONTokener tokener = new JSONTokener(input);
			jsonResponse = new JSONObject(tokener);
			Log.d("JSONResponse", jsonResponse.toString());

			return jsonResponse;

		} catch (Exception e) {
			Log.e("HTTPRESPONSE", e.toString());
			e.printStackTrace();

			try {
				return new JSONObject("{\"detail\":\"" + e.toString() + "\"} ");
			} catch (Exception e2) {
				e2.printStackTrace();
				return null;
			}
		}

	}


	@Override
	public void onPostExecute(JSONObject json) {
		//	if(progressDialog.isShowing())
		//	progressDialog.hide();
		try {

			listener.onTaskDone(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//if (dialog.isShowing()) {
		//	dialog.dismiss();
	}
	//throw thrownException;

}