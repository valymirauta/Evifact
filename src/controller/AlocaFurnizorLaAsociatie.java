package controller;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Asociatii;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AlocaFurnizorLaAsociatie implements Initializable {
    @FXML
    private AnchorPane adaugaAsociatiePane;

    @FXML
    private Label lblTitle;

    @FXML
    private ListView<String> listViewAsocFurn;

    @FXML
    private Button btnAloca;

    @FXML
    void alocaFurnizor(ActionEvent event) {

        adaugaAsociatiePane.getScene().getWindow().hide();

    }

    ObservableList<String> furnizor = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        String query = "SELECT * FROM furnizori";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("denumire"));
                furnizor.add(rs.getString("denumire"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        listViewAsocFurn.getItems().addAll(furnizor);
        listViewAsocFurn.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listViewAsocFurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ObservableList<String> selectedItems = listViewAsocFurn.getSelectionModel().getSelectedItems();
                for (String s : selectedItems) {
                    System.out.println("selected item " + s);
                }


            }
        });
    }

    public int primesteAsociatieSelectata(Asociatii asociatii) {
        int id = asociatii.getId();
        System.out.println("ID este " + id);
        return id;
    }


}


