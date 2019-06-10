package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.exceptions.DbException;
import db.exceptions.DbIntegrityException;
import models.dao.DepartmentDAO;
import models.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department obj) {
		PreparedStatement ps = null;
		try {
			
			String sql = "INSERT INTO department(name) VALUE(?)";
			
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getName());
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				
				while(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				
				DB.closeResultSet(rs);
				
			} else {
				throw new DbException("Unexpected Error! No rows affected!");
			}
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement ps = null;
		try {
			
			String sql = "UPDATE department SET name = ? WHERE id = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getId());
			
			ps.executeUpdate();
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			
			String sql = "DELETE FROM department WHERE id = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
		} catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM department WHERE id = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			Department department = null;
			
			while(rs.next()) {
				department = this.createEntityDepartment(rs);
			}
			
			return department;
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT * FROM department ORDER BY name";
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			List<Department> departments = new ArrayList<Department>();
			
			while(rs.next()) {		
				departments.add(this.createEntityDepartment(rs));
			}
			
			return departments;
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}
	
	private Department createEntityDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("id"), rs.getString("name"));
	}
}
