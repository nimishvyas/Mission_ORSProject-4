package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.model.FacultyModel;

public class TestFaculty {

	public static void main(String[] args) throws Exception {

		//testAdd();
		// testFindByPk();
		 //testFindByEmail();
		// testSearch();
		// testUpdate();
		 testDelete();
	}

	private static void testAdd() throws Exception {

		FacultyModel model = new FacultyModel();

		FacultyBean bean = new FacultyBean();

		bean.setFirstName("Amit");
		bean.setLastName("Sharma");
		bean.setDob(new Date());
		bean.setGender("Male");
		bean.setMobileNo("9999999999");
		bean.setEmail("amit@gmail.com");

		// 👇 manually set (NO DB dependency)
		bean.setCollegeId(1);
		bean.setCollegeName("Rays College");

		bean.setCourseId(1);
		bean.setCourseName("B.Tech");

		bean.setSubjectId(1);
		bean.setSubjectName("Java");

		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");

		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("Faculty Added PK = " + pk);
	}

	private static void testFindByPk() throws Exception {

		FacultyModel model = new FacultyModel();

		FacultyBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println(bean.getId() + " " + bean.getFirstName());
		} else {
			System.out.println("No record found");
		}
	}

	private static void testFindByEmail() throws Exception {

		FacultyModel model = new FacultyModel();

		FacultyBean bean = model.findByEmail("amit@gmail.com");

		if (bean != null) {
			System.out.println(bean.getId() + " " + bean.getEmail());
		} else {
			System.out.println("Not found");
		}
	}

	private static void testSearch() throws Exception {

		FacultyModel model = new FacultyModel();

		FacultyBean bean = new FacultyBean();

		bean.setFirstName("Amit");
		bean.setCollegeName("Rays College");

		List<FacultyBean> list = model.search(bean, 1, 10);

		for (FacultyBean f : list) {
			System.out.println(f.getId() + " " + f.getFirstName());
		}
	}

	private static void testUpdate() throws Exception {

		FacultyModel model = new FacultyModel();

		FacultyBean bean = model.findByPk(1);

		bean.setFirstName("UpdatedName");
		bean.setLastName("UpdatedLast");

		bean.setCollegeId(1);
		bean.setCollegeName("Updated College");

		bean.setCourseId(1);
		bean.setCourseName("Updated Course");

		bean.setSubjectId(1);
		bean.setSubjectName("Updated Subject");

		bean.setModifiedBy("admin");
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		model.update(bean);

		System.out.println("Updated");
	}

	private static void testDelete() throws Exception {

		FacultyModel model = new FacultyModel();

		FacultyBean bean = new FacultyBean();
		bean.setId(1);

		model.delete(bean);

		System.out.println("Deleted");
	}
}
