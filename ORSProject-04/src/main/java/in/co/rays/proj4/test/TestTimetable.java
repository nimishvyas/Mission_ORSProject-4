package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.model.TimetableModel;

public class TestTimetable {

	public static void main(String[] args) throws Exception {

		// testAdd();
		 //testUpdate();
		testDelete();
		// testFindByPk();
		// testCheckByCourseName();
		 //testCheckBySubjectName();
		//testCheckBySemester();
		// testCheckByExamTime();
		// testSearch();
	}

	private static void testAdd() throws Exception {

		TimetableModel model = new TimetableModel();

		TimetableBean bean = new TimetableBean();

		bean.setSemester("1st");
		bean.setDescription("Mid Term");
		bean.setExamDate(new Date());
		bean.setExamTime("10 AM");

		bean.setCourseId(1);
		bean.setCourseName("B.Tech");

		bean.setSubjectId(1);
		bean.setSubjectName("Java");

		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");

		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("Added PK = " + pk);
	}

	private static void testUpdate() throws Exception {

		TimetableModel model = new TimetableModel();

		TimetableBean bean = model.findByPk(1);

		bean.setSemester("2nd");
		bean.setDescription("Final Exam");
		bean.setExamDate(new Date());
		bean.setExamTime("2 PM");

		bean.setCourseId(1);
		bean.setCourseName("B.Tech");

		bean.setSubjectId(1);
		bean.setSubjectName("Advanced Java");

		bean.setModifiedBy("admin");
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		model.update(bean);

		System.out.println("Updated");
	}

	private static void testDelete() throws Exception {

		TimetableModel model = new TimetableModel();

		TimetableBean bean = new TimetableBean();
		bean.setId(1);

		model.delete(bean);

		System.out.println("Deleted");
	}

	private static void testFindByPk() throws Exception {

		TimetableModel model = new TimetableModel();

		TimetableBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println(bean.getId() + " " + bean.getCourseName());
		} else {
			System.out.println("No record found");
		}
	}

	private static void testCheckByCourseName() throws Exception {

		TimetableModel model = new TimetableModel();

		TimetableBean bean = model.checkByCourseName(1L, new Date());

		if (bean != null) {
			System.out.println("Found: " + bean.getCourseName());
		} else {
			System.out.println("Not found");
		}
	}

	private static void testCheckBySubjectName() throws Exception {

		TimetableModel model = new TimetableModel();

		TimetableBean bean = model.checkBySubjectName(1L, 1L, new Date());

		if (bean != null) {
			System.out.println("Found: " + bean.getSubjectName());
		} else {
			System.out.println("Not found");
		}
	}

	private static void testCheckBySemester() throws Exception {

		TimetableModel model = new TimetableModel();

		TimetableBean bean = model.checkBySemester(1L, 1L, "1st", new Date());

		if (bean != null) {
			System.out.println("Found semester");
		} else {
			System.out.println("Not found");
		}
	}

	private static void testCheckByExamTime() throws Exception {

		TimetableModel model = new TimetableModel();

		TimetableBean bean = model.checkByExamTime(1L, 1L, "1st", new Date(), "10 AM", "Mid Term");

		if (bean != null) {
			System.out.println("Found exam time");
		} else {
			System.out.println("Not found");
		}
	}

	private static void testSearch() throws Exception {

		TimetableModel model = new TimetableModel();

		TimetableBean bean = new TimetableBean();

		bean.setCourseId(1);
		bean.setCourseName("B.Tech");
		bean.setSubjectName("Java");
		bean.setSemester("1st");

		List<TimetableBean> list = model.search(bean, 1, 10);

		for (TimetableBean t : list) {
			System.out.println(t.getId() + " " + t.getCourseName());
		}
	}
}
