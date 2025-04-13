package autokolcsonzo.view;

import autokolcsonzo.dao.AutoDAO;
import autokolcsonzo.model.Auto;
import autokolcsonzo.util.AlertUtil;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UjAutoAblak {

    //új autó felvitele
    //a mezők kitöltése után a MEntés gombra kattintva a felvitt adatokat - validáció után - beírom az adatbázis Autók táblájába

    public static void megjelenit(Runnable frissites) {
        Stage stage = new Stage();
        stage.setTitle("Új autó felvitele");

        TextField tipusField = new TextField();
        tipusField.setPromptText("Típus");

        TextField rendszamField = new TextField();
        rendszamField.setPromptText("Rendszám");

        TextField dijField = new TextField();
        dijField.setPromptText("Bérleti díj");

        TextField utasSzamField = new TextField();
        utasSzamField.setPromptText("Utasok száma (opcionális)");

        TextField teherbirasField = new TextField();
        teherbirasField.setPromptText("Teherbírás (opcionális)");

        TextField uzemanyagField = new TextField();
        uzemanyagField.setPromptText("Üzemanyag");

        CheckBox billentBox = new CheckBox("Billent");
        CheckBox elerhetoBox = new CheckBox("Elérhető");
        elerhetoBox.setSelected(true);

        Button mentesGomb = new Button("Mentés");
        mentesGomb.setOnAction(e -> {
            try {
                String tipus = tipusField.getText();
                String rendszam = rendszamField.getText();
                int dij = Integer.parseInt(dijField.getText());
                Integer utasSzam = utasSzamField.getText().isEmpty() ? null : Integer.parseInt(utasSzamField.getText());
                Integer teherbiras = teherbirasField.getText().isEmpty() ? null : Integer.parseInt(teherbirasField.getText());
                String uzemanyag = uzemanyagField.getText();
                Boolean billent = billentBox.isSelected();
                boolean elerheto = elerhetoBox.isSelected();

                AutoDAO autoDAO = new AutoDAO();

                if (autoDAO.vanIlyenRendszam(rendszam)) {
                    AlertUtil.showError("Már létezik ilyen rendszám az adatbázisban.");
                    return;
                }

                Auto auto = new Auto(0, tipus, rendszam, dij, utasSzam, teherbiras, uzemanyag, billent, elerheto);
                autoDAO.hozzaadAuto(auto);

                AlertUtil.showInfo("Az autó sikeresen rögzítve!");

                if (frissites != null) frissites.run(); //mentés után frissítem az ablak mezőit, hogy eltúnjenek belőle a beít adatok
                stage.close();

            } catch (Exception ex) {
                AlertUtil.showError("Hibás adatbevitel: " + ex.getMessage());
            }
        });

        VBox vbox = new VBox(10, tipusField, rendszamField, dijField, utasSzamField,
                teherbirasField, uzemanyagField, billentBox, elerhetoBox, mentesGomb);
        vbox.setStyle("-fx-padding: 10");

        stage.setScene(new Scene(vbox, 400, 400));
        stage.show();
    }
}