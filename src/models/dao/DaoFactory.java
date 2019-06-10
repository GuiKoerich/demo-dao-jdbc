package models.dao;

import db.DB;
import models.dao.impl.DepartmentDaoJDBC;
import models.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDAO createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDAO createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}

}
