package sample;

import database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    DatabaseHandler handler;

    @FXML
    private TextField codBare;

    @FXML
    private Button btnAdauga;

    @FXML
    void adauga(ActionEvent event) throws ParseException {
        String cod_bare = codBare.getText();
        switch (cod_bare.length()) {
            case 42: {
                System.out.println("Factura Aquatim");
                prelucrareAquatim(cod_bare);
                break;
            }
            case 34: {
                System.out.println("Factura Retim");
                prelucrareRetim(cod_bare);
                break;
            }
            case 37:
                System.out.println("Factura Colterm");
                break;
            case 39:
                System.out.println("Factura E.on");
                break;
            case 40: {
                System.out.println("Factura Enel");
                prelucrareEnel(cod_bare);
                break;
            }
            default:
                System.out.println("Cod de bare necunoscut");
        }

        codBare.setText("");
    }

    private void prelucrareEnel(String cb) {
        String idFactura = cb.substring(0, 11);
        System.out.println("ID Factura: " + idFactura);

        String codPlata= cb.substring(29, 38);
        System.out.println("Cod plata: " + codPlata);

        String suma = cb.substring(11, 22);
        String strPattern = "^0+";
        String suma1 = suma.replaceAll(strPattern, "");
        int valoareFactura = Integer.parseInt(suma1);
        System.out.println("Valoare factura: " + (double) valoareFactura / 100 + " lei");

        String dataEmitere = cb.substring(23, 25) + "/" + cb.substring(25, 27) + "/20" + cb.substring(27, 29);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate dataEm = LocalDate.parse(dataEmitere, formatter);
        System.out.println("Data emitere: " + formatter.format(dataEm));
        System.out.println("Data scadenta: " + formatter.format(dataEm.plusDays(30)));





    }

    private void prelucrareRetim(String cb) {
        String codClient = cb.substring(0, 6);
        System.out.println("Cod client: " + codClient);
        String strPattern = "^0+";
        String nrFactura = cb.substring(6, 15).replaceAll(strPattern, "");
        System.out.println("Nr factura: " + nrFactura);

        String dataEmitere = cb.substring(15, 17) + "/" + cb.substring(17, 19) + "/" + cb.substring(19, 23);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate dataEm = LocalDate.parse(dataEmitere, formatter);

        System.out.println("Data emitere: " + formatter.format(dataEm));

        System.out.println("Data scadenta: " + formatter.format(dataEm.plusDays(15)));

        String suma = cb.substring(23, 31);
        String suma1 = suma.replaceAll(strPattern, "");
        int valoareFactura = Integer.parseInt(suma1);
        System.out.println("Valoare factura: " + (double) valoareFactura / 100 + " lei");
    }

    private void prelucrareAquatim(String cb) throws ParseException {

        String codIncasare = cb.substring(0, 10);
        System.out.println("Cod incasare: " + codIncasare);
        String nrFactura = cb.substring(10, 20);
        System.out.println("Nr factura: " + nrFactura);

        String dataEmitere = cb.substring(20, 30);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate dataEm = LocalDate.parse(dataEmitere, formatter);

        System.out.println("Data emitere: " + formatter.format(dataEm));

        System.out.println("Data scadenta: " + formatter.format(dataEm.plusDays(14)));

        String suma = cb.substring(30, cb.length());
        String suma1 = suma.replaceAll("^0+(?=.)", "");
        double valoareFactura = Double.parseDouble(suma1);
        System.out.println("Valoare factura: " + valoareFactura + " lei");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
