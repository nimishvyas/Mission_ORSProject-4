package in.co.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        System.out.println("in welcomeCtl doGet method");
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns view associated with welcome page.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        return ORSView.WELCOME_VIEW;
    }

}