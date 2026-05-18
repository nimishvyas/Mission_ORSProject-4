package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

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
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * UserCtl handles CRUD operations for User entity.
 * 
 * It provides: - Validation of user input fields - Preloading role list for
 * dropdown - Adding new user records - Updating existing user records -
 * Fetching user details by ID - Navigation control for user views
 * 
 * Flow: - GET request → loads user data (if id present) and forwards to view -
 * POST request → performs save, update, cancel, or reset operations
 * 
 * This controller extends BaseCtl to reuse common functionalities like
 * validation, DTO population, and request handling.
 * 
 * URL Mapping: /UserCtl
 * 
 * @author Nimish
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	/** Log4j Logger */
	private static final Logger log = Logger.getLogger(UserCtl.class);

	/**
	 * Preloads role list for dropdown selection.
	 * 
	 * Fetches all roles and stores them in request scope.
	 * 
	 * @param request HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("UserCtl preload() called");

		RoleModel roleModel = new RoleModel();
		try {
			List<RoleBean> roleList = roleModel.list();
			request.setAttribute("roleList", roleList);
			log.info("Preloaded role list, size=" + roleList.size());
		} catch (ApplicationException e) {
			log.error("ApplicationException in preload()", e);
			e.printStackTrace();
		}
	}

	/**
	 * Validates input fields for user form.
	 * 
	 * Validation rules: - First name and last name must be valid - Login id must
	 * not be null - Password must meet length and complexity requirements - Confirm
	 * password must match password - Gender must not be null - Date of birth must
	 * be valid - Role must be selected - Mobile number must be valid (10 digits)
	 * 
	 * @param request HttpServletRequest object
	 * @return true if validation passes, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("UserCtl validate() called");

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
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
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

		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}

		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Password and Confirm Password must be Same!");
			pass = false;
		}

		log.debug("Validation result: " + pass);
		return pass;
	}

	/**
	 * Populates UserBean with request parameters.
	 * 
	 * Maps: - id - firstName - lastName - login - password - confirmPassword -
	 * gender - dob - mobileNo - roleId
	 * 
	 * Also sets audit fields.
	 * 
	 * @param request HttpServletRequest object
	 * @return populated UserBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("UserCtl populateBean() called");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handles GET request.
	 * 
	 * If id is present, fetches user data and sets it in request scope. Then
	 * forwards to user view.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("UserCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		UserModel model = new UserModel();

		if (id > 0) {
			try {
				UserBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Loaded UserBean for id=" + id);
			} catch (ApplicationException e) {
				log.error("ApplicationException in doGet()", e);
				e.printStackTrace();
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.info("doGet() forwarded to view: " + getView());
	}

	/**
	 * Handles POST request for user operations.
	 * 
	 * Supported operations: - Save → adds new user record - Update → updates
	 * existing user record - Cancel → redirects to user list - Reset → reloads form
	 * 
	 * Handles success and error scenarios accordingly.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("UserCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			log.debug("Operation: SAVE");
			UserBean bean = (UserBean) populateBean(request);
			try {
				long pk = model.registerUser(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("User added successfully", request);
				log.info("User added successfully, pk=" + pk);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
				log.warn("Duplicate login during registration: " + bean.getLogin());
			} catch (ApplicationException e) {
				log.error("ApplicationException in doPost() SAVE", e);
				e.printStackTrace();
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			log.debug("Operation: UPDATE");
			UserBean bean = (UserBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
					log.info("User updated successfully, id=" + id);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("User updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
				log.warn("Duplicate login during update: " + bean.getLogin());
			} catch (ApplicationException e) {
				log.error("ApplicationException in doPost() UPDATE", e);
				e.printStackTrace();
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			log.info("Operation: CANCEL, redirecting to USER_LIST_CTL");
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			log.info("Operation: RESET, redirecting to USER_CTL");
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.info("doPost() forwarded to view: " + getView());
	}

	/**
	 * Returns view associated with user form.
	 * 
	 * @return view path
	 */
	@Override
	protected String getView() {
		log.debug("Returning User view page");
		return ORSView.USER_VIEW;
	}
}