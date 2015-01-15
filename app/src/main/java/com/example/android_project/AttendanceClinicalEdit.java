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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class AttendanceClinicalEdit extends ActionBarActivity {

	private final TaskCallback clinicalAttendanceCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("ClinicalAttendance", "Saved");
			Toast toast = Toast.makeText(AttendanceClinicalEdit.this, "Attendance Saved: Week" + week.getSelectedItemPosition() + 1, Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
	};

	private Spinner week = null;
	private EditText comments = null;
	private EditText signature = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_attendance_clinical);

		week = (Spinner) this.findViewById(R.id.weekSpinner);
		comments = (EditText) this.findViewById(R.id.consultComments);
		signature = (EditText) this.findViewById(R.id.consultantSig);
		Button recordAtt = (Button) this.findViewById(R.id.recordAttendanceClinical);
		recordAtt.setClickable(true);
		recordAtt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final int w;

				w = week.getSelectedItemPosition() + 1;
				AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceClinicalEdit.this);
				builder.setMessage("Confirm?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								try {
									Api.setClinicalAttendance(w, signature.getText().toString(), comments.getText().toString(), clinicalAttendanceCallback);
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

		week = (Spinner) this.findViewById(R.id.weekSpinner);


		ArrayAdapter<CharSequence> weekAdapt = ArrayAdapter.createFromResource(this, R.array.weekArray, android.R.layout.simple_spinner_dropdown_item);
		week.setAdapter(weekAdapt);


	}
}
