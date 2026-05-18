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
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/MyProfileCtl" })
public class MyProfileCtl extends BaseCtl {

    /** Logger instance */
    private static final Logger log = Logger.getLogger(MyProfileCtl.class);

    public static final String OP_CHANGE_MY_PASSWORD = "Change Password";

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("Validate method started");

        boolean pass = true;

        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {
            return pass;
        }

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

        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
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

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
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
        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setGender(DataUtility.getString(request.getParameter("gender")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));

        populateDTO(bean, request);

        log.debug("PopulateBean method ended");
        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet method called");

        HttpSession session = request.getSession(true);
        UserBean user = (UserBean) session.getAttribute("user");
        long id = user.getId();

        UserModel model = new UserModel();

        if (id > 0) {
            try {
                log.debug("Fetching user profile id: " + id);
                UserBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error("Error fetching user profile", e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doPost method started");

        HttpSession session = request.getSession(true);

        UserBean user = (UserBean) session.getAttribute("user");
        long id = user.getId();

        String op = DataUtility.getString(request.getParameter("operation"));

        UserModel model = new UserModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            UserBean bean = (UserBean) populateBean(request);

            try {
                log.debug("Updating user profile id: " + id);

                if (id > 0) {
                    user.setFirstName(bean.getFirstName());
                    user.setLastName(bean.getLastName());
                    user.setGender(bean.getGender());
                    user.setMobileNo(bean.getMobileNo());
                    user.setDob(bean.getDob());
                    model.update(user);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Profile has been updated Successfully. ", request);

            } catch (DuplicateRecordException e) {
                log.warn("Duplicate login id while updating profile");
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Login id already exists", request);

            } catch (ApplicationException e) {
                log.error("ApplicationException during profile update", e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {

            log.info("Redirecting to change password page");
            ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
        log.info("doPost method ended");
    }

    @Override
    protected String getView() {
        return ORSView.MY_PROFILE_VIEW;
    }
}