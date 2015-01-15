package com.example.android_project;



import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;


public class Register extends ActionBarActivity {


	private final TaskCallback registerCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("Register", result.toString());
			if (result.has("register")) {
				if (result.getString("register").equals("true")) {
					Toast toast = Toast.makeText(getApplicationContext(), "Registered: " + getStudentNumber().getText().toString(), Toast.LENGTH_LONG);
					toast.show();
					finish();
				}
				else {
					Toast toast = Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG);
					toast.show();
				}

			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG);
				toast.show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				switch (view.getId()) {
					case R.id.buttonSubmit:
						try {
							Api.registerStudent(getStudentNumber().getText().toString(), getPassword().getText().toString(), getEmail().getText().toString(), getFirstName().getText().toString(), getLastName().getText().toString(), registerCallback);
						} catch (JSONException | MalformedURLException e) {
							e.printStackTrace();
						}
						break;
				}
			}
		});
	}

	EditText getFirstName() {
		return (EditText) findViewById(R.id.first_name);
	}

	EditText getLastName() {
		return (EditText) findViewById(R.id.last_name);
	}

	EditText getEmail() {
		return (EditText) findViewById(R.id.email);
	}

	EditText getStudentNumber() {
		return (EditText) findViewById(R.id.student_number);
	}

	EditText getPassword() {
		return (EditText) findViewById(R.id.password);
	}
}

