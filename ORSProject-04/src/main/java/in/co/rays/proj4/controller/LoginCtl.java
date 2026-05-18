package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

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

@WebServlet("/LoginCtl")
public class LoginCtl extends BaseCtl {

    /** Logger instance */
    private static final Logger log = Logger.getLogger(LoginCtl.class);

    public static final String OP_SIGN_IN = "Sign In";
    public static final String OP_SIGN_UP = "Sign Up";
    public static final String OP_LOG_OUT = "Logout";

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("Validate method started");

        boolean pass = true;

        String op = request.getParameter("operation");

        if (OP_SIGN_UP.equalsIgnoreCase(op) || OP_LOG_OUT.equalsIgnoreCase(op)) {
            return pass;
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
		}

        log.debug("Validate method ended with result: " + pass);
        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PopulateBean method started");

        UserBean bean = new UserBean();
        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setPassword(DataUtility.getString(request.getParameter("password")));

        log.debug("PopulateBean method ended");
        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet method called");

        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_LOG_OUT.equalsIgnoreCase(op)) {
            HttpSession session = request.getSession();
            session.invalidate();
            log.info("User logged out successfully");
            ServletUtility.setSuccessMessage("Logout Successful", request);
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doPost method started");

        String op = DataUtility.getString(request.getParameter("operation"));

        UserModel model = new UserModel();
        RoleModel role = new RoleModel();
        HttpSession session = request.getSession();

        if (OP_SIGN_IN.equalsIgnoreCase(op)) {

            UserBean bean = (UserBean) populateBean(request);

            try {
                log.debug("Authenticating user: " + bean.getLogin());

                bean = model.authenticate(bean.getLogin(), bean.getPassword());

                if (bean != null) {

                    log.info("User authenticated successfully");

                    session.setAttribute("user", bean);

                    RoleBean rolebean = role.findByPk(bean.getRoleId());

                    if (rolebean != null) {
                        session.setAttribute("role", rolebean.getName());
                        log.debug("User role set: " + rolebean.getName());
                    }

                    ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
                    return;

                } else {
                    log.warn("Authentication failed for user: " + bean.getLogin());
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setErrorMessage("Invalid LoginId and Password", request);
                }

            } catch (ApplicationException e) {
                log.error("ApplicationException during login", e);
                return;
            }

        } else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

            log.info("Redirecting to Sign Up page");
            ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
        log.info("doPost method ended");
    }

    @Override
    protected String getView() {
        return ORSView.LOGIN_VIEW;
    }
}