package com.example.triage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	/**
	 * To welcome the user and login.
	 */

	/** The name of the file used to store persistant data. */
	private static final String patientRecordFile = "patient_records.txt";

	private EditText editUserName;
	private EditText editPassword;
	private String userName;
	private String password;
	private String position;
	private DataManager manager;
	private SharedPreferences prefs = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.manager = new DataManager(this);

		prefs = getSharedPreferences("com.example.triage", MODE_PRIVATE);

		if (prefs.getBoolean("firstrun", true)) {
			convertTextToDatabase();

			prefs.edit().putBoolean("firstrun", false).commit();
		}

		manager.loadDatabase();

		editUserName = (EditText) findViewById(R.id.user_name_box);
		editPassword = (EditText) findViewById(R.id.password_box);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void clear_1(View v) {
		editUserName.setText("");
	}

	public void clear_2(View v) {
		editPassword.setText("");
	}

	public void logIn(View v) {
		this.userName = editUserName.getText().toString();
		this.password = editPassword.getText().toString();

		try {
			User u = manager.searchUsers(userName, password);
			this.position = u.getPosition();

			/**
			 * Specifies and move to the next Activity Nurse will be directed to
			 * SearchActivity Physician will be directed to
			 * PhysicianSearchActivity
			 */
			if (position.equals("nurse")) {
				Intent intent = new Intent(this, SearchActivity.class);
				startActivity(intent);
			} else if (position.equals("physician")) {
				Intent intent2 = new Intent(this, PhysicianSearchActivity.class);
				startActivity(intent2);
			}
		} catch (UsersNotFoundException e) {
			/** An alert will be prompted if the user is not found. */
			// Build a new AlertDialog
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// Set alert title
			alertDialogBuilder.setTitle("User Not Found");

			// Set dialog message
			alertDialogBuilder
					.setMessage(
							"Your input is invalid or the user doesn't exist.")
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

	private void convertTextToDatabase() {
		ArrayList<Patient> lst = new ArrayList<Patient>();

		try {
			// Read text file
			Scanner sc = new Scanner(new InputStreamReader(getAssets().open(
					patientRecordFile)));
			String[] record;
			while (sc.hasNext()) {
				record = sc.nextLine().split(",");
				String healthCardNumber = record[0];
				String[] name = record[1].split(" ");
				String[] dob = record[2].split("-");

				lst.add(new Patient(name, Integer.parseInt(dob[2]), Integer
						.parseInt(dob[1]), Integer.parseInt(dob[0]),
						healthCardNumber));
			}

			manager.convertTxtToDatabase(lst);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}
