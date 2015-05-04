package com.example.triage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayActivity extends Activity {
	/**
	 * This is the activity to show the user the personal information. Inside,
	 * the user can choose to "Add New Vital Signs", "Add A New Visit Record"
	 * and "View Previous Visit
	 * Records". When a patient comes to the emergency room, the user should "
	 * Add A New Visit Record" first. If not, the application will prompt alert
	 * that reminding the user to add new visit record first. Then while the
	 * patient waiting, the user can record his/her vital signs by pressing the
	 * "Add New Vital Signs" button. Also, the user can view the patient's
	 * previous visit records by clicking on "View Previous Visit Records"
	 * button.
	 */

	/**
	 * The constants representing the names of the objects passing to next
	 * activity.
	 */
	public final static String EXTRA_MESSAGE_DISPLAY = "com.example.triage.VISIT_HEALTHCARD";

	private String healthCardNumber;

	private Patient patient;

	private ListView listView;

	public String currentTime;

	private DataManager manager;

	private ArrayAdapter<String> listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);

		// Gets the Intent passed from MainActivity
		Intent intent = getIntent();

		// Uses key SearchActivity.EXTRA_MESSAGE_SEARCH to get the DataManager
		// and Patient objects.
		this.manager = DataManager.getInstance(this);
		this.healthCardNumber = (String) intent
				.getSerializableExtra(SearchActivity.EXTRA_MESSAGE_SEARCH);
		try {
			this.patient = manager.searchPatientByHealthNum(healthCardNumber);
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
		}

		// Create a new ArrayList<String> and put all the values of the personal
		// information of the Patient into it
		String[] name = patient.getName();
		Date DOB = patient.getDob();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DOB);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		String pDOB = year + "/" + month + "/" + day;
		String healthCard = patient.getHealthcard();
		ArrayList<String> data = new ArrayList<String>();
		data.add("Name: " + name[1] + ", " + name[0]);
		data.add("Date Of Birth: " + pDOB);
		data.add("Health Card Number: " + healthCard);

		// Instantiate a new ListView and set a new ArrayAdapter
		listView = (ListView) findViewById(R.id.visitListView);
		listAdapter = new ArrayAdapter<String>(this, R.layout.info, data);
		listView.setAdapter(listAdapter);
	}

	// the onClick method of the addVisitButton
	public void addVisits(View v) {
		// Get the current time when the user presses the addVisitButton
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
		currentTime = year + "-" + month + "-" + day + " " + hour + ":"
				+ minute + ":" + second;

		// Add a new visit record
		// patient.addVisit(currentTime);
		manager.addVisit(healthCardNumber, currentTime);
		manager.getVisit(healthCardNumber)
				.get(patient.getVisitData().size() - 1).setSeenByDoc(false);

		/** Prompt the message that a new visit record is added successfully. */
		// Build a new AlertDialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// Set alert title
		alertDialogBuilder.setTitle("Add a new visit record");

		// Set dialog message
		alertDialogBuilder
				.setMessage(
						"You have successfully added a new visit record! You can view this visit record in previous visits.")
				.setCancelable(false)
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});

		// Create alert dialog specified
		AlertDialog alertDialog = alertDialogBuilder.create();

		// Show the dialog when the there a new visit record is successfully
		// being added
		alertDialog.show();
	}

	// the onClick method of the viewVisitButton
	public void viewVisit(View view) {
		// If the there is no previous visit record, the alert dialog would
		// prompt out.
		if (this.patient.getVisitData() == null) {
			/** Prompt an alert that the patient doesn't have visit record yet. */

			// Build a new AlertDialog
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// Set alert title
			alertDialogBuilder
					.setTitle("The patient doesn't have visit record yet!");

			// Set dialog message
			alertDialogBuilder
					.setMessage("Add visit record first.")
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

			// Show the dialog when the there is no previous visit record
			alertDialog.show();
		}
		// If there exists the previous visit record
		else {
			// Passes the Patient object to ViewVisitActivity and starts
			// ViewVisitActivity
			Intent view_intent = new Intent(this, ViewVisitActivity.class);
			view_intent.putExtra(EXTRA_MESSAGE_DISPLAY, this.healthCardNumber);
			startActivity(view_intent);
		}
	}

	// the onClick method of the addVitalSignsButton
	public void addVitalSigns(View view) {
		// If the there is no previous visit record, the alert dialog would
		// prompt out.
		if (this.patient.getVisitData() == null || this.patient.getVisitData().size() == 0) {
			// Build a new AlertDialog
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// Set alert title
			alertDialogBuilder
					.setTitle("The patient doesn't have visit record yet!");

			// Set dialog message
			alertDialogBuilder
					.setMessage("Add visit record first.")
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

			// Show the dialog when the there is no previous visit record
			alertDialog.show();
		}
		// If there exists the previous visit record
		else {
			// Passes the Patient object to AddVitalSignsActivity and starts
			// AddVitalSignsActivity
			Intent add_vs_intent = new Intent(this, AddVitalSignsActivity.class);
			add_vs_intent
					.putExtra(EXTRA_MESSAGE_DISPLAY, this.healthCardNumber);
			startActivity(add_vs_intent);

		}
	}

	public void searchAgain(View v) {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
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
