package com.example.android_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class Reflection extends ActionBarActivity {

	private final TaskCallback callback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			try {
				result.get("owner");
				Log.d("SelfAssessment", result.toString());
				Toast toast = Toast.makeText(getApplicationContext(), "Reflection saved", Toast.LENGTH_LONG);
				toast.show();
				finish();
			} catch (Exception e) {
				Log.d("SelfAssessment", result.toString());
				Toast toast = Toast.makeText(getApplicationContext(), "You must complete both fields", Toast.LENGTH_LONG);
				toast.show();

			}

		}
	};

	private final TaskCallback getCallback = new TaskCallback() {
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

				final Spinner mark1 = (Spinner) findViewById(R.id.spinnerRef1);
				final Spinner mark2 = (Spinner) findViewById(R.id.spinnerRef2);
				final EditText hist = (EditText) findViewById(R.id.doDifferentHist);
				final EditText form = (EditText) findViewById(R.id.doDifferentForm);

				form.setText(q2);
				hist.setText(q1);
				mark1.setSelection(Integer.parseInt(m1));
				mark2.setSelection(Integer.parseInt(m2));

			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reflection_edit);

		Button submit = (Button) findViewById(R.id.buttonSubmit);
		final Spinner mark1 = (Spinner) findViewById(R.id.spinnerRef1);
		final Spinner mark2 = (Spinner) findViewById(R.id.spinnerRef2);
		final EditText hist = (EditText) findViewById(R.id.doDifferentHist);
		final EditText form = (EditText) findViewById(R.id.doDifferentForm);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.selfMarksArray, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mark1.setAdapter(adapter);
		mark2.setAdapter(adapter);

		try {
			Api.getSelfAssesmentCont(getCallback);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final int m1 = Integer.valueOf(mark1.getSelectedItem().toString());
				final int m2 = Integer.valueOf(mark2.getSelectedItem().toString());
				final int average = m1 + m2 / 2;

				AlertDialog.Builder builder = new AlertDialog.Builder(Reflection.this);
				builder.setMessage("Confirm?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								try {

									Api.setSelfAssesmentCont(hist.getText().toString(), form.getText().toString(), m1, m2, average, callback);
								} catch (JSONException | MalformedURLException e) {
									e.printStackTrace();
								}
							}
						});
				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		});

	}
}
