package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.rays.proj4.util.ServletUtility;

/**
 * FrontController acts as a filter to intercept all incoming requests
 * for protected resources under "/ctl/*" and "/doc/*".
 * 
 * It checks whether the user session is valid before allowing access.
 * If the session is expired or not available, the user is redirected
 * to the login page with an appropriate error message.
 * 
 * This helps in implementing centralized authentication control.
 * 
 * @author Nimish
 */
@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontController implements Filter {

	/** Logger instance for logging filter activities */
	private static final Logger log = Logger.getLogger(FrontController.class);

	/**
	 * This method is called for every request that matches the filter pattern.
	 * It checks whether the user session exists or not.
	 * 
	 * If session is null:
	 * - Sets error message
	 * - Stores requested URI
	 * - Redirects to login page
	 * 
	 * If session is valid:
	 * - Passes request to next filter or resource
	 * 
	 * @param req  the ServletRequest object
	 * @param resp the ServletResponse object
	 * @param chain the FilterChain to pass control to next filter/resource
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		log.info("FrontController doFilter() called");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		// Get current session
		HttpSession session = request.getSession();

		// Check if user is logged in
		if (session.getAttribute("user") == null) {

			// Set error message
			ServletUtility.setErrorMessage(
					" Your Session has been Expired... Please Login Again", request);

			// Store requested URI for redirect after login
			String str = request.getRequestURI();
			request.setAttribute("uri", str);

			log.warn("Session expired, redirecting to login. URI=" + str);

			// Forward to login page
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
			return;

		} else {

			log.debug("User session valid, continuing filter chain");

			// Continue request processing
			chain.doFilter(req, resp);
		}
	}

	/**
	 * Initialization method of the filter.
	 * Called only once when the filter is instantiated.
	 * 
	 * @param conf FilterConfig object
	 * @throws ServletException
	 */
	public void init(FilterConfig conf) throws ServletException {
		log.info("FrontController init() called");
	}

	/**
	 * Cleanup method of the filter.
	 * Called once when the filter is destroyed.
	 */
	public void destroy() {
		log.info("FrontController destroy() called");
	}
}