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


public class SelfAssessmentView extends ActionBarActivity {

	private LinearLayout linearLayout = null;
	private final TaskCallback getSelfAssessmentCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("getSelAssess", "Saved");
			int count = result.getInt("count");
			if (count == 0) {
				Toast toast = Toast.makeText(getApplicationContext(), "None submitted", Toast.LENGTH_LONG);
				toast.show();
			} else {
				JSONArray results = result.getJSONArray("results");
				for (int i = 0; i < count; i++) {
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

					View view = inflater.inflate(R.layout.exam_view, linearLayout, false);


					JSONObject entry = results.getJSONObject(i);

					String well = entry.getString("well");
					String improve = entry.getString("improve");
					Integer task = entry.getInt("task");
					Integer mark = entry.getInt("mark");
					String diagnosis = entry.getString("diag");

					TextView texttask = (TextView) view.findViewById(R.id.texttask);
					TextView textdiagnosis = (TextView) view.findViewById(R.id.TextView_diagnosis);
					TextView textmark = (TextView) view.findViewById(R.id.TextView_mark);
					TextView textwell = (TextView) view.findViewById(R.id.TextView_well);
					TextView textimprove = (TextView) view.findViewById(R.id.TextView_improve);
					if (task == 0) {
						texttask.setText("History and Mental State");
					}
					if (task == 1) {
						texttask.setText("Formulation(including Risk Assessment)");
					}

					textdiagnosis.setText(diagnosis);

					textwell.setText(well);
					textimprove.setText(improve);
					textmark.setText(mark.toString());

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
				Api.getSelfAssesment(getSelfAssessmentCallback);
			} else {
				Api.getStudentSelfAssesment(getSelfAssessmentCallback, Api.getID());
				Log.d("ID", Api.getID().toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_self_assesment_view, menu);
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
