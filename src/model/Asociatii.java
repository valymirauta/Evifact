package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class Asociatii {
    private final SimpleIntegerProperty Id;
    private final SimpleStringProperty CIF;
    private final SimpleStringProperty denumire;
    private final SimpleStringProperty adresa;


    public Asociatii(Integer id, String CIF, String denumire, String adresa) {
        this.Id = new SimpleIntegerProperty(id);
        this.CIF = new SimpleStringProperty(CIF);
        this.denumire = new SimpleStringProperty(denumire);
        this.adresa = new SimpleStringProperty(adresa);
    }

    public int getId() {
        return Id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return Id;
    }

    public String getCIF() {
        return CIF.get();
    }

    public SimpleStringProperty CIFProperty() {
        return CIF;
    }

    public String getDenumire() {
        return denumire.get();
    }

    public SimpleStringProperty denumireProperty() {
        return denumire;
    }

    public String getAdresa() {
        return adresa.get();
    }

    public SimpleStringProperty adresaProperty() {
        return adresa;
    }


}
