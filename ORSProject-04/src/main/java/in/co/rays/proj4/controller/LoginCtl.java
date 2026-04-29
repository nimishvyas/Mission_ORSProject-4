package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * LoginCtl handles user authentication and session management.
 * 
 * It provides:
 * - User login (Sign In)
 * - User logout
 * - Navigation to user registration (Sign Up)
 * - Session handling for authenticated users
 * 
 * Flow:
 * - GET request → handles logout and displays login page
 * - POST request → processes login or redirects to registration
 * 
 * This controller extends BaseCtl to reuse common functionalities
 * like validation and request handling.
 * 
 * URL Mapping: /LoginCtl
 * 
 * @author Nimish
 */
@WebServlet("/LoginCtl")
public class LoginCtl extends BaseCtl {

    /** Operation constant for user sign in */
    public static final String OP_SIGN_IN = "Sign In";

    /** Operation constant for user sign up */
    public static final String OP_SIGN_UP = "Sign Up";

    /** Operation constant for user logout */
    public static final String OP_LOG_OUT = "Logout";

    /**
     * Validates login credentials.
     * 
     * Validation rules:
     * - Login (email/username) must not be null
     * - Password must not be null
     * 
     * Validation is skipped for Sign Up and Logout operations.
     * 
     * @param request HttpServletRequest object
     * @return true if validation passes, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        String op = request.getParameter("operation");

        if (OP_SIGN_UP.equalsIgnoreCase(op) || OP_LOG_OUT.equalsIgnoreCase(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("login"))) {
            request.setAttribute("login", "login id is required");
            pass = false;
        }  
        if (DataValidator.isNull(request.getParameter("password"))) {
            request.setAttribute("password", "Password is required");
            pass = false;
        }
        return pass;
    }

    /**
     * Populates UserBean with login credentials from request.
     * 
     * Maps:
     * - id
     * - login
     * - password
     * 
     * @param request HttpServletRequest object
     * @return populated UserBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        UserBean bean = new UserBean();
        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setPassword(DataUtility.getString(request.getParameter("password")));
        return bean;
    }

    /**
     * Handles GET request.
     * 
     * If logout operation is triggered:
     * - Invalidates session
     * - Sets success message
     * 
     * Then forwards to login view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_LOG_OUT.equalsIgnoreCase(op)) {
            HttpSession session = request.getSession();
            session.invalidate();
            ServletUtility.setSuccessMessage("Logout Successful", request);
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles POST request for login operations.
     * 
     * Supported operations:
     * - Sign In → authenticates user and creates session
     * - Sign Up → redirects to registration page
     * 
     * On successful login:
     * - Stores user and role in session
     * - Redirects to welcome page
     * 
     * On failure:
     * - Displays error message
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        UserModel model = new UserModel();
        RoleModel role = new RoleModel();
        HttpSession session = request.getSession();

        if (OP_SIGN_IN.equalsIgnoreCase(op)) {
            UserBean bean = (UserBean) populateBean(request);
            try {
                bean = model.authenticate(bean.getLogin(), bean.getPassword());

                if (bean != null) {
                    session.setAttribute("user", bean);
                    RoleBean rolebean = role.findByPk(bean.getRoleId());
                    if (rolebean != null) {
                        session.setAttribute("role", rolebean.getName());
                    }
                    ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
                    return;
                } else {
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setErrorMessage("Invalid LoginId and Password", request);
                }
            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
        } else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns view associated with login page.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        return ORSView.LOGIN_VIEW;
    }

}