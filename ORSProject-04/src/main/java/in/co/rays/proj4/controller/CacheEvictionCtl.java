package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CacheEvictionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CacheEvictionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;



@WebServlet(name = "CacheEvictionCtl", urlPatterns = { "/ctl/CacheEvictionCtl" })
public class CacheEvictionCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(CacheEvictionCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("CacheEvictionCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("evictionCode"))) {
			request.setAttribute("evictionCode", PropertyReader.getValue("error.require", "Eviction Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("keyName"))) {
			request.setAttribute("keyName", PropertyReader.getValue("error.require", "Key Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("keyName"))) {
			request.setAttribute("keyName", "Invalid Key Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("evictionTime"))) {
			request.setAttribute("evictionTime", PropertyReader.getValue("error.require", "Eviction Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("evictionTime"))) {
			request.setAttribute("eventTime", PropertyReader.getValue("error.date", "Eviction Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		log.debug("Validation result: " + pass);
		return pass;
	}

	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("CacheEvictionCtl populateBean() called");

		CacheEvictionBean bean = new CacheEvictionBean();

		bean.setEvictionId(DataUtility.getLong(request.getParameter("id")));
		bean.setEvictionCode(DataUtility.getString(request.getParameter("evictionCode")));
		bean.setKeyName(DataUtility.getString(request.getParameter("keyName")));
		bean.setEvictionTime(DataUtility.getDate(request.getParameter("evictionTime")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("CacheEvictionCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		CacheEvictionModel model = new CacheEvictionModel();

		if (id > 0) {
			try {
				CacheEvictionBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Loaded CacheEvictionBean for id=" + id);
			} catch (ApplicationException e) {
				log.error("Error in doGet()", e);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("CacheEvictionCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CacheEvictionModel model = new CacheEvictionModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CacheEvictionBean bean = (CacheEvictionBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Eviction added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(bean.getKeyName() + " Eviction already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in SAVE", e);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			CacheEvictionBean bean = (CacheEvictionBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Eviction updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(bean.getKeyName() + " Eviction already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in UPDATE", e);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVICTION_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVICTION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	
	@Override
	protected String getView() {
		return ORSView.EVICTION_VIEW;
	}
}