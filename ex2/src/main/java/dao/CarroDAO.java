package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Carro;

public class CarroDAO extends DAO {
	
	public CarroDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Carro carro) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO carro (marca, modelo, cor, ano, placa) "
				       + "VALUES ('"+ carro.getMarca() + "', '"  
				       + carro.getModelo() + "', '" + carro.getCor() + "', '"
				       + carro.getAno() + "', '" + carro.getPlaca() +"');";
			//System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Carro get(int codigo) {
		Carro carro = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM carro WHERE codigo = " + codigo;
			//System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	carro = new Carro(rs.getInt("codigo"), rs.getString("marca"), rs.getString("modelo"), rs.getString("cor"), rs.getInt("ano"), rs.getString("placa"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carro;
	}
	
	
	public List<Carro> get() {
		return get("");
	}
	
	public List<Carro> getOrderByCodigo() {
		return get("codigo");		
	}
	
	
	public List<Carro> getOrderByMarca() {
		return get("marca");		
	}
	
	public List<Carro> getOrderByModelo() {
		return get("modelo");		
	}
	
	public List<Carro> getOrderByCor() {
		return get("cor");		
	}
		
	public List<Carro> getOrderByAno() {
		return get("ano");		
	}
	
	public List<Carro> getOrderByPlaca() {
		return get("placa");		
	}	
	
	private List<Carro> get(String orderBy) {	
	
		List<Carro> carros = new ArrayList<Carro>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM carro" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			//System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Carro c = new Carro(rs.getInt("codigo"), rs.getString("marca"), rs.getString("modelo"), rs.getString("cor"), rs.getInt("ano"), rs.getString("placa"));
	        	carros.add(c);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carros;
	}
	
	public boolean update(Carro carro) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE carro SET marca = '" + carro.getMarca() 
						+ "', modelo = '" + carro.getModelo() 
						+ "', cor = '" + carro.getCor() 
						+ "', ano = " + carro.getAno()
						+ ", placa = '" + carro.getPlaca()
						+ "' WHERE codigo = " + carro.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM carro WHERE codigo = " + codigo;
			//System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}