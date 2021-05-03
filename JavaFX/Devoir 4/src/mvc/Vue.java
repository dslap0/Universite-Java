package mvc;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.control.ToggleGroup;

/*
 * Dans cette classe nous definissons les éléments graphiques de notre
 * application.
 *
 * NB: voir aussi lignes 64-74!
 */
public class Vue extends Application {

	private static Controleur controleur;

	@Override
	public void start(Stage primaryStage) {

		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 200, 200);

			HBox compteurs = new HBox();

			VBox partie1 = new VBox();
			VBox partie2 = new VBox();

			ToggleGroup group = new ToggleGroup();
			RadioButton compteur1 = new RadioButton("Compteur 1");
			compteur1.setToggleGroup(group);
			compteur1.setSelected(true);
			RadioButton compteur2 = new RadioButton("Compteur 2");
			compteur2.setToggleGroup(group);

			Text valeur1 = new Text("0");
			Text valeur2 = new Text("0");

			partie1.getChildren().add(compteur1);
			partie1.getChildren().add(valeur1);

			partie2.getChildren().add(compteur2);
			partie2.getChildren().add(valeur2);

			compteurs.getChildren().add(partie1);
			compteurs.getChildren().add(partie2);

			compteurs.setAlignment(Pos.CENTER);
			partie1.setAlignment(Pos.CENTER);
			partie2.setAlignment(Pos.CENTER);

			BorderPane.setAlignment(valeur1, Pos.CENTER);
			BorderPane.setAlignment(valeur2, Pos.CENTER);
			BorderPane.setAlignment(compteur1, Pos.CENTER);
			BorderPane.setAlignment(compteur2, Pos.CENTER);
			BorderPane.setAlignment(partie1, Pos.CENTER);
			BorderPane.setAlignment(partie2, Pos.CENTER);
			BorderPane.setAlignment(compteurs, Pos.CENTER);

			Button inc = new Button("+1");
			Button dub = new Button("*2");
			Button div = new Button("/2");
			Button dec = new Button("-1");

			BorderPane.setAlignment(inc, Pos.CENTER);
			BorderPane.setAlignment(dub, Pos.CENTER);
			BorderPane.setAlignment(div, Pos.CENTER);
			BorderPane.setAlignment(dec, Pos.CENTER);

			root.setTop(inc);
			root.setBottom(dec);
			root.setLeft(dub);
			root.setRight(div);
			root.setCenter(compteurs);

			// Le controleur manipule tout evenement.

			inc.setOnAction((action) -> {
				controleur.inc();
			});

			dec.setOnAction(action -> {
				controleur.dec();
			});

			dub.setOnAction(action -> {
				controleur.dub();
			});

			div.setOnAction(action -> {
				controleur.div();
			});

			/*
			 * En raison de la conception des applications JavaFX, nous sommes obligés de
			 * créer le modèle et le controleur ici.
			 *
			 * Notez cependant que nous passons l'instance du modèle directement dans le
			 * constructeur du controleur; nous n'y avons pas d'autre accès.
			 *
			 * Pour faciliter les choses, ici le constructeur ne prend pas la Vue entière,
			 * mais juste le sous-ensemble de la Vue (l'objet Text) qu'il doit manipuler.
			 */
			controleur = new Controleur(new Modele(), valeur1, valeur2);

			compteur1.setOnAction((action) -> {
				controleur.changerCompteur(1);
			});

			compteur2.setOnAction((action) -> {
				controleur.changerCompteur(2);
			});

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
