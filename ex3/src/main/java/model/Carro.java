package model;

public class Carro {
	private int codigo;
	private String marca;
	private String modelo;
	private String cor;
	private int ano;
	private String placa;
	
	public Carro() {
		this.codigo = -1;
		this.marca = "";
		this.modelo = "";
		this.cor = "";
		this.ano = 0000;
		this.placa = "";
	}
	
	public Carro(int codigo, String marca, String modelo, String cor, int ano, String placa) {
		this.codigo = codigo;
		this.marca = marca;
		this.modelo = modelo;
		this.cor = cor;
		this.ano = ano;
		this.placa = placa;
	}
	
	public Carro(String marca, String modelo, String cor, int ano, String placa) {
		this.marca = marca;
		this.modelo = modelo;
		this.cor = cor;
		this.ano = ano;
		this.placa = placa;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}		

	@Override
	public String toString() {
		return "\nCodigo: " + codigo
				+"\nMarca: " + marca
				+"\nModelo: " + modelo
				+"\nCor: " + cor
				+"\nAno: " + ano
				+"\nPlaca: " + placa;
				
	}	
}
