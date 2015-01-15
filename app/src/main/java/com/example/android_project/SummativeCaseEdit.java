package com.example.android_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;


public class SummativeCaseEdit extends ActionBarActivity {

	private final TaskCallback getSummativeCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("getSummative", "Saved");
			int count = result.getInt("count");
			if (count == 0) {
				Toast toast = Toast.makeText(SummativeCaseEdit.this, "No marks submitted", Toast.LENGTH_LONG);
				toast.show();
			} else {
				JSONArray results = result.getJSONArray("results");
				JSONObject markResults = results.getJSONObject(count - 1);
				ArrayList<Double> marks = new ArrayList<>();
				marks.add(markResults.getDouble("question_one_a"));
				marks.add(markResults.getDouble("question_one_b"));
				//marks.add(markResults.getDouble("question_one_mark"));

				marks.add(markResults.getDouble("question_two_a"));
				marks.add(markResults.getDouble("question_two_b"));
				//marks.add(markResults.getDouble("question_two_mark"));

				marks.add(markResults.getDouble("question_three_a"));
				marks.add(markResults.getDouble("question_three_b"));
				//marks.add(markResults.getDouble("question_three_mark"));

				marks.add(markResults.getDouble("total"));

				EditText q1a = (EditText) findViewById(R.id.textView_q1a);
				EditText q1b = (EditText) findViewById(R.id.textView_q1b);
				EditText q2a = (EditText) findViewById(R.id.textView_q2a);
				EditText q2b = (EditText) findViewById(R.id.textView_q2b);
				EditText q3a = (EditText) findViewById(R.id.textView_q3a);
				EditText q3b = (EditText) findViewById(R.id.textView_q3b);

				TextView g1a = (TextView) findViewById(R.id.textView_grade1a);
				TextView g1b = (TextView) findViewById(R.id.textView_grade1b);
				TextView g2a = (TextView) findViewById(R.id.textView_grade2a);
				TextView g2b = (TextView) findViewById(R.id.textView_grade2b);
				TextView g3a = (TextView) findViewById(R.id.textView_grade3a);
				TextView g3b = (TextView) findViewById(R.id.textView_grade3b);

				TextView q1t = (TextView) findViewById(R.id.textView_q1t);
				TextView q2t = (TextView) findViewById(R.id.textView_q2t);
				TextView q3t = (TextView) findViewById(R.id.textView_q3t);
				TextView qmt = (TextView) findViewById(R.id.textView_qmt);

				TextView g1t = (TextView) findViewById(R.id.textView_grade1t);
				TextView g2t = (TextView) findViewById(R.id.textView_grade2t);
				TextView g3t = (TextView) findViewById(R.id.textView_grade3t);
				//TextView gmt = (TextView) findViewById(R.id.textView_qmt);

				q1a.setText(marks.get(0).toString());
				q1b.setText(marks.get(1).toString());
				q2a.setText(marks.get(2).toString());
				q2b.setText(marks.get(3).toString());
				q3a.setText(marks.get(4).toString());
				q3b.setText(marks.get(5).toString());

				g1a.setText(getGrade(marks.get(0)));
				g1b.setText(getGrade(marks.get(1)));
				g2a.setText(getGrade(marks.get(2)));
				g2b.setText(getGrade(marks.get(3)));
				g3a.setText(getGrade(marks.get(4)));
				g3b.setText(getGrade(marks.get(5)));
				double q1m = marks.get(0) + marks.get(1);
				double q2m = marks.get(2) + marks.get(3);
				double q3m = marks.get(4) + marks.get(5);

				q1t.setText(String.valueOf(q1m));
				q2t.setText(String.valueOf(q2m));
				q3t.setText(String.valueOf(q3m));

				g1t.setText(getGrade(marks.get(0) + marks.get(1)));
				g2t.setText(getGrade(marks.get(2) + marks.get(3)));
				g3t.setText(getGrade(marks.get(4) + marks.get(5)));
				qmt.setText(String.valueOf(q1m + q2m + q3m));


			}
		}
	};
	private final TaskCallback setSummativeCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("setSummative", "Saved");
			Toast toast = Toast.makeText(SummativeCaseEdit.this, "Saved", Toast.LENGTH_LONG);
			toast.show();
			finish();
			try {
				Api.getStudentSummativeCase(getSummativeCallback);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case_assesment_edit);


		final EditText q1a = (EditText) findViewById(R.id.textView_q1a);
		final EditText q1b = (EditText) findViewById(R.id.textView_q1b);
		final EditText q2a = (EditText) findViewById(R.id.textView_q2a);
		final EditText q2b = (EditText) findViewById(R.id.textView_q2b);
		final EditText q3a = (EditText) findViewById(R.id.textView_q3a);
		final EditText q3b = (EditText) findViewById(R.id.textView_q3b);

		/*TextView g1a = (TextView) findViewById(R.id.textView_grade1a);
		TextView g1b = (TextView) findViewById(R.id.textView_grade1b);
		TextView g2a = (TextView) findViewById(R.id.textView_grade2a);
		TextView g2b = (TextView) findViewById(R.id.textView_grade2b);
		TextView g3a = (TextView) findViewById(R.id.textView_grade3a);
		TextView g3b = (TextView) findViewById(R.id.textView_grade3b);

		TextView q1t = (TextView) findViewById(R.id.textView_q1t);
		TextView q2t = (TextView) findViewById(R.id.textView_q2t);
		TextView q3t = (TextView) findViewById(R.id.textView_q3t);
		TextView qmt = (TextView) findViewById(R.id.textView_qmt);

		TextView g1t = (TextView) findViewById(R.id.textView_grade1t);
		TextView g2t = (TextView) findViewById(R.id.textView_grade2t);
		TextView g3t = (TextView) findViewById(R.id.textView_grade3t);
*/

		Button submit = (Button) findViewById(R.id.buttonSubmit);
		submit.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				final Double[] data = new Double[10];
				data[0] = Double.valueOf(q1a.getText().toString());
				data[1] = Double.valueOf(q1b.getText().toString());
				data[2] = data[0] + data[1];
				data[3] = Double.valueOf(q2a.getText().toString());
				data[4] = Double.valueOf(q2b.getText().toString());
				data[5] = data[3] + data[4];
				data[6] = Double.valueOf(q3a.getText().toString());
				data[7] = Double.valueOf(q3b.getText().toString());
				data[8] = data[6] + data[7];
				data[9] = data[2] + data[5] + data[8];

				AlertDialog.Builder builder = new AlertDialog.Builder(SummativeCaseEdit.this);
				builder.setMessage("Confirm?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								try {
									Api.setSummativeCase(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], setSummativeCallback);
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

		try {
			Api.getStudentSummativeCase(getSummativeCallback);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	CharSequence getGrade(double mark) {
		if (mark > 0 && mark < 9.5) {
			return "NG or G";
		} else if (mark >= 9.5 && mark < 12.5) {
			return "F or E";
		} else if (mark >= 12.5 && mark < 15) {
			return "D to C";
		} else if (mark >= 15 && mark < 17.5) {
			return "C+ to B+";
		} else if (mark >= 17.5 && mark < 12.5) {
			return "A";
		}
		return "NULL";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_case_assessment, menu);
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
