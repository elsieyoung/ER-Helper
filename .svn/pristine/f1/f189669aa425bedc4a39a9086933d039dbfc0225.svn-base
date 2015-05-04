package com.example.triage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPatientActivity extends Activity {
	/** The activity for Nurses to enter patient infomation for new patients. */

	private EditText editFirstName;

	private EditText editLastName;

	private EditText editYear;

	private EditText editMonth;

	private EditText editDay;

	private EditText editHCN;

	private String[] name;

	private String healthCardNumber;

	private int day;

	private int month;

	private int year;

	private DataManager manager;

	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_patient);

		this.manager = DataManager.getInstance(this);

		Intent intent = getIntent();

		editFirstName = (EditText) findViewById(R.id.patient_first_name);
		editLastName = (EditText) findViewById(R.id.patient_last_name);
		editYear = (EditText) findViewById(R.id.YearText);
		editMonth = (EditText) findViewById(R.id.MonthText);
		editDay = (EditText) findViewById(R.id.DayText);
		editHCN = (EditText) findViewById(R.id.patient_hcn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_patient, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.logout:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void submitInfo(View view) {
		if (editFirstName.length() == 0 || editLastName.length() == 0
				|| editYear.length() == 0 || editMonth.length() == 0
				|| editDay.length() == 0 || editHCN.length() == 0) {
			Toast.makeText(view.getContext(), R.string.input_not_filled,
					Toast.LENGTH_SHORT).show();
			return;
		}
		String patternName = "[A-Z][a-z]+";
		String patternYear = "(19|20)[0-9][0-9]";
		String patternMonth = "(0[1-9]|1[012])";
		String patternDay = "(0[1-9]|[12][0-9]|3[01])";
		String patternHCN = "[0-9]*";
		if (!editFirstName.getText().toString().matches(patternName)
				|| !editLastName.getText().toString().matches(patternName)) {
			Toast.makeText(view.getContext(), R.string.invalid_input_Name,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (!editYear.getText().toString().matches(patternYear)
				|| !editMonth.getText().toString().matches(patternMonth)
				|| !editDay.getText().toString().matches(patternDay)) {
			Toast.makeText(view.getContext(), R.string.invalid_input_DOB,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (!editHCN.getText().toString().matches(patternHCN)) {
			Toast.makeText(view.getContext(), R.string.invalid_input_HCN,
					Toast.LENGTH_SHORT).show();
			return;
		}

		this.name = new String[] { editFirstName.getText().toString(),
				editLastName.getText().toString() };
		this.year = Integer.valueOf(editYear.getText().toString());
		this.month = Integer.valueOf(editMonth.getText().toString());
		this.day = Integer.valueOf(editDay.getText().toString());
		this.healthCardNumber = editHCN.getText().toString();

		manager.addPatient(name, healthCardNumber, year, month, day);

		/** Prompt the message that a new patient is added successfully. */
		// Build a new AlertDialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// Set alert title
		alertDialogBuilder.setTitle("New patient added");

		// Set dialog message
		alertDialogBuilder
				.setMessage("You have successfully added a new patient!")
				.setCancelable(false)
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								AddPatientActivity.this.finish();
							}
						});

		// Create alert dialog specified
		AlertDialog alertDialog = alertDialogBuilder.create();

		// Show the dialog when the there is no previous visit record
		alertDialog.show();
	}
}
