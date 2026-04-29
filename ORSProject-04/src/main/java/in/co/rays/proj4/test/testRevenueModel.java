package in.co.rays.proj4.test;

import java.util.List;

import in.co.rays.proj4.bean.RevenueBean;
import in.co.rays.proj4.model.RevenueModel;

public class testRevenueModel {

    public static void main(String[] args) throws Exception {


//        testAdd();
 //     testFindByPk();
  //     testFindByCode();
 //       testUpdate();
 //       testSearch();
        testDelete();
    }

  
  
   
    public static void testAdd() throws Exception {

        RevenueModel model = new RevenueModel();

        RevenueBean bean = new RevenueBean();

        bean.setExpenseCode("EXP001");
        bean.setAmount(5000);
        bean.setCategory("IT");
        bean.setStatus("Active");

        long pk = model.add(bean);

        System.out.println("Added PK = " + pk);
    }

    
    public static void testFindByPk() throws Exception {

        RevenueModel model = new RevenueModel();

        RevenueBean bean = model.findByPk(1);

        if (bean != null) {
            System.out.println(bean.getExpenseId());
            System.out.println(bean.getExpenseCode());
            System.out.println(bean.getAmount());
        } else {
            System.out.println("Record not found");
        }
    }

   
    public static void testFindByCode() throws Exception {

        RevenueModel model = new RevenueModel();

        RevenueBean bean = model.findByExpenseCode("EXP001");

        if (bean != null) {
            System.out.println(bean.getExpenseId());
            System.out.println(bean.getExpenseCode());
        } else {
            System.out.println("Record not found");
        }
    }

    
    public static void testUpdate() throws Exception {

        RevenueModel model = new RevenueModel();

        RevenueBean bean = new RevenueBean();

        bean.setExpenseId(1);
        bean.setExpenseCode("EXP001-UPD");
        bean.setAmount(8000);
        bean.setCategory("Finance");
        bean.setStatus("Inactive");

        model.update(bean);

        System.out.println("Record Updated");
    }

   
    public static void testDelete() throws Exception {

        RevenueModel model = new RevenueModel();

        RevenueBean bean = new RevenueBean();
        bean.setExpenseId(1);

        model.delete(bean);

        System.out.println("Record Deleted");
    }

   
    public static void testSearch() throws Exception {

        RevenueModel model = new RevenueModel();

        RevenueBean bean = new RevenueBean();
        bean.setExpenseCode("EXP");

        List<RevenueBean> list = model.search(bean);

        for (RevenueBean b : list) {
            System.out.println(b.getExpenseId());
            System.out.println(b.getExpenseCode());
            System.out.println(b.getAmount());
            System.out.println(b.getCategory());
            System.out.println(b.getStatus());
        }
    }
}