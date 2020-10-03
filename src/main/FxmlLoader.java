package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {
    private Pane view;

    public Pane getPage(String fileName) {
        try {
            URL fileURL = Main.class.getResource("/main/" + fileName + ".fxml");
            System.out.println(fileURL);
            if (fileURL == null) {
                throw new java.io.FileNotFoundException(("FXML file can't be found"));
            }
            view = new FXMLLoader().load(fileURL);
        } catch (Exception e) {
            System.out.println("No page " + fileName + " please check FxmlLoader");
        }
        return view;

    }
}
