package com.example.android_project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

class CSV {

	private Context mContext;

	CSV(Context context) throws MalformedURLException {
		TaskCallback getSummativeCallback = new TaskCallback() {
			@Override
			public void onTaskDone(JSONObject result) throws JSONException {
				Log.d("getSummative", "Saved");
				int count = result.getInt("count");
				if (count == 0) {
					Toast toast = Toast.makeText(mContext, "No marks submitted", Toast.LENGTH_LONG);
					toast.show();
				} else {
					JSONArray results = result.getJSONArray("results");
					JSONObject markResults = results.getJSONObject(0);
					ArrayList<Integer> marks = new ArrayList<>();
					marks.add(markResults.getInt("question_one_a"));
					marks.add(markResults.getInt("question_one_b"));
					marks.add(markResults.getInt("question_one_mark"));

					marks.add(markResults.getInt("question_two_a"));
					marks.add(markResults.getInt("question_two_b"));
					marks.add(markResults.getInt("question_two_mark"));

					marks.add(markResults.getInt("question_three_a"));
					marks.add(markResults.getInt("question_three_b"));
					marks.add(markResults.getInt("question_three_mark"));

					marks.add(markResults.getInt("total"));
					try {
						ExportCSV(marks);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		if (Api.getID() == null) {
			Api.getSummativeCase(getSummativeCallback);
		} else {
			Api.getStudentSummativeCase(getSummativeCallback);
		}
		mContext = context;
	}

	void ExportCSV(AbstractList<Integer> marks) throws IOException {
		String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/marks.csv";
		CSVWriter writer = new CSVWriter(new FileWriter(csv));

		List<String[]> data = new ArrayList<>();
		data.add(new String[]{"Q1a", marks.get(0).toString()});
		data.add(new String[]{"Q1b", marks.get(1).toString()});
		data.add(new String[]{"Q1m", marks.get(2).toString()});

		data.add(new String[]{"Q2a", marks.get(3).toString()});
		data.add(new String[]{"Q2b", marks.get(4).toString()});
		data.add(new String[]{"Q2m", marks.get(5).toString()});

		data.add(new String[]{"Q3a", marks.get(6).toString()});
		data.add(new String[]{"Q3b", marks.get(7).toString()});
		data.add(new String[]{"Q3m", marks.get(8).toString()});

		data.add(new String[]{"Total", marks.get(9).toString()});

		writer.writeAll(data);

		writer.close();
		Log.d("FILE", csv);
		Toast toast = Toast.makeText(mContext, "File Saved: " + csv, Toast.LENGTH_LONG);
		toast.show();
	}
}
