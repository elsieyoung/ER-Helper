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

public class PhysicianSearchActivity extends Activity {
	/**
	 * This is the activity for physician to search for specific patient by
	 * health card number. And then he/she will be directed to
	 * DisplayPhysicianActivity.
	 */

	/**
	 * The constants representing the names of the objects passing to next
	 * activity.
	 */
	public final static String EXTRA_MESSAGE_SEARCH = "com.example.triage.HEALTHCARD";

	private EditText editHealthCard;
	private DataManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_physician);

		this.manager = DataManager.getInstance(this);
		manager.loadDatabase();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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

	public void searchPatient(View view) {
		// Specifies the next Activity to move to: DisplayActivity.
		Intent intent = new Intent(this, DisplayPhysicianActivity.class);

		// Gets the health card number from the EditText field.
		this.editHealthCard = (EditText) findViewById(R.id.health_card_num_box);
		String healthCardNumber = this.editHealthCard.getText().toString();

		// Passes the Patient object to DisplayActivity and starts
		// DisplayActivity
		// If the health card number is invalid or the patient is not found, and
		// alert dialog would prompt out.
		try {
			@SuppressWarnings("unused")
			Patient p = this.manager.searchPatientByHealthNum(healthCardNumber);
			intent.putExtra(EXTRA_MESSAGE_SEARCH, healthCardNumber);
			startActivity(intent);
		} catch (PatientNotFoundException e) {
			/** Prompt an alert is the patient is not found. */

			// Build a new AlertDialog
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// Set alert title
			alertDialogBuilder.setTitle("Patient Not Found");

			// Set dialog message
			alertDialogBuilder
					.setMessage(
							"Your input is invalid or the patient doesn't exist.")
					.setCancelable(false)
					.setNegativeButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// Create alert dialog specified
			AlertDialog alertDialog = alertDialogBuilder.create();

			// Show the dialog when the PatientNotFoundException occurs
			alertDialog.show();
		}
	}

}
