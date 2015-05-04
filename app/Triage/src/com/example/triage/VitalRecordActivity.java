package com.example.triage;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class VitalRecordActivity extends Activity {

	private String healthCardNumber;

	private DataManager manager;

	private Patient patient;

	private Integer arrivalTime;

	private TextView switchStatus;

	private Switch mySwitch;

	private Visit thisVisit;

	private ArrayAdapter<String> listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vital_record);

		// Gets the Intent passed from ViewVisitActivity
		Intent intent = getIntent();

		// Uses key ViewVisitActivity.EXTRA_MESSAGE_VISIT to get the health card
		// number.
		this.healthCardNumber = (String) intent
				.getSerializableExtra(ViewVisitActivity.EXTRA_MESSAGE_VISIT);

		// Uses key ViewVisitActivity.EXTRA_MESSAGE_VISIT_INDEX to get the
		// position index of the arrival time.
		this.arrivalTime = (Integer) intent
				.getSerializableExtra(ViewVisitActivity.EXTRA_MESSAGE_VISIT_INDEX);

		// Get the DataManager object.
		this.manager = DataManager.getInstance(this);

		// Get the patient object by searching the health card number.
		try {
			this.patient = manager.searchPatientByHealthNum(healthCardNumber);
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
		}

		// Get the current Visit object by referring to the index of the arrival
		// time.
		thisVisit = manager.getVisit(healthCardNumber).get(arrivalTime);

		// Instantiate the toggle button.
		switchStatus = (TextView) findViewById(R.id.switchStatus);
		mySwitch = (Switch) findViewById(R.id.switch1);

		// set the switch to OFF
		mySwitch.setChecked(false);

		// attach a listener to check for changes in state
		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!isChecked) {
					manager.setSeenByDoc(healthCardNumber, arrivalTime, false);
					switchStatus.setText(thisVisit.getTimeSeenByDoc());
				} else {
					manager.setSeenByDoc(healthCardNumber, arrivalTime, true);

					// Get the current time when the user switch the button
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
					String currentTime = year + "-" + month + "/" + day + " "
							+ hour + ":" + minute + ":" + second;

					manager.setTimeSeenByDoc(healthCardNumber, arrivalTime,
							currentTime);

					String result = "The patient has been seen by the doctor on \n"
							+ thisVisit.getTimeSeenByDoc();
					switchStatus.setText(result);
					mySwitch.setEnabled(false);

				}
			}
		});

		// check the current state before we display the screen
		if (!manager.checkSeenByDoc(healthCardNumber, arrivalTime)) {
			switchStatus.setText(thisVisit.getTimeSeenByDoc());
		} else {
			String result = "The patient has been seen by the doctor on \n"
					+ thisVisit.getTimeSeenByDoc();
			switchStatus.setText(result);

			mySwitch.setChecked(true);
			mySwitch.setEnabled(false);
		}

		// Instantiate a new ArrayList<String> and put the values of all the
		// vital signs data.
		ArrayList<VitalSigns> vsList = thisVisit.getVitalData();
		ArrayList<String> data = new ArrayList<String>();
		if (vsList.size() == 0) {
			data.add("There is no vital record yet!");
		} else {
			data.add("Vital Signs:");
			for (VitalSigns vs : vsList) {
				vs.setUrgency(patient.getAge(), vs.getTemperature(),
						vs.getSystolic(), vs.getDiastolic(), vs.getHeartRate());
				data.add(vs.toString() + "\n" + vs.getUrgency());
			}
		}


		ArrayList<Prescription> pres = thisVisit.getPrescription();
		if (pres == null) {
			data.add("There is no prescription yet!");
		} else {
			data.add("Prescriptions:");
			String result = "";
			for (Prescription p : pres) {
				result += p.toString();
			}

			data.add(result);
		}

		// Instantiate a new ListView and set a new ArrayAdapter
		ListView list = (ListView) findViewById(R.id.listView2);
		listAdapter = new ArrayAdapter<String>(this, R.layout.vital_data, data);
		list.setAdapter(listAdapter);

	}

	// the onClick method of the returnButton
	public void returntoViewVisit(View view) {
		// When the returnButton is clicked, the application will jump back to
		// the ViewVisitActivity
		VitalRecordActivity.this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vital_record, menu);
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
