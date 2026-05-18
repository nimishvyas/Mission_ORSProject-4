package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RevenueBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EventModel;
import in.co.rays.proj4.model.RevenueModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * RevenueCtl handles CRUD operations for Event entity.
 * 
 * Features: - Validation of event fields - Add / Update event - Fetch event by
 * ID - Navigation handling
 * 
 * Flow: - GET → Load data (if id present) - POST → Perform Save, Update,
 * Cancel, Reset
 * 
 * URL: /ctl/RevenueCtl
 */
@WebServlet(name = "RevenueCtl", urlPatterns = { "/ctl/RevenueCtl" })
public class RevenueCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(RevenueCtl.class);

	/**
	 * Validate Event Form
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("RevenueCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("expenseCode"))) {
			request.setAttribute("expenseCode", PropertyReader.getValue("error.require", "Expense Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "Amount"));
			pass = false;
		} else if (!DataValidator.isDouble(request.getParameter("amount"))) {
			request.setAttribute("amount", "Invalid Amount");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("category"))) {
			request.setAttribute("category", PropertyReader.getValue("error.require", "category"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("category"))) {
			request.setAttribute("category", "Invalid Category");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		log.debug("Validation result: " + pass);
		return pass;
	}

	/**
	 * Populate RevenueBean from request
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("RevenueCtl populateBean() called");

		RevenueBean bean = new RevenueBean();

		bean.setExpenseId(DataUtility.getLong(request.getParameter("id")));
		bean.setExpenseCode(DataUtility.getString(request.getParameter("expenseCode")));
		bean.setAmount(DataUtility.getDouble(request.getParameter("amount")));
		bean.setCategory(DataUtility.getString(request.getParameter("category")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handle GET request
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("RevenueCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		RevenueModel model = new RevenueModel();

		if (id > 0) {
			try {
				RevenueBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Loaded RevenueBean for id=" + id);
			} catch (ApplicationException e) {
				log.error("Error in doGet()", e);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handle POST request
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("RevenueCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		RevenueModel model = new RevenueModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			RevenueBean bean = (RevenueBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("revenue added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("revenue already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in SAVE", e);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			RevenueBean bean = (RevenueBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("revenue updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("revenue already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in UPDATE", e);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.REVENUE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.REVENUE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Return View
	 */
	@Override
	protected String getView() {
		return ORSView.REVENUE_VIEW;
	}
}