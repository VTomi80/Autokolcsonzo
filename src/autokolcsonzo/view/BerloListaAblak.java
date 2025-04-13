package autokolcsonzo.view;

import autokolcsonzo.dao.BerloDAO;
import autokolcsonzo.model.Berlo;
import autokolcsonzo.util.AlertUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class BerloListaAblak {

    public static void megjelenit(List<Berlo> berlok) {
        Stage stage = new Stage();
        stage.setTitle("Bérlők listája");

        TableView<Berlo> tabla = new TableView<>();

        TableColumn<Berlo, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

        TableColumn<Berlo, String> nevCol = new TableColumn<>("Név");
        nevCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNev()));

        TableColumn<Berlo, String> jogsiCol = new TableColumn<>("Jogosítvány");
        jogsiCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJogositvanySzam()));

        TableColumn<Berlo, String> telCol = new TableColumn<>("Telefonszám");
        telCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefonszam()));

        tabla.getColumns().addAll(idCol, nevCol, jogsiCol, telCol);
        tabla.getItems().addAll(berlok);

        Button torlesGomb = new Button("Kiválasztott bérlő törlése");
        torlesGomb.setOnAction(e -> {
            Berlo kivalasztott = tabla.getSelectionModel().getSelectedItem();
            if (kivalasztott != null) {
                new BerloDAO().torolBerlo(kivalasztott.getId());
                tabla.getItems().remove(kivalasztott);
                AlertUtil.showInfo("A bérlő sikeresen törölve.");
            } else {
                AlertUtil.showError("Nincs kiválasztott bérlő.");
            }
        });

        VBox vbox = new VBox(10, tabla, torlesGomb);
        vbox.setStyle("-fx-padding: 10");

        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
