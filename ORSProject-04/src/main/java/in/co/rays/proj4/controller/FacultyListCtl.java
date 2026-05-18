package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.FacultyModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * FacultyListCtl handles listing, searching, pagination,
 * and deletion of Faculty records.
 * 
 * It provides:
 * - Displaying list of faculty members
 * - Searching faculty based on criteria
 * - Pagination (Next/Previous)
 * - Deleting selected faculty records
 * - Navigation to faculty form
 * 
 * Flow:
 * - GET request → loads initial faculty list with default pagination
 * - POST request → handles search, pagination, delete, reset, and navigation operations
 * 
 * This controller extends BaseCtl to reuse common functionalities
 * like DTO population and request handling.
 * 
 * URL Mapping: /FacultyListCtl
 * 
 * @author Nimish
 */
@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {

    /** Log4j Logger */
    private static final Logger log = Logger.getLogger(FacultyListCtl.class);

    /**
     * Populates FacultyBean with search criteria from request.
     * 
     * Maps:
     * - firstName
     * - lastName
     * - email
     * 
     * @param request HttpServletRequest object
     * @return populated FacultyBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        log.debug("FacultyListCtl populateBean() called");

        FacultyBean bean = new FacultyBean();

        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));

        return bean;
    }

    /**
     * Handles GET request.
     * 
     * Initializes pagination and retrieves first page of faculty records.
     * Sets list, pagination details, and forwards to view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("FacultyListCtl doGet() started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        FacultyBean bean = (FacultyBean) populateBean(request);
        FacultyModel model = new FacultyModel();

        try {
            List<FacultyBean> list = model.search(bean, pageNo, pageSize);
            List<FacultyBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);
            log.info("doGet() forwarded to view: " + getView());

        } catch (ApplicationException e) {
            log.error("ApplicationException in doGet()", e);
            e.printStackTrace();
        }

    }

    /**
     * Handles POST request for list operations.
     * 
     * Supported operations:
     * - Search → filters results and resets page number
     * - Next → moves to next page
     * - Previous → moves to previous page
     * - New → redirects to faculty form
     * - Delete → deletes selected faculty records
     * - Reset → reloads list page
     * - Back → reloads list page
     * 
     * Updates list, pagination details, and forwards to view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("FacultyListCtl doPost() started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        FacultyBean bean = (FacultyBean) populateBean(request);
        FacultyModel model = new FacultyModel();

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
                log.info("Operation: NEW, redirecting to FACULTY_CTL");
                ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                log.debug("Operation: DELETE");
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    FacultyBean deletebean = new FacultyBean();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                        log.info("Faculty deleted successfully, id=" + id);
                        ServletUtility.setSuccessMessage("Faculty is deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                    log.warn("DELETE attempted with no records selected");
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                log.info("Operation: RESET, redirecting to FACULTY_LIST_CTL");
                ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                log.info("Operation: BACK, redirecting to FACULTY_LIST_CTL");
                ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
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
     * Returns view associated with faculty list page.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        log.debug("Returning FacultyList view page");
        return ORSView.FACULTY_LIST_VIEW;
    }
}