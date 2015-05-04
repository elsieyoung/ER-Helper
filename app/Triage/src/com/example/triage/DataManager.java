package com.example.triage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class DataManager {
	/**
	 * This class implements the User interface. It can load data, save data and
	 * and manipulate all the data for methods that can be done as a user of
	 * this app.
	 */

	/** List of patient */
	private List<Patient> patientList;
	private ArrayList<User> userList;
	private DatabaseAdapter dbAdapter;
	private static DataManager instance;
	public transient Activity presentactivity;

	/**
	 * Constructs the data manager and loads all the patient's data from file
	 * 
	 * @param Activity
	 *            Current activity
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public DataManager(Activity activity) {
		presentactivity = activity;
		this.patientList = new ArrayList<Patient>();

		try {
			dbAdapter = new DatabaseAdapter(activity);
			dbAdapter.open();
			this.loadUsersText();
		} catch (IOException e) {
			System.out.println("Database load error: Missing files.");
		}
	}

	public static synchronized DataManager getInstance(Activity activity) {
		if (instance == null) {

			instance = new DataManager(activity);
		}

		return instance;
	}

	/**
	 * Instantiate patient list and insert the patient list to database
	 * 
	 * @param patientList
	 *            List of patients
	 */
	public void convertTxtToDatabase(ArrayList<Patient> patientList) {
		this.patientList = patientList;
		dbAdapter.convertPatientRecord(patientList);
	}

	/**
	 * Add patient with patient information
	 * 
	 * @param name
	 *            Patient's name
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @param year
	 *            Patient's year of birth
	 * @param month
	 *            Patient's month of birth
	 * @param day
	 *            Patient's day of birth
	 */
	public void addPatient(String[] name, String healthCardNumber, int year,
			int month, int day) {
		Patient p = new Patient(name, day, month, year, healthCardNumber);

		dbAdapter.addPatient(p.getFullName(), p.getHealthcard(), p.getSQLDate());
		
		int patientId = dbAdapter.getLastPatientId();
		
		p.setPatientId(patientId);
		
		patientList.add(p);
	}

	/**
	 * Adds visit record to a specific patient, identified by health card number
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @param arrivalTime
	 *            Patient's arrival time
	 * @throws PatientNotFoundException
	 */
	public void addVisit(String healthCardNumber, String arrivalTime) {
		
		int patientId = dbAdapter.getPatientIdByHealthCardNumber(healthCardNumber);
		
		dbAdapter.addVisit(patientId, arrivalTime, null, false);
		
		int visitId = dbAdapter.getLastVisitId();
		
		for (Patient p : patientList) {
			if (p.getHealthcard().equals(healthCardNumber)) {
				patientList.get(patientList.indexOf(p)).addVisit(patientId, visitId, arrivalTime);
			}
		}

	}

	/**
	 * Adds vital sign to a specific visit record, identified by the time and
	 * health card number.
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @param arrivalTime
	 *            Patient's arrival time
	 * @param systolic
	 *            Patient's systolic
	 * @param diastolic
	 *            Patient's diastolic
	 * @param temperature
	 *            Patient's temperature
	 * @param heartRate
	 *            Patient's heart rate
	 * @param recordTime
	 *            Time when vital sign was recorded
	 * @throws UsersNotFoundException
	 */
	public void addVital(String healthCardNumber, String arrivalTime,
			int systolic, int diastolic, float temperature, int heartRate,
			String recordTime) {
		Integer urgency = null;
		for (Patient p : patientList) {
			if (p.getHealthcard().equals(healthCardNumber)) {
				for (Visit v : patientList.get(patientList.indexOf(p))
						.getVisitData()) {
					if (v.getArrivalTime().equals(arrivalTime)) {
						VitalSigns vs = new VitalSigns(systolic, diastolic,
								temperature, heartRate, recordTime, p.getAge());

						patientList
								.get(patientList.indexOf(p))
								.getVisitData()
								.get(patientList.get(patientList.indexOf(p))
										.getVisitData().indexOf(v))
								.getVitalData().add(vs);
						urgency = vs.getUrgencyPoint();

						break;
					}
				}
			}
		}

		int visitId = dbAdapter.getVisitIdByHealthCardNumber(healthCardNumber,
				arrivalTime);

		if (visitId == -1) {

		} else {
			dbAdapter.addVitalSign(visitId, systolic, diastolic, temperature,
					heartRate, urgency, recordTime);
		}
	}

	/**
	 * Add prescription to a certain visit record, identified by
	 * healthCardNumber and arrivalTime
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @param arrivalTime
	 *            Patient's arrival time
	 * @param name
	 *            Medication name
	 * @param instruction
	 *            Medication instruction
	 */
	public void addPrescriptions(String healthCardNumber, String arrivalTime,
			String name, String instruction) {
		for (Patient p : patientList) {
			if (p.getHealthcard().equals(healthCardNumber)) {
				for (Visit v : patientList.get(patientList.indexOf(p))
						.getVisitData()) {
					if (v.getArrivalTime().equals(arrivalTime)) {

						patientList
								.get(patientList.indexOf(p))
								.getVisitData()
								.get(patientList.get(patientList.indexOf(p))
										.getVisitData().indexOf(v))
								.setPrescription(name, instruction);

						break;
					}
				}
			}
		}

		int visitId = dbAdapter.getVisitIdByHealthCardNumber(healthCardNumber,
				arrivalTime);

		dbAdapter.addPrescription(visitId, name, instruction);

	}

	/**
	 * Returns the last visit record of a certain patient identified by health
	 * card number
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @return
	 */
	public Visit getLastVisit(String healthCardNumber) {
		Visit last_visit_record = new Visit();

		for (Patient p : patientList) {
			if (p.getHealthcard().equals(healthCardNumber)) {
				last_visit_record = p.getVisitData().get(
						p.getVisitData().size() - 1);
			}
		}
		return last_visit_record;
	}

	/**
	 * Returns the visit record of a certain patient identified by health card
	 * number
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @return
	 */
	public ArrayList<Visit> getVisit(String healthCardNumber) {
		ArrayList<Visit> visit_record = new ArrayList<Visit>();

		for (Patient p : patientList) {
			if (p.getHealthcard().equals(healthCardNumber)) {
				visit_record = p.getVisitData();
			}
		}
		return visit_record;
	}

	/**
	 * Reads the user.txt file and instantiate to the user list
	 * 
	 * @throws IOException
	 */
	public void loadUsersText() throws IOException {

		ArrayList<User> userLst = new ArrayList<User>();

		// Read text file
		InputStream file = presentactivity.getAssets().open("passwords.txt");
		String[] record;
		BufferedReader br = new BufferedReader(new InputStreamReader(file));
		String line;

		while ((line = br.readLine()) != null) {
			record = line.split(",");
			String username = record[0];
			String psw = record[1];
			String position = record[2];
			userLst.add(new User(username, psw, position));
			this.userList = userLst;
		}
		br.close();
	}

	/**
	 * Load all information of patient from database
	 */
	public void loadDatabase() {
		this.patientList = dbAdapter.loadFromDb();
	}

	/**
	 * Sets whether the patient has met a doctor or not
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @param arrivalTime
	 *            Patient's arrival time
	 * @param bool
	 *            Patient status on meeting the doctor
	 */
	public void setSeenByDoc(String healthCardNumber, Integer arrivalTime,
			boolean bool) {
		String SQLtime = null;

		for (Patient p : patientList) {
			if (p.getHealthcard().equals(healthCardNumber)) {

				SQLtime = patientList.get(patientList.indexOf(p))
						.getVisitData().get(arrivalTime).getArrivalTime();

				patientList.get(patientList.indexOf(p)).getVisitData()
						.get(arrivalTime).setSeenByDoc(bool);

				break;
			}
		}

		if (SQLtime != null) {
			int visitId = dbAdapter.getVisitIdByHealthCardNumber(
					healthCardNumber, SQLtime);

			dbAdapter.updateSeenByDoc(visitId, bool);
		}
	}

	/**
	 * Sets the time seen by doctor of a certain patient on a certain visit
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @param arrivalTime
	 *            Patient's arrival time
	 * @param time
	 *            Time the patient met the doctor
	 */
	public void setTimeSeenByDoc(String healthCardNumber, Integer arrivalTime,
			String time) {
		String SQLTime = null;

		for (Patient p : patientList) {
			if (p.getHealthcard().equals(healthCardNumber)) {

				SQLTime = patientList.get(patientList.indexOf(p))
						.getVisitData().get(arrivalTime).getArrivalTime();
				patientList.get(patientList.indexOf(p)).getVisitData()
						.get(arrivalTime).setTimeSeenByDoc(time);

				break;
			}
		}

		if (SQLTime != null) {
			int visitId = dbAdapter.getVisitIdByHealthCardNumber(
					healthCardNumber, SQLTime);

			dbAdapter.updateTimeSeenByDoc(visitId, time);
		}

	}

	/**
	 * Return whether a patient on a certain visit has seen a doctor
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @param arrivalTime
	 *            Patient's arrival time
	 * @return
	 */
	public boolean checkSeenByDoc(String healthCardNumber, Integer arrivalTime) {
		boolean result = false;
		for (Patient p : patientList) {
			if (p.getHealthcard().equals(healthCardNumber)) {

				result = patientList.get(patientList.indexOf(p)).getVisitData()
						.get(arrivalTime).isSeenByDoc();

			}
		}
		return result;

		// No database required since we manage also in memory.
	}

	/**
	 * Searches patient by health card number
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @return Patient
	 * @throws PatientNotFoundException
	 */
	public Patient searchPatientByHealthNum(String healthCardNumber)
			throws PatientNotFoundException {

		Patient matchedPatient = null;

		for (Patient p : patientList) {
			if (p.getHealthcard().equals(healthCardNumber)) {
				matchedPatient = p;
			}

		}

		if (matchedPatient == null) {
			throw new PatientNotFoundException("No Patient Found");
		}

		return matchedPatient;

	}

	/**
	 * Search user by id and password
	 * 
	 * @param id
	 *            User's id
	 * @param password
	 *            User's password
	 * @return
	 * @throws UsersNotFoundException
	 */
	public User searchUsers(String id, String password)
			throws UsersNotFoundException {

		User matchedUser = null;

		for (User u : userList) {
			if (u.getId().equals(id) && u.getPassword().equals(password)) {
				matchedUser = u;
			}

		}

		if (matchedUser == null) {
			throw new UsersNotFoundException("No User Found");
		}

		return matchedUser;

	}

	/**
	 * Return list of patients who doesn't have a visit record
	 * 
	 * @return
	 */
	public ArrayList<Patient> getUnvisited() {
		ArrayList<Patient> unvisited = new ArrayList<Patient>();
		for (Patient p : patientList) {
			if (p.getVisitData().size() != 0
					&& p.getVisitData().get(p.getVisitData().size() - 1)
							.getVitalData().size() != 0) {
				int index = p.getVisitData().size();
				if (!p.getVisitData().get(index - 1).isSeenByDoc()) {
					unvisited.add(p);
				}
			}
		}

		return unvisited;
	}

	/**
	 * Return the list of all patient
	 * 
	 * @return
	 */
	public List<Patient> getPatientList() {
		return patientList;
	}

	/**
	 * Sets the list of patient
	 * 
	 * @param patientList
	 */
	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
	}

	/**
	 * String representation of data manager
	 */
	@Override
	public String toString() {
		return "DataManager [patient=" + patientList + "]";
	}
}
