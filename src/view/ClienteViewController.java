package view;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import application.Main.MudancaTela;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.dao.ClienteDaoJDBC;
import model.dao.DAOFactory;
import model.dao.DividaDaoJDBC;
import model.entities.Cliente;
import model.entities.Divida;
import view.MainViewController.TableObject;

public class ClienteViewController implements Initializable {

	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfEnde;
	@FXML
	private TextField tfValor;
	@FXML
	private DatePicker dpData;

	@FXML
	private Button btnOk;

	@FXML
	private Button btnCancelar;

	ClienteDaoJDBC clienteDao = null;
	DividaDaoJDBC dividaDao = null;

	Cliente c = new Cliente();

	@FXML
	private void onBtnOk() {

		try {

			System.out.println("onBtnOk");

			if (tfNome.getText().isEmpty()) {
				throw new RuntimeException("Campo nome é obrigatório");
			}

			if (tfEnde.getText().isEmpty()) {
				throw new RuntimeException("Campo endereço é obrigatório");
			}

			if (tfValor.getText().isEmpty()) {
				throw new RuntimeException("Campo valor é obrigatório");
			}

			String nome = tfNome.getText();
			String end = tfEnde.getText();
			Double valor = Double.parseDouble(tfValor.getText());

			LocalDate localDate = dpData.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date data = Date.from(instant);

			
			if (c != null) {
				//vem de update

				c.setEndereco(end);
				c.setNome(nome);

				Divida d = c.getDivida();
				d.setValor(valor);
				d.setDescricao("sem descr.");

				dividaDao.update(d);
				clienteDao.update(c);

				
			
			}else {

					c = new Cliente();
					c.setEndereco(end);
					c.setNome(nome);

					Divida d = new Divida();
					d.setData(data);
					d.setValor(valor);
					d.setDescricao("sem descricao");
					int id_divida = dividaDao.insert(d);
					d.setId(id_divida);

					c.setDivida(d);
					clienteDao.insert(c);

					}


			Main.mudarTela("main", null);

		} catch (NumberFormatException e) {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setHeaderText("Erro no formulário");
			alerta.setContentText("Valor Inválido. Siga o exemplo 23.00");
			alerta.showAndWait();

		} catch (RuntimeException e) {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setHeaderText("Erro no formulário");
			alerta.setContentText(e.getMessage());
			alerta.showAndWait();

		}

	}

	@FXML
	private void onBtnCancelar() {
		System.out.println("onBtnCancelar");
		Main.mudarTela("main", null);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		MudancaTela novoListener = new MudancaTela() {

			@Override
			public void mudarTelaListener(String tela, Object obj) {

				if (tela.equals("cliente")) { 

					if (obj != null) {
						
					
					// vem do botão editar
					TableObject tableObject = (TableObject) obj;
					c = (Cliente) clienteDao.findById(tableObject.getId());
					
					Divida divida = (Divida) dividaDao.findById(c.getDivida().getId());
					c.setDivida(divida);
					
					tfEnde.setText(c.getEndereco());
					tfNome.setText(c.getNome());
					tfValor.setText(String.valueOf(divida.getValor()));
					System.out.println("Id cliente" + c.getId());

					} else {
					// vem do botão novo
						 c = null;
						                        
						tfEnde.clear();
						tfNome.clear();
						tfValor.clear();
					}



			}
			}};
			
			
			Main.addListenerMudancaTela(novoListener); 



		clienteDao = (ClienteDaoJDBC) DAOFactory.createClienteDAO();
		dividaDao = (DividaDaoJDBC) DAOFactory.createDividaDAO();
		
	}
	
}

	


