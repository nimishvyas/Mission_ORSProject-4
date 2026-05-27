package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CloudBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CloudModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 1;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_cloud");
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

	public long add(CloudBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		CloudBean exist = findByname(bean.getFileName());
		if (exist != null) {
			throw new DuplicateRecordException("Event Code already exists");
		}

		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_cloud values (?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFileName());
			pstmt.setDouble(3, bean.getFileSize());
			pstmt.setDate(4, new java.sql.Date(bean.getUploadDate().getTime()));
			pstmt.setString(5, bean.getUserName());
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

	public void update(CloudBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		CloudBean exist = findByname(bean.getFileName());
		if (exist != null && exist.getFileId() != bean.getFileId()) {
			throw new DuplicateRecordException("File Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_cloud set file_name=?, file_size=?, upload_date=?, user_name=? where id=?");

			pstmt.setString(1, bean.getFileName());
			pstmt.setDouble(2, bean.getFileSize());

			pstmt.setDate(3, new java.sql.Date(bean.getUploadDate().getTime()));


			pstmt.setString(4, bean.getUserName());
			pstmt.setLong(5, bean.getFileId());

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

	public void delete(CloudBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_cloud where id=?");
			pstmt.setLong(1, bean.getFileId());

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

	public CloudBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		CloudBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_cloud where id=?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new CloudBean();
				bean.setFileId(rs.getInt(1));
				bean.setFileName(rs.getString(2));
				bean.setFileSize(rs.getDouble(3));
				bean.setUploadDate(rs.getDate(4));
				bean.setUserName(rs.getString(5));
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

	public CloudBean findByname(String name) throws ApplicationException {

		Connection conn = null;
		CloudBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_cloud where file_name=?");
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new CloudBean();
				bean.setFileId(rs.getInt(1));
				bean.setFileName(rs.getString(2));
				bean.setFileSize(rs.getDouble(3));
				bean.setUploadDate(rs.getDate(4));
				bean.setUserName(rs.getString(5));
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

	public List<CloudBean> search(CloudBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<CloudBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_cloud where 1=1");

		if (bean != null) {

			if (bean.getFileId() > 0) {
				sql.append(" and id = " + bean.getFileId());
			}

			if (bean.getFileName() != null && bean.getFileName().length() > 0) {
				sql.append(" and file_name like '" + bean.getFileName() + "%'");
			}
			if (bean.getFileSize() != null && bean.getFileSize() > 0 ) {

				sql.append(" and file_size = " + bean.getFileSize());
			}

			if (bean.getUploadDate() != null && bean.getUploadDate().getTime() > 0) {
				sql.append(" and upload_date = '" + new java.sql.Date( bean.getUploadDate().getTime())+ "'");
			}
			
			if (bean.getUserName() != null && bean.getUserName().length() > 0) {
				sql.append(" and user_name like '" + bean.getUserName() + "%'");
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
				bean = new CloudBean();
				bean.setFileId(rs.getInt(1));
				bean.setFileName(rs.getString(2));
				bean.setFileSize(rs.getDouble(3));
				bean.setUploadDate(rs.getDate(4));
				bean.setUserName(rs.getString(5));

				list.add(bean);
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