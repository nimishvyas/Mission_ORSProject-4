package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EventModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * EventCtl handles CRUD operations for Event entity.
 * 
 * Features: - Validation of event fields - Add / Update event - Fetch event by
 * ID - Navigation handling
 * 
 * Flow: - GET → Load data (if id present) - POST → Perform Save, Update,
 * Cancel, Reset
 * 
 * URL: /ctl/EventCtl
 */
@WebServlet(name = "EventCtl", urlPatterns = { "/ctl/EventCtl" })
public class EventCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(EventCtl.class);

	/**
	 * Validate Event Form
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("EventCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("eventCode"))) {
			request.setAttribute("eventCode", PropertyReader.getValue("error.require", "Event Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("eventName"))) {
			request.setAttribute("eventName", PropertyReader.getValue("error.require", "Event Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("eventName"))) {
			request.setAttribute("eventName", "Invalid Event Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("eventTime"))) {
			request.setAttribute("eventTime", PropertyReader.getValue("error.require", "Event Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("eventTime"))) {
			request.setAttribute("eventTime", PropertyReader.getValue("error.date", "Event Date"));
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
	 * Populate EventBean from request
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("EventCtl populateBean() called");

		EventBean bean = new EventBean();

		bean.setEventId(DataUtility.getLong(request.getParameter("id")));
		bean.setEventCode(DataUtility.getString(request.getParameter("eventCode")));
		bean.setEventName(DataUtility.getString(request.getParameter("eventName")));
		bean.setEventTime(DataUtility.getDate(request.getParameter("eventTime")));
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

		log.info("EventCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		EventModel model = new EventModel();

		if (id > 0) {
			try {
				EventBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Loaded EventBean for id=" + id);
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

		log.info("EventCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		EventModel model = new EventModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			EventBean bean = (EventBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Event added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Event already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in SAVE", e);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			EventBean bean = (EventBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Event updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Event already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in UPDATE", e);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Return View
	 */
	@Override
	protected String getView() {
		return ORSView.EVENT_VIEW;
	}
}