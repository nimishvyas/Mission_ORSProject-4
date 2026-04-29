package in.co.rays.proj4.bean;

/**
 * SubjectBean is a JavaBean class that represents a Subject entity. It is used
 * to handle records of the subject table.
 * 
 * This class contains subject related attributes such as name, description and
 * the course associated with the subject.
 * 
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 * 
 * @author Nimish
 */
public class SubjectBean extends BaseBean {

	/** Name of the subject. */
	private String name;

	/** Course ID of the course to which this subject belongs. */
	private long courseId;

	/** Name of the course to which this subject belongs. */
	private String courseName;

	/** Description of the subject explaining what the subject is about. */
	private String description;

	/**
	 * Returns the name of the subject.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the subject.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the course ID of the course to which this subject belongs.
	 * 
	 * @return courseId
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the course ID of the course to which this subject belongs.
	 * 
	 * @param courseId the courseId to set
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Returns the name of the course to which this subject belongs.
	 * 
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the name of the course to which this subject belongs.
	 * 
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns the description of the subject.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the subject.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the name of the subject as the display value.
	 * This method is used to display the subject name in dropdown lists or UI components.
	 * 
	 * @return name as display value
	 */
	@Override
	public String getValue() {
		return name;
	}
}