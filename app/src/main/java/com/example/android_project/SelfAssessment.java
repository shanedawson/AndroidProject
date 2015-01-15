package com.example.android_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;


public class SelfAssessment extends ActionBarActivity {

	private final TaskCallback selfAssessmentCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			try {
				if (result.getString("owner") != null) {
					Log.d("SelfAssessment", result.toString());
					Toast toast = Toast.makeText(getApplicationContext(), "Self assessment saved", Toast.LENGTH_LONG);
					toast.show();
					finish();
				}
			} catch (Exception e) {
				Log.d("SelfAssessment", result.toString());
				Toast toast = Toast.makeText(getApplicationContext(), "You must complete all fields", Toast.LENGTH_LONG);
				toast.show();
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam);
		Intent intent = getIntent();
		final Integer task = intent.getIntExtra("Type", 0);
		final EditText diagnosis = (EditText) findViewById(R.id.editText_diagnosis);
		final EditText well = (EditText) findViewById(R.id.editText_well);
		final EditText improve = (EditText) findViewById(R.id.editText_improve);
		final Spinner mark = (Spinner) findViewById(R.id.spinnerMark);
		Button submit = (Button) findViewById(R.id.buttonSubmit);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.selfMarksArray, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
		mark.setAdapter(adapter);


		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(SelfAssessment.this);
				builder.setMessage("Confirm?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								try {
									Api.setSelfAssesment(task, diagnosis.getText().toString(), well.getText().toString(), improve.getText().toString(), Integer.valueOf((String) mark.getSelectedItem()), selfAssessmentCallback);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_self_assessment, menu);
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
