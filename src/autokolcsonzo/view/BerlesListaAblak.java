package autokolcsonzo.view;

import autokolcsonzo.dao.BerlesDAO;
import autokolcsonzo.model.BerlesListaElem;
import autokolcsonzo.util.AlertUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BerlesListaAblak {

    public static void megjelenit(List<BerlesListaElem> lista) {
        Stage stage = new Stage();
        stage.setTitle("Bérlések listája");

        TableView<BerlesListaElem> tabla = new TableView<>();

        TableColumn<BerlesListaElem, String> nevCol = new TableColumn<>("Bérlő neve");
        nevCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBerloNev()));

        TableColumn<BerlesListaElem, String> rendszamCol = new TableColumn<>("Autó rendszáma");
        rendszamCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRendszam()));

        TableColumn<BerlesListaElem, String> kezdetCol = new TableColumn<>("Bérlés kezdete");
        kezdetCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getKezdet().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        TableColumn<BerlesListaElem, String> vegCol = new TableColumn<>("Bérlés vége");
        vegCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getKezdet().plusDays(data.getValue().getNapok()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        TableColumn<BerlesListaElem, Integer> napokCol = new TableColumn<>("Napok száma");
        napokCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNapok()).asObject());

        TableColumn<BerlesListaElem, Integer> arCol = new TableColumn<>("Bérlés ára (Ft)");
        arCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAr()).asObject());

        tabla.getColumns().addAll(nevCol, rendszamCol, kezdetCol, vegCol, napokCol, arCol);
        tabla.getItems().addAll(lista);

        Button torlesGomb = new Button("Kiválasztott bérlés törlése");
        torlesGomb.setOnAction(e -> {
            BerlesListaElem kivalasztott = tabla.getSelectionModel().getSelectedItem();
            if (kivalasztott != null) {
                new BerlesDAO().torolBerles(kivalasztott);
                tabla.getItems().remove(kivalasztott);
                AlertUtil.showInfo("A bérlés sikeresen törölve.");
            } else {
                AlertUtil.showError("Nincs kiválasztott bérlés.");
            }
        });

        VBox vbox = new VBox(10, tabla, torlesGomb);
        vbox.setStyle("-fx-padding: 10");

        Scene scene = new Scene(vbox, 800, 400);
        stage.setScene(scene);
        stage.show();
    }
}

