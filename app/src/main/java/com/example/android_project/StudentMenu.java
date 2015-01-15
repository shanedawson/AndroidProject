package com.example.android_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class StudentMenu extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_menu);

		Button selfAssess = (Button) findViewById(R.id.button_selfAssessment);
		selfAssess.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent student = new Intent(StudentMenu.this, SelfAssessmentMenu.class);
				startActivity(student);

			}
		});

		Button student_attendance = (Button) findViewById(R.id.button_student_attendance);
		student_attendance.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent student = new Intent(StudentMenu.this, AttendanceMenu.class);
				startActivity(student);

			}
		});

		Button caseAssessment = (Button) findViewById(R.id.button_casePresentation);
		caseAssessment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent student = new Intent(StudentMenu.this, CaseAssessmentMenu.class);
				startActivity(student);

			}
		});

		Button studentProfile = (Button) findViewById(R.id.myProfile);
		studentProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent student = new Intent(StudentMenu.this, StudentProfile.class);
				startActivity(student);

			}
		});
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setMessage("Logout?");
		builder1.setCancelable(true);
		builder1.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
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
}
