package model.dao;

import java.util.List;

import model.entities.Cliente;

public interface IClienteDAO {
	
	
	//create
	void insert(Cliente obj);

	//recover 
	Object findById(Integer id);
	
	//recover all
	List<Cliente> findAll();

	//update
	void update(Cliente obj);

	//delete
	void deleteById(Integer id);


}
