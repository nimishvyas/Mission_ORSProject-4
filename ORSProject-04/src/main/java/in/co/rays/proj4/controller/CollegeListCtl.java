package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * CollegeListCtl handles listing, searching, pagination, and deletion of
 * College records.
 * 
 * It provides: - Displaying list of colleges - Searching colleges by criteria -
 * Pagination (Next/Previous) - Deleting selected records - Navigation to add
 * new college form
 * 
 * Flow: - GET request → loads initial list with default pagination - POST
 * request → handles search, pagination, delete, reset, and navigation
 * operations
 * 
 * This controller extends BaseCtl to reuse common functionalities like preload,
 * DTO population, and request handling.
 * 
 * URL Mapping: /CollegeListCtl
 * 
 * @author Nimish
 */
@WebServlet(name = "CollegeListCtl", urlPatterns = { "/ctl/CollegeListCtl" })
public class CollegeListCtl extends BaseCtl {

	/** Log4j Logger */
	private static final Logger log = Logger.getLogger(CollegeListCtl.class);

	/**
	 * Preloads college list for dropdown or reference use.
	 * 
	 * Fetches all college records and stores them in request scope.
	 * 
	 * @param request HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("CollegeListCtl preload() called");

		CollegeModel collegeModel = new CollegeModel();

		try {
			List collegeList = collegeModel.list();
			request.setAttribute("collegeList", collegeList);
			log.info("Preloaded college list, size=" + collegeList.size());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates CollegeBean with search criteria from request.
	 * 
	 * Maps: - name - city - collegeId → id
	 * 
	 * @param request HttpServletRequest object
	 * @return populated CollegeBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("CollegeListCtl populateBean() called");

		CollegeBean bean = new CollegeBean();

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setId(DataUtility.getLong(request.getParameter("collegeId")));

		return bean;
	}

	/**
	 * Handles GET request.
	 * 
	 * Initializes pagination and retrieves first page of college records. Sets
	 * list, page details, and forwards to view.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("CollegeListCtl doGet() started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		CollegeBean bean = (CollegeBean) populateBean(request);
		CollegeModel model = new CollegeModel();

		try {
			List<CollegeBean> list = model.search(bean, pageNo, pageSize);
			List<CollegeBean> next = model.search(bean, pageNo + 1, pageSize);

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
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Handles POST request for list operations.
	 * 
	 * Supported operations: - Search → filters records and resets page number -
	 * Next → moves to next page - Previous → moves to previous page - New →
	 * redirects to college form - Delete → deletes selected records - Reset →
	 * reloads list page - Back → reloads list page
	 * 
	 * Updates list, pagination details, and forwards to view.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("CollegeListCtl doPost() started");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		CollegeBean bean = (CollegeBean) populateBean(request);
		CollegeModel model = new CollegeModel();

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
				log.info("Operation: NEW, redirecting to COLLEGE_CTL");
				ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				log.debug("Operation: DELETE");
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					CollegeBean deletebean = new CollegeBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						log.info("College deleted successfully, id=" + id);
						ServletUtility.setSuccessMessage("Data is deleted successfully", request);
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
	 * Returns view associated with college list page.
	 * 
	 * @return view path
	 */
	@Override
	protected String getView() {
		log.debug("Returning CollegeList view page");
		return ORSView.COLLEGE_LIST_VIEW;
	}
}