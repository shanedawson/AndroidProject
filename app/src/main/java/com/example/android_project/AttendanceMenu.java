package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class AttendanceMenu extends ActionBarActivity {


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentattendancemenu);

		Button teachingProgram = (Button) findViewById(R.id.teachingPlacement);
		Button clinicalAttachment = (Button) findViewById(R.id.clinicalAttachment);

		teachingProgram.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent tp = new Intent(AttendanceMenu.this, AttendanceTaught.class);
				startActivity(tp);

			}
		});

		clinicalAttachment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ca = new Intent(AttendanceMenu.this, AttendanceClinicalEdit.class);
				startActivity(ca);

			}
		});


	}

}	