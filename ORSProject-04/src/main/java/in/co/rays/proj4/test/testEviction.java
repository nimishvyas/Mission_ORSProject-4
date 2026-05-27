package in.co.rays.proj4.test;

import java.text.SimpleDateFormat;
import java.util.List;

import in.co.rays.proj4.bean.CacheEvictionBean;
import in.co.rays.proj4.model.CacheEvictionModel;

public class testEviction {

    public static void main(String[] args) throws Exception {

//        testNextPk();
 //      testAdd();
 //      testFindByPk();
 //       testFindByName();
 //      testUpdate();
 //      testSearch();
        testDelete();
    }

    
    public static void testNextPk() throws Exception {

        CacheEvictionModel model = new CacheEvictionModel();
        int pk = model.nextPk();

        System.out.println("Next PK = " + pk);
    }

    
    public static void testAdd() throws Exception {

        CacheEvictionModel model = new CacheEvictionModel();
        CacheEvictionBean bean = new CacheEvictionBean();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        bean.setEvictionCode("EVT001");
        bean.setKeyName("CacheKey1");
        bean.setEvictionTime(sdf.parse("2024-01-01"));
        bean.setStatus("Active");

        long pk = model.add(bean);

        System.out.println("Added PK = " + pk);
    }

  
    public static void testFindByPk() throws Exception {

        CacheEvictionModel model = new CacheEvictionModel();

        CacheEvictionBean bean = model.findByPk(1);

        if (bean != null) {
            System.out.println(bean.getEvictionId());
            System.out.println(bean.getKeyName());
        } else {
            System.out.println("Record not found");
        }
    }


    public static void testFindByName() throws Exception {

        CacheEvictionModel model = new CacheEvictionModel();

        CacheEvictionBean bean = model.findByname("CacheKey1");

        if (bean != null) {
            System.out.println(bean.getEvictionId());
            System.out.println(bean.getKeyName());
        } else {
            System.out.println("Record not found");
        }
    }

 
    public static void testUpdate() throws Exception {

        CacheEvictionModel model = new CacheEvictionModel();
        CacheEvictionBean bean = new CacheEvictionBean();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        bean.setEvictionId(1);
        bean.setEvictionCode("EVT001-UPD");
        bean.setKeyName("CacheKeyUpdated");
        bean.setEvictionTime(sdf.parse("2024-02-01"));
        bean.setStatus("Inactive");

        model.update(bean);

        System.out.println("Record Updated");
    }

  
    public static void testDelete() throws Exception {

        CacheEvictionModel model = new CacheEvictionModel();

        CacheEvictionBean bean = new CacheEvictionBean();
        bean.setEvictionId(1);

        model.delete(bean);

        System.out.println("Record Deleted");
    }

 
    public static void testSearch() throws Exception {

        CacheEvictionModel model = new CacheEvictionModel();

        CacheEvictionBean bean = new CacheEvictionBean();
        bean.setKeyName("Cache");

        List<CacheEvictionBean> list = model.search(bean, 1, 10);

        for (CacheEvictionBean b : list) {
            System.out.println(b.getEvictionId());
            System.out.println(b.getKeyName());
            System.out.println(b.getStatus());
        }
    }
}