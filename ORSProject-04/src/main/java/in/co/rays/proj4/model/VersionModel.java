package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.JdbcConnection;

import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.bean.VersionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class VersionModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 1;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Select max(id) from st_version");
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

	public long add(VersionBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		VersionBean existBean = findByCode(bean.getVersionCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Version name already exist");
		}
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_version values (?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getVersionCode());
			pstmt.setString(3, bean.getVersionName());
			pstmt.setDate(4, new java.sql.Date(bean.getReleaseDate().getTime()));
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
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void update(VersionBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		VersionBean existBean = findByCode(bean.getVersionCode());
		if (existBean != null && existBean.getVersionId() != bean.getVersionId()) {
			throw new DuplicateRecordException("Version name already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_version set code = ?, name = ?, release_date = ?, status = ? where id = ?");
			pstmt.setString(1, bean.getVersionCode());
			pstmt.setString(2, bean.getVersionName());
			pstmt.setDate(3, new java.sql.Date(bean.getReleaseDate().getTime()));
			pstmt.setString(4, bean.getStatus());
			pstmt.setLong(5, bean.getVersionId());
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

	public void delete(VersionBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_version where id = ?");
			pstmt.setLong(1, bean.getVersionId());
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

	public VersionBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		VersionBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_version where id = ?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new VersionBean();
				bean.setVersionId(rs.getLong(1));
				bean.setVersionCode(rs.getString(2));
				bean.setVersionName(rs.getString(3));
				bean.setReleaseDate(rs.getDate(4));
				bean.setStatus(rs.getString(5));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in find by pk" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public VersionBean findByCode(String code) throws ApplicationException {
		Connection conn = null;
		VersionBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_version where code = ?");
			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new VersionBean();
				bean.setVersionId(rs.getLong(1));
				bean.setVersionCode(rs.getString(2));
				bean.setVersionName(rs.getString(3));
				bean.setReleaseDate(rs.getDate(4));
				bean.setStatus(rs.getString(5));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in find by pk" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<VersionBean> search(VersionBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;

		List<VersionBean> list = new ArrayList<VersionBean>();

		StringBuffer sql = new StringBuffer("select * from st_version where 1=1");

		if (bean != null) {

			if (bean.getVersionId() > 0) {

				sql.append(" and id = " + bean.getVersionId());
			}
			if (bean.getVersionCode() != null && bean.getVersionCode().length() > 0) {

				sql.append(" and code like '" + bean.getVersionCode() + "%'");
			}
			if (bean.getVersionName() != null && bean.getVersionName().length() > 0) {

				sql.append(" and name like '" + bean.getVersionName() + "%'");
			}
			if (bean.getReleaseDate() != null && bean.getReleaseDate().getDate() > 0) {

				sql.append(" and release_date = " + bean.getReleaseDate());
			}
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {

				sql.append(" and status = '" + bean.getStatus() + "%'");
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
				bean = new VersionBean();
				bean.setVersionId(rs.getLong(1));
				bean.setVersionCode(rs.getString(2));
				bean.setVersionName(rs.getString(3));
				bean.setReleaseDate(rs.getDate(4));
				bean.setStatus(rs.getString(5));
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
