package model.dao;

import db.DB;

public class DAOFactory {
	
	public static IClienteDAO createClienteDAO() {
		return new ClienteDaoJDBC();
	}

	public static IDividaDAO createDividaDAO() {
		return new DividaDaoJDBC();
	}

	
	
}
