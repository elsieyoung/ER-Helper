package com.example.triage;

import java.util.Calendar;

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

public class AddVitalSignsActivity extends Activity {
	/**
	 * This is the activity for the user to add new vital signs of specific
	 * patient. When the user click on the "Add New Vital Signs" in the
	 * DisplayActivity, the application will lead the user to this activity.
	 * Inside, the user can input the systolic, diastolic and temperature and
	 * heart rate. When the user presses the "Submit" button, the new data will
	 * be saved and the application will jump back to the DisplayActivity.
	 */

	private EditText editSystolic;

	private EditText editDiastolic;

	private EditText editHeartRate;

	private EditText editTemperature;

	private DataManager manager;

	private String healthCardNumber;

	private String arrivalTime;

	private String recordTime;

	private Integer systolic;

	private Integer diastolic;

	private Integer heartRate;

	private Integer temperature;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_vital_signs);

		// Gets the Intent passed from DisplayActivity
		Intent intent = getIntent();

		// Uses key DisplayActivity.EXTRA_MESSAGE_DISPLAY to get the health card
		// number.
		this.healthCardNumber = (String) intent
				.getSerializableExtra(DisplayActivity.EXTRA_MESSAGE_DISPLAY);

		// Uses key DisplayActivity.EXTRA_MESSAGE_DISPLAY_2 to get the
		// DataManager object.
		this.manager = DataManager.getInstance(this);

		// Get the last visit record and the corresponding arrival time and the
		// Patient's health card number
		this.arrivalTime = manager.getLastVisit(healthCardNumber)
				.getArrivalTime();

		// Initiate the EditTexts
		editSystolic = (EditText) findViewById(R.id.systolic);
		editDiastolic = (EditText) findViewById(R.id.diastolic);
		editHeartRate = (EditText) findViewById(R.id.heartRate);
		editTemperature = (EditText) findViewById(R.id.temperature);

	}

	// the onClick method of the submitButton
	public void submit(View view) {
		if (editSystolic.length() == 0 || editDiastolic.length() == 0
				|| editHeartRate.length() == 0 || editTemperature.length() == 0) {
			Toast.makeText(view.getContext(), R.string.input_not_filled,
					Toast.LENGTH_SHORT).show();
			return;
		}
		// Get the input vital signs from the text box
		this.systolic = Integer.valueOf(editSystolic.getText().toString());
		this.diastolic = Integer.valueOf(editDiastolic.getText().toString());
		this.heartRate = Integer.valueOf(editHeartRate.getText().toString());
		this.temperature = Integer
				.valueOf(editTemperature.getText().toString());

		if (editSystolic.length() > 4 || editDiastolic.length() > 4
				|| editHeartRate.length() > 4 || editTemperature.length() > 4) {
			Toast.makeText(view.getContext(), R.string.invalid_input,
					Toast.LENGTH_SHORT).show();
			return;
		}
		// Get the input vital signs from the text box
		this.systolic = Integer.valueOf(editSystolic.getText().toString());
		this.diastolic = Integer.valueOf(editDiastolic.getText().toString());
		this.heartRate = Integer.valueOf(editHeartRate.getText().toString());
		this.temperature = Integer
				.valueOf(editTemperature.getText().toString());

		// Get the current time when the user presses the submitButton
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DATE));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(c.get(Calendar.MINUTE));
		String second = String.valueOf(c.get(Calendar.SECOND));
		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (second.length() == 1) {
			second = "0" + second;
		}
		recordTime = year + "-" + month + "-" + day + " " + hour + ":" + minute
				+ ":" + second;

		manager.addVital(healthCardNumber, arrivalTime, systolic, diastolic,
				temperature, heartRate, recordTime);

		/** Prompt the message that a new vital record is added successfully. */
		// Build a new AlertDialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// Set alert title
		alertDialogBuilder.setTitle("Add a new vital record");

		// Set dialog message
		alertDialogBuilder
				.setMessage("You have successfully added a new vital record!")
				.setCancelable(false)
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								AddVitalSignsActivity.this.finish();
							}
						});

		// Create alert dialog specified
		AlertDialog alertDialog = alertDialogBuilder.create();

		// Show the dialog when the there is no previous visit record
		alertDialog.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_vital_signs, menu);
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
}
