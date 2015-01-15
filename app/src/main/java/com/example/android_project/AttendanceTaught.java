package com.example.android_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class AttendanceTaught extends ActionBarActivity {
	private final TaskCallback taughtAttendanceCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("TaughtAttendance", "Saved");
			Toast toast = Toast.makeText(getApplicationContext(), "Attendance Saved", Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
	};
	private Spinner week = null;
	private Spinner day = null;
	private Spinner time = null;
	private EditText attendanceKey = null;
	private int d = 0;
	private int w = 0;
	private boolean am = false;
	private boolean pm = false;
	private final TaskCallback keyCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			if (result.has("key")) {
				if (result.getString("key").equals("valid")) {
					Log.d("key", "valid");
					try {
						Api.setTaughtAttendance(w, d, am, pm, taughtAttendanceCallback);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				} else {
					Toast toast = Toast.makeText(getApplicationContext(), "Invalid Key", Toast.LENGTH_LONG);
					toast.show();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_attendance_teaching);

		Button recordAtt = (Button) this.findViewById(R.id.recordAttendanceTeach);
		recordAtt.setClickable(true);
		recordAtt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int t = time.getSelectedItemPosition();
				if (t == 0) {
					am = true;
					pm = false;
				} else {
					am = false;
					pm = true;
				}
				d = day.getSelectedItemPosition() + 1;
				w = week.getSelectedItemPosition() + 1;
				AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceTaught.this);
				builder.setMessage("Confirm?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								try {
									Api.checkKey(attendanceKey.getText().toString(), keyCallback);
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
		day = (Spinner) this.findViewById(R.id.daySpinner);
		time = (Spinner) this.findViewById(R.id.ampmSpinner);
		attendanceKey = (EditText) findViewById(R.id.attendance_key);

		ArrayAdapter<CharSequence> weekAdapt = ArrayAdapter.createFromResource(this, R.array.weekArray, android.R.layout.simple_spinner_dropdown_item);
		week.setAdapter(weekAdapt);

		ArrayAdapter<CharSequence> dayAdapt = ArrayAdapter.createFromResource(this, R.array.dayArray, android.R.layout.simple_spinner_dropdown_item);
		day.setAdapter(dayAdapt);

		ArrayAdapter<CharSequence> AMPMAdapt = ArrayAdapter.createFromResource(this, R.array.AMPMArray, android.R.layout.simple_spinner_dropdown_item);
		time.setAdapter(AMPMAdapt);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_attendance, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return id == R.id.action_settings || super.onOptionsItemSelected(item);
	}
}
