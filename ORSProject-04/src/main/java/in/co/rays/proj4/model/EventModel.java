package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.JdbcConnection;

import in.co.rays.proj4.bean.UserBean;
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
			PreparedStatement pstmt = conn.prepareStatement("Select max(id) from st_event");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting pk" + e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
	
	public long add(EventBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		EventBean existBean = findByname(bean.getEventName());
		if(existBean != null ) {
			throw new DuplicateRecordException("Event name already exist");
		}
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_event values (?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getEventCode());
			pstmt.setString(3, bean.getEventName());
			pstmt.setDate(4, new java.sql.Date(bean.getEventTime().getTime()));
			pstmt.setString(5, bean.getStatus());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in add rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in add method" + e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
		
	}
	
	public void update(EventBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		EventBean existBean = findByname(bean.getEventName());
		if(existBean != null ) {
			throw new DuplicateRecordException("Event name already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("update st_event set eventCode = ?, eventName = ?, eventTime = ?, status = ? where eventId = ?");
			pstmt.setString(1, bean.getEventCode());
			pstmt.setString(2, bean.getEventName());
			pstmt.setDate(3, new java.sql.Date(bean.getEventTime().getTime()));
			pstmt.setString(4, bean.getStatus());
			pstmt.setLong(5, bean.getEventId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in update rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in update method" + e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}
	
	public void delete (EventBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_event where id = ?");
			pstmt.setLong(1, bean.getEventId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception in delete rollback" + e2.getMessage());
			}
			throw new ApplicationException("Exception in delete method" + e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public EventBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		EventBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_event where id = ?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
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
			throw new ApplicationException("Exception in find by pk" + e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	public EventBean findByname(String name) throws ApplicationException {
		Connection conn = null;
		EventBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_event where eventName = ?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
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
			throw new ApplicationException("Exception in find by pk" + e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public List<EventBean> search (EventBean bean) throws ApplicationException{
		Connection conn = null;
		
		ArrayList<EventBean> list = new ArrayList<EventBean>();
		
		StringBuffer sql = new StringBuffer("select * from st_event where 1=1");
		
		if (bean != null) {
			
			if(bean.getEventId() > 0) {
				
				sql.append(" and eventId = " + bean.getEventId());
			}
			if (bean.getEventCode() != null && bean.getEventCode().length() > 0) {
				
				sql.append(" and eventCode like '" + bean.getEventCode() + "%'");
			}
			if (bean.getEventName() != null && bean.getEventName().length() > 0) {
				
				sql.append(" and eventName like '" + bean.getEventName() + "%'");
			}
			if (bean.getEventTime() != null && bean.getEventTime().getDate() > 0) {
				
				sql.append(" and eventTime = " + bean.getEventTime());
			}
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				
				sql.append(" and status = '" + bean.getStatus() + "%'");
			}
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				bean.setEventId(rs.getLong(1));
				bean.setEventCode(rs.getString(2));
				bean.setEventName(rs.getString(3));
				bean.setEventTime(rs.getDate(4));
				bean.setStatus(rs.getString(5));
			}
			list.add(bean);
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			throw new ApplicationException("Exception in find by pk" + e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
			
		
		
		
	}
}
