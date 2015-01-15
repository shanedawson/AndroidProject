package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelfAssessmentMenu extends ActionBarActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.selfassessmenu);
		Button history = (Button) findViewById(R.id.selfassesshistory);
		Button risk = (Button) findViewById(R.id.selfassessrisk);
		Button reflection = (Button) findViewById(R.id.selfassessreflection);
		clickListener(history, OperationType.history);
		clickListener(risk, OperationType.formulation);

		reflection.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent reflectionHist = new Intent(SelfAssessmentMenu.this, Reflection.class);
				startActivity(reflectionHist);


			}


		});
	}

	void clickListener(TextView butt, final OperationType selectedOpType) {
		// final Context ct = this.getBaseContext();
		butt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int task;

				switch (selectedOpType) {
					case history:
						task = 0;
						break;
					case formulation:
						task = 1;
						break;
					default:
						task = 0;
				}

				Intent intent = new Intent(getApplicationContext(), SelfAssessment.class);
				intent.putExtra("Type", task);
				startActivity(intent);
			}
		});

	}

	public static enum OperationType {history, formulation}

}
