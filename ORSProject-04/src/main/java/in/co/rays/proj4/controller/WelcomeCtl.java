package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.util.ServletUtility;

/**
 * WelcomeCtl handles the display of the welcome page.
 * 
 * It provides:
 * - Forwarding request to welcome view
 * - Entry point after successful login or navigation
 * 
 * Flow:
 * - GET request → forwards user to welcome page
 * 
 * This controller extends BaseCtl to maintain consistency
 * with the application's controller structure.
 * 
 * URL Mapping: /WelcomeCtl
 * 
 * @author Nimish
 */
@WebServlet("/WelcomeCtl")
public class WelcomeCtl extends BaseCtl {

    /** Log4j Logger */
    private static final Logger log = Logger.getLogger(WelcomeCtl.class);

    /**
     * Handles GET request.
     * 
     * Forwards request to welcome view page.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("WelcomeCtl doGet() started");
        
        ServletUtility.forward(getView(), request, response);
        log.info("doGet() forwarded to view: " + getView());
    }

    /**
     * Returns view associated with welcome page.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        log.debug("Returning Welcome view page");
        return ORSView.WELCOME_VIEW;
    }
}