package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * StudentBean is a JavaBean class that represents a Student entity. It is used
 * to handle records of the student table.
 * 
 * This class contains student related attributes such as personal details,
 * contact information and the college associated with the student.
 * 
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 * 
 * @author Nimish
 */
public class StudentBean extends BaseBean {

	/** First name of the student. */
	private String firstName;

	/** Last name of the student. */
	private String lastName;

	/** Date of birth of the student. */
	private Date dob;

	/** Gender of the student. */
	private String gender;

	/** Mobile number of the student. */
	private String mobileNo;

	/** Email address of the student. */
	private String email;

	/** College ID of the college where the student is enrolled. */
	private long collegeId;

	/** Name of the college where the student is enrolled. */
	private String collegeName;

	/**
	 * Returns the first name of the student.
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the student.
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the student.
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the student.
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the date of birth of the student.
	 * 
	 * @return dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the student.
	 * 
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Returns the gender of the student.
	 * 
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the student.
	 * 
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Returns the mobile number of the student.
	 * 
	 * @return mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the student.
	 * 
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Returns the email address of the student.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the student.
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the college ID of the college where the student is enrolled.
	 * 
	 * @return collegeId
	 */
	public long getCollegeId() {
		return collegeId;
	}

	/**
	 * Sets the college ID of the college where the student is enrolled.
	 * 
	 * @param collegeId the collegeId to set
	 */
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	/**
	 * Returns the name of the college where the student is enrolled.
	 * 
	 * @return collegeName
	 */
	public String getCollegeName() {
		return collegeName;
	}

	/**
	 * Sets the name of the college where the student is enrolled.
	 * 
	 * @param collegeName the collegeName to set
	 */
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	/**
	 * Returns the full name of the student by combining first name and last name.
	 * This method is used to display the student name in dropdown lists or UI components.
	 * 
	 * @return firstName and lastName combined as display value
	 */
	@Override
	public String getValue() {
		return firstName + " " + lastName;
	}
}