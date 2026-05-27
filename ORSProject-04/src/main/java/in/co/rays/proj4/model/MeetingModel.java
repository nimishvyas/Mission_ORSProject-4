package in.co.rays.proj4.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.JdbcConnection;

import in.co.rays.proj4.bean.MeetingBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class MeetingModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 1;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Select max(id) from st_meeting");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting pk" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public long add(MeetingBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		MeetingBean existBean = findByName(bean.getHostName());
		if (existBean != null) {
			throw new DuplicateRecordException("Host name already exist");
		}
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_meeting values (?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getHostName());
			pstmt.setString(3, bean.getPlatform());
			pstmt.setInt(4, bean.getDuration());
			pstmt.setInt(5, bean.getParticipants());
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
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void update(MeetingBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		MeetingBean existBean = findByName(bean.getHostName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Host name already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_meeting set host_name = ?, platform = ?, duration = ?, participants = ? where id = ?");
			pstmt.setString(1, bean.getHostName());
			pstmt.setString(2, bean.getPlatform());
			pstmt.setInt(3, bean.getDuration());
			pstmt.setInt(4, bean.getParticipants());
			pstmt.setLong(5, bean.getId());
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
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public void delete(MeetingBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_meeting where id = ?");
			pstmt.setLong(1, bean.getId());
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
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public MeetingBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		MeetingBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_meeting where id = ?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new MeetingBean();
				bean.setId(rs.getLong(1));
				bean.setHostName(rs.getString(2));
				bean.setPlatform(rs.getString(3));
				bean.setDuration(rs.getInt(4));
				bean.setParticipants(rs.getInt(5));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in find by pk " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public MeetingBean findByName(String name) throws ApplicationException {
		Connection conn = null;
		MeetingBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_meeting where host_name = ?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MeetingBean();
				bean.setId(rs.getLong(1));
				bean.setHostName(rs.getString(2));
				bean.setPlatform(rs.getString(3));
				bean.setDuration(rs.getInt(4));
				bean.setParticipants(rs.getInt(5));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in find by code" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<MeetingBean> search(MeetingBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;

		ArrayList<MeetingBean> list = new ArrayList<MeetingBean>();

		StringBuffer sql = new StringBuffer("select * from st_meeting where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {

				sql.append(" and id = " + bean.getId());
			}
			if (bean.getHostName() != null && bean.getHostName().length() > 0) {

				sql.append(" and host_name like '" + bean.getHostName() + "%'");
			}
			if (bean.getPlatform() != null && bean.getPlatform().length() > 0) {

				sql.append(" and platform like '" + bean.getPlatform() + "%'");
			}
			
			if (bean.getDuration() != null && bean.getDuration() > 0) {

				sql.append(" and duration = " + bean.getId());
			}
			if(bean.getParticipants() != null && bean.getParticipants() > 0) {

				sql.append(" and participants = " + bean.getId());
			}
		}
		if (pageSize > 0) {
			sql.append(" limit " + (pageNo - 1) * pageSize + "," + pageSize);
		}
		System.out.println(sql.toString());
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MeetingBean();
				bean.setId(rs.getLong(1));
				bean.setHostName(rs.getString(2));
				bean.setPlatform(rs.getString(3));
				bean.setDuration(rs.getInt(4));
				bean.setParticipants(rs.getInt(5));
				list.add(bean);
			}
			
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in find by pk" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
}
