package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;

import dao.FilmeDAO;
import entidades.Filme;

public class FilmeDAOImpl implements FilmeDAO {

	@Override
	public void insert(Connection conn, Filme filme) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("INSERT INTO en_filme(id_filme,data_lancamento,nome,descricao) VALUES(?,?,?,?)");
       
        Integer idFilme = this.getNextId(conn);
        Date dataSQL = new java.sql.Date(filme.getDataLancamento().getTime());
        myStmt.setInt(1, idFilme);
		myStmt.setDate(2, dataSQL);
        myStmt.setString(3, filme.getNome());
        myStmt.setString(4, filme.getDescricao());
        
        myStmt.execute();

       
        conn.commit();
        filme.setIdFilme(idFilme);
        
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("SELECT nextval('seq_en_filme')");
		ResultSet rs = myStmt.executeQuery();
		rs.next();

		return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Filme filme) throws Exception {
		
		PreparedStatement ps = conn
				.prepareStatement("UPDATE en_filme SET data_lancamento=?, nome=?, descricao=? WHERE id_filme=?");
		ps.setDate(1, new java.sql.Date(filme.getDataLancamento().getTime()));
		ps.setString(2, filme.getNome());
		ps.setString(3, filme.getDescricao());
		ps.setInt(4, filme.getIdFilme());
		ps.executeUpdate();
		ps.close();

		conn.commit();
	
	}

	@Override
	public void delete(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("DELETE FROM re_aluguel_filme WHERE id_filme=?;"+"DELETE FROM en_filme WHERE id_filme = ?");
		
		myStmt.setInt(1, idFilme);
		myStmt.setInt(2, idFilme);
		myStmt.execute();
		

		conn.commit();
	
	}

	@Override
	public Filme find(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM en_filme WHERE id_filme = ?");
		myStmt.setInt(1, idFilme);
		ResultSet rs = myStmt.executeQuery();
		
        if (!rs.next()) {
            return null;
        }
        
        Filme filme = new Filme(rs.getInt("id_filme"), new Date(rs.getDate("data_lancamento").getTime()),
				rs.getString("nome"), rs.getString("descricao"));
        
		conn.commit();
		return filme;
	}
	
	@Override
	public Collection<Filme> list(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM en_filme");
		ResultSet rs = myStmt.executeQuery();
		
		Collection<Filme> filmes = new ArrayList<>();
		
        while (rs.next()) {
            Integer idFilme = rs.getInt("id_filme");
            Date data = rs.getDate("data_lancamento");
            String nome = rs.getString("nome");
            String descricao = rs.getString("descricao");
            
            filmes.add(new Filme(idFilme, data, nome, descricao));
        }
		return filmes;
	}

	@Override
	public String toString() {
		return "FilmeDAOImpl [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
