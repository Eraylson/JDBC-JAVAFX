package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.entities.Cliente;
import model.entities.Divida;

public class ClienteDaoJDBC implements IClienteDAO {

	Connection conn = null;

	public ClienteDaoJDBC() {
		this.conn = DB.getConnection();

	}

	@Override
	public void insert(Cliente obj) {

		PreparedStatement st = null;
		try {

			String sql = "INSERT INTO cliente (Nome, Endereco, Divida) VALUES  (?, ?, ?)";
			st = conn.prepareStatement(sql);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setInt(3, obj.getDivida().getId());

			int linhas = st.executeUpdate();

			if (linhas > 0) {
				System.out.println("Adicionado");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {

			DB.closeStatement(st);
		}

	}

	@Override
	public Object findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		Cliente c = new Cliente();

		try {

			String sql = "SELECT id, Nome, Endereco, Divida FROM cliente WHERE ID = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				int id_c = rs.getInt("Id");
				String nome = rs.getString("Nome");
				String endereco = rs.getString("Endereco");
				int id_divida = rs.getInt("Divida");

				c.setId(id_c);
				c.setEndereco(endereco);
				c.setNome(nome);

				Divida d = new Divida();
				d.setId(id_divida);

				c.setDivida(d);

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

		return c;

	}

	public List<Cliente> findByName(String nomeBusca) {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		List<Cliente> clientes = new ArrayList<Cliente>();

		try {

			String sql = "SELECT id, Nome, Endereco, Divida FROM cliente WHERE Nome LIKE ?";
			st = conn.prepareStatement(sql);
			st.setString(1, '%' + nomeBusca + '%');

			rs = st.executeQuery();

			while (rs.next()) {

				int id_c = rs.getInt("Id");
				String nome = rs.getString("Nome");
				String endereco = rs.getString("Endereco");
				int id_divida = rs.getInt("Divida");

				Cliente c = new Cliente();
				c.setId(id_c);
				c.setEndereco(endereco);
				c.setNome(nome);

				Divida d = new Divida();
				d.setId(id_divida);

				c.setDivida(d);

				clientes.add(c);

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

		return clientes;

	}

	@Override
	public List<Cliente> findAll() {

		List<Cliente> clientes = new ArrayList<Cliente>();

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT id, Nome, Endereco, Divida FROM cliente";
			st = conn.prepareStatement(sql);

			rs = st.executeQuery();

			while (rs.next()) {

				int id_c = rs.getInt("Id");
				String nome = rs.getString("Nome");
				String endereco = rs.getString("Endereco");
				int id_divida = rs.getInt("Divida");

				Cliente c = new Cliente();
				c.setId(id_c);
				c.setEndereco(endereco);
				c.setNome(nome);

				Divida d = new Divida();
				d.setId(id_divida);

				c.setDivida(d);

				clientes.add(c);

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

		return clientes;
	}

	@Override
	public void update(Cliente obj) {

		PreparedStatement st = null;
		try {

			String sql = "UPDATE cliente SET Nome=?, Endereco=? WHERE id = ?";
			st = conn.prepareStatement(sql);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setInt(3, obj.getId());

			int linhas = st.executeUpdate();

			if (linhas > 0) {
				System.out.println("Alterado");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {

			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {

			String sql = "DELETE FROM cliente WHERE id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			int linhas = st.executeUpdate();
			if (linhas > 0) {
				System.out.println("Deletado");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {

			DB.closeStatement(st);
		}

	}

}
