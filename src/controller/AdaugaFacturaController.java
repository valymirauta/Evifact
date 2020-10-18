package controller;

import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AdaugaFacturaController implements Initializable {

    DatabaseHandler handler;

    @FXML
    private TextField codBare;

    @FXML
    private Button btnIncarca;

    @FXML
    private Label lblFurnizor;

    @FXML
    private Label lblCodBare;

    @FXML
    private Label lblCodClient;

    @FXML
    private Label lblSerieNrFactura;

    @FXML
    private Label lblDataEmiterii;

    @FXML
    private Label lblDataScadenta;

    @FXML
    private Label lblValoare;

    @FXML
    private Label lblAsociatia;

    @FXML
    private Button btnAdauga;

    public static int IdCodClient;
    public static String denScurta;
    public static String seriNrFactura;


    @FXML
    void adauga(ActionEvent event) {
        String sql = "SELECT serieNrFactura FROM facturi";
        try {
            Statement stmt = DatabaseHandler.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                seriNrFactura = rs.getString("serieNrFactura");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (lblSerieNrFactura.getText().equals(seriNrFactura)) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Factura a fost deja preluata");
            alert.setContentText("Introduceti alta factura");
            alert.showAndWait();
            curatareFormular();
        } else {

            String query = "INSERT INTO facturi (codBare, codClient, serieNrFactura,dataEmiterii,dataScadenta,valoare,achitat,codClientID) VALUES (?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query);
                pstmt.setString(1, lblCodBare.getText());
                pstmt.setString(2, lblCodClient.getText());
                pstmt.setString(3, lblSerieNrFactura.getText());
                pstmt.setString(4, lblDataEmiterii.getText());
                pstmt.setString(5, lblDataScadenta.getText());
                pstmt.setString(6, lblValoare.getText());
                pstmt.setInt(7, 0);
                pstmt.setInt(8, IdCodClient);
                pstmt.executeUpdate();

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
            curatareFormular();
        }
    }

    @FXML
    void btnIPreiaFactura(ActionEvent event) throws ParseException {
        String cod_bare = codBare.getText();
        switch (cod_bare.length()) {
            case 42: {
                System.out.println("Factura Aquatim");
                lblFurnizor.setText("Aquatim");
                prelucrareAquatim(cod_bare);
                break;
            }
            case 34: {
                System.out.println("Factura Retim");
                lblFurnizor.setText("Retim");
                prelucrareRetim(cod_bare);
                break;
            }
            case 37:
                lblFurnizor.setText("Colterm");
                System.out.println("Factura Colterm");
                break;
            case 39:
                lblFurnizor.setText("Eon");
                System.out.println("Factura E.on");
                break;
            case 40: {
                System.out.println("Factura Enel");
                lblFurnizor.setText("Enel");
                prelucrareEnel(cod_bare);
                break;
            }
            default: {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Cod de bare necunoscut");
                alert.setContentText("Verificati codul de bare");
                alert.showAndWait();
            }
        }
        codBare.setText("");
    }


    private void prelucrareAquatim(String cb) throws ParseException {

        lblCodBare.setText(cb);
        String codIncasare = cb.substring(0, 10);
        //System.out.println("Cod incasare: " + codIncasare);
        lblCodClient.setText(codIncasare);

        String nrFactura = cb.substring(10, 20);
        // System.out.println("Nr factura: " + nrFactura);
        lblSerieNrFactura.setText(nrFactura);

        String dataEmitere = cb.substring(20, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate dataEm = LocalDate.parse(dataEmitere, formatter);
        String dataEmitereS = formatter.format(dataEm);
        String dataScadentaS = formatter.format(dataEm.plusDays(14));
        // System.out.println("Data emitere: " + dataEmitereS);
        //System.out.println("Data scadenta: " + dataScadentaS);
        lblDataEmiterii.setText(dataEmitereS);
        lblDataScadenta.setText(dataScadentaS);

        String suma = cb.substring(30, cb.length());
        String suma1 = suma.replaceAll("^0+(?=.)", "");
        double valoareFactura = Double.parseDouble(suma1);
        // System.out.println("Valoare factura: " + valoareFactura + " lei");
        lblValoare.setText(String.valueOf(valoareFactura));
        cautareDate(codIncasare);
    }

    private void prelucrareEnel(String cb) {
        lblCodBare.setText(cb);
        String idFactura = cb.substring(0, 11);
        //System.out.println("ID Factura: " + idFactura);
        lblSerieNrFactura.setText(idFactura);

        String codPlata = cb.substring(29, 38);
        //System.out.println("Cod plata: " + codPlata);
        lblCodClient.setText(codPlata);

        String suma = cb.substring(11, 23);
        String strPattern = "^0+";
        String suma1 = suma.replaceAll(strPattern, "");
        // System.out.println(suma1);
        int valoareFactura = Integer.parseInt(suma1);
        double valoare = (double) valoareFactura / 100;
        // System.out.println("Valoare factura: " + valoare + " lei");
        lblValoare.setText(String.valueOf(valoare));

        String dataEmitere = cb.substring(23, 25) + "/" + cb.substring(25, 27) + "/20" + cb.substring(27, 29);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataEm = LocalDate.parse(dataEmitere, formatter);
        String dataEmitereS = formatter.format(dataEm);
        String dataScadentaS = formatter.format(dataEm.plusDays(15));

        // System.out.println("Data emitere: " + dataEmitereS);
        // System.out.println("Data scadenta: " + dataScadentaS);

        lblDataEmiterii.setText(dataEmitereS);
        lblDataScadenta.setText(dataScadentaS);

        cautareDate(codPlata);
    }

    private void prelucrareRetim(String cb) {
        lblCodBare.setText(cb);
        String codClient = cb.substring(0, 6);
        System.out.println("Cod client: " + codClient);
        lblCodClient.setText(codClient);

        String strPattern = "^0+";
        String nrFactura = cb.substring(6, 15).replaceAll(strPattern, "");
        System.out.println("Nr factura: " + nrFactura);
        lblSerieNrFactura.setText(nrFactura);

        String dataEmitere = cb.substring(15, 17) + "/" + cb.substring(17, 19) + "/" + cb.substring(19, 23);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate dataEm = LocalDate.parse(dataEmitere, formatter);
        String dataEmitereS = formatter.format(dataEm);
        String dataScadentaS = formatter.format(dataEm.plusDays(15));
        System.out.println("Data emitere: " + dataEmitereS);
        System.out.println("Data scadenta: " + dataScadentaS);
        lblDataEmiterii.setText(dataEmitereS);
        lblDataScadenta.setText(dataScadentaS);

        String suma = cb.substring(23, 31);
        String suma1 = suma.replaceAll(strPattern, "");
        int valoareFactura = Integer.parseInt(suma1);
        double valoare = (double) valoareFactura / 100;
        System.out.println("Valoare factura: " + (double) valoareFactura / 100 + " lei");
        lblValoare.setText(String.valueOf(valoare));

        cautareDate(codClient);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        curatareFormular();
    }

    public void cautareDate(String codPlata) {

        String query1 = "SELECT Id FROM codClient WHERE codClient=?";
        String query2 = "SELECT denumireScurta FROM asociatii WHERE Id=(SELECT asociatiiID FROM codClient WHERE codClient=?)";
        try {
            PreparedStatement pstmt = DatabaseHandler.conn.prepareStatement(query1);
            pstmt.setString(1, codPlata);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                IdCodClient = rs.getInt("Id");
            }

            PreparedStatement pstmt1 = DatabaseHandler.conn.prepareStatement(query2);
            pstmt1.setString(1, codPlata);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                denScurta = rs1.getString("denumireScurta");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        lblAsociatia.setText(denScurta);
    }

    public void curatareFormular() {
        lblAsociatia.setText("");
        lblFurnizor.setText("");
        lblCodBare.setText("");
        lblCodClient.setText("");
        lblSerieNrFactura.setText("");
        lblDataEmiterii.setText("");
        lblDataScadenta.setText("");
        lblValoare.setText("");
    }

}
