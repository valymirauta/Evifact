package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Furnizori {
    private final SimpleIntegerProperty Id;
    private final SimpleStringProperty CUI;
    private final SimpleStringProperty denumire;
    private final SimpleStringProperty adresa;

    public Furnizori(Integer id, String CUI, String denumire, String adresa) {
        this.Id = new SimpleIntegerProperty(id);
        this.CUI = new SimpleStringProperty(CUI);
        this.denumire = new SimpleStringProperty(denumire);
        this.adresa = new SimpleStringProperty(adresa);
    }

    public int getId() {
        return Id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return Id;
    }

    public String getCUI() {
        return CUI.get();
    }

    public SimpleStringProperty CUIProperty() {
        return CUI;
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
