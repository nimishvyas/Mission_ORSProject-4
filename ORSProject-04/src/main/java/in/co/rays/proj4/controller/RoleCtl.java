package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/ctl/RoleCtl")
public class RoleCtl extends BaseCtl {

    /** Logger instance */
    private static final Logger log = Logger.getLogger(RoleCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("Validate method started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        log.debug("Validate method ended with result: " + pass);
        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PopulateBean method started");

        RoleBean bean = new RoleBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        log.debug("PopulateBean method ended");
        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet method called");

        long id = DataUtility.getLong(request.getParameter("id"));
        RoleModel model = new RoleModel();

        if (id > 0) {
            try {
                log.debug("Fetching role with id: " + id);
                RoleBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (Exception e) {
                log.error("Error fetching role by id", e);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doPost method started");

        String op = DataUtility.getString(request.getParameter("operation"));

        RoleModel model = new RoleModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            RoleBean bean = (RoleBean) populateBean(request);

            try {
                log.debug("Adding new role");
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully saved", request);

            } catch (DuplicateRecordException e) {
                log.warn("Duplicate role while saving");
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Role already exists", request);

            } catch (ApplicationException e) {
                log.error("ApplicationException during role save", e);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            RoleBean bean = (RoleBean) populateBean(request);

            try {
                log.debug("Updating role id: " + id);

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(op, request);

            } catch (DuplicateRecordException e) {
                log.warn("Duplicate role while updating");
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully updated", request);

            } catch (ApplicationException e) {
                log.error("ApplicationException during role update", e);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            log.info("Cancel operation triggered");
            ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            log.info("Reset operation triggered");
            ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
        log.info("doPost method ended");
    }

    @Override
    protected String getView() {
        return ORSView.ROLE_VIEW;
    }
}