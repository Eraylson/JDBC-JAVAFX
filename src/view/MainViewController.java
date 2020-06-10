package view;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import application.Main.MudancaTela;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.ClienteDaoJDBC;
import model.dao.DAOFactory;
import model.dao.DividaDaoJDBC;
import model.entities.Cliente;
import model.entities.Divida;

public class MainViewController implements Initializable {

	@FXML
	private TableView<TableObject> tblClientes;

	@FXML
	private TableColumn<TableObject, String> clcNome;

	@FXML
	private TableColumn<TableObject, Double> clValor;

	@FXML
	private TableColumn<TableObject, Date> clData;

	@FXML
	private Button btnOk;

	@FXML
	private Button btnEdit;

	@FXML
	private Button btnApagar;
	
	@FXML
	private Button btnPesquisar;
	
	@FXML
	private TextField tfPesquisar;
	
	

	ClienteDaoJDBC clienteDao = null;
	DividaDaoJDBC dividaDao = null;

	@FXML
	public void onBtnNovo() {
		System.out.println("onBtnNovo");
		Main.mudarTela("cliente", null);
	}

	@FXML
	public void onBtnEditar() {
		System.out.println("onBtnEditar");
		
		TableObject selecionado = tblClientes.getSelectionModel().getSelectedItem();

		if( selecionado != null) {
			Main.mudarTela("cliente", selecionado);
		}

		
	}

	@FXML
	public void onBtnApagar() {
		System.out.println("onBtnApagar");
		
		//pega o elemento
		TableObject selecionado = tblClientes.getSelectionModel().getSelectedItem();
		 
		
		//confirma a solicitação 
		 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		 alert.setTitle("Confirmação");
		 alert.setHeaderText("Deseja excluir o Cliente?");
		 alert.setContentText(selecionado.getNome());
		 
		 Optional<ButtonType> resultado =  alert.showAndWait();
		 
		 
		 
		 //se confirmardo então será deletado
		 if (resultado.get() == ButtonType.OK) {
		 clienteDao.deleteById(selecionado.getId());
		 updateTable(null);
		 }

		
		
		
		
	}
	@FXML
	public void onBtnPesquisar() {
		
		//captura o texto digitado
		String nomePesquisa = tfPesquisar.getText();
		//chama o método de atualizar a tabela
		updateTable(nomePesquisa);	
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		MudancaTela novoListener = new MudancaTela() {
			@Override
			public void mudarTelaListener(String tela, Object obj) {

				associarColunas();
				updateTable(null);
			}
		};
		
		

		Main.addListenerMudancaTela(novoListener);

		clienteDao = (ClienteDaoJDBC) DAOFactory.createClienteDAO();
		dividaDao = (DividaDaoJDBC) DAOFactory.createDividaDAO();

		associarColunas();
		updateTable(null);

	}

	private void associarColunas() {

		PropertyValueFactory clNomeProperty = new PropertyValueFactory<>("nome");
		PropertyValueFactory clValorProperty = new PropertyValueFactory<>("valor");
		PropertyValueFactory clDataProperty = new PropertyValueFactory<>("data");

		clcNome.setCellValueFactory(clNomeProperty);
		clValor.setCellValueFactory(clValorProperty);
		clData.setCellValueFactory(clDataProperty);

	}

	private void updateTable(Object obj) {
		tblClientes.getItems().clear(); // limpa antes de adicionar

		// lista para obter os clientes
		List<Cliente> clientes;
		
		if (obj != null) {
			String nomeDeBusca = (String) obj;
			clientes = clienteDao.findByName(nomeDeBusca);
		}else {
			clientes = clienteDao.findAll();
		}

		

		String nome = null;
		Double valor = null;
		Date data = null;
		Integer id_cliente = null;

		for (Cliente c : clientes) {

			nome = c.getNome();
			id_cliente = c.getId();

			Divida d = (Divida) dividaDao.findById(c.getDivida().getId());
			valor = d.getValor();
			data = d.getData();

			TableObject table = new TableObject(id_cliente, nome, valor, data.toString());

			tblClientes.getItems().add(table);

		}

	}

	public static class TableObject {
		private int id;
		private String nome;
		private Double valor;
		private String data;

		public TableObject(int id, String nome, Double valor, String data) {

			this.id = id;
			this.nome = nome;
			this.valor = valor;
			this.data = data;
		}

		public TableObject(String nome, Double valor, String data) {

			this.nome = nome;
			this.valor = valor;
			this.data = data;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public Double getValor() {
			return valor;
		}

		public void setValor(Double valor) {
			this.valor = valor;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

	}

}
