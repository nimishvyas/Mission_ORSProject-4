package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/StudentListCtl" })
public class StudentListCtl extends BaseCtl {

    /** Logger instance */
    private static final Logger log = Logger.getLogger(StudentListCtl.class);

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PopulateBean method started");

        StudentBean bean = new StudentBean();

        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));

        log.debug("PopulateBean method ended");
        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet method called");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        StudentBean bean = (StudentBean) populateBean(request);
        StudentModel model = new StudentModel();

        try {
            log.debug("Fetching student list pageNo=" + pageNo);

            List<StudentBean> list = model.search(bean, pageNo, pageSize);
            List<StudentBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                log.warn("No student records found");
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error("Error while fetching student list", e);
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

        StudentBean bean = (StudentBean) populateBean(request);
        StudentModel model = new StudentModel();

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

                log.info("Redirecting to student form");
                ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                log.info("Delete operation triggered");

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    StudentBean deletebean = new StudentBean();

                    for (String id : ids) {
                        log.debug("Deleting student id: " + id);
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                    }

                    ServletUtility.setSuccessMessage("Student is deleted successfully", request);

                } else {
                    log.warn("Delete attempted without selecting records");
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                log.info("Reset operation triggered");
                ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {

                log.info("Back operation triggered");
                ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
                return;
            }

            log.debug("Fetching updated student list pageNo=" + pageNo);

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
            log.error("Error in student list doPost", e);
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    @Override
    protected String getView() {
        return ORSView.STUDENT_LIST_VIEW;
    }
}