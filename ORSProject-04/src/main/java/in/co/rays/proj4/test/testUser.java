package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;

public class testUser {
	public static void main(String[] args) throws Exception {
		
		//testadd();
		//testSearch();
		//testUpdate();
		//testFindByPk();
		//testLogin();
		//testDelete();
		testAuthenticate();
	}

	private static void testadd() throws ApplicationException, DuplicateRecordException, ParseException {
		
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		
		bean.setFirstName("Kapil");
		bean.setLastName("Malviya");
		bean.setLogin("kmalviya30@gmail.com");
		bean.setPassword("kapil@123");
		bean.setDob(sdf.parse("05-07-1997"));
		bean.setRoleId(1L);
		bean.setGender("Male");
		bean.setMobileNo("9407411301");
		bean.setConfirmPassword("kapil@123");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		UserModel model = new UserModel();

		model.add(bean);

	}
	private static void testSearch() {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			List list = new ArrayList();
			// bean.setFirstName("Roshani");
			// bean.setLastName("Bandhiye");
			// bean.setLogin("banti@gmail.com");
			// bean.setId(8L);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
			
				System.out.println(bean.getGender());
			
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testUpdate() throws DuplicateRecordException, ParseException {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			bean = model.findByPk(1L);
			bean.setFirstName("sagar");
			bean.setLastName("goyal");
			bean.setLogin("sagarjain@gmail.com");
			bean.setPassword("sagar123");
			bean.setDob(sdf.parse("05-07-1997"));
			bean.setMobileNo("9940940949");
			bean.setRoleId(1L);
			bean.setGender("Male");
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.update(bean);

			System.out.println("test update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testFindByPk() {
		try {
			UserBean bean = new UserBean();
			long pk = 1L;
			UserModel model = new UserModel();
			bean = model.findByPk(pk);
			if (bean == null) {
				System.out.println("Test find by pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			
			System.out.println(bean.getGender());
			
			

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testLogin() {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			bean = model.findByLogin("sagarjain@gmail.com");
			if (bean == null) {
				System.out.println("Test findByLogin fail");
			}
			System.out.println(bean.getId());

			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			
			System.out.println(bean.getGender());
			
		
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws ApplicationException {
		UserBean bean = new UserBean();
		bean.setId(1);
		UserModel model = new UserModel();
		model.delete(bean);
		
	}
	
	private static void testAuthenticate() throws Exception {

	    UserModel model = new UserModel();

	    UserBean bean = model.authenticate("sagarjain@gmail.com", "sagar123");

	    if (bean != null) {
	        System.out.println("Login Success: " + bean.getFirstName());
	    } else {
	        System.out.println("Invalid login");
	    }
	}

	
}
