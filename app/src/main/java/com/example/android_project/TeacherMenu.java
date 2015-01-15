package com.example.android_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class TeacherMenu extends ActionBarActivity {
	private static ListView studentList;
	private final TaskCallback studentCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("StudentList", result.toString());

			ArrayList<String> data = new ArrayList<>();
			int count = result.getInt("count");
			if (count == 0) {
				Toast toast = Toast.makeText(getApplicationContext(), "No results", Toast.LENGTH_LONG);
				toast.show();
			}
			JSONArray students = result.getJSONArray("results");
			final ArrayList<Integer> ids = new ArrayList<>();
			for (int i = 0; i < count; i++) {
				JSONObject student = students.getJSONObject(i);
				data.add(student.get("first_name") + " " + student.get("last_name"));
				ids.add(student.getInt("id"));
			}

			@SuppressWarnings("unchecked") ListAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.customlayout, data);
			studentList.setAdapter(adapter);
			studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
					Intent intent = new Intent(getApplicationContext(), TeacherStudentView.class);
					Api.setID(ids.get(i));
					startActivity(intent);
				}

			});
		}
	};
	private final TaskCallback keyCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("Key", result.toString());
			Toast toast = Toast.makeText(getApplicationContext(), "Key: " + result.get("key"), Toast.LENGTH_LONG);
			toast.show();
			keyField.setText("Key: " + result.getString("key"));
		}
	};
	private EditText searchField = null;
	private TextView keyField = null;

	public void onBackPressed() {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setMessage("Logout?");
		builder1.setCancelable(true);
		builder1.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Api.setID(null);
						Api.setToken(null);
						finish();
					}
				});
		builder1.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert11 = builder1.create();
		alert11.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_menu);
		Button searchButton = (Button) this.findViewById(R.id.searchButton);
		searchField = (EditText) this.findViewById(R.id.searchField);
		studentList = (ListView) this.findViewById(R.id.studentList);
		Button keyButton = (Button) this.findViewById(R.id.buttonKey);
		keyField = (TextView) this.findViewById(R.id.key);

		try {
			Api.getStudentList(studentCallback);
			Api.getKey(keyCallback);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		keyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Api.generateKey(keyCallback);
				} catch (Exception e) {
					e.printStackTrace();
					Toast toast = Toast.makeText(getApplicationContext(), "Key generation failed", Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});

		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (searchField.getText().toString().equals("")) {
						Api.getStudentList(studentCallback);
					} else {
						Api.query(searchField.getText().toString(), studentCallback);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

		});
	}

}
