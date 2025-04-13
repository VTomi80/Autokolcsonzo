package autokolcsonzo.view;

import autokolcsonzo.dao.BerlesDAO;
import autokolcsonzo.dao.BerloDAO;
import autokolcsonzo.dao.AutoDAO;
import autokolcsonzo.model.Berlo;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RiportAblak {

    //ez egy viszonylag egyszerű osztály, a riport ablakot tartalmazza, benne 3 gombot
    //a gombok lenyomására meghívom a megfelelő osztály listázó metódusát

    private static final BerloDAO berloDAO = new BerloDAO();
    private static final AutoDAO autoDAO = new AutoDAO();

    public static void megjelenit() {
        Stage riportStage = new Stage();
        riportStage.setTitle("Riportok");

        Button listazBerlokBtn = new Button("Bérlők listázása");
        Button listazAutokBtn = new Button("Autók listázása");
        Button listazBerlesekBtn = new Button("Bérlések listázása");

        listazBerlokBtn.setOnAction(e -> {
            BerloListaAblak.megjelenit(berloDAO.listazBerlok());
        });

             listazAutokBtn.setOnAction(e -> {
              AutoListaAblak.megjelenit(autoDAO.listazAutok());
        });

        listazBerlesekBtn.setOnAction(e -> {
            BerlesDAO berlesDAO = new BerlesDAO();
            BerlesListaAblak.megjelenit(berlesDAO.listazBerleseket());
        });

        VBox vbox = new VBox(10, listazBerlokBtn, listazAutokBtn, listazBerlesekBtn);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        riportStage.setScene(new Scene(vbox, 300, 200));
        riportStage.show();
    }
}
