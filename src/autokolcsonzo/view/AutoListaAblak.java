package autokolcsonzo.view;

import autokolcsonzo.dao.AutoDAO;
import autokolcsonzo.model.Auto;
import autokolcsonzo.util.AlertUtil;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

//Autók listázása az Autok táblából.
//Az autók tábla tartalmát egy Auto objektumokból álló listába teszem, átadom a tábla mezőit az objektum argumentumainak, majd megjelenítem

public class AutoListaAblak {

    public static void megjelenit(List<Auto> autok) {

        Stage stage = new Stage();
        stage.setTitle("Autók listája");

        TableView<Auto> tabla = new TableView<>();

        TableColumn<Auto, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

        TableColumn<Auto, String> tipCol = new TableColumn<>("Típus");
        tipCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipus()));

        TableColumn<Auto, String> rendszCol = new TableColumn<>("Rendszám");
        rendszCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRendszam()));

        TableColumn<Auto, Integer> dijCol = new TableColumn<>("Díj");
        dijCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDij()).asObject());

        TableColumn<Auto, Integer> utasCol = new TableColumn<>("Utasok száma");
        utasCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUtasSzam()));

        TableColumn<Auto, Integer> teherCol = new TableColumn<>("Teherbírás");
        teherCol.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getTeherbiras())); //Mivel integer típus, de lehet null értéke, ezért másképp kell átvenni, mint a többi mezőt

        TableColumn<Auto, String> uzemCol = new TableColumn<>("Üzemanyag");
        uzemCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUzemanyag()));

        TableColumn<Auto, Boolean> billentCol = new TableColumn<>("Billent");
        billentCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().getBillent()));

        TableColumn<Auto, Boolean> elerhetotCol = new TableColumn<>("Elérhető");
        elerhetotCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isElerheto()));


        tabla.getColumns().addAll(idCol, tipCol, rendszCol, dijCol, utasCol, teherCol, uzemCol, billentCol, elerhetotCol);tabla.getItems().addAll(autok);

        Button torlesGomb = new Button("Kiválasztott autó törlése");
        torlesGomb.setOnAction(e -> {
            Auto kivalasztott = tabla.getSelectionModel().getSelectedItem();
            if (kivalasztott != null) { //ha van kiválasztott rekord, amikor megnyomjuk a Törlés gombot, akkor
                new AutoDAO().torolAuto(kivalasztott.getId());  //törlöm a kiválasztott rekordot a listából
                tabla.getItems().remove(kivalasztott);
                AlertUtil.showInfo("Az autó sikeresen törölve.");
            } else {
                AlertUtil.showError("Nincs kiválasztott autó.");
            }
        });

        VBox vbox = new VBox(10, tabla, torlesGomb);
        vbox.setStyle("-fx-padding: 10");

        Scene scene = new Scene(vbox, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
}
