package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.JdbcConnection;

import in.co.rays.proj4.bean.GamingBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class GamingModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 1;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Select max(id) from st_gaming");
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

	public long add(GamingBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		GamingBean existBean = findByCode(bean.getTournamentCode());
		if (existBean != null) {
			throw new DuplicateRecordException("Tournament code already exist");
		}
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_gaming values (?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getTournamentCode());
			pstmt.setString(3, bean.getGameName());
			pstmt.setDouble(4, bean.getPrizePool());
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

	public void update(GamingBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		GamingBean existBean = findByCode(bean.getTournamentCode());
		if (existBean != null && existBean.getTournamentId() != bean.getTournamentId()) {
			throw new DuplicateRecordException("Version name already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_gaming set tournament_code = ?, game_name = ?, prize_pool = ?, status = ? where id = ?");
			pstmt.setString(1, bean.getTournamentCode());
			pstmt.setString(2, bean.getGameName());
			pstmt.setDouble(3, bean.getPrizePool());
			pstmt.setString(4, bean.getStatus());
			pstmt.setLong(5, bean.getTournamentId());
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

	public void delete(GamingBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_gaming where id = ?");
			pstmt.setLong(1, bean.getTournamentId());
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

	public GamingBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		GamingBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_gaming where id = ?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new GamingBean();
				bean.setTournamentId(rs.getLong(1));
				bean.setTournamentCode(rs.getString(2));
				bean.setGameName(rs.getString(3));
				bean.setPrizePool(rs.getDouble(4));
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

	public GamingBean findByCode(String code) throws ApplicationException {
		Connection conn = null;
		GamingBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_gaming where tournament_code = ?");
			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new GamingBean();
				bean.setTournamentId(rs.getLong(1));
				bean.setTournamentCode(rs.getString(2));
				bean.setGameName(rs.getString(3));
				bean.setPrizePool(rs.getDouble(4));
				bean.setStatus(rs.getString(5));
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

	public List<GamingBean> search(GamingBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;

		ArrayList<GamingBean> list = new ArrayList<GamingBean>();

		StringBuffer sql = new StringBuffer("select * from st_gaming where 1=1");

		if (bean != null) {

			if (bean.getTournamentId() > 0) {

				sql.append(" and id = " + bean.getTournamentId());
			}
			if (bean.getTournamentCode() != null && bean.getTournamentCode().length() > 0) {

				sql.append(" and tournament_code like '" + bean.getTournamentCode() + "%'");
			}
			if (bean.getGameName() != null && bean.getGameName().length() > 0) {

				sql.append(" and game_name like '" + bean.getGameName() + "%'");
			}
			
			if (bean.getPrizePool() != null && bean.getPrizePool() > 0 ) {

				sql.append(" and prize_pool = " + bean.getPrizePool());
			}
			
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {

				sql.append(" and status like '" + bean.getStatus() + "%'");
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
				bean = new GamingBean();
				bean.setTournamentId(rs.getLong(1));
				bean.setTournamentCode(rs.getString(2));
				bean.setGameName(rs.getString(3));
				bean.setPrizePool(rs.getDouble(4));
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
