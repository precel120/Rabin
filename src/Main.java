import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("One Time Pad");

        //Ustawianie parametrów Grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(5);

        //Tworzenie pól w Grid
        Label nazwaSzyfr = new Label("Podaj szyfr:");
        GridPane.setConstraints(nazwaSzyfr, 0, 0);

        TextField tekstSzyfr = new TextField();
        tekstSzyfr.setPromptText("Twój szyfr");
        GridPane.setConstraints(tekstSzyfr, 1, 0);

        Label nazwaPlik = new Label("Podaj ścieżkę do pliku:");
        GridPane.setConstraints(nazwaPlik, 0, 1);

        TextField tekstPlik = new TextField();
        tekstSzyfr.setPromptText("Twój szyfr");
        GridPane.setConstraints(tekstPlik, 1, 1);

        Label nazwaZaszyfr = new Label("Zaszyfrowany:");
        GridPane.setConstraints(nazwaZaszyfr, 0, 2);

        TextField tekstZaszyfr = new TextField();
        GridPane.setConstraints(tekstZaszyfr, 1, 2);

        Label nazwaOdszyfr = new Label("Odszyfrowany:");
        GridPane.setConstraints(nazwaOdszyfr, 0, 3);

        TextField tekstOdszyfr = new TextField();
        GridPane.setConstraints(tekstOdszyfr, 1, 3);

        //Przyciski
        Button button_szyfr = new Button("Szyfruj");
        GridPane.setConstraints(button_szyfr, 3, 0);

        button_szyfr.setOnAction(e -> {
            String txt_from = tekstSzyfr.getText();
            byte[] byteText = txt_from.getBytes();
            Szyfruj slowo = new Szyfruj(byteText);

            BigInteger wyswZaszyfr = new BigInteger(Arrays.toString(slowo.szyfruj()));
            BigInteger wyswOdszyfr = new BigInteger(String.valueOf(slowo.deszyfruj(slowo.szyfruj())));
            String txt_przed = wyswZaszyfr.toString();
            String txt_po = wyswOdszyfr.toString();
            tekstZaszyfr.setText(txt_przed);
            tekstOdszyfr.setText(txt_po);
        });

        Button button_szyfrplik = new Button("Szyfruj z pliku");
        GridPane.setConstraints(button_szyfrplik, 3, 1);

        button_szyfrplik.setOnAction(e -> {
            String filePath = tekstPlik.getText();
            byte[] bytePath = filePath.getBytes();
            Pliki pliki=new Pliki();
            Szyfruj slowo = new Szyfruj(pliki.readFile(bytePath));

            BigInteger wyswZaszyfr = new BigInteger(String.valueOf(slowo.szyfruj()));
            BigInteger wyswOdszyfr = new BigInteger(String.valueOf(slowo.deszyfruj(slowo.szyfruj())));
            String txt_przed = wyswZaszyfr.toString();
            String txt_po = wyswOdszyfr.toString();
            tekstZaszyfr.setText(txt_przed);
            tekstOdszyfr.setText(txt_po);

            try {
                pliki.writeFile("C:\\Users\\Michał\\Desktop\\gowno2.jpeg", slowo.deszyfruj(slowo.szyfruj()));
            }
            catch (IOException el) {
                el.printStackTrace();
            }
        });

        //Dodawanie do Grid
        grid.getChildren().addAll(nazwaSzyfr, tekstSzyfr, nazwaPlik, tekstPlik, nazwaZaszyfr, tekstZaszyfr, nazwaOdszyfr, tekstOdszyfr, button_szyfr, button_szyfrplik);


        Scene scene = new Scene(grid, 440, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}