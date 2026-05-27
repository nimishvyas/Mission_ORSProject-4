package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * UserListCtl handles listing, searching, pagination,
 * and deletion of User records.																					
 * It provides:
 * - Preloading role list for filtering
 * - Displaying list of users
 * - Searching users based on criteria
 * - Pagination (Next/Previous)
 * - Deleting selected user records
 * - Navigation to user form
 * 
 * Flow:
 * - GET request → loads initial user list with default pagination
 * - POST request → handles search, pagination, delete, reset, and navigation operations
 * 
 * This controller extends BaseCtl to reuse common functionalities
 * like DTO population and request handling.
 * 
 * URL Mapping: /UserListCtl
 * 
 * @author Nimish
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {

    /** Log4j Logger */
    private static final Logger log = Logger.getLogger(UserListCtl.class);

    /**
     * Preloads role list for dropdown/filter.
     * 
     * Fetches all roles and stores them in request scope.
     * 
     * @param request HttpServletRequest object
     */
    @Override
    protected void preload(HttpServletRequest request) {
        log.debug("UserListCtl preload() called");

        RoleModel roleModel = new RoleModel();
        try {
            List roleList = roleModel.list();
            request.setAttribute("roleList", roleList);
            log.info("Preloaded role list, size=" + roleList.size());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates UserBean with search criteria from request.
     * 
     * Maps:
     * - firstName
     * - login
     * - roleId
     * 
     * @param request HttpServletRequest object
     * @return populated UserBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        log.debug("UserListCtl populateBean() called");

        UserBean bean = new UserBean();

        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
        

        return bean;
    }

    /**
     * Handles GET request.
     * 
     * Initializes pagination and retrieves first page of user records.
     * Sets list, pagination details, and forwards to view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("UserListCtl doGet() started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        UserBean bean = (UserBean) populateBean(request);
        UserModel model = new UserModel();

        try {
            List<UserBean> list = model.search(bean, pageNo, pageSize);
            List<UserBean> next = model.search(bean, pageNo + 1, pageSize);

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
     * - New → redirects to user form
     * - Delete → deletes selected user records
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

        log.info("UserListCtl doPost() started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        UserBean bean = (UserBean) populateBean(request);
        UserModel model = new UserModel();

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
                log.info("Operation: NEW, redirecting to USER_CTL");
                ServletUtility.redirect(ORSView.USER_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                log.debug("Operation: DELETE");
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    UserBean deleteBean = new UserBean();
                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getInt(id));
                        model.delete(deleteBean);
                        log.info("User deleted successfully, id=" + id);
                        ServletUtility.setSuccessMessage("User deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                    log.warn("DELETE attempted with no records selected");
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                log.info("Operation: RESET, redirecting to USER_LIST_CTL");
                ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                log.info("Operation: BACK, redirecting to USER_LIST_CTL");
                ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
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
            return;
        }
    }

    /**
     * Returns view associated with user list page.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        log.debug("Returning UserList view page");
        return ORSView.USER_LIST_VIEW;
    }
}