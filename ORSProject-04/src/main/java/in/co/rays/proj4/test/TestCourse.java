package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.model.CourseModel;

public class TestCourse {

	public static void main(String[] args) throws Exception {

		 //testAdd();
		//testUpdate();
		 testDelete();
		// testFindByPk();
		// testFindByName();
		// testSearch();
		// testList();
	}

	private static void testAdd() throws Exception {

		CourseModel model = new CourseModel();

		CourseBean bean = new CourseBean();
		bean.setName("Java");
		bean.setDuration("6 Months");
		bean.setDescription("Core Java Course");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("Course Added with PK = " + pk);
	}

	private static void testUpdate() throws Exception {

		CourseModel model = new CourseModel();

		CourseBean bean = model.findByPk(1);

		bean.setDuration("1 Year");

		model.update(bean);

		System.out.println("Course Updated");
	}

	private static void testDelete() throws Exception {

		CourseModel model = new CourseModel();

		CourseBean bean = new CourseBean();
		bean.setId(1);

		model.delete(bean);

		System.out.println("Course Deleted");
	}

	private static void testFindByPk() throws Exception {

		CourseModel model = new CourseModel();

		CourseBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println(bean.getName());
		} else {
			System.out.println("No record found");
		}
	}

	private static void testFindByName() throws Exception {

		CourseModel model = new CourseModel();

		CourseBean bean = model.findByName("Java");

		if (bean != null) {
			System.out.println(bean.getId() + " " + bean.getName());
		} else {
			System.out.println("Course not found");
		}
	}

	private static void testSearch() throws Exception {

		CourseModel model = new CourseModel();

		CourseBean bean = new CourseBean();
		bean.setName("Java");

		List<CourseBean> list = model.search(bean, 1, 10);

		for (CourseBean c : list) {
			System.out.println(c.getId() + " " + c.getName());
		}
	}

	private static void testList() throws Exception {

		CourseModel model = new CourseModel();

		List<CourseBean> list = model.list();

		for (CourseBean c : list) {
			System.out.println(c.getId() + " " + c.getName());
		}
	}

}
