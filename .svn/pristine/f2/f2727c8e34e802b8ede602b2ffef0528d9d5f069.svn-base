package com.example.triage;

public class Prescription {
	/**
	 * This class is designed for each individual perscriptpion object. Inside,
	 * we can store a medication and instructions for a specific visit of the
	 * patient, which are entered by physicians.
	 */

	/** Visit ID shows which visit this prescription belong to */
	private int visitId;

	/** The name of the medication of this Prescription. */
	private String medication;

	/** The instructions of this Prescription. */
	private String instructions;

	/**
	 * An empty constructor for a Prescription.
	 */
	public Prescription() {
	}

	/**
	 * Constructs a Prescription with the medication and instructions.
	 * 
	 * @param prescription
	 *            The name of this Prescription.
	 * @param instructions
	 *            The instructions of this Prescription.
	 */
	public Prescription(String medication, String instructions) {
		this.setMedication(medication);
		this.setInstructions(instructions);

	}

	public int getVisitId() {
		return visitId;
	}

	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}

	/**
	 * Returns the name of this Prescription.
	 * 
	 * @return The name of this Prescription.
	 */
	public String getMedication() {
		return medication;
	}

	/**
	 * Sets the name of medication in this Prescription.
	 * 
	 * @param The
	 *            the name of a new medication.
	 */
	public void setMedication(String medication) {
		this.medication = medication;
	}

	/**
	 * Returns the instructions of this Prescription.
	 * 
	 * @return The instructions of this Prescription.
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * Sets the instructions in Prescription.
	 * 
	 * @param The
	 *            instructions of a new Prescription.
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	/**
	 * Returns a string representation of the prescription.
	 * 
	 * @return A string representation of Prescription.
	 */
	public String toString() {
		return "Medication Name: " + this.medication + "\nInstructions: "
				+ this.instructions + "\n" + "\n";
	}
}
