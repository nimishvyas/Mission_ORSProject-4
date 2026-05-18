package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * CollegeCtl handles CRUD operations for College entity.
 * 
 * It provides:
 * - Validation of college details
 * - Adding new college records
 * - Updating existing college records
 * - Fetching college details by ID
 * - Navigation control for college views
 * 
 * Flow:
 * - GET request → loads college data (if id present) and forwards to view
 * - POST request → performs save, update, cancel, or reset operations
 * 
 * This controller extends BaseCtl to utilize common functionalities such as
 * validation, DTO population, and request handling.
 * 
 * URL Mapping: /CollegeCtl
 * 
 * @author Nimish
 */
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {

    /** Log4j Logger */
    private static final Logger log = Logger.getLogger(CollegeCtl.class);

    /**
     * Validates input fields for college form.
     * 
     * Validation rules:
     * - Name must not be null and must be valid
     * - Address must not be null
     * - State must not be null
     * - City must not be null
     * - Phone number must:
     *   - Not be null
     *   - Be exactly 10 digits
     *   - Be a valid phone number
     * 
     * @param request HttpServletRequest object
     * @return true if validation passes, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        log.debug("CollegeCtl validate() called");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("address"))) {
            request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("state"))) {
            request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("city"))) {
            request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Phone No"));
            pass = false;
        } else if (!DataValidator.isPhoneLength(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", "Phone No must have 10 digits");
            pass = false;
        } else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", "Invalid Phone No");
            pass = false;
        }

        log.debug("Validation result: " + pass);
        return pass;
    }

    /**
     * Populates CollegeBean with request parameters.
     * 
     * Maps form fields to bean properties and sets audit fields.
     * 
     * @param request HttpServletRequest object
     * @return populated CollegeBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        log.debug("CollegeCtl populateBean() called");

        CollegeBean bean = new CollegeBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setAddress(DataUtility.getString(request.getParameter("address")));
        bean.setState(DataUtility.getString(request.getParameter("state")));
        bean.setCity(DataUtility.getString(request.getParameter("city")));
        bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request.
     * 
     * If id is present, fetches college data and sets it in request scope.
     * Then forwards to college view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("CollegeCtl doGet() started");

        long id = DataUtility.getLong(request.getParameter("id"));

        CollegeModel model = new CollegeModel();

        if (id > 0) {
            try {
                CollegeBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
                log.info("Loaded CollegeBean for id=" + id);
            } catch (ApplicationException e) {
                log.error("ApplicationException in doGet()", e);
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
        log.info("doGet() forwarded to view: " + getView());
    }

    /**
     * Handles POST request for various operations.
     * 
     * Operations:
     * - Save → adds new college record
     * - Update → updates existing college record
     * - Cancel → redirects to college list
     * - Reset → reloads form
     * 
     * Handles success and error scenarios appropriately.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("CollegeCtl doPost() started");

        String op = DataUtility.getString(request.getParameter("operation"));

        CollegeModel model = new CollegeModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {
            log.debug("Operation: SAVE");
            CollegeBean bean = (CollegeBean) populateBean(request);
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully saved", request);
                log.info("College added successfully, pk=" + pk);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("College Name already exists", request);
                log.warn("Duplicate college name during save: " + bean.getName());
            } catch (ApplicationException e) {
                log.error("ApplicationException in doPost() SAVE", e);
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            log.debug("Operation: UPDATE");
            CollegeBean bean = (CollegeBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                    log.info("College updated successfully, id=" + id);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully updated", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("College Name already exists", request);
                log.warn("Duplicate college name during update: " + bean.getName());
            } catch (ApplicationException e) {
                log.error("ApplicationException in doPost() UPDATE", e);
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            log.info("Operation: CANCEL, redirecting to COLLEGE_LIST_CTL");
            ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
            return;
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            log.info("Operation: RESET, redirecting to COLLEGE_CTL");
            ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
        log.info("doPost() forwarded to view: " + getView());
    }

    /**
     * Returns view associated with college form.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        log.debug("Returning College view page");
        return ORSView.COLLEGE_VIEW;
    }
}