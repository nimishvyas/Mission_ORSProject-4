package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CloudBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CloudModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;



@WebServlet(name = "CloudCtl", urlPatterns = { "/ctl/CloudCtl" })
public class CloudCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(CloudCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("CloudCtl validate() started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("fileName"))) {
			request.setAttribute("fileName", PropertyReader.getValue("error.require", "File Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("fileName"))) {
			request.setAttribute("fileName", "Invalid File Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("fileSize"))) {
			request.setAttribute("fileSize", PropertyReader.getValue("error.require", "File Size"));
			pass = false;
		} else if (!DataValidator.isDouble(request.getParameter("fileSize"))) {
			request.setAttribute("fileSize", "Invalid file Size");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("uploadDate"))) {
			request.setAttribute("uploadDate", PropertyReader.getValue("error.require", "uploadDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("uploadDate"))) {
			request.setAttribute("uploadDate", PropertyReader.getValue("error.date", "Upload Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "userName"));
			pass = false;
		}

		log.debug("Validation result: " + pass);
		return pass;
	}

	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("CloudCtl populateBean() called");

		CloudBean bean = new CloudBean();

		bean.setFileId(DataUtility.getInt(request.getParameter("id")));
		bean.setFileName(DataUtility.getString(request.getParameter("fileName")));
		bean.setFileSize(DataUtility.getDouble(request.getParameter("fileSize")));
		bean.setUploadDate(DataUtility.getDate(request.getParameter("uploadDate")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));

		populateDTO(bean, request);

		return bean;
	}

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("CloudCtl doGet() started");

		long id = DataUtility.getLong(request.getParameter("id"));

		CloudModel model = new CloudModel();

		if (id > 0) {
			try {
				CloudBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				log.info("Loaded CloudBean for id=" + id);
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

		log.info("CloudCtl doPost() started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CloudModel model = new CloudModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CloudBean bean = (CloudBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("File added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("File already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in SAVE", e);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			CloudBean bean = (CloudBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("File updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("File already exists", request);

			} catch (ApplicationException e) {
				log.error("Error in UPDATE", e);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CLOUD_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CLOUD_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	
	@Override
	protected String getView() {
		return ORSView.CLOUD_VIEW;
	}
}