package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaoJDBC.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class UserPosDAO {

	private Connection connection;

	public UserPosDAO() {

		connection = SingleConnection.getConnection();
	}

	public void salvar(Userposjava userposjava) {
		try {

			String sql = "insert into userposjava (nome, email) values (?,?)"; // String do SQL
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, userposjava.getNome()); // perimetros sendo adicionados
			insert.setString(2, userposjava.getEmail());
			insert.execute(); // sql sendo executado no banco de dados
			connection.commit(); // salva no banco

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void salvarTelefone(Telefone telefone) {

		try {

			String sql = "INSERT INTO public.telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public List<Userposjava> listar() throws Exception {

		List<Userposjava> list = new ArrayList<Userposjava>();

		String sql = "select * from userposjava ";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));

			list.add(userposjava);
		}

		return list;

	}

	public Userposjava buscar(long id) throws Exception {

		Userposjava retorno = new Userposjava();
		String sql = "select * from userposjava where id =  " + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // retornar apenas um ou nenhum

			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));

		}

		return retorno;

	}

	public List<BeanUserFone> listaBeanUserFone(Long idUser) {

		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();

		String sql = " select nome, numero, email from telefoneuser as fone ";
		sql += " inner join userposjava as userp ";
		sql += " on fone.usuariopessoa = userp.id ";
		sql += " where userp.id = " + idUser;

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				BeanUserFone beanUserFone = new BeanUserFone();
				beanUserFone.setEmail(resultSet.getString("email"));
				beanUserFone.setNome(resultSet.getString("nome"));
				beanUserFone.setNumero(resultSet.getString("numero"));

				beanUserFones.add(beanUserFone);

			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return beanUserFones;
	}

	public void atualizar(Userposjava userposjava) throws SQLException {

		try {

			String sql = "update userposjava set nome = ? where id =  " + userposjava.getId();
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, userposjava.getNome());
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}

	}

	public void deletar(long id) throws SQLException {

		try {

			String sql = "delete from userposjava where id = " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}

	}

	public void deleteFonesPorUser(Long idUser) {

		String sqlFone = "delete from telefoneuser where usuariopessoa = " + idUser;
		String sqlUser = "delete from userposjava where id = " + idUser;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sqlFone);
			statement.executeUpdate();
			connection.commit();
			
			statement = connection.prepareStatement(sqlUser);
			statement.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {

			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

}
