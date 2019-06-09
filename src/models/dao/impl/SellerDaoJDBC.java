package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
				seller = this.createEntity(rs);
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
	
	private Seller createEntity(ResultSet rs) {
		try {
			Seller seller = new Seller();
			
			seller.setId(rs.getInt("id"));
			seller.setName(rs.getString("name"));
			seller.setBirthDate(rs.getDate("birthDate"));
			seller.setEmail(rs.getString("email"));
			seller.setBaseSalary(rs.getDouble("baseSalary"));
			
			Department department = new Department();
			department.setId(rs.getInt("department"));
			department.setName(rs.getString("depName"));
			
			seller.setDepartment(department);
			
			return seller;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
