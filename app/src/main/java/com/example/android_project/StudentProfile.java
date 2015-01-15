package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.net.MalformedURLException;


public class StudentProfile extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_profile);
		Button attendance = (Button) findViewById(R.id.attendanceProfile);
		Button clinicalAttendance = (Button) findViewById(R.id.clinicalAttendanceProfile);
		Button selfassesment = (Button) findViewById(R.id.selfAssessmentProfile);
		Button reflection = (Button) findViewById(R.id.reflectionProfile);
		Button casePresentation = (Button) findViewById(R.id.casePresentationProfile);
		Button csv = (Button) findViewById(R.id.exportCSV);

		csv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					new CSV(getApplicationContext());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		});
		attendance.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ca = new Intent(getApplicationContext(), AttendanceTaughtView.class);
				startActivity(ca);

			}
		});

		reflection.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ca = new Intent(getApplicationContext(), ReflectionView.class);
				startActivity(ca);

			}
		});

		clinicalAttendance.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ca = new Intent(getApplicationContext(), AttendanceClinicalView.class);
				startActivity(ca);

			}
		});

		selfassesment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ca = new Intent(getApplicationContext(), SelfAssessmentView.class);
				startActivity(ca);

			}
		});

		casePresentation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ca = new Intent(getApplicationContext(), CaseAssessmentMenu.class);
				startActivity(ca);

			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_student_profile, menu);
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
