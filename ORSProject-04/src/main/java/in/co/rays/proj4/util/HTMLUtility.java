package in.co.rays.proj4.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import in.co.rays.proj4.bean.DropDownListBean;
import in.co.rays.proj4.model.RoleModel;

/**
 * HTMLUtility is a helper class used to generate HTML components dynamically.
 * 
 * It primarily provides methods to create HTML <select> (dropdown) elements
 * using:
 * - HashMap (key-value pairs)
 * - List of DropDownListBean objects
 * 
 * This utility is commonly used in JSP/Servlet-based applications to populate
 * dropdown fields dynamically from database or static data.
 * 
 * It also includes test methods for demonstration purposes.
 * 
 * @author Nimish
 */
public class HTMLUtility {

    /**
     * Generates an HTML dropdown (select element) using a HashMap.
     * 
     * Each key-value pair in the map represents:
     * - key → option value
     * - value → option display text
     * 
     * The selected value will be marked as selected in the dropdown.
     * 
     * @param name        name attribute of the select element
     * @param selectedVal value to be pre-selected
     * @param map         HashMap containing key-value pairs for dropdown
     * @return HTML string representing the dropdown
     */
    public static String getList(String name, String selectedVal, HashMap<String, String> map) {

        StringBuffer sb = new StringBuffer(
                "<select style=\"width: 169px;text-align-last: center;\"; class='form-control' name='" + name + "'>");

        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        Set<String> keys = map.keySet();
        String val = null;

        for (String key : keys) {
            val = map.get(key);
            if (key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("\n<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Generates an HTML dropdown (select element) using a List of DropDownListBean.
     * 
     * Each DropDownListBean object contains:
     * - key → option value
     * - value → option display text
     * 
     * The selected value will be marked as selected in the dropdown.
     * 
     * @param name        name attribute of the select element
     * @param selectedVal value to be pre-selected
     * @param list        List containing DropDownListBean objects
     * @return HTML string representing the dropdown
     */
    public static String getList(String name, String selectedVal, List list) {

        // Collections.sort(list);

        List<DropDownListBean> dd = (List<DropDownListBean>) list;

        StringBuffer sb = new StringBuffer("<select style=\"width: 169px;text-align-last: center;\"; "
                + "class='form-control' name='" + name + "'>");

        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        String key = null;
        String val = null;

        for (DropDownListBean obj : dd) {
            key = obj.getKey();
            val = obj.getValue();

            if (key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("\n<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Test method to generate dropdown using HashMap.
     * 
     * Creates a sample gender map and prints generated HTML.
     */
    public static void testGetListByMap() {

        HashMap<String, String> map = new HashMap<>();
        map.put("male", "male");
        map.put("female", "female");

        String selectedValue = "male";
        String htmlSelectFromMap = HTMLUtility.getList("gender", selectedValue, map);

        System.out.println(htmlSelectFromMap);
    }

    /**
     * Test method to generate dropdown using List from RoleModel.
     * 
     * Fetches role list from database and prints generated HTML.
     * 
     * @throws Exception if any error occurs while fetching data
     */
    public static void testGetListByList() throws Exception {

        RoleModel model = new RoleModel();

        // UserModel model = new UserModel();

        List list = model.list();

        String selectedValue = "1";

        String htmlSelectFromList = HTMLUtility.getList("role", selectedValue, list);

        System.out.println(htmlSelectFromList);
    }

    /**
     * Main method for testing HTMLUtility methods.
     * 
     * @param args command line arguments
     * @throws Exception if any error occurs
     */
    public static void main(String[] args) throws Exception {

        // testGetListByMap();

//      testGetListByList();

    }

}