package com.example.triage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAdapter {

	private DatabaseHelper dbAdapter;
	private SQLiteDatabase mDb;

	private final Context context;

	/** Database Tables */
	private static final String TABLE_PATIENT = "patient";
	private static final String TABLE_VISIT = "visit";
	private static final String TABLE_VITAL_SIGN = "vital_sign";
	private static final String TABLE_PRESCIPTION = "prescription";

	/** Database Adapter Constructor */
	public DatabaseAdapter(Context context) {
		this.context = context;
	}

	/** Create Database */
	public DatabaseAdapter open() throws SQLException {
		dbAdapter = new DatabaseHelper(context);
		mDb = dbAdapter.getWritableDatabase();

		return this;
	}

	/** Close Database */
	public void close() {
		dbAdapter.close();
	}

	/**
	 * Add patient into database
	 * 
	 * @param name
	 *            Patient's name
	 * @param healthCardNumber
	 *            Patient's Health Card Number
	 * @param dob
	 *            Patient's Date of Birth
	 */
	public void addPatient(String name, String healthCardNumber, String dob) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("dateOfBirth", dob);
		values.put("healthCardNumber", healthCardNumber);

		mDb.insert(TABLE_PATIENT, null, values);
	}

	/**
	 * Add visit to a patient into database
	 * 
	 * @param patientId
	 *            Patient's unqiue ID used for database
	 * @param arrivalTime
	 *            Patient's arrival time
	 * @param timeSeenByDoc
	 *            Patient's time seen by a doctor
	 * @param seenByDoc
	 *            Boolean that checks whether patient has met a doctor or not
	 */
	public void addVisit(int patientId, String arrivalTime,
			String timeSeenByDoc, boolean seenByDoc) {
		ContentValues values = new ContentValues();
		values.put("patientId", patientId);
		values.put("arrivalTime", arrivalTime);
		// values.put("timeSeenByDoc", timeSeenByDoc);
		// values.put("seenByDoc", seenByDoc);

		mDb.insert(TABLE_VISIT, null, values);
	}

	/**
	 * Add vital sign to a certain visit into database
	 * 
	 * @param visitId
	 *            Visit's Unique ID used for database
	 * @param systolic
	 *            Patient's systolic during a certain visit
	 * @param diastolic
	 *            Patient's diastolic during a certain visit
	 * @param temperature
	 *            Patient's temperature during a certain visit
	 * @param heartRate
	 *            Patient's heart rate during a certain visit
	 * @param urgency
	 *            Level of urgency of a patient during a certain visit
	 * @param recordTime
	 *            Time when the vital was recorded
	 */
	public void addVitalSign(int visitId, int systolic, int diastolic,
			double temperature, int heartRate, int urgency, String recordTime) {
		ContentValues values = new ContentValues();
		values.put("visitId", visitId);
		values.put("systolic", systolic);
		values.put("diastolic", diastolic);
		values.put("temperature", temperature);
		values.put("heartRate", heartRate);
		values.put("urgency", urgency);
		values.put("recordTime", recordTime);

		mDb.insert(TABLE_VITAL_SIGN, null, values);
	}

	/**
	 * Add prescription to a certain visit into database
	 * 
	 * @param visitId
	 *            Visit's Unqiue ID used for database
	 * @param medication
	 *            The name of medication patient's recieve during a certain
	 *            visit
	 * @param instructions
	 *            Instructions on how to use medication
	 */
	public void addPrescription(int visitId, String medication,
			String instructions) {
		ContentValues values = new ContentValues();
		values.put("visitId", visitId);
		values.put("medication", medication);
		values.put("instructions", instructions);

		mDb.insert(TABLE_PRESCIPTION, null, values);
	}

	/**
	 * Update the time seen by doctor on a certain visit
	 * 
	 * @param visitId
	 * @param time
	 */
	public void updateTimeSeenByDoc(int visitId, String time) {
		ContentValues values = new ContentValues();
		values.put("timeSeenByDoc", time);

		mDb.update(TABLE_VISIT, values, "visitId = " + visitId, null);
	}

	public void updateSeenByDoc(int visitId, boolean bool) {
		int SQLbool = 0;

		if (bool == true) {
			SQLbool = 1;
		}

		ContentValues values = new ContentValues();
		values.put("seenByDoc", SQLbool);

		mDb.update(TABLE_VISIT, values, "visitId = " + visitId, null);
	}

	/**
	 * Search a patient by health card number
	 * 
	 * @param healthCardNumber
	 * @return Mathcing Patient object
	 * @throws PatientNotFoundException
	 */
	public Patient searchPatient(String healthCardNumber)
			throws PatientNotFoundException {
		Patient p = null;

		try {
			p = new Patient();

			String selectPatientQry = "SELECT * FROM patient WHERE healthCardNumber = "
					+ healthCardNumber + ";";
			Cursor patientResult = mDb.rawQuery(selectPatientQry, null);

			if (patientResult.getCount() == 1) {
				patientResult.moveToFirst();
				ArrayList<Visit> vList = new ArrayList<Visit>();

				p.setPatientId(patientResult.getInt(patientResult
						.getColumnIndex("patientId")));
				p.setName(patientResult.getString(
						patientResult.getColumnIndex("name")).split(" "));
				p.setHealthcard(patientResult.getString(patientResult
						.getColumnIndex("healthCardNumber")));
				p.setDob(convertDateFormat(patientResult
						.getString(patientResult.getColumnIndex("dateOfBirth"))));
				p.setAge(convertDateFormat(patientResult
						.getString(patientResult.getColumnIndex("dateOfBirth"))));

				String selectVisitQry = "SELECT * FROM visit WHERE patientId = "
						+ p.getPatientId() + ";";
				Cursor visitResult = mDb.rawQuery(selectVisitQry, null);

				visitResult.moveToFirst();

				while (!visitResult.isAfterLast()) {
					ArrayList<Prescription> presList = new ArrayList<Prescription>();
					ArrayList<VitalSigns> vsList = new ArrayList<VitalSigns>();

					Visit v = convertCursorToVisit(visitResult);

					String selectVitalQry = "SELECT * FROM vital_sign WHERE visitId = "
							+ v.getVisitId() + ";";

					Cursor vitalResult = mDb.rawQuery(selectVitalQry, null);

					vitalResult.moveToFirst();

					while (!vitalResult.isAfterLast()) {
						VitalSigns vs = convertCursorToVital(visitResult,
								p.getAge());

						vsList.add(vs);
						vitalResult.moveToNext();
					}

					vitalResult.close();

					String selectPrescriptionQry = "SELECT * FROM prescription WHERE visitId = "
							+ v.getVisitId() + ";";

					Cursor presResult = mDb.rawQuery(selectPrescriptionQry,
							null);

					presResult.moveToFirst();

					while (!presResult.isAfterLast()) {
						Prescription pres = convertCursorToPrescription(presResult);

						presList.add(pres);
						presResult.moveToNext();
					}

					presResult.close();

					v.setPrescription(presList);
					v.setVitalData(vsList);
					vList.add(v);

					visitResult.moveToNext();
				}

				visitResult.close();

				p.setVisitData(vList);

			} else {
				throw new PatientNotFoundException("NOT FOUND");
			}
		} catch (SQLException e) {
			Log.d(" ERROTRIAGER :: SQL", e.toString());
		}

		return p;
	}

	/**
	 * Load all patient data from sql database
	 * 
	 * @return A list of patient with information about each patient
	 */
	public ArrayList<Patient> loadFromDb() {
		ArrayList<Patient> patientList = null;

		try {
			patientList = new ArrayList<Patient>();

			String selectAllPatientQry = "SELECT * FROM " + TABLE_PATIENT + ";";
			Cursor patientResult = mDb.rawQuery(selectAllPatientQry, null);

			patientResult.moveToFirst();

			while (!patientResult.isAfterLast()) {
				ArrayList<Visit> vList = new ArrayList<Visit>();
				Patient p = convertCursorToPatient(patientResult);

				String selectVisitQry = "SELECT * FROM " + TABLE_VISIT
						+ " WHERE patientId = " + p.getPatientId() + ";";
				Cursor visitResult = mDb.rawQuery(selectVisitQry, null);

				visitResult.moveToFirst();

				while (!visitResult.isAfterLast()) {
					ArrayList<VitalSigns> vsList = new ArrayList<VitalSigns>();
					ArrayList<Prescription> presList = new ArrayList<Prescription>();

					Visit v = convertCursorToVisit(visitResult);

					String selectVitalQry = "SELECT * FROM " + TABLE_VITAL_SIGN
							+ " WHERE visitId = " + v.getVisitId() + ";";
					Cursor vitalResult = mDb.rawQuery(selectVitalQry, null);

					vitalResult.moveToFirst();

					while (!vitalResult.isAfterLast()) {
						VitalSigns vs = convertCursorToVital(vitalResult,
								p.getAge());

						vsList.add(vs);
						vitalResult.moveToNext();
					}

					vitalResult.close();

					String selectPrescriptionQry = "SELECT * FROM "
							+ TABLE_PRESCIPTION + " WHERE visitId = "
							+ v.getVisitId() + ";";
					Cursor prescriptionResult = mDb.rawQuery(
							selectPrescriptionQry, null);

					prescriptionResult.moveToFirst();

					while (!prescriptionResult.isAfterLast()) {
						Prescription pres = convertCursorToPrescription(prescriptionResult);

						presList.add(pres);
						prescriptionResult.moveToNext();
					}

					prescriptionResult.close();

					v.setPrescription(presList);
					v.setVitalData(vsList);

					vList.add(v);
					visitResult.moveToNext();
				}

				p.setVisitData(vList);

				visitResult.close();
				patientList.add(p);
				patientResult.moveToNext();
			}

			patientResult.close();

		} catch (SQLException e) {
			Log.d("TRIAGE ERROR :: SQL", e.toString());
		}

		return patientList;
	}

	/**
	 * Read and convert "patient_record.txt" to database in patient table
	 * 
	 * @param patientList
	 *            List of patient with basic information
	 */
	public void convertPatientRecord(ArrayList<Patient> patientList) {
		try {
			for (Patient p : patientList) {
				ContentValues values = new ContentValues();
				values.put("name", p.getFullName());
				values.put("dateOfBirth", p.getSQLDate());
				values.put("healthCardNumber", p.getHealthcard());

				mDb.insert(TABLE_PATIENT, null, values);
			}
		} catch (SQLException e) {
			Log.d("ERROTRIAGER :: SQL", e.toString());
		}
	}

	/**
	 * Returns the patient ID identified by health card number
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @return
	 */
	public int getPatientIdByHealthCardNumber(String healthCardNumber) {
		int patientId = -1;

		String selectPatientQry = "SELECT * FROM patient WHERE healthCardNumber = "
				+ healthCardNumber + ";";
		Cursor result = mDb.rawQuery(selectPatientQry, null);

		if (result.getCount() == 1) {
			result.moveToFirst();

			patientId = result.getInt(result.getColumnIndex("patientId"));
		}

		result.close();

		return patientId;
	}

	/**
	 * Returns the visit ID identified by health card number and arrival time
	 * 
	 * @param healthCardNumber
	 *            Patient's health card number
	 * @param arrivalTime
	 *            Patient's arrival time on a certain visit
	 * @return
	 */
	public int getVisitIdByHealthCardNumber(String healthCardNumber,
			String arrivalTime) {
		int visitId = -1;

		String selectPatientQry = "SELECT * FROM patient WHERE healthCardNumber = "
				+ healthCardNumber + ";";
		Cursor result = mDb.rawQuery(selectPatientQry, null);

		if (result.getCount() == 1) {
			result.moveToFirst();

			int patientId = result.getInt(result.getColumnIndex("patientId"));

			String selectVisitQry = "SELECT * FROM visit WHERE patientId = "
					+ patientId + " AND arrivalTime = \'" + arrivalTime + "\';";
			Cursor visitResult = mDb.rawQuery(selectVisitQry, null);

			if (result.getCount() == 1) {
				visitResult.moveToFirst();

				visitId = visitResult.getInt(visitResult
						.getColumnIndex("visitId"));
			}
			result.close();
			visitResult.close();
		}

		return visitId;
	}

	/**
	 * Returns the last patient ID found in database
	 * 
	 * @return lastPatientId The last patientId found in database
	 */
	public int getLastPatientId() {
		int lastPatientId = -1;

		String selectQry = "SELECT last_insert_rowid() AS lastId FROM patient";

		Cursor result = mDb.rawQuery(selectQry, null);

		if (result.getCount() == 1) {
			result.moveToFirst();

			lastPatientId = result.getInt(result.getColumnIndex("lastId"));
		}

		return lastPatientId;
	}

	/**
	 * Returns the last visit ID found in database
	 * 
	 * @return lastVisitId The last visitId found in database
	 */
	public int getLastVisitId() {
		int lastVisitId = -1;

		String selectQry = "SELECT last_insert_rowid() AS lastId FROM visit";

		Cursor result = mDb.rawQuery(selectQry, null);

		if (result.getCount() == 1) {
			result.moveToFirst();

			lastVisitId = result.getInt(result.getColumnIndex("lastId"));
		}

		return lastVisitId;
	}

	/**
	 * Convert sql format result into Patient object
	 * 
	 * @param c
	 *            Result of patient from database
	 * @return Patient object
	 */
	private Patient convertCursorToPatient(Cursor c) {
		Patient p = new Patient();

		p.setPatientId(c.getInt(c.getColumnIndex("patientId")));
		p.setName(c.getString(c.getColumnIndex("name")).split(" "));
		p.setHealthcard(String.valueOf(c.getInt(c
				.getColumnIndex("healthCardNumber"))));
		p.setDob(convertDateFormat(c.getString(c.getColumnIndex("dateOfBirth"))));
		p.setAge(convertDateFormat(c.getString(c.getColumnIndex("dateOfBirth"))));

		return p;
	}

	/**
	 * Convert sql format result into Visit object
	 * 
	 * @param c
	 *            Result of visit from database
	 * @return Visit object
	 */
	private Visit convertCursorToVisit(Cursor c) {
		Visit v = new Visit();

		int sqlSeenByDoc = c.getInt(c.getColumnIndex("seenByDoc"));
		boolean seenByDoc = false;

		// 0 : false
		// 1 : true
		if (sqlSeenByDoc == 1) {
			seenByDoc = true;
		}

		v.setVisitId(c.getInt(c.getColumnIndex("visitId")));
		v.setPatientId(c.getInt(c.getColumnIndex("patientId")));
		v.setArrivalTime(c.getString(c.getColumnIndex("arrivalTime")));
		v.setTimeSeenByDoc(c.getString(c.getColumnIndex("timeSeenByDoc")));
		v.setSeenByDoc(seenByDoc);

		return v;
	}

	/**
	 * Convert sql format result into VitalSigns object
	 * 
	 * @param c
	 *            Result of vital signs from database
	 * @return VitalSigns object
	 */
	private VitalSigns convertCursorToVital(Cursor c, int age) {
		int visitId = c.getInt(c.getColumnIndex("visitId"));
		int diastolic = c.getInt(c.getColumnIndex("diastolic"));
		int systolic = c.getInt(c.getColumnIndex("systolic"));
		int heartRate = c.getInt(c.getColumnIndex("heartRate"));
		float temperature = c.getFloat(c.getColumnIndex("temperature"));
		String recordTime = c.getString(c.getColumnIndex("recordTime"));

		VitalSigns vs = new VitalSigns(systolic, diastolic, temperature,
				heartRate, recordTime, age);
		vs.setVisitId(visitId);

		return vs;
	}

	/**
	 * Convert sql format result into Prescription object
	 * 
	 * @param c
	 *            Result of prescription from database
	 * @return Prescription object
	 */
	private Prescription convertCursorToPrescription(Cursor c) {
		Prescription pres = new Prescription();

		pres.setVisitId(c.getInt(c.getColumnIndex("visitId")));
		pres.setMedication(c.getString(c.getColumnIndex("medication")));
		pres.setInstructions(c.getString(c.getColumnIndex("instructions")));

		return pres;
	}

	/**
	 * Convert SQL date format into Date object
	 * 
	 * @param sqlDate
	 *            SQL Date Format
	 * @return Date class object
	 */
	@SuppressLint("SimpleDateFormat")
	private Date convertDateFormat(String sqlDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;
		try {
			date = formatter.parse(sqlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

}
