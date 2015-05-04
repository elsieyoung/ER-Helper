package com.example.triage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity {
	/**
	 * This activity is for nurses to search for specific patient by health card
	 * number (then directed to DisplayActivity) and add new patient (then
	 * directed to the AddPatientActivity). There is also a list of patients
	 * displayed who have not yet been seen by a doctor categorized and ordered
	 * by decreasing urgency according to hospital policy.
	 */

	/**
	 * The constants representing the names of the objects passing to next
	 * activity.
	 */
	public final static String EXTRA_MESSAGE_SEARCH = "com.example.triage.HEALTHCARD";

	private EditText editHealthCard;
	private DataManager manager;
	private ListView listView;
	private ArrayAdapter<String> listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		this.manager = DataManager.getInstance(this);
		manager.loadDatabase();
		// try {
		// manager.loadPatient();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (ClassNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		ArrayList<String> data = new ArrayList<String>();

		LinkedHashMap<String, LinkedList<Patient>> sortPatient = sortPatientByUrgency();

		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		for (String key : sortPatient.keySet()) {
			for (Patient p : sortPatient.get(key)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(p.getDob());
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int month = calendar.get(Calendar.MONTH) + 1;
				int year = calendar.get(Calendar.YEAR);
				String pDOB = day + "/" + month + "/" + year;
				String pString = "Name: " + p.getName()[1] + ", "
						+ p.getName()[0] + "\n" + "Date of Birth: " + pDOB
						+ "\n" + "Health Card #: " + p.getHealthcard();
				if (map.containsKey(key)) {
					map.get(key).add(pString);
				} else {
					ArrayList<String> l = new ArrayList<String>();
					l.add(pString);
					map.put(key, l);
				}
			}
		}
		data.add("Urgent Patients:");
		data.add("Less Urgent Patients:");
		data.add("Non Urgent Patients:");
		if (map.get("Urgent") != null) {
			for (String p : map.get("Urgent")) {
				int index_urgent = data.indexOf("Urgent Patients:");
				data.add(index_urgent + 1, p);
			}
		}else{
			int index_urgent = data.indexOf("Urgent Patients:");
			data.add(index_urgent + 1,"None");
		}
		if (map.get("Less Urgent") != null) {
			for (String p : map.get("Less Urgent")) {
				int index_less_urgent = data.indexOf("Less Urgent Patients:");
				data.add(index_less_urgent + 1, p);
			}
		}else{
			int index_less_urgent = data.indexOf("Less Urgent Patients:");
			data.add(index_less_urgent + 1, "None");
		}

		if (map.get("Non Urgent") != null) {
			for (String p : map.get("Non Urgent")) {
				int index_non_urgent = data.indexOf("Non Urgent Patients:");
				data.add(index_non_urgent + 1, p);
			}
		}else{
			int index_non_urgent = data.indexOf("Non Urgent Patients:");
			data.add(index_non_urgent + 1,"None");
		}

		// Instantiate a new ListView and set a new ArrayAdapter
		listView = (ListView) findViewById(R.id.sortListView);
		listAdapter = new ArrayAdapter<String>(this, R.layout.sort, data);
		listView.setAdapter(listAdapter);
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
		Intent intent = new Intent(this, DisplayActivity.class);

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

	public LinkedHashMap<String, LinkedList<Patient>> sortPatientByUrgency() {
		ArrayList<Patient> unvisited = manager.getUnvisited();
		LinkedHashMap<String, LinkedList<Patient>> sortedPatient = new LinkedHashMap<String, LinkedList<Patient>>();
		sortedPatient.put(new String("Urgent"), new LinkedList<Patient>());
		sortedPatient.put(new String("Less Urgent"), new LinkedList<Patient>());
		sortedPatient.put(new String("Non Urgent"), new LinkedList<Patient>());

		for (Patient patient : unvisited) {
			String healthCardNumber = patient.getHealthcard();

			String urgency = manager
					.getLastVisit(healthCardNumber)
					.getVitalData()
					.get(manager.getLastVisit(healthCardNumber).getVitalData()
							.size() - 1).calculateUrgency(patient.getAge());
			if (urgency == "Urgent") {
				sortedPatient.get("Urgent").addFirst(patient);
			}
			if (urgency == "Less Urgent") {
				sortedPatient.get("Less Urgent").add(patient);
			}
			if (urgency == "Non Urgent") {
				sortedPatient.get("Non Urgent").add(patient);
			}
		}
		return sortedPatient;
	}

	public void AddPatient(View v) {
		// Specifies the next Activity to move to: AddPatientActivity
		Intent intent = new Intent(this, AddPatientActivity.class);

		// Starts SearchActivity
		startActivity(intent);
	}
}
