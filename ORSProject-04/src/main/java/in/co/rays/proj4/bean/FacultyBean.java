package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * FacultyBean is a JavaBean class that represents a Faculty entity. It is used
 * to handle records of the faculty table.
 * 
 * This class contains faculty related attributes such as personal details,
 * contact information and the college, course and subject associated with
 * the faculty member.
 * 
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 * 
 * @author Nimish
 */
public class FacultyBean extends BaseBean {

	/** First name of the faculty. */
	private String firstName;

	/** Last name of the faculty. */
	private String lastName;

	/** Date of birth of the faculty. */
	private Date dob;

	/** Gender of the faculty. */
	private String gender;

	/** Mobile number of the faculty. */
	private String mobileNo;

	/** Email address of the faculty. */
	private String email;

	/** College ID of the college where the faculty is assigned. */
	private long collegeId;

	/** Name of the college where the faculty is assigned. */
	private String collegeName;

	/** Course ID of the course that the faculty is teaching. */
	private long courseId;

	/** Name of the course that the faculty is teaching. */
	private String courseName;

	/** Subject ID of the subject that the faculty is teaching. */
	private long subjectId;

	/** Name of the subject that the faculty is teaching. */
	private String subjectName;

	/**
	 * Returns the first name of the faculty.
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the faculty.
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the faculty.
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the faculty.
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the date of birth of the faculty.
	 * 
	 * @return dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the faculty.
	 * 
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Returns the gender of the faculty.
	 * 
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the faculty.
	 * 
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Returns the mobile number of the faculty.
	 * 
	 * @return mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the faculty.
	 * 
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Returns the email address of the faculty.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the faculty.
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the college ID of the college where the faculty is assigned.
	 * 
	 * @return collegeId
	 */
	public long getCollegeId() {
		return collegeId;
	}

	/**
	 * Sets the college ID of the college where the faculty is assigned.
	 * 
	 * @param collegeId the collegeId to set
	 */
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	/**
	 * Returns the name of the college where the faculty is assigned.
	 * 
	 * @return collegeName
	 */
	public String getCollegeName() {
		return collegeName;
	}

	/**
	 * Sets the name of the college where the faculty is assigned.
	 * 
	 * @param collegeName the collegeName to set
	 */
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	/**
	 * Returns the course ID of the course that the faculty is teaching.
	 * 
	 * @return courseId
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the course ID of the course that the faculty is teaching.
	 * 
	 * @param courseId the courseId to set
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Returns the name of the course that the faculty is teaching.
	 * 
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the name of the course that the faculty is teaching.
	 * 
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns the subject ID of the subject that the faculty is teaching.
	 * 
	 * @return subjectId
	 */
	public long getSubjectId() {
		return subjectId;
	}

	/**
	 * Sets the subject ID of the subject that the faculty is teaching.
	 * 
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * Returns the name of the subject that the faculty is teaching.
	 * 
	 * @return subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Sets the name of the subject that the faculty is teaching.
	 * 
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Returns the display value of the faculty bean.
	 * This method is used to display the faculty in dropdown lists or UI components.
	 * 
	 * @return null as display value is not defined for faculty
	 */
	@Override
	public String getValue() {
		return null;
	}
}