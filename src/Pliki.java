import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.*;
import java.nio.file.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Pliki {

    public byte[] readFile(String pathFile) {
        byte[] evr;
        String error;

        File file = new File(pathFile);
        byte[] fileByte = new byte[(int) file.length()];
        FileInputStream inFile;

        try {
            inFile = new FileInputStream(file);
            inFile.read(fileByte);

        } catch (FileNotFoundException var5) {
            error = "Błąd";
            evr = error.getBytes();
            return evr;
        } catch (IOException var6) {
            error = "Zła ścieżka";
            evr = error.getBytes();
            return evr;
        }

        return fileByte;
    }

    public void writeFile(String pathFile, byte[] cipher) throws IOException {

        Path path = Paths.get(pathFile);
        Files.write(path, cipher);
    }

    public static String pathBox(String komunikat){
        Stage alert = new Stage();
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Wybierz swoją drogę");
        alert.setMinWidth(250);
        final String[] path = {""};

        Label label = new Label(komunikat);
        TextField field = new TextField();


        Button close = new Button("OK");
        close.setOnAction(e -> {
            path[0] = field.getText();
            alert.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, field, close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alert.setScene(scene);
        alert.showAndWait();

        return path[0];

    }
}