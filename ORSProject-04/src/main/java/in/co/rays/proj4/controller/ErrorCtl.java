package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        ServletUtility.forward(getView(), request, response);
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

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns error view path.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        return ORSView.ERROR_VIEW;
    }
}