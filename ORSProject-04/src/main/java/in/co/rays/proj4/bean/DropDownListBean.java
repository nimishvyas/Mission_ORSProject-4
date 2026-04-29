package in.co.rays.proj4.bean;

/**
 * DropDownListBean is an interface that provides a common contract for all bean
 * classes that need to be displayed in dropdown lists or UI components.
 * 
 * Any bean class that implements this interface must provide a unique key and a
 * display value so that it can be used to populate dropdown options in the UI.
 * 
 * @author Nimish
 */
public interface DropDownListBean {

	/**
	 * Returns the unique key for the dropdown option.
	 * Usually the id of the record is returned as the key.
	 * 
	 * @return key as String
	 */
	public String getKey();

	/**
	 * Returns the display value for the dropdown option.
	 * Usually the name or title of the record is returned as the display value.
	 * 
	 * @return value as String
	 */
	public String getValue();
}