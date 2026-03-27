package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.List;

import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.model.SubjectModel;

public class TestSubject {

	public static void main(String[] args) throws Exception {

		 //testAdd();
		// testUpdate();
		 testDelete();
		//testFindByPk();
		// testFindByName();
		// testSearch();
		 //testList();
	}

	private static void testAdd() throws Exception {

		SubjectModel model = new SubjectModel();

		SubjectBean bean = new SubjectBean();
		bean.setName("Java");
		bean.setCourseId(1); // MUST exist in st_course
	    bean.setCourseName("B.Tech");  

		bean.setDescription("Core Java");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("Subject Added with PK = " + pk);
	}

	private static void testUpdate() throws Exception {

		SubjectModel model = new SubjectModel();

		SubjectBean bean = model.findByPk(1);

		bean.setName("Advanced Java");

		model.update(bean);

		System.out.println("Subject Updated");
	}

	private static void testDelete() throws Exception {

		SubjectModel model = new SubjectModel();

		SubjectBean bean = new SubjectBean();
		bean.setId(1);

		model.delete(bean);

		System.out.println("Subject Deleted");
	}

	private static void testFindByPk() throws Exception {

		SubjectModel model = new SubjectModel();

		SubjectBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println(bean.getName());
		} else {
			System.out.println("No record found");
		}
	}

	private static void testFindByName() throws Exception {

		SubjectModel model = new SubjectModel();

		SubjectBean bean = model.findByName("Java");

		if (bean != null) {
			System.out.println(bean.getId() + " " + bean.getName());
		} else {
			System.out.println("Subject not found");
		}
	}

	private static void testSearch() throws Exception {

		SubjectModel model = new SubjectModel();

		SubjectBean bean = new SubjectBean();
		bean.setName("Java");

		List<SubjectBean> list = model.search(bean, 1, 10);

		for (SubjectBean s : list) {
			System.out.println(s.getId() + " " + s.getName());
		}
	}

	private static void testList() throws Exception {

		SubjectModel model = new SubjectModel();

		List<SubjectBean> list = model.list();

		for (SubjectBean s : list) {
			System.out.println(s.getId() + " " + s.getName());
		}
	}
}
