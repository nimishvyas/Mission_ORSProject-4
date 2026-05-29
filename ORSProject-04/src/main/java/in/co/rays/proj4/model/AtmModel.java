package in.co.rays.proj4.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.JdbcConnection;

import in.co.rays.proj4.bean.AtmBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class AtmModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 1;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Select max(id) from st_atm");
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

	public long add(AtmBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		AtmBean existBean = findByCode(bean.getSecurityCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Security Code already exist");
		}
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_atm values (?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getBankName());
			pstmt.setString(3, bean.getLocation());
			pstmt.setDouble(4, bean.getCashAvailable());
			pstmt.setInt(5, bean.getSecurityCode());
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

	public void update(AtmBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		AtmBean existBean = findByCode(bean.getSecurityCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Host name already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_atm set bank_name = ?, location = ?, cash_available = ?, security_code = ? where id = ?");
			pstmt.setString(1, bean.getBankName());
			pstmt.setString(2, bean.getLocation());
			pstmt.setDouble(3, bean.getCashAvailable());
			pstmt.setInt(4, bean.getSecurityCode());
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

	public void delete(AtmBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_atm where id = ?");
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

	public AtmBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		AtmBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_atm where id = ?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new AtmBean();
				bean.setId(rs.getLong(1));
				bean.setBankName(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setCashAvailable(rs.getDouble(4));
				bean.setSecurityCode(rs.getInt(5));
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

	public AtmBean findByCode(Integer code) throws ApplicationException {
		Connection conn = null;
		AtmBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_atm where security_code = ?");
			pstmt.setInt(1, code);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new AtmBean();
				bean.setId(rs.getLong(1));
				bean.setBankName(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setCashAvailable(rs.getDouble(4));
				bean.setSecurityCode(rs.getInt(5));
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

	public List<AtmBean> search(AtmBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;

		ArrayList<AtmBean> list = new ArrayList<AtmBean>();

		StringBuffer sql = new StringBuffer("select * from st_atm where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {

				sql.append(" and id = " + bean.getId());
			}
			if (bean.getBankName() != null && bean.getBankName().length() > 0) {

				sql.append(" and bank_name like '" + bean.getBankName() + "%'");
			}
			if (bean.getLocation() != null && bean.getLocation().length() > 0) {

				sql.append(" and location like '" + bean.getLocation() + "%'");
			}
			
			if (bean.getCashAvailable() != null && bean.getCashAvailable() > 0) {

				sql.append(" and cash_available = " + bean.getCashAvailable());
			}
			if(bean.getSecurityCode() != null && bean.getSecurityCode() > 0) {

				sql.append(" and security_code = " + bean.getSecurityCode());
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
				bean = new AtmBean();
				bean.setId(rs.getLong(1));
				bean.setBankName(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setCashAvailable(rs.getDouble(4));
				bean.setSecurityCode(rs.getInt(5));
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

	public List<AtmBean> list() throws ApplicationException {
		
		return search(null, 0, 0);
	}
}
