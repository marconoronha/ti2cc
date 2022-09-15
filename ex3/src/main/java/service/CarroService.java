package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.CarroDAO;
import model.Carro;
import spark.Request;
import spark.Response;


public class CarroService {

	private CarroDAO carroDAO = new CarroDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_MARCA = 2;
	private final int FORM_ORDERBY_MODELO = 3;
	private final int FORM_ORDERBY_COR = 4;
	private final int FORM_ORDERBY_ANO = 5;
	private final int FORM_ORDERBY_PLACA = 6;
	
	
	public CarroService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Carro(), FORM_ORDERBY_MARCA);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Carro(), orderBy);
	}

	
	public void makeForm(int tipo, Carro carro, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umCarro = "";
		if(tipo != FORM_INSERT) {
			umCarro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/carro/list/1\">Novo Carro</a></b></font></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t</table>";
			umCarro += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/carro/";
			String name, marca, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Carro";
				marca = "Fiat, Volvo, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + carro.getCodigo();
				name = "Atualizar Carro (ID " + carro.getCodigo() + ")";
				marca = carro.getMarca();
				buttonLabel = "Atualizar";
			}
			umCarro += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umCarro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td>&nbsp;Marca: <input class=\"input--register\" type=\"text\" name=\"marca\" value=\""+ marca +"\"></td>";
			umCarro += "\t\t\t<td>Modelo: <input class=\"input--register\" type=\"text\" name=\"modelo\" value=\""+ carro.getModelo() +"\"></td>";
			umCarro += "\t\t\t<td>Cor: <input class=\"input--register\" type=\"text\" name=\"cor\" value=\""+ carro.getCor() +"\"></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td>&nbsp;Ano: <input class=\"input--register\" type=\"text\" name=\"ano\" value=\""+ Integer.toString(carro.getAno())+ "\"></td>";
			umCarro += "\t\t\t<td>Placa: <input class=\"input--register\" type=\"text\" name=\"placa\" value=\""+ carro.getPlaca() + "\"></td>";
			umCarro += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t</table>";
			umCarro += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umCarro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Carro (ID " + carro.getCodigo() + ")</b></font></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td>&nbsp;Marca: "+ carro.getMarca() +"</td>";
			umCarro += "\t\t\t<td>Modelo: "+ carro.getModelo() +"</td>";
			umCarro += "\t\t\t<td>Cor: "+ carro.getCor() +"</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td>&nbsp;Ano: "+ Integer.toString(carro.getAno()) + "</td>";
			umCarro += "\t\t\t<td>Placa: "+ carro.getPlaca() + "</td>";
			umCarro += "\t\t\t<td>&nbsp;</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-CARRO>", umCarro);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Carros</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/carro/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/carro/list/" + FORM_ORDERBY_MARCA + "\"><b>Marca</b></a></td>\n" +
        		"\t<td><a href=\"/carro/list/" + FORM_ORDERBY_MODELO + "\"><b>Modelo</b></a></td>\n" +
        		"\t<td><a href=\"/carro/list/" + FORM_ORDERBY_COR + "\"><b>Cor</b></a></td>\n" +
        		"\t<td><a href=\"/carro/list/" + FORM_ORDERBY_ANO + "\"><b>Ano</b></a></td>\n" +
        		"\t<td><a href=\"/carro/list/" + FORM_ORDERBY_PLACA + "\"><b>Placa</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Carro> carros;
		if (orderBy == FORM_ORDERBY_ID) {                 	carros = carroDAO.getOrderByCodigo();
		} else if (orderBy == FORM_ORDERBY_MARCA) {			carros = carroDAO.getOrderByMarca();
		} else if (orderBy == FORM_ORDERBY_MODELO) {		carros = carroDAO.getOrderByModelo();
		} else if (orderBy == FORM_ORDERBY_COR) {			carros = carroDAO.getOrderByCor();
		} else if (orderBy == FORM_ORDERBY_ANO) {			carros = carroDAO.getOrderByAno();
		} else if (orderBy == FORM_ORDERBY_PLACA) {			carros = carroDAO.getOrderByPlaca();
		} else {											carros = carroDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Carro p : carros) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getCodigo() + "</td>\n" +
            		  "\t<td>" + p.getMarca() + "</td>\n" +
            		  "\t<td>" + p.getModelo() + "</td>\n" +
            		  "\t<td>" + p.getCor() + "</td>\n" +
            		  "\t<td>" + p.getAno() + "</td>\n" +
            		  "\t<td>" + p.getPlaca() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/carro/" + p.getCodigo() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/carro/update/" + p.getCodigo() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteCarro('" + p.getCodigo() + "', '" + p.getMarca() + "', '" + p.getModelo() + "', '" + p.getCor()+ "', '" + p.getAno() + "', '" + p.getPlaca() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-CARRO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String marca = request.queryParams("marca");
		String modelo = request.queryParams("modelo");
		String cor = request.queryParams("cor");
		int ano = Integer.parseInt(request.queryParams("ano"));
		String placa = request.queryParams("placa");
		
		String resp = "";
		
		Carro carro = new Carro(marca, modelo, cor, ano, placa);
		
		if(carroDAO.insert(carro) == true) {
            resp = "Carro (" + marca + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Carro (" + marca + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Carro carro = (Carro) carroDAO.get(id);
		
		if (carro != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, carro, FORM_ORDERBY_MARCA);
        } else {
            response.status(404); // 404 Not found
            String resp = "Carro " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Carro carro = (Carro) carroDAO.get(id);
		
		if (carro != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, carro, FORM_ORDERBY_MARCA);
        } else {
            response.status(404); // 404 Not found
            String resp = "carro " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Carro carro = carroDAO.get(id);
        String resp = "";       

        if (carro != null) {
        	carro.setMarca(request.queryParams("descricao"));
        	carro.setModelo(request.queryParams("preco"));
        	carro.setCor(request.queryParams("quantidade"));
        	carro.setAno(Integer.parseInt(request.queryParams("dataFabricacao")));
        	carro.setPlaca(request.queryParams("dataValidade"));
        	carroDAO.update(carro);
        	response.status(200); // success
            resp = "Carro (ID " + carro.getCodigo() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Carro (ID \" + carro.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Carro carro = carroDAO.get(id);
        String resp = "";
        System.out.print(id);

        if (carro != null) {
            carroDAO.delete(id);
            response.status(200); // success
            resp = "Carro (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Carro (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}
