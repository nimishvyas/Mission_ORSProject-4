package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * MarksheetCtl handles add, update, validation, and retrieval
 * operations for marksheet records.
 * 
 * @author Nimish
 */
@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {

    /** Logger instance */
    private static final Logger log = Logger.getLogger(MarksheetCtl.class);

    /**
     * Preloads student list for dropdown.
     * 
     * @param request HttpServletRequest object
     */
    @Override
    protected void preload(HttpServletRequest request) {

        log.debug("Preload method started");

        StudentModel studentModel = new StudentModel();
        try {
            List studentList = studentModel.list();
            request.setAttribute("studentList", studentList);
            log.debug("Student list loaded, size: " + (studentList != null ? studentList.size() : 0));
        } catch (ApplicationException e) {
            log.error("Error while preloading student list", e);
        }
    }

    /**
     * Validates marksheet form fields.
     * 
     * @param request HttpServletRequest object containing client request
     * @return true if validation passes, otherwise false
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("Validate method started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("studentId"))) {
            request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("rollNo"))) {
            request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("physics"))) {
            request.setAttribute("physics", PropertyReader.getValue("error.require", "Marks"));
            pass = false;
        } else if (DataValidator.isNotNull(request.getParameter("physics"))
                && !DataValidator.isInteger(request.getParameter("physics"))) {
            request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
            pass = false;
        } else if (DataUtility.getInt(request.getParameter("physics")) > 100
                || DataUtility.getInt(request.getParameter("physics")) < 0) {
            request.setAttribute("physics", "Marks should be in 0 to 100");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("chemistry"))) {
            request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Marks"));
            pass = false;
        } else if (DataValidator.isNotNull(request.getParameter("chemistry"))
                && !DataValidator.isInteger(request.getParameter("chemistry"))) {
            request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
            pass = false;
        } else if (DataUtility.getInt(request.getParameter("chemistry")) > 100
                || DataUtility.getInt(request.getParameter("chemistry")) < 0) {
            request.setAttribute("chemistry", "Marks should be in 0 to 100");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("maths"))) {
            request.setAttribute("maths", PropertyReader.getValue("error.require", "Marks"));
            pass = false;
        } else if (DataValidator.isNotNull(request.getParameter("maths"))
                && !DataValidator.isInteger(request.getParameter("maths"))) {
            request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
            pass = false;
        } else if (DataUtility.getInt(request.getParameter("maths")) > 100
                || DataUtility.getInt(request.getParameter("maths")) < 0) {
            request.setAttribute("maths", "Marks should be in 0 to 100");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("studentId"))) {
            request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
            pass = false;
        }

        log.debug("Validate method ended with result: " + pass);
        return pass;
    }

    /**
     * Populates MarksheetBean with request parameters.
     * 
     * @param request HttpServletRequest object
     * @return populated BaseBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PopulateBean method started");

        MarksheetBean bean = new MarksheetBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
        bean.setName(DataUtility.getString(request.getParameter("name")));

        if (request.getParameter("physics") != null && request.getParameter("physics").length() != 0) {
            bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
        }
        if (request.getParameter("chemistry") != null && request.getParameter("chemistry").length() != 0) {
            bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
        }
        if (request.getParameter("maths") != null && request.getParameter("maths").length() != 0) {
            bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
        }

        bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

        populateDTO(bean, request);

        log.debug("PopulateBean method ended");
        return bean;
    }

    /**
     * Handles HTTP GET request.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if servlet error occurs
     * @throws IOException      if input/output error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet method called");

        long id = DataUtility.getLong(request.getParameter("id"));

        MarksheetModel model = new MarksheetModel();

        if (id > 0) {
            try {
                log.debug("Fetching marksheet with id: " + id);
                MarksheetBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error("Error fetching marksheet by id", e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST request for add, update, reset,
     * and cancel operations.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if servlet error occurs
     * @throws IOException      if input/output error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doPost method started");

        String op = DataUtility.getString(request.getParameter("operation"));

        MarksheetModel model = new MarksheetModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            MarksheetBean bean = (MarksheetBean) populateBean(request);

            try {
                log.debug("Adding new marksheet");
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Marksheet added successfully", request);

            } catch (DuplicateRecordException e) {
                log.warn("Duplicate RollNo while adding marksheet");
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Roll No already exists", request);

            } catch (ApplicationException e) {
                log.error("ApplicationException during add", e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            MarksheetBean bean = (MarksheetBean) populateBean(request);

            try {
                log.debug("Updating marksheet id: " + id);

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Marksheet updated successfully", request);

            } catch (DuplicateRecordException e) {
                log.warn("Duplicate RollNo while updating marksheet");
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Roll No already exists", request);

            } catch (ApplicationException e) {
                log.error("ApplicationException during update", e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            log.info("Cancel operation triggered");
            ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            log.info("Reset operation triggered");
            ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
        log.info("doPost method ended");
    }

    /**
     * Returns the view page path.
     * 
     * @return marksheet view path
     */
    @Override
    protected String getView() {
        return ORSView.MARKSHEET_VIEW;
    }
}