package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.GamingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;

import in.co.rays.proj4.model.GamingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * GamingCtl handles CRUD operations for Event entity.
 * 
 * Features: - Validation of event fields - Add / Update event - Fetch event by
 * ID - Navigation handling
 * 
 * Flow: - GET → Load data (if id present) - POST → Perform Save, Update,
 * Cancel, Reset
 * 
 * URL: /ctl/GamingCtl
 */
@WebServlet(name = "GamingCtl", urlPatterns = { "/ctl/GamingCtl" })
public class GamingCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(GamingCtl.class);

	/**
	 * Validate Event Form
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("GamingCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("tournamentCode"))) {
			request.setAttribute("tournamentCode", PropertyReader.getValue("error.require", "Tournament Code"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gameName"))) {
			request.setAttribute("gameName", PropertyReader.getValue("error.require", "Game Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("gameName"))) {
			request.setAttribute("gameName", "Invalid Game Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("prizePool"))) {
			request.setAttribute("prizePool", PropertyReader.getValue("error.require", "Prize pool"));
			pass = false;
		} else if (!DataValidator.isDouble(request.getParameter("prizePool"))) {
			request.setAttribute("amount", "Invalid prizePool");
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
	 * Populate GamingBean from request
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("GamingCtl populateBean() called");

		GamingBean bean = new GamingBean();

		bean.setTournamentId(DataUtility.getLong(request.getParameter("id")));
		bean.setTournamentCode(DataUtility.getString(request.getParameter("tournamentCode")));
		bean.setGameName(DataUtility.getString(request.getParameter("gameName")));
		bean.setPrizePool(DataUtility.getDouble(request.getParameter("prizePool")));
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

		log.info("GamingCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		GamingModel model = new GamingModel();

		if (id > 0) {
			try {
				GamingBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Loaded GamingBean for id=" + id);
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

		log.info("GamingCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		GamingModel model = new GamingModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			GamingBean bean = (GamingBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Game added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Game already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in SAVE", e);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			GamingBean bean = (GamingBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("game already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in UPDATE", e);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.GAMING_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.GAMING_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Return View
	 */
	@Override
	protected String getView() {
		return ORSView.GAMING_VIEW;
	}
}