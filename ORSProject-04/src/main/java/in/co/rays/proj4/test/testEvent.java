package in.co.rays.proj4.test;

import java.text.SimpleDateFormat;
import java.util.List;

import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EventModel;

public class testEvent {

	public static void main(String[] args) throws Exception {

		testAdd();
		//testUpdate();
		// testDelete();
		// testFindByPk();
		// testFindByEventCode();
		//testSearch();
	}

	// ================= ADD =================
	public static void testAdd() throws Exception {

		EventModel model = new EventModel();

		EventBean bean = new EventBean();

		bean.setEventCode("EVT101");
		bean.setEventName("Tech Conference");
		bean.setEventTime(new SimpleDateFormat("dd-MM-yyyy").parse("15-06-2026"));
		bean.setStatus("Active");

		try {
			long pk = model.add(bean);
			System.out.println("Event added successfully, pk = " + pk);
		} catch (DuplicateRecordException e) {
			System.out.println("Duplicate Event Code");
		}
	}

	// ================= UPDATE =================
	public static void testUpdate() throws Exception {

		EventModel model = new EventModel();

		EventBean bean = new EventBean();

		bean.setEventId(1); // existing ID
		bean.setEventCode("EVT101");
		bean.setEventName("Updated Event");
		bean.setEventTime(new SimpleDateFormat("dd-MM-yyyy").parse("20-06-2026"));
		bean.setStatus("Inactive");

		try {
			model.update(bean);
			System.out.println("Event updated successfully");
		} catch (DuplicateRecordException e) {
			System.out.println("Duplicate Event Code");
		}
	}

	// ================= DELETE =================
	public static void testDelete() throws Exception {

		EventModel model = new EventModel();

		EventBean bean = new EventBean();
		bean.setEventId(1);

		model.delete(bean);

		System.out.println("Event deleted successfully");
	}

	// ================= FIND BY PK =================
	public static void testFindByPk() throws Exception {

		EventModel model = new EventModel();

		EventBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println("Event Found:");
			System.out.println("ID: " + bean.getEventId());
			System.out.println("Code: " + bean.getEventCode());
			System.out.println("Name: " + bean.getEventName());
			System.out.println("Date: " + bean.getEventTime());
			System.out.println("Status: " + bean.getStatus());
		} else {
			System.out.println("No record found");
		}
	}

	// ================= FIND BY EVENT CODE =================
	public static void testFindByEventCode() throws Exception {

		EventModel model = new EventModel();

		EventBean bean = model.findByEventCode("EVT101");

		if (bean != null) {
			System.out.println("Event Found:");
			System.out.println(bean.getEventName());
		} else {
			System.out.println("No record found");
		}
	}

	// ================= SEARCH =================
	public static void testSearch() throws Exception {

		EventModel model = new EventModel();

		EventBean bean = new EventBean();

		bean.setEventName("Tech"); // filter

		List<EventBean> list = model.search(bean, 1, 10);

		if (list.size() == 0) {
			System.out.println("No records found");
		}

		for (EventBean e : list) {
			System.out.println("----------------------");
			System.out.println("ID: " + e.getEventId());
			System.out.println("Code: " + e.getEventCode());
			System.out.println("Name: " + e.getEventName());
			System.out.println("Date: " + e.getEventTime());
			System.out.println("Status: " + e.getStatus());
		}
	}
}