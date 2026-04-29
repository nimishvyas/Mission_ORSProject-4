package in.co.rays.proj4.bean;

/**
 * MarksheetBean is a JavaBean class that represents a Marksheet entity. It is
 * used to handle records of the marksheet table.
 * 
 * This class contains marksheet related attributes such as roll number, student
 * details and marks obtained in physics, chemistry and maths subjects.
 * 
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 * 
 * @author Nimish
 */
public class MarksheetBean extends BaseBean {

	/** Roll number of the student. */
	private String rollNo;

	/** Student ID of the student associated with this marksheet. */
	private long studentId;

	/** Name of the student. */
	private String name;

	/** Marks obtained by the student in Physics. */
	private Integer physics;

	/** Marks obtained by the student in Chemistry. */
	private Integer chemistry;

	/** Marks obtained by the student in Maths. */
	private Integer maths;

	/**
	 * Returns the roll number of the student.
	 * 
	 * @return rollNo
	 */
	public String getRollNo() {
		return rollNo;
	}

	/**
	 * Sets the roll number of the student.
	 * 
	 * @param rollNo the rollNo to set
	 */
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	/**
	 * Returns the student ID associated with this marksheet.
	 * 
	 * @return studentId
	 */
	public long getStudentId() {
		return studentId;
	}

	/**
	 * Sets the student ID associated with this marksheet.
	 * 
	 * @param studentId the studentId to set
	 */
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	/**
	 * Returns the name of the student.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the student.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the marks obtained by the student in Physics.
	 * 
	 * @return physics
	 */
	public Integer getPhysics() {
		return physics;
	}

	/**
	 * Sets the marks obtained by the student in Physics.
	 * 
	 * @param physics the physics marks to set
	 */
	public void setPhysics(Integer physics) {
		this.physics = physics;
	}

	/**
	 * Returns the marks obtained by the student in Chemistry.
	 * 
	 * @return chemistry
	 */
	public Integer getChemistry() {
		return chemistry;
	}

	/**
	 * Sets the marks obtained by the student in Chemistry.
	 * 
	 * @param chemistry the chemistry marks to set
	 */
	public void setChemistry(Integer chemistry) {
		this.chemistry = chemistry;
	}

	/**
	 * Returns the marks obtained by the student in Maths.
	 * 
	 * @return maths
	 */
	public Integer getMaths() {
		return maths;
	}

	/**
	 * Sets the marks obtained by the student in Maths.
	 * 
	 * @param maths the maths marks to set
	 */
	public void setMaths(Integer maths) {
		this.maths = maths;
	}

	/**
	 * Returns the display value of the marksheet bean.
	 * This method is used to display the marksheet in dropdown lists or UI components.
	 * 
	 * @return null as display value is not defined for marksheet
	 */
	@Override
	public String getValue() {
		return null;
	}
}