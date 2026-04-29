package in.co.rays.proj4.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.controller.BaseCtl;
import in.co.rays.proj4.controller.ORSView;

/**
 * ServletUtility is a helper class that provides common utility methods
 * for handling HTTP requests and responses in a servlet-based application.
 * 
 * It simplifies:
 * - Page navigation (forward/redirect)
 * - Setting and retrieving messages (error/success)
 * - Handling request attributes (bean, list, pagination)
 * - Exception handling
 * 
 * This class is widely used in controller classes to reduce repetitive code.
 * 
 * @author Nimish
 */
public class ServletUtility {

    /**
     * Forwards the request to a specified page.
     * 
     * @param page     destination page (JSP/Servlet)
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws IOException
     * @throws ServletException
     */
    public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    /**
     * Redirects the response to a specified page.
     * 
     * @param page     destination URL
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws IOException
     * @throws ServletException
     */
    public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.sendRedirect(page);
    }

    /**
     * Retrieves an error message using a custom property key.
     * 
     * @param property attribute key
     * @param request  HttpServletRequest object
     * @return error message or empty string if not found
     */
    public static String getErrorMessage(String property, HttpServletRequest request) {

        String val = (String) request.getAttribute(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Retrieves a general message using a custom property key.
     * 
     * @param property attribute key
     * @param request  HttpServletRequest object
     * @return message or empty string if not found
     */
    public static String getMessage(String property, HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets an error message in request scope.
     * 
     * @param msg     error message
     * @param request HttpServletRequest object
     */
    public static void setErrorMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_ERROR, msg);
    }

    /**
     * Retrieves error message from request scope.
     * 
     * @param request HttpServletRequest object
     * @return error message or empty string if not found
     */
    public static String getErrorMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets a success message in request scope.
     * 
     * @param msg     success message
     * @param request HttpServletRequest object
     */
    public static void setSuccessMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
    }

    /**
     * Retrieves success message from request scope.
     * 
     * @param request HttpServletRequest object
     * @return success message or empty string if not found
     */
    public static String getSuccessMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Stores a BaseBean object in request scope.
     * 
     * @param bean    BaseBean object
     * @param request HttpServletRequest object
     */
    public static void setBean(BaseBean bean, HttpServletRequest request) {
        request.setAttribute("bean", bean);
    }

    /**
     * Retrieves BaseBean object from request scope.
     * 
     * @param request HttpServletRequest object
     * @return BaseBean object
     */
    public static BaseBean getBean(HttpServletRequest request) {
        return (BaseBean) request.getAttribute("bean");
    }

    /**
     * Retrieves a request parameter value.
     * 
     * @param property parameter name
     * @param request  HttpServletRequest object
     * @return parameter value or empty string if null
     */
    public static String getParameter(String property, HttpServletRequest request) {
        String val = (String) request.getParameter(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Stores a list in request scope.
     * 
     * @param list    list of objects
     * @param request HttpServletRequest object
     */
    public static void setList(List list, HttpServletRequest request) {
        request.setAttribute("list", list);
    }

    /**
     * Retrieves list from request scope.
     * 
     * @param request HttpServletRequest object
     * @return list of objects
     */
    public static List getList(HttpServletRequest request) {
        return (List) request.getAttribute("list");
    }

    /**
     * Sets current page number in request scope.
     * 
     * @param pageNo  current page number
     * @param request HttpServletRequest object
     */
    public static void setPageNo(int pageNo, HttpServletRequest request) {
        request.setAttribute("pageNo", pageNo);
    }

    /**
     * Retrieves current page number from request scope.
     * 
     * @param request HttpServletRequest object
     * @return page number
     */
    public static int getPageNo(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageNo");
    }

    /**
     * Sets page size in request scope.
     * 
     * @param pageSize number of records per page
     * @param request  HttpServletRequest object
     */
    public static void setPageSize(int pageSize, HttpServletRequest request) {
        request.setAttribute("pageSize", pageSize);
    }

    /**
     * Retrieves page size from request scope.
     * 
     * @param request HttpServletRequest object
     * @return page size
     */
    public static int getPageSize(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageSize");
    }

    /**
     * Handles exceptions by setting exception in request scope
     * and redirecting to a centralized error page.
     * 
     * @param e        exception object
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws IOException
     * @throws ServletException
     */
    public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("exception", e);
        response.sendRedirect(ORSView.ERROR_CTL);
    }

}