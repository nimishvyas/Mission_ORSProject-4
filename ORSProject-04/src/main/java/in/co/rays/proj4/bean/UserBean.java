package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * UserBean is a JavaBean class that represents a User entity. It is used to
 * handle records of the ST_USER table.
 * 
 * This class contains user related attributes such as name, login credentials,
 * personal details and role information.
 * 
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 * 
 * @author Nimish
 */
public class UserBean extends BaseBean {

	/** First name of the user. */
	private String firstName;

	/** Last name of the user. */
	private String lastName;

	/** Login ID of the user, usually an email or username. */
	private String login;

	/** Password of the user. */
	private String password;

	/** Confirm password used for validation during registration or password change. */
	private String confirmPassword;

	/** Date of birth of the user. */
	private Date dob;

	/** Mobile number of the user. */
	private String mobileNo;

	/** Role ID associated with the user, used to determine user permissions. */
	private long roleId;

	/** Gender of the user. */
	private String gender;

	/**
	 * Returns the first name of the user.
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the user.
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the user.
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the user.
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the login ID of the user.
	 * 
	 * @return login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login ID of the user.
	 * 
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Returns the password of the user.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the confirm password value of the user.
	 * 
	 * @return confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Sets the confirm password value of the user.
	 * 
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Returns the date of birth of the user.
	 * 
	 * @return dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the user.
	 * 
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Returns the mobile number of the user.
	 * 
	 * @return mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the user.
	 * 
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Returns the role ID of the user.
	 * 
	 * @return roleId
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * Sets the role ID of the user.
	 * 
	 * @param roleId the roleId to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * Returns the gender of the user.
	 * 
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the user.
	 * 
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Returns the full name of the user by combining first name and last name.
	 * This method is used to display the user name in dropdown lists or UI components.
	 * 
	 * @return firstName and lastName combined as display value
	 */
	@Override
	public String getValue() {
		return firstName + " " + lastName;
	}
	
}