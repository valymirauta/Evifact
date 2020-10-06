package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {
    private Pane view;

    public Pane getPage(String fileName) {
        String Url_File= "/view/"+ fileName + ".fxml";
        try {
            URL fileURL = getClass().getResource(Url_File);

            if (fileURL == null) {
                throw new java.io.FileNotFoundException(("FXML file can't be found"));
            }
            view = new FXMLLoader().load(fileURL);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No page " + fileName + " please check FxmlLoader");
        }
        return view;
    }
}
