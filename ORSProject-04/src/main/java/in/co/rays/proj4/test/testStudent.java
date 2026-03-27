package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.model.StudentModel;

public class testStudent {

	public static void main(String[] args) throws Exception {

		//testAdd();
		// testUpdate();
		// testDelete();
		// testFindByPk();
		// testFindByEmail();
		// testSearch();
	}

	private static void testAdd() throws Exception {

		StudentModel model = new StudentModel();

		StudentBean bean = new StudentBean();
		bean.setFirstName("Rahul");
		bean.setLastName("Sharma");
		bean.setDob(new java.util.Date());
		bean.setGender("Male");
		bean.setMobileNo("9999999999");
		bean.setEmail("rahul@gmail.com");
		bean.setCollegeId(1);
		bean.setCollegeName("Rays");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("Student Added with PK = " + pk);
	}

	private static void testUpdate() throws Exception {

		StudentModel model = new StudentModel();

		StudentBean bean = model.findByPk(1);

		bean.setFirstName("UpdatedStudent");

		model.update(bean);

		System.out.println("Student Updated");
	}

	private static void testDelete() throws Exception {

		StudentModel model = new StudentModel();

		StudentBean bean = new StudentBean();
		bean.setId(1);

		model.delete(bean);

		System.out.println("Student Deleted");
	}

	private static void testFindByPk() throws Exception {

		StudentModel model = new StudentModel();

		StudentBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println(bean.getFirstName());
		} else {
			System.out.println("No record found");
		}
	}

	private static void testFindByEmail() throws Exception {

		StudentModel model = new StudentModel();

		StudentBean bean = model.findByEmail("rahul@gmail.com");

		if (bean != null) {
			System.out.println(bean.getId() + " " + bean.getFirstName());
		} else {
			System.out.println("Student not found");
		}
	}

	private static void testSearch() throws Exception {

		StudentModel model = new StudentModel();

		StudentBean bean = new StudentBean();
		bean.setFirstName("Rahul");

		List<StudentBean> list = model.search(bean, 1, 10);

		for (StudentBean s : list) {
			System.out.println(s.getId() + " " + s.getFirstName());
		}
	}
}
