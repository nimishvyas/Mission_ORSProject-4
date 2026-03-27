package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.model.CollegeModel;

public class testCollege {

	public static void main(String[] args) throws Exception {
		//testAdd();
		// testUpdate();
		// testDelete();
		 //testFindByPk();
		//testFindByName();
		// testSearch();
	}

	private static void testAdd() throws Exception {

		CollegeModel model = new CollegeModel();

		CollegeBean bean = new CollegeBean();
		bean.setName("Rays College");
		bean.setAddress("Indore");
		bean.setState("MP");
		bean.setCity("Indore");
		bean.setPhoneNo("9999999999");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("College Added with PK = " + pk);
	}

	private static void testUpdate() throws Exception {

		CollegeModel model = new CollegeModel();

		CollegeBean bean = model.findByPk(1);

		bean.setName("Updated College");

		model.update(bean);

		System.out.println("College Updated");
	}

	private static void testDelete() throws Exception {

		CollegeModel model = new CollegeModel();

		CollegeBean bean = new CollegeBean();
		bean.setId(1);

		model.delete(bean);

		System.out.println("College Deleted");
	}

	private static void testFindByPk() throws Exception {

		CollegeModel model = new CollegeModel();

		CollegeBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
		} else {
			System.out.println("No record found");
		}
	}

	private static void testFindByName() throws Exception {

		CollegeModel model = new CollegeModel();

		CollegeBean bean = model.findByName("Rays College");

		if (bean != null) {
			System.out.println(bean.getId() + " " + bean.getName());
		} else {
			System.out.println("College not found");
		}
	}

	private static void testSearch() throws Exception {

		CollegeModel model = new CollegeModel();

		CollegeBean bean = new CollegeBean();
		bean.setName("Rays");

		List<CollegeBean> list = model.search(bean, 1, 10);

		for (CollegeBean c : list) {
			System.out.println(c.getId() + " " + c.getName());
		}
		
	}
}
