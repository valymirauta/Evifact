package controller;

import database.DatabaseHandler;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Asociatii;

import javax.jws.WebParam;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListareAsociatiiController implements Initializable {

    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane listaAsociatiiPane;

    @FXML
    private TableView<Asociatii> tabelAsociatii;

    @FXML
    private TableColumn<Asociatii, Integer> colNrCrt;

    @FXML
    private TableColumn<Asociatii, String> colCIF;

    @FXML
    private TableColumn<Asociatii, String> colDenumire;

    @FXML
    private TableColumn<Asociatii, String> colAdresa;

    @FXML
    private TableColumn<Asociatii, Asociatii> colActiuni;




    private FXMLLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateCols();
        loadData();
    }

    @FXML
    void asociatieNoua(ActionEvent event) {
        try {
            loader = new FXMLLoader(getClass().getResource("/view/AdaugAsociatii.fxml"));
            Parent mainCallWindowFXML = loader.load();

            Scene scene = new Scene(mainCallWindowFXML, 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Adauga asociatie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    @FXML
    public void initiateCols() {
        colNrCrt.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCIF.setCellValueFactory(new PropertyValueFactory<>("CIF"));
        colDenumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        colAdresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));

        colActiuni.setCellValueFactory(features -> new ReadOnlyObjectWrapper(features.getValue()));

        colActiuni.setCellFactory(new Callback<TableColumn<Asociatii, Asociatii>, TableCell<Asociatii, Asociatii>>() {
            @Override
            public TableCell<Asociatii, Asociatii> call(TableColumn<Asociatii, Asociatii> btnCol) {
                return new TableCell<Asociatii, Asociatii>() {
                    final Button button = new Button("Aloca Furnizor");

                    {
                        setGraphic(button);
                        button.setMinWidth(130);
                    }

                    @Override
                    public void updateItem(final Asociatii asociatii, boolean empty) {
                        super.updateItem(asociatii, empty);

                        if (asociatii != null) {
                            setGraphic(button);
                            button.setOnAction(event -> {
                                Asociatii selecteazaAsociatie= tabelAsociatii.getItems().get(getIndex());
                                System.out.println(selecteazaAsociatie.getId());
                                alocaFurnizor();
                            });
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }


    public void loadData() {
        list.removeAll();
        String query = "SELECT * FROM asociatii";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.addAll(new Asociatii(rs.getInt("Id"), rs.getString("CIF"), rs.getString("denumire"),
                        rs.getString("adresa")));
            }
            tabelAsociatii.getItems().setAll(list);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void inchide(ActionEvent event) {
        listaAsociatiiPane.setVisible(false);
    }

    @FXML
    void refresh(ActionEvent event) {
        loadData();
    }

    void alocaFurnizor() {
        try {
            loader = new FXMLLoader(getClass().getResource("/view/AlocaFurnziorLaAsociatie.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root, 600, 400);

            Stage stage = new Stage();
            stage.setTitle("Adauga furnizor la asociatie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

}








