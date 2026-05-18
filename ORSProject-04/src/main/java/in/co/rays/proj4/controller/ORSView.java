package in.co.rays.proj4.controller;

/**
 * ORSView defines all application view paths and controller URLs.
 * 
 * It acts as a centralized place for:
 * - JSP view locations
 * - Controller URL mappings
 * - Application context configuration
 * 
 * Benefits:
 * - Avoids hardcoding paths across the application
 * - Improves maintainability and readability
 * - Ensures consistency in navigation
 * 
 * Structure:
 * - APP_CONTEXT → base context path of the application
 * - PAGE_FOLDER → folder containing JSP views
 * - *_VIEW → JSP page paths
 * - *_CTL → Controller URL mappings
 * 
 * Naming Convention:
 * - VIEW → used for forwarding to JSP pages
 * - CTL → used for redirecting to controllers
 * 
 * Example:
 * - LOGIN_VIEW → /jsp/LoginView.jsp
 * - LOGIN_CTL → /ORSProject-04/LoginCtl
 * 
 * This interface is used across controllers for navigation.
 * 
 * @author Nimish
 */
public interface ORSView {

    /** Application context path */
    public String APP_CONTEXT = "/ORSProject-04";

    /** Folder containing JSP pages */
    public String PAGE_FOLDER = "/jsp";
    
    /** Welcome page view and controller */
    public String WELCOME_VIEW = PAGE_FOLDER + "/WelcomeView.jsp";
    public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";
    
    /** JavaDoc index page */
    public String JAVA_DOC = APP_CONTEXT + "/doc/index.html";
    
    /** User Registration view and controller */
    public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
    public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";

    /** Login view and controller */
    public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
    public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";

    /** User form view and controller */
    public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
    public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";

    /** User list view and controller */
    public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
    public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl"; 
    
    /** Get Marksheet view and controller */
    public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
    public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";

    /** Marksheet merit list view and controller */
    public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";
    public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";
    
    /** Role view and controller */
    public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
    public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";

    /** Role list view and controller */
    public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
    public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";

    /** College view and controller */
    public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
    public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";

    /** College list view and controller */
    public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
    public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";

    /** Student view and controller */
    public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
    public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";

    /** Student list view and controller */
    public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
    public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";

    /** Marksheet view and controller */
    public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
    public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";

    /** Marksheet list view and controller */
    public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
    public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";

    /** Course view and controller */
    public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
    public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";

    /** Course list view and controller */
    public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
    public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";

    /** Subject view and controller */
    public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
    public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";

    /** Subject list view and controller */
    public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
    public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";

    /** Timetable view and controller */
    public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimetableView.jsp";
    public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimetableCtl";

    /** Timetable list view and controller */
    public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimetableListView.jsp";
    public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimetableListCtl";

    /** Faculty view and controller */
    public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
    public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";

    /** Faculty list view and controller */
    public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
    public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";

    /** Forget password view and controller */
    public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
    public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

    /** My profile view and controller */
    public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
    public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";

    /** Change password view and controller */
    public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
    public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";

    /** Error view and controller */
    public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
    public String ERROR_CTL = APP_CONTEXT + "/ctl/ErrorCtl";
    
    /** Event view and controller */
    public String EVENT_VIEW = PAGE_FOLDER + "/EventView.jsp";
    public String EVENT_CTL = APP_CONTEXT + "/ctl/EventCtl";

    /** Event list view and controller */
    public String EVENT_LIST_VIEW = PAGE_FOLDER + "/EventListView.jsp";
    public String EVENT_LIST_CTL = APP_CONTEXT + "/ctl/EventListCtl";

	public String VERSION_VIEW = PAGE_FOLDER + "/VersionView.jsp";
	public String VERSION_CTL = APP_CONTEXT + "/ctl/VersionCtl";
	 
	public String VERSION_LIST_VIEW = PAGE_FOLDER + "/VersionListView.jsp";
	public String VERSION_LIST_CTL = APP_CONTEXT + "/ctl/VersionListCtl";

	public String REVENUE_VIEW = PAGE_FOLDER + "/RevenueView.jsp";
	public String REVENUE_CTL = APP_CONTEXT + "/ctl/RevenueCtl";
	 
	public String REVENUE_LIST_VIEW = PAGE_FOLDER + "/RevenueListView.jsp";
	public String REVENUE_LIST_CTL = APP_CONTEXT + "/ctl/RevenueListCtl";

}