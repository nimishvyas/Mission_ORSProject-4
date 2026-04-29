package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * RoleCtl handles CRUD operations for Role entity.
 * 
 * It provides:
 * - Validation of role input fields
 * - Adding new role records
 * - Updating existing role records
 * - Fetching role details by ID
 * - Navigation control for role views
 * 
 * Flow:
 * - GET request → loads role data (if id present) and forwards to view
 * - POST request → performs save, update, cancel, or reset operations
 * 
 * This controller extends BaseCtl to reuse common functionalities
 * like validation, DTO population, and request handling.
 * 
 * URL Mapping: /RoleCtl
 * 
 * @author Nimish
 */
@WebServlet("/ctl/RoleCtl")
public class RoleCtl extends BaseCtl{

    /**
     * Validates input fields for role form.
     * 
     * Validation rules:
     * - Name must not be null and must be valid
     * - Description must not be null
     * 
     * @param request HttpServletRequest object
     * @return true if validation passes, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        
        boolean pass = true;
        
        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Name");
            pass =false;
        }
        
        if(DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }
        
        return pass;
    }
    
    /**
     * Populates RoleBean with request parameters.
     * 
     * Maps:
     * - id
     * - name
     * - description
     * 
     * Also sets audit fields.
     * 
     * @param request HttpServletRequest object
     * @return populated RoleBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        RoleBean bean = new RoleBean();
        
        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        populateDTO(bean, request);
        return bean;
    }
    
    /**
     * Handles GET request.
     * 
     * If id is present, fetches role data and sets it in request scope.
     * Then forwards to role view.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id  = DataUtility.getLong(request.getParameter("id"));
        RoleModel model = new RoleModel();
        
        if (id > 0) {
            try {
                RoleBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }
    
    /**
     * Handles POST request for role operations.
     * 
     * Supported operations:
     * - Save → adds new role record
     * - Update → updates existing role record
     * - Cancel → redirects to role list
     * - Reset → reloads form
     * 
     * Handles success and error scenarios accordingly.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String op = DataUtility.getString(request.getParameter("operation"));
        
        RoleModel model = new RoleModel();
        
        long id = DataUtility.getLong(request.getParameter("id"));
        
        if (OP_SAVE.equalsIgnoreCase(op)) {
            RoleBean bean = (RoleBean) populateBean(request);
            
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully saved", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Role already exists", request);
            }catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            RoleBean bean = (RoleBean) populateBean(request);
            
            try {
                if(id>0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(op, request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully updated", request);
            }catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
            
        }else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
            return;
        }else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns view associated with role form.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        return ORSView.ROLE_VIEW;
    }
}