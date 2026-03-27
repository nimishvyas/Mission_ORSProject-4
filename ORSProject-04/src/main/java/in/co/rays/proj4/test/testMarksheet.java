package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.model.MarksheetModel;

public class testMarksheet {

	public static void main(String[] args) throws Exception {

		//testAdd();
		//testUpdate();
		//testDelete();
		//testFindByPk();
		//testFindByRollNo();
		//testSearch();
		//testMeritList();
	}
	
	private static void testAdd() throws Exception {

	    MarksheetModel model = new MarksheetModel();

	    MarksheetBean bean = new MarksheetBean();
	    bean.setRollNo("R001");
	    bean.setStudentId(1);   // MUST exist in student table
	    bean.setPhysics(70);
	    bean.setChemistry(65);
	    bean.setMaths(80);
	    bean.setCreatedBy("admin");
	    bean.setModifiedBy("admin");
	    bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
	    bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

	    long pk = model.add(bean);

	    System.out.println("Marksheet Added with PK = " + pk);
	}
	
	private static void testUpdate() throws Exception {

	    MarksheetModel model = new MarksheetModel();

	    MarksheetBean bean = model.findByPk(1);

	    bean.setName("Test");

	    model.update(bean);

	    System.out.println("Marksheet Updated");
	}
	
	private static void testDelete() throws Exception {

	    MarksheetModel model = new MarksheetModel();

	    MarksheetBean bean = new MarksheetBean();
	    bean.setId(1);

	    model.delete(bean);

	    System.out.println("Marksheet Deleted");
	}
	
	private static void testFindByPk() throws Exception {

	    MarksheetModel model = new MarksheetModel();

	    MarksheetBean bean = model.findByPk(1);

	    if (bean != null) {
	        System.out.println(bean.getName());
	    } else {
	        System.out.println("No record found");
	    }
	}
	
	private static void testFindByRollNo() throws Exception {

	    MarksheetModel model = new MarksheetModel();

	    MarksheetBean bean = model.findByRollNo("R001");

	    if (bean != null) {
	        System.out.println(bean.getId() + " " + bean.getName());
	    } else {
	        System.out.println("Not found");
	    }
	}
	
	private static void testSearch() throws Exception {

	    MarksheetModel model = new MarksheetModel();

	    MarksheetBean bean = new MarksheetBean();
	    bean.setName("Test");

	    List<MarksheetBean> list = model.search(bean, 1, 10);

	    for (MarksheetBean m : list) {
	        System.out.println(m.getId() + " " + m.getName());
	    }
	}
	
	private static void testMeritList() throws Exception {

	    MarksheetModel model = new MarksheetModel();

	    List<MarksheetBean> list = model.getMeritList(1, 10);

	    for (MarksheetBean m : list) {
	        int total = m.getPhysics() + m.getChemistry() + m.getMaths();
	        System.out.println(m.getRollNo() + " " + total);
	    }
	}
}
