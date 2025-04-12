package autokolcsonzo;

import autokolcsonzo.dao.AutoDAO;
import autokolcsonzo.dao.BerloDAO;
import autokolcsonzo.dao.BerlesDAO;
import autokolcsonzo.model.Auto;
import autokolcsonzo.model.Berlo;
import autokolcsonzo.model.Berles;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainUI extends Application {

    private AutoDAO autoDAO = new AutoDAO();
    private BerloDAO berloDAO = new BerloDAO();
    private BerlesDAO berlesDAO = new BerlesDAO();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Autókölcsönző");

        // Bérlő adatok
        TextField nevField = new TextField();
        nevField.setPromptText("Bérlő neve");

        TextField jogositvanyField = new TextField();
        jogositvanyField.setPromptText("Jogosítvány szám");

        TextField telefonField = new TextField();
        telefonField.setPromptText("Telefonszám");

        // Autó választás
        ComboBox<String> autoComboBox = new ComboBox<>();
        autoComboBox.getItems().addAll("Toyota Corolla", "Lada 2107", "BMW X5");

        // Bérlés kezdete
        DatePicker kezdetPicker = new DatePicker();

        // Bérlés ideje (napok száma)
        Spinner<Integer> napokSpinner = new Spinner<>(1, 30, 1);

        // Bérlés gomb
        Button berlesGomb = new Button("Bérlés létrehozása");
        berlesGomb.setOnAction(e -> {
            String nev = nevField.getText();
            String jogositvany = jogositvanyField.getText();
            String telefon = telefonField.getText();
            String autoTipus = autoComboBox.getValue();
            LocalDate kezdet = kezdetPicker.getValue();
            int napok = napokSpinner.getValue();

            if (nev.isEmpty() || jogositvany.isEmpty() || telefon.isEmpty() || autoTipus == null || kezdet == null) {
                showAlert(Alert.AlertType.ERROR, "Hiba", "Minden mezőt ki kell tölteni!");
            } else {
                // Ellenőrizzük, hogy elérhető-e az autó
                Auto auto = autoDAO.listazAutokat().stream()
                        .filter(a -> a.getTipus().equals(autoTipus))
                        .findFirst()
                        .orElse(null);

                if (auto != null && berlesDAO.autoElérheto(auto.getId(), kezdet, napok)) {
                    Berlo berlo = new Berlo(0, nev, jogositvany, telefon);
                    berloDAO.hozzaadBerlo(berlo);

                    Berles berles = new Berles(0, auto, berlo, kezdet, napok, auto.getDij() * napok);
                    berlesDAO.hozzaadBerles(berles);

                    showAlert(Alert.AlertType.INFORMATION, "Siker", "A bérlés sikeresen létrejött!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Hiba", "Az autó nem elérhető ezen a napon.");
                }
            }
        });

        // UI elrendezés
        VBox vbox = new VBox(10, nevField, jogositvanyField, telefonField, autoComboBox, kezdetPicker, napokSpinner, berlesGomb);
        vbox.setMinWidth(300);

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Segédmetódus az üzenetekhez
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
