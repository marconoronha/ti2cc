package app;

import java.util.List;
import java.util.Scanner;

import dao.CarroDAO;
import model.Carro;

public class Aplicacao {
	
	public static Scanner leitor = new Scanner(System.in);
	public static CarroDAO carroDAO = new CarroDAO();
	
	public static void main(String[] args) throws Exception {
		

		int opcao;
		
		do {
			opcao = menu();

            switch (opcao) {
                case 1: {
                	menuBuscar();
                } break;
                
                case 2: {
                	 menuInserir();
                } break;

                case 3: {
                	menuDeletar();
                } break;

                case 4: {
                	menuAtualizar();
                } break;

                case 0: break;

                default: System.out.println("Opção inválida");
            }
        } while(opcao != 0);
		
	}
	
	public static int menu(){
        int opcao;
        
        System.out.println("\n\n---- MENU ----");
        System.out.println("1) Buscar por");
        System.out.println("2) Inserir");
        System.out.println("3) Deletar");
        System.out.println("4) Atualizar");
        System.out.println("0) Sair");
        System.out.print("\nOpção: ");
        
        try {
            opcao = Integer.valueOf(leitor.nextLine());
        } catch(NumberFormatException e) {
            opcao = -1;
        }

        return opcao;
    }
	
	public static void menuInserir(){
		int opcao;
        Carro novoCarro = new Carro();
        
        System.out.println("\n\n---- INSERIR ----");
        
        try {
	        System.out.print("Digite a marca do carro: ");
	        novoCarro.setMarca(leitor.nextLine());
	        
	        System.out.print("Digite o modelo do carro: ");
	        novoCarro.setModelo(leitor.nextLine());
	        
	        System.out.print("Digite a cor do carro: ");
	        novoCarro.setCor(leitor.nextLine());
	        
	        System.out.print("Digite o ano do carro: ");
	        novoCarro.setAno(Integer.parseInt(leitor.nextLine()));
	        
	        System.out.print("Digite a placa do carro: ");
	        novoCarro.setPlaca(leitor.nextLine());
	        
	        System.out.println("\nSalvar?");
	        System.out.println("1) Sim");
	        System.out.println("2) Cancelar");
	        System.out.print("\nOpção: ");
        
            opcao = Integer.valueOf(leitor.nextLine());
            if(opcao == 1)
            	carroDAO.insert(novoCarro);
            else
            	System.out.println("Operação cancelada.");
            	
        } catch(NumberFormatException e) {
            System.out.println("Opção inválida!");
            opcao = -1;
        }
    }
	
	public static void menuAtualizar(){
		int opcao;
        Carro novoCarro = new Carro();
        
        List<Carro> carros = carroDAO.get();
        for (Carro c : carros) {
    		System.out.println(c.toString());
    		System.out.println("-----------------------");
    	}
        
        try {
	        System.out.println("\n\n---- Atualizar ----");        
	        System.out.print("Digite o código do carro a ser atualizado: ");
	        novoCarro.setCodigo(Integer.parseInt(leitor.nextLine()));
	        
	        System.out.print("Digite a marca do carro: ");
	        novoCarro.setMarca(leitor.nextLine());
	        
	        System.out.print("Digite o modelo do carro: ");
	        novoCarro.setModelo(leitor.nextLine());
	        
	        System.out.print("Digite a cor do carro: ");
	        novoCarro.setCor(leitor.nextLine());
	        
	        System.out.print("Digite o ano do carro: ");
	        novoCarro.setAno(Integer.parseInt(leitor.nextLine()));
	        
	        System.out.print("Digite a placa do carro: ");
	        novoCarro.setPlaca(leitor.nextLine());
	        
	        System.out.println("\nSalvar?");
	        System.out.println("1) Sim");
	        System.out.println("2) Cancelar");
	        System.out.print("\nOpção: ");
        
            opcao = Integer.valueOf(leitor.nextLine());
            if(opcao == 1)
            	carroDAO.update(novoCarro);
            else
            	System.out.println("Operação cancelada.");
            	
        } catch(NumberFormatException e) {
            System.out.println("Opção inválida!");
            opcao = -1;
        }
    }
	
	public static void menuDeletar(){
		int opcao;
        int codigo;
        
        List<Carro> carros = carroDAO.get();
        for (Carro c : carros) {
    		System.out.println(c.toString());
    		System.out.println("-----------------------");
    	}
        
        try {
	        System.out.println("\n\n---- Deletar ----");        
	        System.out.print("Digite o código do carro a ser deletado: ");
	        codigo = Integer.parseInt(leitor.nextLine());
	        
	        if(carroDAO.get(codigo) != null) {
		        System.out.println(carroDAO.get(codigo));
		        System.out.println("\nTem certeza que deseja deletar esse carro?");
		        System.out.println("1) Sim");
		        System.out.println("2) Cancelar");
		        System.out.print("\nOpção: ");
	               
	            opcao = Integer.valueOf(leitor.nextLine());
	            if(opcao == 1)
	            	carroDAO.delete(codigo);
	            else
	            	System.out.println("Operação cancelada.");
	        }
	        else {
	        	System.out.println("Codigo não encontrado.");
	        }
	        
        } catch(NumberFormatException e) {
            System.out.println("Opção inválida!");
            opcao = -1;
        }
    }
	
	public static void menuBuscar(){
		int opcao;
		List<Carro> carros = carroDAO.get();
		
        System.out.println("\n\n---- BUSCAR POR ----");
        System.out.println("1) Marca");
        System.out.println("2) Modelo");
        System.out.println("3) Cor");
        System.out.println("4) Ano");
        System.out.println("5) Placa");
        System.out.println("0) Sair");
        System.out.print("\nOpção: ");
        
        try {
            opcao = Integer.valueOf(leitor.nextLine());
        } catch(NumberFormatException e) {
            opcao = -1;
        }
        
        switch (opcao) {
	        case 1: {
	        	carros= carroDAO.getOrderByMarca();
	        } break;
	        
	        case 2: {
	        	carros= carroDAO.getOrderByModelo();
	        } break;
	
	        case 3: {
	        	carros= carroDAO.getOrderByCor();
	        } break;
	
	        case 4: {
	        	carros= carroDAO.getOrderByAno();
	        } break;
	        	
	        case 5: {
	        	carros= carroDAO.getOrderByPlaca();
	        } break;
	
	        case 0: break;
	
	        default: System.out.println("Opção inválida");
	    }        
        if(opcao != 0)
        	for (Carro c : carros) {
        		System.out.println(c.toString());
        		System.out.println("-----------------------");
        	}
    }
}