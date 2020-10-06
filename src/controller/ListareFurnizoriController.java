package controller;

import database.DatabaseHandler;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Main;
import model.Asociatii;
import model.Furnizori;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListareFurnizoriController implements Initializable {

    ObservableList list = FXCollections.observableArrayList();


    @FXML
    private AnchorPane listaFurnizoriPane;

    @FXML
    private TableView<Furnizori> tabelFurnizori;

    @FXML
    private TableColumn<Furnizori, Integer> colNrCrt;

    @FXML
    private TableColumn<Furnizori, String> colCIF;

    @FXML
    private TableColumn<Furnizori, String> colDenumire;

    @FXML
    private TableColumn<Furnizori, String> colAdresa;

    @FXML
    private TableColumn<Furnizori, Furnizori> colActiuni;

    private FXMLLoader loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initiateCols();
        loadData();
    }

    @FXML
    void furnizorNou(ActionEvent event) {
        try {
            loader = new FXMLLoader(getClass().getResource("/view/AdaugFurnizor.fxml"));
            Parent mainCallWindowFXML = loader.load();


            Scene scene = new Scene(mainCallWindowFXML, 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Adauga furnizor");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void initiateCols() {
        colNrCrt.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCIF.setCellValueFactory(new PropertyValueFactory<>("CUI"));
        colDenumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        colAdresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));

        colActiuni.setCellValueFactory(features -> new ReadOnlyObjectWrapper(features.getValue()));

        colActiuni.setCellFactory(new Callback<TableColumn<Furnizori, Furnizori>, TableCell<Furnizori, Furnizori>>() {
            @Override
            public TableCell<Furnizori, Furnizori> call(TableColumn<Furnizori, Furnizori> btnCol) {
                return new TableCell<Furnizori, Furnizori>() {
                    final Button button1 = new Button("Cod client");
                    {
                        setGraphic(button1);
                        button1.setMinWidth(130);
                    }
                    @Override
                    public void updateItem(final Furnizori furnizori, boolean empty) {
                        super.updateItem(furnizori, empty);
                        if (furnizori != null) {
                            setGraphic(button1);
                            button1.setOnAction(event -> {
                                adaugaCodClient();
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
        String query = "SELECT * FROM furnizori";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.addAll(new Furnizori(rs.getInt("Id"), rs.getString("CUI"), rs.getString("denumire"),
                        rs.getString("adresa")));
            }
            tabelFurnizori.getItems().setAll(list);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void inchide(ActionEvent event) {
        listaFurnizoriPane.setVisible(false);
    }


    void adaugaCodClient() {
        try {
            loader = new FXMLLoader(getClass().getResource("/view/AdaugaCodClient.fxml"));
            Parent mainCallWindowFXML = loader.load();

            Scene scene = new Scene(mainCallWindowFXML, 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Adauga cod client");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

}

