package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DbException;
import model.entities.Divida;

public class DividaDaoJDBC implements IDividaDAO {

	Connection conn = null;

	public DividaDaoJDBC() {
		this.conn = DB.getConnection();

	}

	@Override
	public Integer insert(Divida obj) {

		Integer id = null;

		PreparedStatement st = null;

		try {

			String sql = "INSERT INTO divida (Valor, Descricao, DataVencimento) VALUES  (?,?,?)";

			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setDouble(1, obj.getValor());

			st.setString(2, obj.getDescricao());

			st.setDate(3, new java.sql.Date(obj.getData().getTime()));

			int linhas = st.executeUpdate();

			if (linhas > 0) {
				ResultSet rs = st.getGeneratedKeys(); // retorna o id criado

				while (rs.next()) {
					id = rs.getInt(1); // 1 é a 1ª e unica coluna
				}

				System.out.println("Adicionado com o id: " + id);

				DB.closeResultSet(rs);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {

			DB.closeStatement(st);

		}

		return id;
	}

	@Override
	public Object findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Divida divida = new Divida();

		try {

			String sql = "SELECT id, Valor, Descricao, DataVencimento FROM divida WHERE ID = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				int id_e = rs.getInt("Id");
				double valor = rs.getDouble("Valor");
				String descricao = rs.getString("Descricao");
				Date dataVencimento = rs.getDate("DataVencimento");

				divida.setValor(valor);
				divida.setData(dataVencimento);
				divida.setDescricao(descricao);
				divida.setId(id_e);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

		return divida;
	}

	@Override
	public List<Divida> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Divida obj) {

		PreparedStatement st = null;
		try {

			String sql = "UPDATE divida SET Valor=?, Descricao=?, DataVencimento=? WHERE id = ?";
			st = conn.prepareStatement(sql);
			st.setDouble(1, obj.getValor());
			st.setString(2, obj.getDescricao());
			st.setDate(3, new java.sql.Date(obj.getData().getTime()));
			st.setInt(4, obj.getId());

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
		// TODO Auto-generated method stub

	}
}