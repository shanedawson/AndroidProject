package com.example.android_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;


public class MainActivity extends ActionBarActivity {


	private final TaskCallback isAdminCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("Admin", result.toString());
			if (result.getString("admin").equals("true")) {
				Log.d("LOGINSUCCESS", result.toString());
				Intent teacherMenu = new Intent(MainActivity.this, TeacherMenu.class);
				startActivity(teacherMenu);
			} else {
				Log.d("LOGINSUCCESS", result.toString());
				Intent studentMenu = new Intent(MainActivity.this, StudentMenu.class).putExtra("token", Api.getToken());
				startActivity(studentMenu);
				//Intent teacherMenu = new Intent(MainActivity.this, TeacherMenu.class);
				//startActivity(teacherMenu);
				//Toast toast = Toast.makeText(getApplicationContext(), "You have a student account", Toast.LENGTH_LONG);
				//toast.show();
			}

		}
	};
	private final TaskCallback teacherLoginCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) {
			try {
				if (result.getString("token") != null) {
					Api.setToken(result.getString("token"));
					Api.setID(null);
					Api.isAdmin(isAdminCallback);
				} else {
					Toast toast = Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG);
					toast.show();
				}
			} catch (JSONException | MalformedURLException e) {
				e.printStackTrace();
			}

		}
	};
	private EditText username = null;
	private EditText password = null;

	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setMessage("Exit?");
		builder.setCancelable(true);
		builder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finish();
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button login = (Button) findViewById(R.id.button1);
		//Button teacherOrTutor = (Button) findViewById(R.id.button2);
		username = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		Button register = (Button) findViewById(R.id.register);


		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Register.class);
				startActivity(intent);
			}
		});

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					Api.loginStudent(username.getText().toString(), password.getText().toString(), teacherLoginCallback);

				} catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG);
					toast.show();
					e.printStackTrace();
				}

			}
		});

	/*	teacherOrTutor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Api.loginStudent(username.getText().toString(), password.getText().toString(), teacherLoginCallback);
				} catch (JSONException |  MalformedURLException e) {
					e.printStackTrace();
				}


			}
		});*/


	}

}

