package com.example.triage;

public class User {
	/**
	 * A class to represent single user. The user id, password and status (nurse
	 * or physician) is stored and to be used.
	 */

	/** This user's id. */
	private String id;

	/** This user's password. */
	private String password;

	/** This user's status. */
	private String position;

	/**
	 * Construct a User using information given.
	 * 
	 * @param id
	 * @param password
	 * @param status
	 */
	public User(String id, String password, String status) {
		this.id = id;
		this.password = password;
		this.position = status;
	}

	/**
	 * Getter for user's id
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for user's id
	 * 
	 * @param id
	 *            User's login id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter for user's password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for user's password
	 * 
	 * @param password
	 *            User's login password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for user's status
	 * 
	 * @return
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Setter for user's status
	 * 
	 * @param position
	 *            Position of user (Nurse or Physician)
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * toString method for user class
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", status="
				+ position + "]";
	}
}