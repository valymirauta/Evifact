package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Facturi {
    private final SimpleIntegerProperty Id;
    private final SimpleStringProperty codBare;
    private final SimpleStringProperty codClient;
    private final SimpleStringProperty serieNr;
    private final SimpleStringProperty dataEmitere;
    private final SimpleStringProperty dataScadenta;
    private final SimpleDoubleProperty suma;
    private final SimpleBooleanProperty achitat;



    public Facturi(int id, String codBare, String codClient, String serieNr, String dataEmitere, String dataScadenta, double suma, boolean achitat) {
        Id = new SimpleIntegerProperty(id);
        this.codBare = new SimpleStringProperty(codBare);
        this.codClient = new SimpleStringProperty(codClient);
        this.serieNr = new SimpleStringProperty(serieNr);
        this.dataEmitere = new SimpleStringProperty(dataEmitere);
        this.dataScadenta = new SimpleStringProperty(dataScadenta);
        this.suma = new SimpleDoubleProperty(suma);
        this.achitat = new SimpleBooleanProperty(achitat);
    }

    public int getId() {
        return Id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return Id;
    }

    public void setId(int id) {
        this.Id.set(id);
    }

    public String getCodBare() {
        return codBare.get();
    }

    public SimpleStringProperty codBareProperty() {
        return codBare;
    }

    public void setCodBare(String codBare) {
        this.codBare.set(codBare);
    }

    public String getCodClient() {
        return codClient.get();
    }

    public SimpleStringProperty codClientProperty() {
        return codClient;
    }

    public void setCodClient(String codClient) {
        this.codClient.set(codClient);
    }

    public String getSerieNr() {
        return serieNr.get();
    }

    public SimpleStringProperty serieNrProperty() {
        return serieNr;
    }

    public void setSerieNr(String serieNr) {
        this.serieNr.set(serieNr);
    }

    public String getDataEmitere() {
        return dataEmitere.get();
    }

    public SimpleStringProperty dataEmitereProperty() {
        return dataEmitere;
    }

    public void setDataEmitere(String dataEmitere) {
        this.dataEmitere.set(dataEmitere);
    }

    public String getDataScadenta() {
        return dataScadenta.get();
    }

    public SimpleStringProperty dataScadentaProperty() {
        return dataScadenta;
    }

    public void setDataScadenta(String dataScadenta) {
        this.dataScadenta.set(dataScadenta);
    }

    public double getSuma() {
        return suma.get();
    }

    public SimpleDoubleProperty sumaProperty() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma.set(suma);
    }

    public boolean isAchitat() {
        return achitat.get();
    }

    public SimpleBooleanProperty achitatProperty() {
        return achitat;
    }

    public void setAchitat(boolean achitat) {
        this.achitat.set(achitat);
    }
}
