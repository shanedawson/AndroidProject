package com.example.android_project;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;


public class SummativeCaseView extends ActionBarActivity {

	private final TaskCallback getSummativeCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("getSummative", "Saved");
			int count = result.getInt("count");
			if (count == 0) {
				Toast toast = Toast.makeText(getApplicationContext(), "No marks submitted", Toast.LENGTH_LONG);
				toast.show();
			} else {
				JSONArray results = result.getJSONArray("results");
				JSONObject markResults = results.getJSONObject(0);
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

				TextView q1a = (TextView) findViewById(R.id.textView_q1a);
				TextView q1b = (TextView) findViewById(R.id.textView_q1b);
				TextView q2a = (TextView) findViewById(R.id.textView_q2a);
				TextView q2b = (TextView) findViewById(R.id.textView_q2b);
				TextView q3a = (TextView) findViewById(R.id.textView_q3a);
				TextView q3b = (TextView) findViewById(R.id.textView_q3b);

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

			/*	for(int i =1; i<rows.size();i++){

					TableRow entry = (TableRow)tableLayout.getChildAt(i);
					TextView mark = (TextView)entry.getChildAt(1);
					TextView grade = (TextView)entry.getChildAt(2);
					mark.setText(marks.get(i-1).toString());
					grade.setText(getGrade(marks.get(i-1)));*/

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case_assessment);
		TextView q1a = (TextView) findViewById(R.id.textView_q1a);
		TextView q1b = (TextView) findViewById(R.id.textView_q1b);
		TextView q2a = (TextView) findViewById(R.id.textView_q2a);
		TextView q2b = (TextView) findViewById(R.id.textView_q2b);
		TextView q3a = (TextView) findViewById(R.id.textView_q3a);
		TextView q3b = (TextView) findViewById(R.id.textView_q3b);


		q1a.setText("0");
		q1b.setText("0");
		q2a.setText("0");
		q2b.setText("0");
		q3a.setText("0");
		q3b.setText("0");


		try {
			Api.getSummativeCase(getSummativeCallback);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	CharSequence getGrade(double mark) {
		if (mark > 0 && mark < 9.5) {
			return "  NG/G";
		} else if (mark >= 9.5 && mark < 12.5) {
			return "      F/E";
		} else if (mark >= 12.5 && mark < 15) {
			return "      D/C";
		} else if (mark >= 15 && mark < 17.5) {
			return " C+/B+";
		} else if (mark >= 17.5) {
			return "     A";
		}
		return "0";
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