package autokolcsonzo.view;

import autokolcsonzo.dao.BerloDAO;
import autokolcsonzo.model.Berlo;
import autokolcsonzo.util.AlertUtil;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UjBerloAblak {

    //új bérlő felvitele ablak, ugyanúgy működik, mint az új autó felvitele osztály, csak más mezőkkel

    public static void megjelenit() {
        Stage stage = new Stage();
        stage.setTitle("Új bérlő felvitele");

        TextField nevField = new TextField();
        nevField.setPromptText("Név");

        TextField jogsiField = new TextField();
        jogsiField.setPromptText("Jogosítványszám");

        TextField telField = new TextField();
        telField.setPromptText("Telefonszám");

        Button mentGomb = new Button("Mentés");
        mentGomb.setOnAction(e -> {
            try {
                String nev = nevField.getText();
                String jogsi = jogsiField.getText();
                String tel = telField.getText();

                if (nev.isEmpty() || jogsi.isEmpty() || tel.isEmpty()) {
                    AlertUtil.showError("Minden mezőt ki kell tölteni!");
                    return;
                }

                BerloDAO dao = new BerloDAO();

                if (dao.vanIlyenJogsi(jogsi)) {
                    AlertUtil.showError("Ez a jogosítványszám már létezik az adatbázisban!");
                    return;
                }

                dao.hozzaadBerlo(new Berlo(0, nev, jogsi, tel));
                AlertUtil.showInfo("A bérlő sikeresen elmentve!");
                stage.close();

            } catch (Exception ex) {
                AlertUtil.showError("Hiba történt a mentés során: " + ex.getMessage());
            }
        });

        VBox vbox = new VBox(10, nevField, jogsiField, telField, mentGomb);
        vbox.setStyle("-fx-padding: 10");

        stage.setScene(new Scene(vbox, 300, 200));
        stage.show();
    }
}
