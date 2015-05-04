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

public class DisplayPhysicianActivity extends Activity {
	/** The activity to display patient infomation for physicians. */

	/** The constants representing the names of the objects passing to next activity.*/
	public final static String EXTRA_MESSAGE_DISPLAY_PHYSICIAN = "com.example.triage.VISIT_HEALTHCARD";

	private String healthCardNumber;
	
	private Patient patient;
	
	private ListView listView;
	
	private DataManager manager;
	
	private ArrayAdapter<String> listAdapter ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_physician);
		
	 
		Intent intent = getIntent();
		
		this.healthCardNumber = (String) intent.getSerializableExtra("com.example.triage.HEALTHCARD");
		
		// Get the DataManager object.
		this.manager = DataManager.getInstance(this);
		//Uses key SearchActivity.EXTRA_MESSAGE_Search to get the health card number.
		//this.healthCardNumber = (String) intent.getSerializableExtra(SearchActivity.EXTRA_MESSAGE_SEARCH);
		
		//Get the patient object by searching the health card number.
		try {
			this.patient = manager.searchPatientByHealthNum(healthCardNumber);
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
		}
		 
		//Create a new ArrayList<String> and put all the values of the personal information of the Patient into it
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
	    
	        
	    //Instantiate a new ListView and set a new ArrayAdapter    
	    listView = (ListView) findViewById( R.id.visitListView );
	    listAdapter = new ArrayAdapter<String>(this, R.layout.info, data); 
	    listView.setAdapter( listAdapter );    	 
	}
	
	    //the onClick method of the viewVisitButton
		public void viewVisit(View view) {
			// If the there is no previous visit record, the alert dialog would prompt out.
			if (this.patient.getVisitData() == null) {
				/** Prompt an alert that the patient doesn't have visit record yet. */
				
				// Build a new AlertDialog
	    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	     
	    		// Set alert title
	    		alertDialogBuilder.setTitle("View Previous Visits");
	     
	    		// Set dialog message
	    		alertDialogBuilder
	    			.setMessage("The patient doesn't have visit record yet!")
	    			.setCancelable(false)
	    			.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog,int id) {
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
			//If there exists the previous visit record
			else {
			//Passes the Patient object to ViewVisitActivity and starts ViewVisitActivity
			Intent view_intent = new Intent(this, ViewVisitActivity.class);
			view_intent.putExtra(EXTRA_MESSAGE_DISPLAY_PHYSICIAN, this.healthCardNumber);
			startActivity(view_intent);	
			}
		}
	
	

	//the onClick method of the addPrescriptionButton
	public void addPrescription(View view){
		// If the there is no previous visit record, the alert dialog would prompt out.
				if (this.patient.getVisitData() == null) {
					// Build a new AlertDialog
		    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		     
		    		// Set alert title
		    		alertDialogBuilder.setTitle("No visit record");
		     
		    		// Set dialog message
		    		alertDialogBuilder
		    			.setMessage("The patient doesn't have visit record yet!")
		    			.setCancelable(false)
		    			.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		    				public void onClick(DialogInterface dialog,int id) {
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
				//If there exists the previous visit record
				else {
			//Passes the Patient object to AddVitalSignsActivity and starts AddVitalSignsActivity
			Intent add_pres_intent = new Intent(this, AddPrescriptionActivity.class);
			add_pres_intent.putExtra(EXTRA_MESSAGE_DISPLAY_PHYSICIAN, this.healthCardNumber); 
			startActivity(add_pres_intent);}
			
		}
	
	public void searchAgain2(View v){
		Intent intent = new Intent(this,PhysicianSearchActivity.class);
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
        	Intent intent = new Intent (this,MainActivity.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}
	
}

	
	

