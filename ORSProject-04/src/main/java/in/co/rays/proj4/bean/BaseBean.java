package in.co.rays.proj4.bean;

import java.sql.Timestamp;

/**
 * BaseBean is an abstract class that works as a common base for all bean
 * classes in the project. It contains shared audit fields like id, createdBy,
 * modifiedBy, createdDatetime and modifiedDatetime so that every bean class
 * does not have to define these fields separately. It also implements
 * DropDownListBean interface so any bean extending this class can be used in
 * dropdown lists.
 * 
 * @author Nimish
 */
public abstract class BaseBean implements DropDownListBean {

	/** Unique identifier of the record, works as a primary key. */
	protected long id;

	/** Stores the username of the person who created this record. */
	protected String createdBy;

	/** Stores the username of the person who last modified this record. */
	protected String modifiedBy;

	/** Stores the date and time when this record was created. */
	protected Timestamp createdDatetime;

	/** Stores the date and time when this record was last modified. */
	protected Timestamp modifiedDatetime;

	/**
	 * Returns the unique id of the record.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the unique id of the record.
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the username of the person who created this record.
	 * 
	 * @return createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the username of the person who created this record.
	 * 
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Returns the username of the person who last modified this record.
	 * 
	 * @return modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the username of the person who last modified this record.
	 * 
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Returns the date and time when this record was created.
	 * 
	 * @return createdDatetime
	 */
	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	/**
	 * Sets the date and time when this record was created.
	 * 
	 * @param createdDatetime the createdDatetime to set
	 */
	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	/**
	 * Returns the date and time when this record was last modified.
	 * 
	 * @return modifiedDatetime
	 */
	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	/**
	 * Sets the date and time when this record was last modified.
	 * 
	 * @param modifiedDatetime the modifiedDatetime to set
	 */
	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	/**
	 * Returns the id of the record as a String. This method is required by the
	 * DropDownListBean interface so that the bean can be used as a dropdown option
	 * with a unique key.
	 * 
	 * @return id as String
	 */
	@Override
	public String getKey() {
		return id + "";
	}
}