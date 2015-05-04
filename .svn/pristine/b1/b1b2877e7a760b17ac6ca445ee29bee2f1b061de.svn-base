package com.example.triage;



import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddPrescriptionActivity extends Activity {
	/** The activity for physicians to add new prescriptions for a patient. */

	private EditText editMedication;

	private EditText editInstruction;

	private DataManager manager;

	private String healthCardNumber;

	private String medication;

	private String instruction;
	
	private LinearLayout container;

	private String arrivalTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_prescription);

		// Gets the Intent passed from DisplayPhysicianActivity
		Intent intent = getIntent();

		// Uses key DisplayPhysicianActivity.EXTRA_MESSAGE_DISPLAY_PHYSICIAN to get the health card
		// number.
		this.healthCardNumber = (String) intent
				.getSerializableExtra(DisplayPhysicianActivity.EXTRA_MESSAGE_DISPLAY_PHYSICIAN);


		this.manager = DataManager.getInstance(this);


		// Get the last visit record and the corresponding arrival time and the
		// Patient's health card number
		this.arrivalTime = manager.getLastVisit(healthCardNumber).getArrivalTime();

		// Initiate the EditTexts
		editMedication = (EditText) findViewById(R.id.medication);
		editInstruction = (EditText) findViewById(R.id.instruction);
		
		container = (LinearLayout)findViewById(R.id.container);
		
	}
	
	// the onClick method of the plusButton
	@SuppressLint("InflateParams")
	public void addEdittext(View view){
		if( editMedication.length() == 0 || editInstruction.length() == 0) {
	          Toast.makeText(view.getContext(), R.string.input_not_filled, Toast.LENGTH_SHORT).show();
	          return;
	      }
		this.medication = editMedication.getText().toString();
		this.instruction = editInstruction.getText().toString();
		manager.addPrescriptions(healthCardNumber, arrivalTime, medication, instruction);
		
		LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View addView = layoutInflater.inflate(R.layout.row, null);
		final TextView textOut = (TextView)addView.findViewById(R.id.textout);
		textOut.setText("Medication: " + medication+"\n"+"Instructions: "+ instruction);
		editMedication.setText("");
		editInstruction.setText("");
		
	    
	    container.addView(addView);
	    LayoutTransition transition = new LayoutTransition();
	    container.setLayoutTransition(transition);
	    
	   
	   }
	  
	 
	
	

	// the onClick method of the submitButton
	public void submitPres(View view) {
		if( editMedication.length() != 0 || editInstruction.length() != 0) {
	          Toast.makeText(view.getContext(), R.string.add_first, Toast.LENGTH_SHORT).show();
	          return;
	      }
		
		if(( editMedication.length() == 0 || editInstruction.length() == 0) &&
				manager.getLastVisit(healthCardNumber).getPrescription().size()==0 ) {
	          Toast.makeText(view.getContext(), R.string.input_not_filled, Toast.LENGTH_SHORT).show();
	          return;
	      }
		
		 /** Prompt the message that new prescriptions are added successfully. */
		// Build a new AlertDialog
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		 
				// Set alert title
				alertDialogBuilder.setTitle("Add new prescriptions");
		 
				// Set dialog message
				alertDialogBuilder
					.setMessage("You have successfully added new prescriptions!")
					.setCancelable(false)
					.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
								AddPrescriptionActivity.this.finish();
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
        	Intent intent = new Intent (this,MainActivity.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}
}
