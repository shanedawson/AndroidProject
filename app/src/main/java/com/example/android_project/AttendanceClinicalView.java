package com.example.android_project;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;


public class AttendanceClinicalView extends ActionBarActivity {

	private LinearLayout linearLayout = null;
	private final TaskCallback callback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("getClinicalAttendance", "Saved");
			int count = result.getInt("count");
			if (count == 0) {
				Toast toast = Toast.makeText(getApplicationContext(), "No attendance submitted", Toast.LENGTH_LONG);
				toast.show();
			} else {
				JSONArray results = result.getJSONArray("results");
				for (int i = 0; i < count; i++) {
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

					View view = inflater.inflate(R.layout.activity_clinical_attendance_view, linearLayout, false);

					JSONObject entry = results.getJSONObject(i);

					String week = entry.getString("week");
					String signature = entry.getString("signature");
					String comments = entry.getString("comments");

					TextView tcomments = (TextView) view.findViewById(R.id.TextView_comments);
					TextView tweek = (TextView) view.findViewById(R.id.TextView_week);
					TextView tsignature = (TextView) view.findViewById(R.id.TextView_sig);

					tcomments.setText(comments);
					tsignature.setText(signature);
					tweek.append(week);


					linearLayout.addView(view);
				}
			}

		}


	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam_list);
		linearLayout = (LinearLayout) findViewById(R.id.examViewLinear);
		try {
			if (Api.getID() == null) {
				Api.getClinicalAttendance(callback);
			} else {
				Api.getStudentClinicalAttendance(callback, Api.getID());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_clinical_attendance_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
