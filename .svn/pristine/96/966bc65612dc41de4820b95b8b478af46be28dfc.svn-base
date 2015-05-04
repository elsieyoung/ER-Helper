package com.example.triage;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewVisitActivity extends Activity {
	/**
	 * This is the activity for the user to view a patient's previous visit
	 * records. When the user press the "Return To Patient Information" button,
	 * the application will lead the user back to the DisplayActivity.
	 */

	/**
	 * The constants representing the names of the objects passing to next
	 * activity.
	 */
	public final static String EXTRA_MESSAGE_VISIT = "com.example.triage.HEALTHCARD";
	public final static String EXTRA_MESSAGE_VISIT_INDEX = "com.example.triage.VISIT_INDEX";

	private ListView list;

	private ArrayAdapter<String> listAdapter;

	private String healthCardNumber;

	private DataManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_visit);

		// Gets the Intent passed from DisplayActivity and
		// DisplayPhysicianActivity
		Bundle bundle = getIntent().getExtras();
		// Uses key to get the health card number.
		this.healthCardNumber = (String) bundle
				.getSerializable("com.example.triage.VISIT_HEALTHCARD");

		// Get the DataManager object.
		this.manager = DataManager.getInstance(this);

		// Instantiate a new ArrayList<String> and put the values of all the
		// visit data from the Patient into it.
		ArrayList<Visit> visits = manager.getVisit(healthCardNumber);
		ArrayList<String> data = new ArrayList<String>();
		for (Visit visit : visits) {
			data.add("Visit On " + visit.getArrivalTime().toString());
		}

		// Instantiate a new ListView and set a new ArrayAdapter
		list = (ListView) findViewById(R.id.listView1);
		listAdapter = new ArrayAdapter<String>(this, R.layout.previous_visit,
				data);
		list.setAdapter(listAdapter);

		// Implement onClick method on the ListView
		getOnClickListitem();
	}

	// the onClick method of the ListView
	private void getOnClickListitem() {
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent view_visit_intent = new Intent(ViewVisitActivity.this,
						VitalRecordActivity.class);
				int time = position;

				// Pass the healthCardNumber and the index of arrivalTime to the
				// next activity
				Bundle extras = new Bundle();
				extras.putSerializable(EXTRA_MESSAGE_VISIT, healthCardNumber);
				extras.putSerializable(EXTRA_MESSAGE_VISIT_INDEX, time);

				view_visit_intent.putExtras(extras);

				startActivity(view_visit_intent);
			}
		});
	}

	// the onClick method of the returnButton
	public void returntoPatient(View view) {
		// When the returnButton is clicked, the application will jump back to
		// the DisplayActivity
		ViewVisitActivity.this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_visit, menu);
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
