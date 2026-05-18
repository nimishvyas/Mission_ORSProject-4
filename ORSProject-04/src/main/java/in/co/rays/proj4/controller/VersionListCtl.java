package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.VersionBean;
import in.co.rays.proj4.bean.VersionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.VersionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "VersionListCtl", urlPatterns = { "/ctl/VersionListCtl" })
public class VersionListCtl extends BaseCtl {

	private static final Logger log = Logger.getLogger(VersionListCtl.class);

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("VersionCtl populateBean() called");

		VersionBean bean = new VersionBean();

		
		bean.setVersionCode(DataUtility.getString(request.getParameter("versionCode")));
		bean.setVersionName(DataUtility.getString(request.getParameter("versionName")));
		
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		VersionBean bean = (VersionBean) populateBean(request);
		VersionModel model = new VersionModel();

		try {

			List<VersionBean> list = model.search(bean, pageNo, pageSize);
			List<VersionBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<VersionBean> list = null;
		List<VersionBean> next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0)
				? DataUtility.getInt(PropertyReader.getValue("page.size"))
				: pageSize;

		VersionBean bean = (VersionBean) populateBean(request);
		VersionModel model = new VersionModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
					|| OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.VERSION_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {
						VersionBean deleteBean = new VersionBean();
						deleteBean.setVersionId(DataUtility.getLong(id));
						model.delete(deleteBean);
					}

					ServletUtility.setSuccessMessage("Version deleted successfully", request);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.VERSION_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getView() {
		return ORSView.VERSION_LIST_VIEW;
	}
}