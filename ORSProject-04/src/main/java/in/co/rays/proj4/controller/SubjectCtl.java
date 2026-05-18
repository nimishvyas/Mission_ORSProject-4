package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {

    /** Logger instance */
    private static final Logger log = Logger.getLogger(SubjectCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {

        log.debug("Preload method started");

        CourseModel courseModel = new CourseModel();
        try {
            List courseList = courseModel.list();
            request.setAttribute("courseList", courseList);
            log.debug("Course list loaded, size: " + (courseList != null ? courseList.size() : 0));
        } catch (ApplicationException e) {
            log.error("Error while preloading course list", e);
        }
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("Validate method started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("courseId"))) {
            request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        log.debug("Validate method ended with result: " + pass);
        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PopulateBean method started");

        SubjectBean bean = new SubjectBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        log.debug("PopulateBean method ended");
        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet method called");

        long id = DataUtility.getLong(request.getParameter("id"));

        SubjectModel model = new SubjectModel();

        if (id > 0) {
            try {
                log.debug("Fetching subject with id: " + id);
                SubjectBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error("Error fetching subject by id", e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doPost method started");

        String op = DataUtility.getString(request.getParameter("operation"));

        SubjectModel model = new SubjectModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            SubjectBean bean = (SubjectBean) populateBean(request);

            try {
                log.debug("Adding new subject");
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Subject added successfully", request);

            } catch (DuplicateRecordException e) {
                log.warn("Duplicate subject name while adding");
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Subject Name already exists", request);

            } catch (ApplicationException e) {
                log.error("ApplicationException during subject add", e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            SubjectBean bean = (SubjectBean) populateBean(request);

            try {
                log.debug("Updating subject id: " + id);

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Subject updated successfully", request);

            } catch (DuplicateRecordException e) {
                log.warn("Duplicate subject name while updating");
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Subject Name already exists", request);

            } catch (ApplicationException e) {
                log.error("ApplicationException during subject update", e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            log.info("Cancel operation triggered");
            ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            log.info("Reset operation triggered");
            ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
        log.info("doPost method ended");
    }

    @Override
    protected String getView() {
        return ORSView.SUBJECT_VIEW;
    }
}