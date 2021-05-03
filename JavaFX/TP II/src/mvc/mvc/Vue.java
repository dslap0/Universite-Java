package mvc;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;


/**
 * Dans cette classe nous définissons les éléments graphiques de notre
 * application.
 * 
 * @author Adrien Charron
 * @author Michalis Famelis
 */
public class Vue extends Application {
    // On est obligé d'avoir un controleur ici à cause de la manière dont
    // JavaFX fonctionne.
    private static Controleur controleur;

    @Override
    public synchronized void start(Stage primaryStage) {

        try {
            // On crée la scène où on va afficher tout le reste
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);

            // On crée une HBox qui va contenir tous nos éléments centraux
            HBox compteurs = new HBox(10);

            // Deux VBox sont créées pour y mettre les compteurs 1 et 2
            VBox partie1 = new VBox();
            VBox partie2 = new VBox();

            // On crée des RadioButtons pour les compteurs
            ToggleGroup group = new ToggleGroup();
            RadioButton compteur1 = new RadioButton("Compteur 1");
            compteur1.setToggleGroup(group);
            compteur1.setSelected(true);
            RadioButton compteur2 = new RadioButton("Compteur 2");
            compteur2.setToggleGroup(group);

            // Le texte représentant la valeur des compteurs
            Text valeur1 = new Text("0");
            Text valeur2 = new Text("0");

            // On ajoute aux parties correspondantes leur compteur et leur
            // texte respectifs
            partie1.getChildren().add(compteur1);
            partie1.getChildren().add(valeur1);
            partie2.getChildren().add(compteur2);
            partie2.getChildren().add(valeur2);

            // On ajoute les deux VBox à la HBox
            compteurs.getChildren().add(partie1);
            compteurs.getChildren().add(partie2);

            // On crée les boutons pour manipuler les compteurs
            Button inc = new Button("+1");
            Button mult = new Button("*2");
            Button div = new Button("/2");
            Button dec = new Button("-1");

            // On aligne tout au centre
            compteurs.setAlignment(Pos.CENTER);
            partie1.setAlignment(Pos.CENTER);
            partie2.setAlignment(Pos.CENTER);
            BorderPane.setAlignment(inc, Pos.CENTER);
            BorderPane.setAlignment(mult, Pos.CENTER);
            BorderPane.setAlignment(div, Pos.CENTER);
            BorderPane.setAlignment(dec, Pos.CENTER);

            // On crée le menu
            MenuBar menuBar = new MenuBar();
            Menu menu = new Menu("Menu");
            menuBar.getMenus().add(menu);

            // On crée les éléments du menu, soit undo, redo et historique
            MenuItem undo = new MenuItem("Undo");
            MenuItem redo = new MenuItem("Redo");
            MenuItem historique = new MenuItem("Historique");

            // Raccourcis claviers pour undo et redo
            undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
            redo.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));

            // On ajoute les éléments au menu
            menu.getItems().addAll(undo, redo, historique);

            // On met la barre de menu dans une HBox pour pouvoir la mettre
            // dans un BorderPane
            HBox menuDisplay = new HBox(menuBar);

            // On crée un nouveau BorderPane pour y mettre le menu et
            // l'incrémenteur pour que les deux éléments puissent être dans le
            // haut du BorderPane principal
            BorderPane hautDePage = new BorderPane();

            // On positionne les éléments dans le haut de la page
            hautDePage.setTop(menuDisplay);
            hautDePage.setBottom(inc);

            root.setTop(hautDePage);
            root.setBottom(dec);
            root.setLeft(mult);
            root.setRight(div);
            root.setCenter(compteurs);

            // Le controleur manipule tout evenement
            inc.setOnAction(action -> controleur.inc());
            dec.setOnAction(action -> controleur.dec());
            mult.setOnAction(action -> controleur.mult());
            div.setOnAction(action -> controleur.div());
            undo.setOnAction(action -> controleur.undo());
            redo.setOnAction(action -> controleur.redo());
            historique.setOnAction(action -> controleur.historique());

            // On instancie le controleur
            controleur = new Controleur(new Modele(), valeur1, valeur2);

            // On change de compteur lorsqu'on clique
            compteur1.setOnAction(action -> controleur.changerCompteur(1));
            compteur2.setOnAction(action -> controleur.changerCompteur(2));

            // On montre la fenêtre générée
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
