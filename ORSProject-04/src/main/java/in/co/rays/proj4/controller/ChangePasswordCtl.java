package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * ChangePasswordCtl handles user password change functionality.
 * 
 * It provides:
 * - Validation of old, new, and confirm passwords
 * - Password strength and format checks
 * - Interaction with UserModel to update password
 * - Navigation between change password and profile views
 * 
 * Flow:
 * - GET request → displays change password page
 * - POST request → validates input and updates password
 * 
 * This controller extends BaseCtl to reuse common functionality
 * such as validation, DTO population, and request handling.
 * 
 * URL Mapping: /ChangePasswordCtl
 * 
 * @author Nimish
 */
@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl {

    /** Log4j Logger */
    private static final Logger log = Logger.getLogger(ChangePasswordCtl.class);

    /** Operation constant for navigating to profile page */
    public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

    /**
     * Validates input fields for password change.
     * 
     * Validation rules:
     * - Old password must not be null
     * - New password must:
     *   - Not be null
     *   - Be different from old password
     *   - Be 8–12 characters long
     *   - Contain uppercase, lowercase, digit, and special character
     * - Confirm password must not be null
     * - New and confirm passwords must match
     * 
     * @param request HttpServletRequest object
     * @return true if validation passes, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        log.debug("ChangePasswordCtl validate() called");

        boolean pass = true;

        String op = request.getParameter("operation");

        if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("oldPassword"))) {
            request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
            pass = false;
        } else if (request.getParameter("oldPassword").equals(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", "Old and New passwords should be different");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
            pass = false;
        } else if (!DataValidator.isPasswordLength(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", "Password should be 8 to 12 characters");
            pass = false;
        } else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword", "Must contain uppercase, lowercase, digit & special character");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
            pass = false;
        }

        if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
                && !"".equals(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", "New and confirm passwords not matched");
            pass = false;
        }

        log.debug("Validation result: " + pass);
        return pass;
    }

    /**
     * Populates UserBean with request parameters.
     * 
     * Maps:
     * - oldPassword → password
     * - confirmPassword → confirmPassword
     * 
     * Also populates audit fields using BaseCtl.populateDTO().
     * 
     * @param request HttpServletRequest object
     * @return populated UserBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        log.debug("ChangePasswordCtl populateBean() called");

        UserBean bean = new UserBean();

        bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));
        bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request.
     * 
     * Forwards user to change password view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("ChangePasswordCtl doGet() started");
        ServletUtility.forward(getView(), request, response);
        log.info("doGet() forwarded to view: " + getView());
    }

    /**
     * Handles POST request for password change.
     * 
     * Flow:
     * - Retrieves operation and parameters
     * - Gets logged-in user from session
     * - Calls UserModel to update password
     * - Handles success and error scenarios
     * - Redirects or forwards accordingly
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("ChangePasswordCtl doPost() started");

        String op = DataUtility.getString(request.getParameter("operation"));
        String newPassword = (String) request.getParameter("newPassword");

        UserBean bean = (UserBean) populateBean(request);
        UserModel model = new UserModel();

        HttpSession session = request.getSession(true);
        UserBean user = (UserBean) session.getAttribute("user");
        long id = user.getId();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            log.debug("Operation: SAVE");
            try {
                boolean flag = model.changePassword(id, bean.getPassword(), newPassword);
                if (flag == true) {
                    bean = model.findByLogin(user.getLogin());
                    session.setAttribute("user", bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Password has been changed Successfully", request);
                    log.info("Password changed successfully for user id=" + id);
                }
            } catch (RecordNotFoundException e) {
                ServletUtility.setErrorMessage("Old Password is Invalid", request);
                log.warn("Invalid old password for user id=" + id);
            } catch (ApplicationException e) {
                log.error("ApplicationException in doPost() SAVE", e);
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
            log.info("Operation: CHANGE_MY_PROFILE, redirecting to MY_PROFILE_CTL");
            ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
            return;
        }
        ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
        log.info("doPost() forwarded to view: " + ORSView.CHANGE_PASSWORD_VIEW);
    }

    /**
     * Returns view associated with change password functionality.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        log.debug("Returning ChangePassword view page");
        return ORSView.CHANGE_PASSWORD_VIEW;
    }
}