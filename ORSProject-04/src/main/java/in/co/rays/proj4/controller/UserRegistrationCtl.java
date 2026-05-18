package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * UserRegistrationCtl handles user self-registration functionality.
 * 
 * It provides: - Validation of registration input fields - Creation of new user
 * accounts with STUDENT role - Handling duplicate login scenarios - Navigation
 * control for registration page
 * 
 * Flow: - GET request → loads registration page - POST request → performs
 * signup or reset operations
 * 
 * This controller extends BaseCtl to reuse common functionalities like
 * validation, DTO population, and request handling.
 * 
 * URL Mapping: /UserRegistrationCtl
 * 
 * @author Nimish
 */
@WebServlet("/UserRegistrationCtl")
public class UserRegistrationCtl extends BaseCtl {

	/** Log4j Logger */
	private static final Logger log = Logger.getLogger(UserRegistrationCtl.class);

	/**
	 * Operation constant for user signup.
	 */
	public static final String OP_SIGN_UP = "Sign Up";

	/**
	 * Validates input fields for user registration form.
	 * 
	 * Validation rules: - First name and last name must be valid - Login must be a
	 * valid email - Password must meet length and complexity requirements - Confirm
	 * password must match password - Gender must not be null - Date of birth must
	 * be valid - Mobile number must be valid (10 digits)
	 * 
	 * @param request HttpServletRequest object
	 * @return true if validation passes, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("UserRegistrationCtl validate() called");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "Invalid First Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Invalid Last Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password", "Password should be 8 to 12 characters");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}

		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Password and Confirm Password must be Same!");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}

		log.debug("Validation result: " + pass);
		return pass;
	}

	/**
	 * Populates UserBean with registration form data.
	 * 
	 * Sets: - firstName - lastName - login - password - confirmPassword - gender -
	 * dob - mobileNo - roleId (default: STUDENT)
	 * 
	 * Also sets audit fields.
	 * 
	 * @param request HttpServletRequest object
	 * @return populated UserBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("UserRegistrationCtl populateBean() called");

		UserBean bean = new UserBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setRoleId(RoleBean.STUDENT);

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handles GET request.
	 * 
	 * Loads registration page.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("UserRegistrationCtl doGet() started");
		
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles POST request for registration operations.
	 * 
	 * Supported operations: - Sign Up → registers new user - Reset → reloads
	 * registration page
	 * 
	 * Handles duplicate login scenarios and success messages.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("UserRegistrationCtl doPost() started");
		

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			log.debug("Operation: SIGN_UP");
			UserBean bean = (UserBean) populateBean(request);

			try {
				long pk = model.registerUser(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Registration successful!", request);
				log.info("User registered successfully, pk=" + pk);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				log.warn("Duplicate login during registration: " + bean.getLogin());
			} catch (ApplicationException e) {
				log.error("ApplicationException in doPost() SIGN_UP", e);
				e.printStackTrace();
				return;
			}
			ServletUtility.forward(getView(), request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			log.info("Operation: RESET, redirecting to USER_REGISTRATION_CTL");
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

	}

	/**
	 * Returns view associated with user registration page.
	 * 
	 * @return view path
	 */
	@Override
	protected String getView() {
		log.debug("Returning UserRegistration view page");
		return ORSView.USER_REGISTRATION_VIEW;
	}
}