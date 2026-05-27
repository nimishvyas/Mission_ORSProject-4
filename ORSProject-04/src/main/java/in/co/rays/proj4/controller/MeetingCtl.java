package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MeetingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;

import in.co.rays.proj4.model.MeetingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;


@WebServlet(name = "MeetingCtl", urlPatterns = { "/ctl/MeetingCtl" })
public class MeetingCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(MeetingCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MeetingCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("hostName"))) {
			request.setAttribute("hostName", PropertyReader.getValue("error.require", "Host Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("hostName"))) {
			request.setAttribute("hostName", "Invalid Host Name");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("platform"))) {
			request.setAttribute("platform", PropertyReader.getValue("error.require", "Platform"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("platform"))) {
			request.setAttribute("platform", "Invalid Platform");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("duration"))) {
			request.setAttribute("duration", "Invalid duration");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("participants"))) {
			request.setAttribute("participants", PropertyReader.getValue("error.require", "Participants"));
			pass = false;
		}else if (!DataValidator.isInteger(request.getParameter("participants"))) {
			request.setAttribute("participants", "Invalid Participants");
			pass = false;
		}

		log.debug("Validation result: " + pass);
		return pass;
	}

	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("MeetingCtl populateBean() called");

		MeetingBean bean = new MeetingBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setHostName(DataUtility.getString(request.getParameter("hostName")));
		bean.setPlatform(DataUtility.getString(request.getParameter("platform")));
		bean.setDuration(DataUtility.getInt(request.getParameter("duration")));
		bean.setParticipants(DataUtility.getInt(request.getParameter("participants")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handle GET request
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("MeetingCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		MeetingModel model = new MeetingModel();

		if (id > 0) {
			try {
				MeetingBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Loaded MeetingBean for id=" + id);
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

		log.info("MeetingCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		MeetingModel model = new MeetingModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			MeetingBean bean = (MeetingBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Meeting added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Meeting already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in SAVE", e);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			MeetingBean bean = (MeetingBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Meeting updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Host name already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in UPDATE", e);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MEETING_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MEETING_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Return View
	 */
	@Override
	protected String getView() {
		return ORSView.MEETING_VIEW;
	}
}