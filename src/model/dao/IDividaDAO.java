package model.dao;

import java.util.List;

import model.entities.Divida;

public interface IDividaDAO {
	
	//create
	Integer insert(Divida obj);

	//recover 
	Object findById(Integer id);
	//recover all
	List<Divida> findAll();

	//update
	void update(Divida obj);

	//delete
	void deleteById(Integer id);
	}


