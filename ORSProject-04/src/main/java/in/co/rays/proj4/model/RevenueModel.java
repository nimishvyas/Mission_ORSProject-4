package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.RevenueBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class RevenueModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 1;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Select max(id) from st_revenue");
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

	public long add(RevenueBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		RevenueBean existBean = findByExpenseCode(bean.getExpenseCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Expense Code already exist");
		}
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_revenue values (?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getExpenseCode());
			pstmt.setDouble(3, bean.getAmount());
			pstmt.setString(4, bean.getCategory());
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

	public void update(RevenueBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		RevenueBean existBean = findByExpenseCode(bean.getExpenseCode());
		if (existBean != null && existBean.getExpenseId() != bean.getExpenseId()) {
			throw new DuplicateRecordException("Expense Code already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_revenue set code = ?, amount = ?, category = ?, status = ? where id = ?");
			pstmt.setString(1, bean.getExpenseCode());
			pstmt.setDouble(2, bean.getAmount());
			pstmt.setString(3, bean.getCategory());
			pstmt.setString(4, bean.getStatus());
			pstmt.setLong(5, bean.getExpenseId());
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

	public void delete(RevenueBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_revenue where id = ?");
			pstmt.setLong(1, bean.getExpenseId());
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

	public RevenueBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		RevenueBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_revenue where id = ?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new RevenueBean();
				bean.setExpenseId(rs.getLong(1));
				bean.setExpenseCode(rs.getString(2));
				bean.setAmount(rs.getDouble(3));
				bean.setCategory(rs.getString(4));
				bean.setStatus(rs.getString(5));
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

	public RevenueBean findByExpenseCode(String code) throws ApplicationException {
		Connection conn = null;
		RevenueBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_revenue where code = ?");
			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RevenueBean();
				bean.setExpenseId(rs.getLong(1));
				bean.setExpenseCode(rs.getString(2));
				bean.setAmount(rs.getDouble(3));
				bean.setCategory(rs.getString(4));
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

	public List<RevenueBean> search(RevenueBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;

		ArrayList<RevenueBean> list = new ArrayList<RevenueBean>();

		StringBuffer sql = new StringBuffer("select * from st_revenue where 1=1");

		if (bean != null) {

			if (bean.getExpenseId() > 0) {

				sql.append(" and id = " + bean.getExpenseId());
			}
			if (bean.getExpenseCode() != null && bean.getExpenseCode().length() > 0) {

				sql.append(" and code like '" + bean.getExpenseCode() + "%'");
			}
			if (bean.getAmount() != null && bean.getAmount() > 0 ) {

				sql.append(" and amount = " + bean.getAmount());
			}
			if (bean.getCategory() != null && bean.getCategory().length() > 0) {

				sql.append(" and category like '" + bean.getCategory() + "%'");
			}
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {

				sql.append(" and status = '" + bean.getStatus() + "%'");
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
				bean = new RevenueBean();
				bean.setExpenseId(rs.getLong(1));
				bean.setExpenseCode(rs.getString(2));
				bean.setAmount(rs.getDouble(3));
				bean.setCategory(rs.getString(4));
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
