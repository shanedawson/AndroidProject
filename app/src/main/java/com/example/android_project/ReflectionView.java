package com.example.android_project;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;


public class ReflectionView extends ActionBarActivity {

	private final TaskCallback callback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			int count = result.getInt("count");
			if (count == 0) {
				Toast toast = Toast.makeText(getApplicationContext(), "None submitted", Toast.LENGTH_LONG);
				toast.show();
			} else {
				JSONArray results = result.getJSONArray("results");
				JSONObject entry = results.getJSONObject(0);
				String q1 = entry.getString("question_one");
				String q2 = entry.getString("question_two");
				String m1 = entry.getString("question_one_mark");
				String m2 = entry.getString("question_two_mark");

				form.setText(q2);
				hist.setText(q1);
				mark1.append(m1);
				mark2.append(m2);

			}
		}
	};
	private TextView form = null;
	private TextView hist = null;
	private TextView mark1 = null;
	private TextView mark2 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reflection_view);
		form = (TextView) findViewById(R.id.TextViewForm);
		hist = (TextView) findViewById(R.id.TextViewHist);
		mark1 = (TextView) findViewById(R.id.TextViewRef1);
		mark2 = (TextView) findViewById(R.id.TextViewRef2);


		try {
			if (Api.getID() == null) {
				Api.getSelfAssesmentCont(callback);
			} else {
				Api.getStudentSelfAssesmentCont(callback, Api.getID());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_reflection_view, menu);
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
