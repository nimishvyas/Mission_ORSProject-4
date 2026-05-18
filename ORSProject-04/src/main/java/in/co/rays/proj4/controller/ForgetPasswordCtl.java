package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * ForgetPasswordCtl handles the functionality of password recovery.
 * 
 * It provides:
 * - Validation of login (email)
 * - Sending password to user's registered email
 * - Displaying success or error messages
 * 
 * Flow:
 * - GET request → displays forget password page
 * - POST request → validates email and triggers password recovery process
 * 
 * This controller extends BaseCtl to reuse common functionalities
 * like validation and request handling.
 * 
 * URL Mapping: /ForgetPasswordCtl
 * 
 * @author Nimish
 */
@WebServlet(name = "ForgetPasswordCtl", urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {

    /** Log4j Logger */
    private static final Logger log = Logger.getLogger(ForgetPasswordCtl.class);

    /**
     * Validates login (email) input.
     * 
     * Validation rules:
     * - Email must not be null
     * - Email must be in valid format
     * 
     * @param request HttpServletRequest object
     * @return true if validation passes, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        log.debug("ForgetPasswordCtl validate() called");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
            pass = false;
        }

        log.debug("Validation result: " + pass);
        return pass;
    }

    /**
     * Populates UserBean with login (email) from request.
     * 
     * @param request HttpServletRequest object
     * @return populated UserBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        log.debug("ForgetPasswordCtl populateBean() called");

        UserBean bean = new UserBean();

        bean.setLogin(DataUtility.getString(request.getParameter("login")));

        return bean;
    }

    /**
     * Handles GET request.
     * 
     * Forwards request to forget password view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("ForgetPasswordCtl doGet() started");
        ServletUtility.forward(getView(), request, response);
        log.info("doGet() forwarded to view: " + getView());
    }

    /**
     * Handles POST request for password recovery.
     * 
     * Flow:
     * - Validates email
     * - Calls UserModel to send password to email
     * - Displays success or error messages
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("ForgetPasswordCtl doPost() started");

        String op = DataUtility.getString(request.getParameter("operation"));

        UserBean bean = (UserBean) populateBean(request);

        UserModel model = new UserModel();

        if (OP_GO.equalsIgnoreCase(op)) {
            log.debug("Operation: GO");
            try {
                boolean flag = model.forgetPassword(bean.getLogin());
                if (flag) {
                    ServletUtility.setSuccessMessage("Password has been sent to your email id", request);
                    log.info("Password recovery email sent to: " + bean.getLogin());
                }
            } catch (RecordNotFoundException e) {
                ServletUtility.setErrorMessage(e.getMessage(), request);
                log.warn("Login not found during password recovery: " + bean.getLogin());
            } catch (ApplicationException e) {
                log.error("ApplicationException in doPost() GO", e);
                e.printStackTrace();
                ServletUtility.setErrorMessage("Please check your internet connection..!!", request);
            }
            ServletUtility.forward(getView(), request, response);
            log.info("doPost() forwarded to view: " + getView());
        }
    }

    /**
     * Returns view associated with forget password page.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        log.debug("Returning ForgetPassword view page");
        return ORSView.FORGET_PASSWORD_VIEW;
    }
}