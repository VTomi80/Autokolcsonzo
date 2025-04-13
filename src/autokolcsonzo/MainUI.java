package autokolcsonzo;

import autokolcsonzo.dao.AutoDAO;
import autokolcsonzo.dao.BerloDAO;
import autokolcsonzo.dao.BerlesDAO;
import autokolcsonzo.model.Auto;
import autokolcsonzo.model.Berlo;
import autokolcsonzo.model.Berles;
import autokolcsonzo.util.AlertUtil;
import autokolcsonzo.view.RiportAblak;
import autokolcsonzo.view.UjAutoAblak;
import autokolcsonzo.view.UjBerloAblak;
import javafx.application.Application; //a grafikus megjelenítéshez szükséges a javafx osztály importálása
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class MainUI extends Application {

    private AutoDAO autoDAO = new AutoDAO();
    private BerloDAO berloDAO = new BerloDAO();
    private BerlesDAO berlesDAO = new BerlesDAO();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Autókölcsönző"); //ablakfejléc beállítása

        TextField nevField = new TextField(); //textmező
        nevField.setPromptText("Bérlő neve");

        ComboBox<String> jogsiComboBox = new ComboBox<>(); //legördülő menü a jogosítványszámnak, ami a név mezőbe beírt érték alapján keresi ki a lista elemeit a berlok táblából
        jogsiComboBox.setPromptText("Válassz jogosítványszámot");

        //kettő figyelőt állítok be, az egyik a név mezőbe beírást figyeli, hogy az alapján frissítse a legördülő menóben a jogosítványszámokat

        nevField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                String nev = nevField.getText();
                if (!nev.isEmpty()) {
                    List<String> jogsiLista = berloDAO.listazJogositvanyokatNevAlapjan(nev); //Kilistázzuk a megadott névhez tartozó jogosítványszámokat a berlok táblából
                    jogsiComboBox.getItems().setAll(jogsiLista);
                }
            }
        });

        TextField telefonField = new TextField();
        telefonField.setPromptText("Telefonszám");

        //a második figyelő a név + jogosítványszám mezőket figyeli, ha kiválasztottuk a beírt névhez a megfelelő jogosítványsázmot, a telefonszámot kikeresi a Berlok táblából, és beírja a text mezőbe

        ChangeListener<Object> frissito = (obs, oldVal, newVal) -> {
            String nev = nevField.getText();
            String jogsi = jogsiComboBox.getValue();
            if (nev != null && !nev.isEmpty() && jogsi != null && !jogsi.isEmpty()) {
                Berlo berlo = berloDAO.keresBerloNevEsJogsiAlapjan(nev, jogsi);
                if (berlo != null) {
                    telefonField.setText(berlo.getTelefonszam());
                }
            }
        };

        nevField.textProperty().addListener(frissito);
        jogsiComboBox.valueProperty().addListener(frissito);

        ComboBox<String> autoComboBox = new ComboBox<>();
        autoComboBox.getItems().addAll(autoDAO.listazEgyediTipusokat());

        DatePicker kezdetPicker = new DatePicker(); //DAtePickert használok, hogy a naptárból tudjunk dátumot választani
        Spinner<Integer> napokSpinner = new Spinner<>(1, 30, 1);

        Button berlesGomb = new Button("Bérlés létrehozása");
        berlesGomb.setOnAction(e -> {
            //A Bérlés létrehozása gombbal validálom az adatokat, és ha minden rendben van, beírom a mezők tartalmát a bérlések táblába
            String nev = nevField.getText(); //kiveszem az adatokat a mezőkből
            String jogositvany = jogsiComboBox.getValue();
            String telefon = telefonField.getText();
            String autoTipus = autoComboBox.getValue();
            LocalDate kezdet = kezdetPicker.getValue();
            int napok = napokSpinner.getValue();

            if (nev.isEmpty() || jogositvany == null || jogositvany.isEmpty() //megvizsgálom, hogy a kulcs mezők nem üresek-e
                    || telefon.isEmpty() || autoTipus == null || kezdet == null) {
                AlertUtil.showError("Minden mezőt ki kell tölteni!"); //ha üres valamelyik mező, hibaüzenetet küldök
                return;
            }

            Berlo berlo = berloDAO.keresBerloNevEsJogsiAlapjan(nev, jogositvany);
            if (berlo == null) {
                AlertUtil.showError("A bérlő nem található. Kérjük, vigye fel előbb az adatbázisba."); //ha nem található a megadott bérlő a táblában, hibaüzenetet kóldök
                return;
            }

            Auto auto = autoDAO.listazAutok().stream()
                    .filter(a -> a.getTipus().equals(autoTipus))
                    .findFirst()
                    .orElse(null);

            if (auto != null && berlesDAO.autoElerheto(auto.getId(), kezdet, napok)) { //Megvizsgálom, hogy ki lett-e választva autótípus a bérléshez, és a kiválasztott autó elérhető-e az adott időpontban
                Berles berles = new Berles(0, auto, berlo, kezdet, napok, auto.getDij() * napok); //Ha igen, akkor a kiolvasott adatokat elhelyezem egy berles nevű objektumban
                berlesDAO.hozzaadBerles(berles); //lementem a bérlés objektumot a bérlések táblába
                AlertUtil.showInfo("A bérlés sikeresen létrejött!"); //Ha sikeres a mentés, üzenetet küldök
            } else {
                AlertUtil.showError("Az autó nem elérhető ezen a napon."); //ha az autó nem elérhető az adott időpontban, hibaüzenetet adok vissza
            }
        });

        Button riportokGomb = new Button("Riportok"); //Riprotok nézetet megnyitó gomb
        riportokGomb.setOnAction(e -> RiportAblak.megjelenit());

        Button ujBerloGomb = new Button("Új bérlő felvitele"); //új bérlő felvitele nézetet megnyitó gomb
        ujBerloGomb.setOnAction(e -> UjBerloAblak.megjelenit());

        Button ujAutoGomb = new Button("Új autó felvitele"); //új autó felvitele nézetet megnyitó gomb
        ujAutoGomb.setOnAction(e -> UjAutoAblak.megjelenit(() -> {
            autoComboBox.getItems().setAll(autoDAO.listazEgyediTipusokat());
        }));

        kezdetPicker.setTooltip(new Tooltip("Válaszd ki a bérlés kezdőnapját.")); //tooltip üzenet a datepcikerhez, ha az egeret fölévisszük, megjelenik a segítség
        napokSpinner.setTooltip(new Tooltip("Add meg, hány napig szeretnéd bérelni az autót.")); //tooltip üzenet a napok számának megadására szolgáló mezőhöz

        //VBox definiálása, a látható mezőket, gombokat megadva
        VBox vbox = new VBox(10, nevField, jogsiComboBox,
                telefonField, autoComboBox, kezdetPicker,
                napokSpinner, berlesGomb, riportokGomb,
                ujAutoGomb, ujBerloGomb);
        vbox.setMinWidth(300);

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
