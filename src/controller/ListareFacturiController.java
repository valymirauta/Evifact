package controller;

import database.DatabaseHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Facturi;
import model.Furnizori;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListareFacturiController implements Initializable {

    ObservableList list = FXCollections.observableArrayList();



    @FXML
    private AnchorPane listaFacturiPane;

    @FXML
    private Button btnAdaugaFactura;

    @FXML
    private TableView<Facturi> tabelFacturi;

    @FXML
    private TableColumn<Facturi, Integer> colNrCrt;

    @FXML
    private TableColumn<Facturi, String> colCodClient;

    @FXML
    private TableColumn<Facturi, String> colCodBare;

    @FXML
    private TableColumn<Facturi, String> colSerieNr;

    @FXML
    private TableColumn<Facturi, String> colDataEmitere;

    @FXML
    private TableColumn<Facturi, String> colDataScadenta;

    @FXML
    private TableColumn<Facturi, String> colSuma;

    @FXML
    private Button btnInchide;

    @FXML
    void inchide(ActionEvent event) {
        listaFacturiPane.setVisible(false);
    }

    @FXML
    void facturaNoua(ActionEvent event) {
        try {
            loader = new FXMLLoader(getClass().getResource("/view/AdaugaFacturi.fxml"));
            Parent mainCallWindowFXML = loader.load();

            Scene scene = new Scene(mainCallWindowFXML, 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Adauga factura");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    private FXMLLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateCols();
        loadData();
    }

    public void initiateCols() {

        colNrCrt.setCellFactory(col -> {
            TableCell<Facturi, Integer> indexCell = new TableCell<>();
            ReadOnlyObjectProperty<TableRow> rowProperty = indexCell.tableRowProperty();
            ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
                TableRow<Facturi> row = rowProperty.get();
                if (row != null) { // can be null during CSS processing
                    int rowIndex = row.getIndex();
                    if (rowIndex < row.getTableView().getItems().size()) {
                        return Integer.toString(rowIndex+1);
                    }
                }
                return null;
            }, rowProperty);
            indexCell.textProperty().bind(rowBinding);
            return indexCell;
        });

        // colNrCrt.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCodBare.setCellValueFactory(new PropertyValueFactory<>("codBare"));
        colCodClient.setCellValueFactory(new PropertyValueFactory<>("codClient"));
        colSerieNr.setCellValueFactory(new PropertyValueFactory<>("serieNr"));
        colDataEmitere.setCellValueFactory(new PropertyValueFactory<>("dataEmitere"));
        colDataScadenta.setCellValueFactory(new PropertyValueFactory<>("dataScadenta"));
        colSuma.setCellValueFactory(new PropertyValueFactory<>("suma"));

    }

    public void loadData() {
        list.removeAll();
        String query = "SELECT * FROM facturi";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.addAll(new Facturi(rs.getInt("Id"), rs.getString("codBare"), rs.getString("codClient"),
                        rs.getString("serieNrFactura"), rs.getString("dataEmiterii"),
                        rs.getString("dataScadenta"), rs.getDouble("valoare"), rs.getBoolean("achitat")));
            }
            tabelFacturi.getItems().setAll(list);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}

