package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.VersionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EventModel;
import in.co.rays.proj4.model.VersionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * VersionCtl handles CRUD operations for Event entity.
 * 
 * Features: - Validation of event fields - Add / Update event - Fetch event by
 * ID - Navigation handling
 * 
 * Flow: - GET → Load data (if id present) - POST → Perform Save, Update,
 * Cancel, Reset
 * 
 * URL: /ctl/VersionCtl
 */
@WebServlet(name = "VersionCtl", urlPatterns = { "/ctl/VersionCtl" })
public class VersionCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(VersionCtl.class);

	/**
	 * Validate Event Form
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("VersionCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("versionCode"))) {
			request.setAttribute("versionCode", PropertyReader.getValue("error.require", "Version Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("versionName"))) {
			request.setAttribute("versionName", PropertyReader.getValue("error.require", "Version Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("versionName"))) {
			request.setAttribute("versionName", "Invalid Event Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("releaseDate"))) {
			request.setAttribute("releaseDate", PropertyReader.getValue("error.require", "releaseDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("releaseDate"))) {
			request.setAttribute("releaseDate", PropertyReader.getValue("error.date", "Release Date"));
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
	 * Populate VersionBean from request
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("VersionCtl populateBean() called");

		VersionBean bean = new VersionBean();

		bean.setVersionId(DataUtility.getLong(request.getParameter("id")));
		bean.setVersionCode(DataUtility.getString(request.getParameter("versionCode")));
		bean.setVersionName(DataUtility.getString(request.getParameter("versionName")));
		bean.setReleaseDate(DataUtility.getDate(request.getParameter("releaseDate")));
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

		log.info("VersionCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		VersionModel model = new VersionModel();

		if (id > 0) {
			try {
				VersionBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Loaded VersionBean for id=" + id);
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

		log.info("VersionCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		VersionModel model = new VersionModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			VersionBean bean = (VersionBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Version added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Version already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in SAVE", e);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			VersionBean bean = (VersionBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Version updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Version already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in UPDATE", e);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VERSION_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VERSION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Return View
	 */
	@Override
	protected String getView() {
		return ORSView.VERSION_VIEW;
	}
}