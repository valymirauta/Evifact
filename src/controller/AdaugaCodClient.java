package controller;

import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdaugaCodClient implements Initializable {

    @FXML
    private AnchorPane adaugaCodClient;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblFurnizor;

    @FXML
    private ComboBox<String> alegeAsociatia;

    @FXML
    private TextField txtCodClient;

    @FXML
    private TextField txtTipUtilitate;

    @FXML
    private Button btnAdauga;

    public static int IdFurnizor;
    public static int IdAsociatie;
    public static int IdAsociatieFurnizor;

    @FXML
    void alegeAsociatia(ActionEvent event) {

    }

    @FXML
    void adaugaCodClient(ActionEvent event) {

        String codClient = txtCodClient.getText();
       if( verificaCodClient()){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setHeaderText("Codul de client a fost deja introdus");
           alert.setContentText("Introduceti alt cod.");
           alert.showAndWait();

       } else {
           String tipUtilitate = txtTipUtilitate.getText();

           if (codClient.isEmpty() || tipUtilitate.isEmpty()) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setContentText("Fill required fields!");
               alert.showAndWait();
           } else {
               String selected = alegeAsociatia.getValue();

               String query1 = "SELECT Id FROM asociatii WHERE denumireScurta=?";

               try {
                   PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query1);
                   pstmt.setString(1, selected);
                   ResultSet rs = pstmt.executeQuery();
                   while (rs.next()) {
                       IdAsociatie = rs.getInt("Id");
                       System.out.println("2: " + IdAsociatie);
                   }
                   String query3 = "SELECT Id FROM asociatii_furnizori WHERE (asociatiiID=? and furnizoriID=?)";
                   PreparedStatement pstmt3 = DatabaseHandler.conn.prepareStatement(query3);
                   System.out.println("3: " + IdFurnizor);
                   pstmt3.setInt(1, IdAsociatie);
                   pstmt3.setInt(2, IdFurnizor);
                   ResultSet rs3 = pstmt3.executeQuery();
                   while (rs3.next()) {
                       IdAsociatieFurnizor = rs3.getInt("Id");
                       System.out.println("4: " + IdAsociatieFurnizor);
                   }

                   String query2 = "INSERT INTO codClient (codClient,tipUtilitate, asociatii_furnizoriID) VALUES (?,?,?)";
                   PreparedStatement pstmt1 = DatabaseHandler.conn.prepareStatement(query2);
                   pstmt1.setString(1, codClient);
                   pstmt1.setString(2, tipUtilitate);
                   pstmt1.setInt(3, IdAsociatieFurnizor);
                   pstmt1.executeUpdate();

                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setHeaderText(null);
                   alert.setContentText("Successfully added");
                   alert.showAndWait();

               } catch (SQLException ex) {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setHeaderText(null);
                   alert.setContentText("Operation failed");
                   alert.showAndWait();
                   System.out.println(ex);
               }
           }
       }

        adaugaCodClient.getScene().

                getWindow().

                hide();

    }

    public Boolean verificaCodClient() {
        String sql = "SELECT codClient FROM codClient";
        try {
            Statement stmt = DatabaseHandler.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String codClientExistent = rs.getString("codClient");
                if (txtCodClient.getText().equals(codClientExistent)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return  false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        String query = "SELECT denumireScurta FROM asociatii ";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                alegeAsociatia.getItems().add(rs.getString("denumireScurta"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void setFurnizor(int Id, String denumire) {
        lblFurnizor.setText(denumire);
        IdFurnizor = Id;
    }

}


//cautare cod client pentru furnzior Aquatim

//SELECT codClient, denumire  FROM codClient
// LEFT JOIN asociatii_furnizori ON codClient.asociatii_furnizoriID=asociatii_furnizori.Id
// LEFT JOIN furnizori ON asociatii_furnizori.furnizoriID=furnizori.Id
// WHERE denumire LIKE 'Aquatim%'


