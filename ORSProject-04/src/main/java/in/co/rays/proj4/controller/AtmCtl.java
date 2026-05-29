package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.AtmBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;

import in.co.rays.proj4.model.AtmModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;


@WebServlet(name = "AtmCtl", urlPatterns = { "/ctl/AtmCtl" })
public class AtmCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(AtmCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("AtmCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("bankName"))) {
			request.setAttribute("bankName", PropertyReader.getValue("error.require", "Bank Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("bankName"))) {
			request.setAttribute("bankName", "Invalid Bank Name");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("location"))) {
			request.setAttribute("location", PropertyReader.getValue("error.require", "Location"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("location"))) {
			request.setAttribute("location", "Invalid Location");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cashAvailable"))) {
			request.setAttribute("cashAvailable", PropertyReader.getValue("error.require", "Cash Available"));
			pass = false;
		} else if (!DataValidator.isDouble(request.getParameter("cashAvailable"))) {
			request.setAttribute("cashAvailable", "Invalid Cash Available");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("securityCode"))) {
			request.setAttribute("securityCode", PropertyReader.getValue("error.require", "securityCode"));
			pass = false;
		}else if (!DataValidator.isInteger(request.getParameter("securityCode"))) {
			request.setAttribute("securityCode", "Invalid Security Code");
			pass = false;
		}

		log.debug("Validation result: " + pass);
		return pass;
	}

	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("AtmCtl populateBean() called");

		AtmBean bean = new AtmBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setBankName(DataUtility.getString(request.getParameter("bankName")));
		bean.setLocation(DataUtility.getString(request.getParameter("location")));
		bean.setCashAvailable(DataUtility.getDouble(request.getParameter("cashAvailable")));
		bean.setSecurityCode(DataUtility.getInt(request.getParameter("securityCode")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handle GET request
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("AtmCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		AtmModel model = new AtmModel();

		if (id > 0) {
			try {
				AtmBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Loaded AtmBean for id=" + id);
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

		log.info("AtmCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		AtmModel model = new AtmModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			AtmBean bean = (AtmBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Atm System added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Security Code already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in SAVE", e);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			AtmBean bean = (AtmBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Atm System updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Security Code already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in UPDATE", e);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ATM_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ATM_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Return View
	 */
	@Override
	protected String getView() {
		return ORSView.ATM_VIEW;
	}
}