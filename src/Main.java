import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

import java.util.Arrays;
import java.util.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/teste", "postgres", "1234");
            conn.setAutoCommit(false);

            //Demonstrar o funcionamento aqui
            
        	//Área do Cliente
            
            //Criando a Entidade Cliente
            Cliente cliente = new Cliente("Bernardo");
            
            //Implementando a entidade Cliente
			ClienteDAO clienteDAO = new ClienteDAOImpl();
			
			//Funcionamento
			clienteDAO.insert(conn, cliente);
			clienteDAO.list(conn).forEach(System.out::println);;
			cliente.setNome("PariPassu");
			clienteDAO.edit(conn, cliente);
			//clienteDAO.delete(conn, cliente.getIdCliente());
			
			/*
			 * Fim da �rea de Cliente
			 */
			
            //�rea de Filme
            
            //Criando entidade Filme
            
            Filme filme = new Filme ();
            filme.setDataLancamento(new Date(System.currentTimeMillis()));
            filme.setNome ("Coringa");
            filme.setDescricao("Filme do coringa");
            
            //Implementando a entidade Filme 
            FilmeDAOImpl filmeDAO = new FilmeDAOImpl ();
           
            //Funcionamento
            filmeDAO.insert(conn, filme);
            filmeDAO.getNextId(conn);
            filmeDAO.edit(conn, filme);
            //filmeDAO.delete(conn, filme.getIdFilme());
            filmeDAO.find(conn, filme.getIdFilme());
            filmeDAO.list(conn).forEach(System.out::println);;
            
			/*
			 * Fim da �rea de Filmes
			 */
            
            //�rea do Aluguel
            
            //Criando Entidade Aluguel
            
			AluguelDAO aluguelDAO = new AluguelDAOImpl();
            
			Aluguel aluguel = new Aluguel();
			aluguel.setCliente(cliente);
			aluguel.setDataAluguel(new Date(System.currentTimeMillis()));
			aluguel.setFilmes(Arrays.asList(new FilmeDAOImpl().find(conn, 1)));
			aluguel.setValor(100);

			aluguelDAO.insert(conn, aluguel);
			//aluguelDAO.delete(conn, aluguel);
			aluguelDAO.find(conn, aluguel.getIdAluguel());
			//aluguelDAO.list(conn).forEach(System.out::println);
			aluguel.setValor(30);
			aluguelDAO.edit(conn, aluguel);
			
			/*
			 * Fim da Área de Aluguel
			 */
			
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fim do teste.");
    }
}