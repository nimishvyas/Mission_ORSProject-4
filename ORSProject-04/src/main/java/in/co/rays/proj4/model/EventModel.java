package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EventModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 1;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_event");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in nextPk " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	public long add(EventBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		EventBean exist = findByEventCode(bean.getEventCode());
		if (exist != null) {
			throw new DuplicateRecordException("Event Code already exists");
		}

		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_event (id, eventCode, eventName, eventTime, status) values (?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getEventCode());
			pstmt.setString(3, bean.getEventName());

			if (bean.getEventTime() != null) {
				pstmt.setDate(4, new Date(bean.getEventTime().getTime()));
			} else {
				pstmt.setDate(4, null);
			}

			pstmt.setString(5, bean.getStatus());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback error " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(EventBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		EventBean exist = findByEventCode(bean.getEventCode());
		if (exist != null && exist.getEventId() != bean.getEventId()) {
			throw new DuplicateRecordException("Event Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_event set eventCode=?, eventName=?, eventTime=?, status=? where id=?");

			pstmt.setString(1, bean.getEventCode());
			pstmt.setString(2, bean.getEventName());

			if (bean.getEventTime() != null) {
				pstmt.setDate(3, new Date(bean.getEventTime().getTime()));
			} else {
				pstmt.setDate(3, null);
			}

			pstmt.setString(4, bean.getStatus());
			pstmt.setLong(5, bean.getEventId());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback error " + ex.getMessage());
			}
			throw new ApplicationException("Exception in update " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(EventBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_event where id=?");
			pstmt.setLong(1, bean.getEventId());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback error " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public EventBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		EventBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_event where id=?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new EventBean();
				bean.setEventId(rs.getLong(1));
				bean.setEventCode(rs.getString(2));
				bean.setEventName(rs.getString(3));
				bean.setEventTime(rs.getDate(4));
				bean.setStatus(rs.getString(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public EventBean findByEventCode(String code) throws ApplicationException {

		Connection conn = null;
		EventBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_event where eventCode=?");
			pstmt.setString(1, code);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new EventBean();
				bean.setEventId(rs.getLong(1));
				bean.setEventCode(rs.getString(2));
				bean.setEventName(rs.getString(3));
				bean.setEventTime(rs.getDate(4));
				bean.setStatus(rs.getString(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByEventCode " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<EventBean> search(EventBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<EventBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_event where 1=1");

		if (bean != null) {

			if (bean.getEventId() > 0) {
				sql.append(" and id = " + bean.getEventId());
			}

			if (bean.getEventCode() != null && bean.getEventCode().length() > 0) {
				sql.append(" and eventCode like '" + bean.getEventCode() + "%'");
			}

			if (bean.getEventName() != null && bean.getEventName().length() > 0) {
				sql.append(" and eventName like '" + bean.getEventName() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			sql.append(" limit " + (pageNo - 1) * pageSize + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				EventBean b = new EventBean();

				b.setEventId(rs.getLong(1));
				b.setEventCode(rs.getString(2));
				b.setEventName(rs.getString(3));
				b.setEventTime(rs.getDate(4));
				b.setStatus(rs.getString(5));

				list.add(b);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}