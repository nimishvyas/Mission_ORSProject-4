package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * GetMarksheetCtl handles retrieval of marksheet details using roll number.
 * 
 * @author Nimish
 */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })
public class GetMarksheetCtl extends BaseCtl {

    /** Logger instance */
    private static final Logger log = Logger.getLogger(GetMarksheetCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("Validate method started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("rollNo"))) {
            request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
            pass = false;
        }

        log.debug("Validate method ended with result: " + pass);
        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("PopulateBean method started");

        MarksheetBean bean = new MarksheetBean();
        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

        log.debug("PopulateBean method ended");
        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doGet method called");
        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("doPost method started");

        String op = DataUtility.getString(request.getParameter("operation"));

        MarksheetModel model = new MarksheetModel();
        MarksheetBean bean = (MarksheetBean) populateBean(request);

        if (OP_GO.equalsIgnoreCase(op)) {
            try {
                log.debug("Fetching marksheet for RollNo: " + bean.getRollNo());

                bean = model.findByRollNo(bean.getRollNo());

                if (bean != null) {
                    log.info("Marksheet found");
                    ServletUtility.setBean(bean, request);
                } else {
                    log.warn("Marksheet not found for RollNo: " + bean.getRollNo());
                    ServletUtility.setErrorMessage("RollNo Does Not exists", request);
                }

            } catch (ApplicationException e) {
                log.error("ApplicationException occurred", e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
        log.info("doPost method ended");
    }

    @Override
    protected String getView() {
        return ORSView.GET_MARKSHEET_VIEW;
    }
}