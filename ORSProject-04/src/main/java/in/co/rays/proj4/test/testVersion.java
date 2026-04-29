package in.co.rays.proj4.test;

import java.text.SimpleDateFormat;
import java.util.List;

import in.co.rays.proj4.bean.VersionBean;
import in.co.rays.proj4.model.VersionModel;

public class testVersion {

	 public static void main(String[] args) throws Exception {


	        testAdd();
//	        testFindByPk();
//	        testFindByName();
//	        testUpdate();
//	        testSearch();
//	        testDelete();
	    }

	   

	  
	    public static void testAdd() throws Exception {

	        VersionModel model = new VersionModel();
	        VersionBean bean = new VersionBean();

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	        bean.setVersionCode("V001");
	        bean.setVersionName("Version 1"); 
	        bean.setReleaseDate(sdf.parse("2024-01-01"));
	        bean.setStatus("Active");

	        long pk = model.add(bean);

	        System.out.println("Added PK = " + pk);
	    }
	   
	    public static void testFindByPk() throws Exception {

	        VersionModel model = new VersionModel();

	        VersionBean bean = model.findByPk(1);

	        if (bean != null) {
	            System.out.println(bean.getVersionId());
	            System.out.println(bean.getVersionName());
	        } else {
	            System.out.println("Record not found");
	        }
	    }

	 
	    public static void testFindByName() throws Exception {

	        VersionModel model = new VersionModel();

	        VersionBean bean = model.findByname("Version 1");

	        if (bean != null) {
	            System.out.println(bean.getVersionId());
	            System.out.println(bean.getVersionName());
	        } else {
	            System.out.println("Record not found");
	        }
	    }

	    
	    public static void testUpdate() throws Exception {

	        VersionModel model = new VersionModel();
	        VersionBean bean = new VersionBean();

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	        bean.setVersionId(1);
	        bean.setVersionCode("V001-Updated");
	        bean.setVersionName("Version 1 Updated");
	        bean.setReleaseDate(sdf.parse("2024-02-01"));
	        bean.setStatus("Inactive");

	        model.update(bean);

	        System.out.println("Record Updated");
	    }

	    
	    public static void testDelete() throws Exception {

	        VersionModel model = new VersionModel();
	        VersionBean bean = new VersionBean();

	        bean.setVersionId(1);

	        model.delete(bean);

	        System.out.println("Record Deleted");
	    }

	
	    public static void testSearch() throws Exception {

	        VersionModel model = new VersionModel();
	        VersionBean bean = new VersionBean();

	        bean.setVersionName("Version");

	        List<VersionBean> list = model.search(bean);

	        for (VersionBean b : list) {
	            System.out.println(b.getVersionId());
	            System.out.println(b.getVersionName());
	        }
	    }
}
