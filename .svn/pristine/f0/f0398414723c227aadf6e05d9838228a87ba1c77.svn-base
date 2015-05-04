package com.example.triage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	/** Database Information */
	private static final String DATABASE_NAME = "Triage";
	private static final int DATABASE_VERSION = 1;

	/** Database Tables */
	private static final String TABLE_PATIENT = "patient";
	private static final String TABLE_VISIT = "visit";
	private static final String TABLE_VITAL_SIGN = "vital_sign";
	private static final String TABLE_PRESCIPTION = "prescription";

	/** Database Table CREATE Query */
	private static final String DATABASE_CREATE_PATIENT = "CREATE TABLE patient("
			+ "patientId INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "name TEXT NOT NULL,"
			+ "dateOfBirth TEXT NOT NULL,"
			+ "healthCardNumber INT UNQIUE NOT NULL);";
	private static final String DATABASE_CREATE_VISIT = "CREATE TABLE visit("
			+ "visitId INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "patientId INT NOT NULL," + "arrivalTime TEXT NOT NULL,"
			+ "timeSeenByDoc TEXT," + "seenByDoc INT);";
	private static final String DATABASE_CREATE_VITAL_SIGN = "CREATE TABLE vital_sign("
			+ "visitId INTEGER NOT NULL,"
			+ "systolic INT,"
			+ "diastolic INT,"
			+ "temperature REAL,"
			+ "heartRate INT,"
			+ "urgency INT,"
			+ "recordTime TEXT);";
	private static final String DATABASE_CREATE_PRESCRIPTION = "CREATE TABLE prescription("
			+ "visitId INTEGER NOT NULL,"
			+ "medication TEXT NOT NULL,"
			+ "instructions TEXT NOT NULL);";

	/** Constructor for DatabaseHelper */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/** Execute CREATE Query */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_PATIENT);
		db.execSQL(DATABASE_CREATE_VISIT);
		db.execSQL(DATABASE_CREATE_VITAL_SIGN);
		db.execSQL(DATABASE_CREATE_PRESCRIPTION);
	}

	/** Updates the current database to a new database */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISIT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VITAL_SIGN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESCIPTION);
		onCreate(db);
	}

}