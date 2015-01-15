package com.example.android_project;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

/**
 * ${PACKAGE_NAME}
 * ${PROJECT_NAME}
 * shane
 */
public class AttendanceTaughtView extends ActionBarActivity {

	private final TaskCallback getAttendanceCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			if (result.getInt("count") != 0) {
				JSONArray results = result.getJSONArray("results");
				int count = result.getInt("count");
				for (int i = 0; i < count; i++) {
					JSONObject entry = results.getJSONObject(i);
					int day = entry.getInt("day");
					int week = entry.getInt("week");
					String amb = entry.getString("am");
					String pmb = entry.getString("pm");
					TableRow tableRow = (TableRow) tableLayout.getChildAt(day);
					LinearLayout linearLayout = (LinearLayout) tableRow.getChildAt(week + 1);
					TextView am = (TextView) linearLayout.getChildAt(0);
					TextView pm = (TextView) linearLayout.getChildAt(1);
					if (amb.equals("true")) {
						am.setText("1");
					}
					if (pmb.equals("true")) {
						pm.setText("1");
					}

				}

			}


		}
	};
	private TableLayout tableLayout = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setGravity(Gravity.CENTER);
		tableLayout = new TableLayout(this);
		tableLayout.setStretchAllColumns(true);

		//tableLayout.setBackgroundColor(Color.BLACK);
		tableLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER));
		tableLayout.setColumnShrinkable(0, true);

		for (int row = 0; row < 6; row++) {
			TableRow tableRow = new TableRow(this);
			tableRow.setBackgroundColor(Color.WHITE);


			TableLayout.LayoutParams rowLayoutParams = new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.MATCH_PARENT, 1.0f);
			rowLayoutParams.setMargins(1, 1, 1, 1);

			tableRow.setLayoutParams(rowLayoutParams);

			Integer week[] = {1, 2, 3, 4, 5, 6};
			String day[] = getResources().getStringArray(R.array.dayArray);

			for (int col = 0; col < 8; col++) {
				if (col != 0 && row != 0) {
					LinearLayout rowLinearLayout = new LinearLayout(this);
					rowLinearLayout.setOrientation(LinearLayout.VERTICAL);
					TextView am = new TextView(this);
					TextView pm = new TextView(this);
					am.setPadding(2, 2, 2, 2);
					am.setLayoutParams(new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.FILL_PARENT,
							LinearLayout.LayoutParams.FILL_PARENT));
					pm.setLayoutParams(new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.FILL_PARENT,
							LinearLayout.LayoutParams.FILL_PARENT));
					am.setGravity(Gravity.CENTER);
					pm.setGravity(Gravity.CENTER);
					pm.setPadding(2, 2, 2, 2);
					if (col != 1) {
						am.setText("0");
						pm.setText("0");
					}
					rowLinearLayout.addView(am);
					rowLinearLayout.addView(pm);
					tableRow.addView(rowLinearLayout);
				}

				if (col == 0) {
					TextView textView = new TextView(this);
					textView.setGravity(Gravity.CENTER);
					if (row == 0) {
						textView.setText("Week");
					} else {
						textView.setText(day[row - 1]);
					}
					tableRow.addView(textView);
				} else if (row == 0) {
					TextView textView = new TextView(this);
					textView.setGravity(Gravity.CENTER);

					if (col == 1)
						textView.setText("");
					else {
						textView.setText(week[col - 2].toString());
					}
					tableRow.addView(textView);

				}

				tableRow.setGravity(Gravity.CENTER);
			}
			tableLayout.addView(tableRow);
		}
		linearLayout.addView(tableLayout);
		setContentView(linearLayout);
		try {
			if (Api.getID() == null) {
				Api.getTaughtAttendance(getAttendanceCallback);
			} else {
				Api.getStudentTaughtAttendance(getAttendanceCallback, Api.getID());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}