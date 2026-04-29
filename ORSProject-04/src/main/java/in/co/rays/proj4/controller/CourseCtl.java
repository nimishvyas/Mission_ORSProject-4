package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * CourseCtl handles CRUD operations for Course entity.
 * 
 * It provides:
 * - Validation of course input fields
 * - Adding new course records
 * - Updating existing course records
 * - Fetching course details by ID
 * - Navigation control for course views
 * 
 * Flow:
 * - GET request → loads course data (if id present) and forwards to view
 * - POST request → performs save, update, cancel, or reset operations
 * 
 * This controller extends BaseCtl to reuse common functionalities such as
 * validation, DTO population, and request handling.
 * 
 * URL Mapping: /CourseCtl
 * 
 * @author Nimish
 */
@WebServlet(name = "CourseCtl", urlPatterns = {"/ctl/CourseCtl"})
public class CourseCtl extends BaseCtl{

    /**
     * Validates input fields for course form.
     * 
     * Validation rules:
     * - Name must not be null and must be valid
     * - Duration must not be null
     * - Description must not be null
     * 
     * @param request HttpServletRequest object
     * @return true if validation passes, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        
        boolean pass = true;
        
        if(DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid name");
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("duration"))) {
            request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        return pass;
    }
    
    /**
     * Populates CourseBean with request parameters.
     * 
     * Maps form fields to bean properties and sets audit fields.
     * 
     * @param request HttpServletRequest object
     * @return populated CourseBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        
        CourseBean bean = new CourseBean();
        
        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDuration(DataUtility.getString(request.getParameter("duration")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        
        populateDTO(bean, request);
        
        return bean;
    }

    /**
     * Handles GET request.
     * 
     * If id is present, fetches course data and sets it in request scope.
     * Then forwards to course view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        long id = DataUtility.getLong(request.getParameter("id"));
        
        CourseModel model = new CourseModel();
        
        if (id > 0) {
            
            try {
                CourseBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }
    
    /**
     * Handles POST request for course operations.
     * 
     * Supported operations:
     * - Save → adds new course record
     * - Update → updates existing course record
     * - Cancel → redirects to course list
     * - Reset → reloads course form
     * 
     * Handles success and error scenarios accordingly.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String op = DataUtility.getString(request.getParameter("operation"));
        
        CourseModel model = new CourseModel();
        
        long id = DataUtility.getLong(request.getParameter("id"));
        
        if (OP_SAVE.equalsIgnoreCase(op)) {
            CourseBean bean = (CourseBean) populateBean(request);
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Course added successfully", request);
            } catch (DuplicateRecordException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
            catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if(OP_UPDATE.equalsIgnoreCase(op)) {
            CourseBean bean = (CourseBean) populateBean(request);
            try {
                if(id>0) {
                model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Course Updated Successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Course Already Exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
            return;
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns view associated with course form.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        
        return ORSView.COURSE_VIEW; 
    }
}