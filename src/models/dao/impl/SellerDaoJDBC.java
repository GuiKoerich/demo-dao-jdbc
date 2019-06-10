package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.exceptions.DbException;
import models.dao.SellerDAO;
import models.entities.Department;
import models.entities.Seller;

public class SellerDaoJDBC implements SellerDAO {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT seller.*, department.name as depName FROM seller " +
						 "JOIN department ON department.id = seller.id " +
						 "WHERE seller.id = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			Seller seller = null;
			
			while(rs.next()) {
				Department department = this.createEntityDepartment(rs);
				seller = this.createEntitySeller(rs, department);
			}
			
			return seller;
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT seller.*, department.name as depName FROM seller " +
						 "JOIN department ON department.id = seller.id " +
						 "WHERE department.id = ? ORDER BY name";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, department.getId());
			
			rs = ps.executeQuery();
			
			List<Seller> sellers = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<Integer, Department>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("departmentId"));
				
				if(dep == null) {
					dep = this.createEntityDepartment(rs);
					map.put(rs.getInt("departmentId"), dep);
				}
				
				sellers.add(this.createEntitySeller(rs, dep));
			}
			
			return sellers;
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}
	
	private Seller createEntitySeller(ResultSet rs, Department department) throws SQLException {
		
		Seller seller = new Seller();
		
		seller.setId(rs.getInt("id"));
		seller.setName(rs.getString("name"));
		seller.setBirthDate(rs.getDate("birthDate"));
		seller.setEmail(rs.getString("email"));
		seller.setBaseSalary(rs.getDouble("baseSalary"));
		seller.setDepartment(department);
		
		return seller;
	}
	
	private Department createEntityDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("departmentId"), rs.getString("depName"));
	}

}
