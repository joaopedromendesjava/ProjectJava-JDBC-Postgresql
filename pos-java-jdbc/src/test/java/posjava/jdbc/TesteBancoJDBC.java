package posjava.jdbc;

import java.util.List;
import org.junit.Test;
import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJDBC {

	@Test
	public void initBanco() { // metodo de insert

		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = new Userposjava();

		userposjava.setNome("Nathalia");
		userposjava.setEmail("nathaliaribeiro447@gmail.com");

		userPosDAO.salvar(userposjava);

	}

	@Test
	public void initListar() {

		UserPosDAO dao = new UserPosDAO();
		try {

			List<Userposjava> list = dao.listar();

			for (Userposjava userposjava : list) {
				System.out.println(userposjava.toString());
				System.out.println("----------------------------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void initBuscar() {

		UserPosDAO dao = new UserPosDAO();

		try {
			Userposjava userposjava = dao.buscar(2L);

			System.out.println(userposjava);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void initatualizar() {

		UserPosDAO dao = new UserPosDAO();

		Userposjava objetoBanco;
		try {
			objetoBanco = dao.buscar(3L);
			objetoBanco.setNome("nome mudado com metodo atualizar");
			dao.atualizar(objetoBanco);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Test
	public void initdeletar() {

		try {

			UserPosDAO dao = new UserPosDAO();
			dao.deletar(5L);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testeInsertTelefone() {

		Telefone telefone = new Telefone();
		telefone.setNumero("(31) 33120738");
		telefone.setTipo("Loja");
		telefone.setUsuario(10L);

		UserPosDAO dao = new UserPosDAO();
		dao.salvarTelefone(telefone);

	}

	@Test
	public void testeCarregaFoneUser() {

		UserPosDAO dao = new UserPosDAO();
		List<BeanUserFone> beanUserFones = dao.listaBeanUserFone(10L);

		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
			System.out.println("--------------------------------------------------------");

		}

	}

	@Test
	public void testeDeleteUserFone() {

		UserPosDAO dao = new UserPosDAO();
		dao.deleteFonesPorUser(11L);
	}

}
