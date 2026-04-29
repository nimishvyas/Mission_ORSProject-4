package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CacheEvictionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CacheEvictionModel {
	
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 1;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("Select max(id) from st_eviction");
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
	
	public long add(CacheEvictionBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		CacheEvictionBean existBean = findByname(bean.getKeyName());
		if(existBean != null ) {
			throw new DuplicateRecordException("Event name already exist");
		}
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_eviction values (?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getEvictionCode());
			pstmt.setString(3, bean.getKeyName());
			pstmt.setDate(4, new java.sql.Date(bean.getEvictionTime().getTime()));
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
	
	public void update(CacheEvictionBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		CacheEvictionBean existBean = findByname(bean.getKeyName());
		if(existBean != null ) {
			throw new DuplicateRecordException("Event name already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("update st_eviction set code = ?, keyName = ?, evictionTime = ?, status = ? where id = ?");
			pstmt.setString(1, bean.getEvictionCode());
			pstmt.setString(2, bean.getKeyName());
			pstmt.setDate(3, new java.sql.Date(bean.getEvictionTime().getTime()));
			pstmt.setString(4, bean.getStatus());
			pstmt.setLong(5, bean.getEvictionId());
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
	
	public void delete (CacheEvictionBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_eviction where id = ?");
			pstmt.setLong(1, bean.getEvictionId());
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
	
	public CacheEvictionBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		CacheEvictionBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_eviction where id = ?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				bean = new CacheEvictionBean();
				bean.setEvictionId(rs.getLong(1));  
				bean.setEvictionCode(rs.getString(2));
				bean.setKeyName(rs.getString(3));
				bean.setEvictionTime(rs.getDate(4));
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
	public CacheEvictionBean findByname(String name) throws ApplicationException {
		Connection conn = null;
		CacheEvictionBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_eviction where keyName = ?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				bean = new CacheEvictionBean();
				bean.setEvictionId(rs.getLong(1));  
				bean.setEvictionCode(rs.getString(2));
				bean.setKeyName(rs.getString(3));
				bean.setEvictionTime(rs.getDate(4));
				bean.setStatus(rs.getString(5));
			}
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			throw new ApplicationException("Exception in find by name" + e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	public List<CacheEvictionBean> search (CacheEvictionBean bean) throws ApplicationException{
		Connection conn = null;
		
		ArrayList<CacheEvictionBean> list = new ArrayList<CacheEvictionBean>();
		
		StringBuffer sql = new StringBuffer("select * from st_eviction where 1=1");
		
		if (bean != null) {
			
			if(bean.getEvictionId() > 0) {
				
				sql.append(" and id = " + bean.getEvictionId());
			}
			if (bean.getEvictionCode() != null && bean.getEvictionCode().length() > 0) {
				
				sql.append(" and code like '" + bean.getEvictionCode() + "%'");
			}
			if (bean.getKeyName() != null && bean.getKeyName().length() > 0) {
				
				sql.append(" and keyName like '" + bean.getKeyName() + "%'");
			}
			if (bean.getEvictionTime() != null && bean.getEvictionTime().getDate() > 0) {
				
				sql.append(" and evictionTime = " + bean.getEvictionTime());
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
				bean.setEvictionId(rs.getLong(1));  
				bean.setEvictionCode(rs.getString(2));
				bean.setKeyName(rs.getString(3));
				bean.setEvictionTime(rs.getDate(4));
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
