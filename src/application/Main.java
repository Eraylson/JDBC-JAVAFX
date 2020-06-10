package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.dao.ClienteDaoJDBC;
import model.entities.Cliente;

public class Main extends Application {
	// atributo do palco
	private static Stage stage;

	// tela inicial
	private static Scene mainScene;
	private static Scene clientScene;

	@Override
	public void start(Stage primaryStage) {
		try {

			// carregar o o fxml
			URL fxmlMainView = getClass().getResource("/view/mainView.fxml");

			// carrega a tela
			Parent parentMainView = FXMLLoader.load(fxmlMainView);

			// carrega a tela cliente
			Parent parentContaView = FXMLLoader.load(getClass().getResource("/view/clienteView.fxml"));
			clientScene = new Scene(parentContaView);

			stage = primaryStage;
			primaryStage.setTitle("Contas de Clientes");

			// cria a cena
			mainScene = new Scene(parentMainView);

			primaryStage.setScene(mainScene);
			// exibe a tela
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		/*
		 * ClienteDaoJDBC clienteDAO = new ClienteDaoJDBC(); List<Cliente> clientes =
		 * clienteDAO.findByName("João");
		 * 
		 * if(!clientes.isEmpty()) { for(Cliente c: clientes) {
		 * System.out.println(c.toString()); } }else {
		 * System.out.println("Não encontrado"); }
		 * 
		 * 
		 * System.exit(0);
		 */

		
		
		
		launch(args);
	}

	public static void mudarTela(String tela, Object obj ) {
		switch (tela) {

		case "main":
			stage.setScene(mainScene);
			notificarTodosListeners("main", obj);

			break;
		case "cliente":
			stage.setScene(clientScene);
			notificarTodosListeners("cliente", obj);
			break;
		}

	}
	
	
	

	public static interface MudancaTela {

		void mudarTelaListener(String tela, Object obj);
	}

	private static ArrayList<MudancaTela> listeners = new ArrayList<>();

	public static void addListenerMudancaTela(MudancaTela novoListener) {
		listeners.add(novoListener);
	}

	private static void notificarTodosListeners(String tela, Object obj) {

		for (MudancaTela listener : listeners) {
			listener.mudarTelaListener(tela, obj);
		}
	}

}
