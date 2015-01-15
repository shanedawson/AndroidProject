package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class CaseAssessmentMenuTeacher extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.case_presenatation_menu);


		Button form2 = (Button) findViewById(R.id.button_form2);
		form2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent student = new Intent(CaseAssessmentMenuTeacher.this, FormativeCaseEdit.class);
				student.putExtra("CASE", 1);
				startActivity(student);

			}
		});

		Button summative = (Button) findViewById(R.id.button_summative);
		summative.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent student = new Intent(CaseAssessmentMenuTeacher.this, SummativeCaseEdit.class);
				startActivity(student);

			}
		});

		Button form1 = (Button) findViewById(R.id.button_form1);
		form1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent student = new Intent(CaseAssessmentMenuTeacher.this, FormativeCaseEdit.class);
				student.putExtra("CASE", 0);
				startActivity(student);

			}
		});
	}
}
