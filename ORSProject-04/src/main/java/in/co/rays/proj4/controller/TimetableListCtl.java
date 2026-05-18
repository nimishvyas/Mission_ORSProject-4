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
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimetableModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TimetableListCtl", urlPatterns = { "/ctl/TimetableListCtl" })
public class TimetableListCtl extends BaseCtl {

    /** Logger instance */
    private static final Logger log = Logger.getLogger(TimetableListCtl.class);

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
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PopulateBean method started");

        TimetableBean bean = new TimetableBean();

        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
        bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));

        log.debug("PopulateBean method ended");
        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet method called");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        TimetableBean bean = (TimetableBean) populateBean(request);
        TimetableModel model = new TimetableModel();

        try {
            log.debug("Fetching timetable list pageNo=" + pageNo);

            List<TimetableBean> list = model.search(bean, pageNo, pageSize);
            List<TimetableBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                log.warn("No timetable records found");
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error("Error while fetching timetable list", e);
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doPost method started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        TimetableBean bean = (TimetableBean) populateBean(request);
        TimetableModel model = new TimetableModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

                log.debug("Pagination/Search operation: " + op);

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                log.info("Redirecting to timetable form");
                ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                log.info("Delete operation triggered");

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    TimetableBean deletebean = new TimetableBean();

                    for (String id : ids) {
                        log.debug("Deleting timetable id: " + id);
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                    }

                    ServletUtility.setSuccessMessage("Data is deleted successfully", request);

                } else {
                    log.warn("Delete attempted without selecting records");
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                log.info("Reset operation triggered");
                ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {

                log.info("Back operation triggered");
                ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
                return;
            }

            log.debug("Fetching updated timetable list pageNo=" + pageNo);

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                log.warn("No records found after operation");
                ServletUtility.setErrorMessage("No record found ", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error("Error in timetable list doPost", e);
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    @Override
    protected String getView() {
        return ORSView.TIMETABLE_LIST_VIEW;
    }
}