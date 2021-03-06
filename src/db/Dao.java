package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.Parola;


public class Dao {
	
	public int contaParole(int lunghezza){
		Connection conn = DBConnect.getConnection();
		String query = "select count(*) from parola where length(nome)='?'";
		int count=0 ;
		try{
			PreparedStatement st =conn.prepareStatement(query);
			st.setInt(1, lunghezza);
			ResultSet res = st.executeQuery();
			res.first();
			count=res.getInt(1);
			st.close();
			conn.close();
		}catch(SQLException e ){
			e.printStackTrace();
			throw new RuntimeException ("Errore nel db! \n", e);
		}
		return count ;
	}
	
//	public int contaParole(int lunghezza){
//		Connection conn = DBConnect.getConnection();
//		String query = "select count(*) from parola where length(nome)='?'";
//		//int count=0 ;
//		int contatore=-1; 
//		try{
//			PreparedStatement st =conn.prepareStatement(query);
//			st.setInt(1, lunghezza);
//			ResultSet res = st.executeQuery();
//			if(res.next()){
//				contatore = res.getInt("contatore");
//			}
//			st.close();
//			conn.close();
//		}catch(SQLException e ){
//			e.printStackTrace();
//			throw new RuntimeException ("Errore nel db! \n", e);
//		}
//		return contatore ;
//	}
//	
	
	public List<Parola> getParoleDiLunghezza(int lunghezza){
		Connection conn = DBConnect.getConnection();
		String query = "select * from parola where length(nome) = ?";
		PreparedStatement st ;
		try{
			st =conn.prepareStatement(query);
			List<Parola> parole = new LinkedList<Parola>();
			st.setInt(1, lunghezza);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Parola p = new Parola(res.getInt("id"), res.getString("nome"));
				parole.add(p);
			}
			conn.close();
			return parole;
			
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}

	public boolean isPresente(String nome){
		Connection conn = DBConnect.getConnection();
		String query = "select * from parola where nome =? ;";
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, nome);
			ResultSet res = st.executeQuery();
			if(res.next()){
				Parola p = new Parola(res.getInt("id"), res.getString("nome"));
				return true;
			}
		}catch(SQLException e ){
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
