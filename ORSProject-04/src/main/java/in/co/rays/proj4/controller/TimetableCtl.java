package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimetableModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TimetableCtl", urlPatterns = { "/ctl/TimetableCtl" })
public class TimetableCtl extends BaseCtl {

    /** Logger instance */
    private static final Logger log = Logger.getLogger(TimetableCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {

        log.debug("Preload method started");

        SubjectModel subjectModel = new SubjectModel();
        CourseModel courseModel = new CourseModel();

        try {
            List subjectList = subjectModel.list();
            request.setAttribute("subjectList", subjectList);

            List courseList = courseModel.list();
            request.setAttribute("courseList", courseList);

            log.debug("Subject & Course lists loaded");

        } catch (ApplicationException e) {
            log.error("Error while preloading subject/course lists", e);
        }
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("Validate method started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("semester"))) {
            request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("examDate"))) {
            request.setAttribute("examDate", PropertyReader.getValue("error.require", "Date of Exam"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("examDate"))) {
            request.setAttribute("examDate", PropertyReader.getValue("error.date", "Date of Exam"));
            pass = false;
        } else if (DataValidator.isSunday(request.getParameter("examDate"))) {
            request.setAttribute("examDate", "Exam should not be on Sunday");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("examTime"))) {
            request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("courseId"))) {
            request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("subjectId"))) {
            request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
            pass = false;
        }

        log.debug("Validate method ended with result: " + pass);
        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PopulateBean method started");

        TimetableBean bean = new TimetableBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setSemester(DataUtility.getString(request.getParameter("semester")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        bean.setExamTime(DataUtility.getString(request.getParameter("examTime")));
        bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

        populateDTO(bean, request);

        log.debug("PopulateBean method ended");
        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet method called");

        long id = DataUtility.getLong(request.getParameter("id"));

        TimetableModel model = new TimetableModel();

        if (id > 0) {
            try {
                log.debug("Fetching timetable id: " + id);
                TimetableBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error("Error fetching timetable by id", e);
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

        TimetableModel model = new TimetableModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            TimetableBean bean = (TimetableBean) populateBean(request);

            TimetableBean bean1;
            TimetableBean bean2;
            TimetableBean bean3;

            try {
                log.debug("Checking duplicates before saving timetable");

                bean1 = model.checkByCourseName(bean.getCourseId(), bean.getExamDate());
                bean2 = model.checkBySubjectName(bean.getCourseId(), bean.getSubjectId(), bean.getExamDate());
                bean3 = model.checkBySemester(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
                        bean.getExamDate());

                if (bean1 == null && bean2 == null && bean3 == null) {

                    log.debug("No duplicates found, saving timetable");

                    long pk = model.add(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Timetable added successfully", request);

                } else {

                    log.warn("Duplicate timetable detected");

                    bean = (TimetableBean) populateBean(request);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setErrorMessage("Timetable already exist!", request);
                }

            } catch (DuplicateRecordException e) {
                log.warn("DuplicateRecordException while saving timetable");
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Timetable already exist!", request);

            } catch (ApplicationException e) {
                log.error("ApplicationException during timetable save", e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            TimetableBean bean = (TimetableBean) populateBean(request);

            TimetableBean bean4;

            try {
                log.debug("Checking duplicates before updating timetable id: " + id);

                bean4 = model.checkByExamTime(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
                        bean.getExamDate(), bean.getExamTime(), bean.getDescription());

                if (id > 0 && bean4 == null) {

                    log.debug("No duplicates found, updating timetable");

                    model.update(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Timetable updated successfully", request);

                } else {

                    log.warn("Duplicate timetable detected during update");

                    bean = (TimetableBean) populateBean(request);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setErrorMessage("Timetable already exist!", request);
                }

            } catch (DuplicateRecordException e) {
                log.warn("DuplicateRecordException while updating timetable");
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Timetable already exist!", request);

            } catch (ApplicationException e) {
                log.error("ApplicationException during timetable update", e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            log.info("Cancel operation triggered");
            ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            log.info("Reset operation triggered");
            ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
        log.info("doPost method ended");
    }

    @Override
    protected String getView() {
        return ORSView.TIMETABLE_VIEW;
    }
}