package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * CourseListCtl handles listing, searching, pagination,
 * and deletion of Course records.
 * 
 * It provides:
 * - Displaying list of courses
 * - Searching courses based on criteria
 * - Pagination support (Next/Previous)
 * - Deleting selected course records
 * - Navigation to course form
 * 
 * Flow:
 * - GET request → loads initial course list with default pagination
 * - POST request → handles search, pagination, delete, reset, and navigation operations
 * 
 * This controller extends BaseCtl to reuse common functionalities
 * like preload, DTO population, and request handling.
 * 
 * URL Mapping: /CourseListCtl
 * 
 * @author Nimish
 */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {

    /** Log4j Logger */
    private static final Logger log = Logger.getLogger(CourseListCtl.class);

    /**
     * Preloads course list for dropdown or reference usage.
     * 
     * Fetches all course records and stores them in request scope.
     * 
     * @param request HttpServletRequest object
     */
    @Override
    protected void preload(HttpServletRequest request) {
        log.debug("CourseListCtl preload() called");

        CourseModel courseModel = new CourseModel();

        try {
            List courList = courseModel.list();
            request.setAttribute("courseList", courList);
            log.info("Preloaded course list, size=" + courList.size());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates CourseBean with request parameters for search criteria.
     * 
     * Maps:
     * - id
     * - name
     * - duration
     * - description
     * 
     * Also sets audit fields.
     * 
     * @param request HttpServletRequest object
     * @return populated CourseBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        log.debug("CourseListCtl populateBean() called");

        CourseBean bean = new CourseBean();
        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDuration(DataUtility.getString(request.getParameter("duration")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request.
     * 
     * Initializes pagination and retrieves first page of course records.
     * Sets list, page details, and forwards to view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("CourseListCtl doGet() started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        CourseBean bean = (CourseBean) populateBean(request);
        CourseModel model = new CourseModel();

        try {
            List<CourseBean> list = model.search(bean, pageNo, pageSize);
            List<CourseBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setBean(bean, request);
            ServletUtility.setPageSize(pageSize, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);
            log.info("doGet() forwarded to view: " + getView());
        } catch (ApplicationException e) {
            log.error("ApplicationException in doGet()", e);
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Handles POST request for list operations.
     * 
     * Supported operations:
     * - Search → filters results and resets page number
     * - Next → moves to next page
     * - Previous → moves to previous page
     * - New → redirects to course form
     * - Delete → deletes selected course records
     * - Reset → reloads list page
     * - Back → reloads list page
     * 
     * Updates list, pagination details, and forwards to view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("CourseListCtl doPost() started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        CourseBean bean = (CourseBean) populateBean(request);
        CourseModel model = new CourseModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                    log.debug("Operation: SEARCH");
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                    log.debug("Operation: NEXT, pageNo=" + pageNo);
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                    log.debug("Operation: PREVIOUS, pageNo=" + pageNo);
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                log.info("Operation: NEW, redirecting to COURSE_CTL");
                ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                log.debug("Operation: DELETE");
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    CourseBean deletebean = new CourseBean();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                        log.info("Course deleted successfully, id=" + id);
                        ServletUtility.setSuccessMessage("Course deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                    log.warn("DELETE attempted with no records selected");
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                log.info("Operation: RESET, redirecting to COLLEGE_LIST_CTL");
                ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                log.info("Operation: BACK, redirecting to COLLEGE_LIST_CTL");
                ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);
            log.info("doPost() forwarded to view: " + getView());

        } catch (ApplicationException e) {
            log.error("ApplicationException in doPost()", e);
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
            return;
        }

    }

    /**
     * Returns view associated with course list page.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        log.debug("Returning CourseList view page");
        return ORSView.COURSE_LIST_VIEW;
    }

}