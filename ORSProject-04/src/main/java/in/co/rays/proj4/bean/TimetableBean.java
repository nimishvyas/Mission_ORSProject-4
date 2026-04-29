package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * TimetableBean is a JavaBean class that represents a Timetable entity. It is
 * used to handle records of the timetable table.
 * 
 * This class contains timetable related attributes such as semester, description,
 * exam date, exam time and the course and subject associated with the timetable.
 * 
 * It extends BaseBean to inherit common properties like id, createdBy, etc.
 * 
 * @author Nimish
 */
public class TimetableBean extends BaseBean {

	/** Semester for which the timetable is created. */
	private String semester;

	/** Description of the timetable entry. */
	private String description;

	/** Date on which the exam is scheduled. */
	private Date examDate;

	/** Time at which the exam is scheduled. */
	private String examTime;

	/** Course ID of the course associated with this timetable entry. */
	private long courseId;

	/** Name of the course associated with this timetable entry. */
	private String courseName;

	/** Subject ID of the subject associated with this timetable entry. */
	private long subjectId;

	/** Name of the subject associated with this timetable entry. */
	private String subjectName;

	/**
	 * Returns the semester for which the timetable is created.
	 * 
	 * @return semester
	 */
	public String getSemester() {
		return semester;
	}

	/**
	 * Sets the semester for which the timetable is created.
	 * 
	 * @param semester the semester to set
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}

	/**
	 * Returns the description of the timetable entry.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the timetable entry.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the date on which the exam is scheduled.
	 * 
	 * @return examDate
	 */
	public Date getExamDate() {
		return examDate;
	}

	/**
	 * Sets the date on which the exam is scheduled.
	 * 
	 * @param examDate the examDate to set
	 */
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	/**
	 * Returns the time at which the exam is scheduled.
	 * 
	 * @return examTime
	 */
	public String getExamTime() {
		return examTime;
	}

	/**
	 * Sets the time at which the exam is scheduled.
	 * 
	 * @param examTime the examTime to set
	 */
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	/**
	 * Returns the course ID of the course associated with this timetable entry.
	 * 
	 * @return courseId
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the course ID of the course associated with this timetable entry.
	 * 
	 * @param courseId the courseId to set
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Returns the name of the course associated with this timetable entry.
	 * 
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the name of the course associated with this timetable entry.
	 * 
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns the subject ID of the subject associated with this timetable entry.
	 * 
	 * @return subjectId
	 */
	public long getSubjectId() {
		return subjectId;
	}

	/**
	 * Sets the subject ID of the subject associated with this timetable entry.
	 * 
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * Returns the name of the subject associated with this timetable entry.
	 * 
	 * @return subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Sets the name of the subject associated with this timetable entry.
	 * 
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Returns the display value of the timetable bean.
	 * This method is used to display the timetable in dropdown lists or UI components.
	 * 
	 * @return null as display value is not defined for timetable
	 */
	@Override
	public String getValue() {
		return null;
	}
}