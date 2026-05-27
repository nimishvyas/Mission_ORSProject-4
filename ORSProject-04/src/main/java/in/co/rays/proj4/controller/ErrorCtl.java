package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.util.ServletUtility;

/**
 * ErrorCtl handles navigation to the error page.
 * 
 * It is responsible for:
 * - Forwarding requests to a centralized error view
 * - Displaying exception or error details set in request scope
 * 
 * Flow:
 * - GET request → forwards to error view
 * - POST request → forwards to error view
 * 
 * This controller is typically invoked when an exception occurs
 * and is handled via ServletUtility.handleException().
 * 
 * URL Mapping: /ErrorCtl
 * 
 * @author Nimish
 */
@WebServlet("/ctl/ErrorCtl")
public class ErrorCtl extends BaseCtl {

    /** Log4j Logger */
    private static final Logger log = Logger.getLogger(ErrorCtl.class);

    /**
     * Handles GET request.
     * 
     * Forwards request to error view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("ErrorCtl doGet() started");
        ServletUtility.forward(getView(), request, response);
        log.info("doGet() forwarded to view: " + getView());
    }

    /**
     * Handles POST request.
     * 
     * Forwards request to error view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("ErrorCtl doPost() started");
        ServletUtility.forward(getView(), request, response);
        log.info("doPost() forwarded to view: " + getView());
    }
    
  

    /**
     * Returns error view path.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        log.debug("Returning Error view page");
        return ORSView.ERROR_VIEW;
    }
}