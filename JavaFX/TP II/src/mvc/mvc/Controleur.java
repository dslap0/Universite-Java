package mvc;

import javafx.scene.text.Text;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Classe qui fait le lien entre la logique de l'application et les éléments
 * graphiques, et qui interprète les actions posées par l'utilisateur.
 * 
 * @author Adrien Charron
 * @author Michalis Famelis
 * @author Nicolas Levasseur
 */
public class Controleur {
	// Partie logique du programme
	private Modele modele;
	// Parties graphiques modifiables du programme
	private Text vueGauche, vueDroite;

	/**
	 * Constructeur pour un controleur qui pourra modifier le modèle et les
	 * éléments de la vue.
	 * 
	 * @param modele    Modèle de l'application, gère la logique.
	 * @param vueGauche Compteur de gauche de la vue.
	 * @param vueDroite Compteur de droite de la vue.
	 */
	public Controleur(Modele modele, Text vueGauche, Text vueDroite) {
		this.modele = modele;
		this.vueGauche = vueGauche;
		this.vueDroite = vueDroite;
	}

	/**
	 * Méthode utilisée en interne lorsqu'on modifie un élément du controleur
	 * pour réfléter ces changements sur la vue.
	 */
	private void updateText() {
		vueGauche.setText(String.valueOf(modele.getValeur1()));
		vueDroite.setText(String.valueOf(modele.getValeur2()));
	}

	/**
	 * Méthode utilisée pour incrémenter le compteur courant.
	 */
	public void inc() {
		modele.additionner(1);
		updateText();
	}

	/**
	 * Méthode utilisée pour décrémenter le compteur courant.
	 */
	public void dec() {
		modele.soustraire(1);
		updateText();
	}

	/**
	 * Méthode utilisée pour doubler le compteur courant.
	 */
	public void mult() {
		modele.multiplier(2);
		updateText();
	}

	/**
	 * Méthode utilisée pour diviser de moitié (avec une divison entière) le
	 * compteur courant.
	 */
	public void div() {
		modele.diviser(2);
		updateText();
	}

	/**
	 * Méthode utilisée pour changer le compteur modifiable.
	 */
	public void changerCompteur(int iCompteur) {
		modele.setIValeur(iCompteur);
	}

	/**
	 * Méthode utilisée pour revenir à un état du système précédent.
	 */
	public void undo() {
		modele.incCurs();
		updateText();
	}

	/**
	 * Méthode utilisée pour aller à un état du système ayant été altéré par un
	 * retour en arrière.
	 */
	public void redo() {
		modele.decCurs();
		updateText();
	}

	/**
	 * Cette méthode affiche l'historique dans un fichier historique.txt.
	 */
	public void historique() {
		// On crée le fichier txt ainsi qu'un BufferedWriter pour y écrire du
		// texte.
		try (
				FileWriter fw = new FileWriter("historique.txt", false)) {
			BufferedWriter writer = new BufferedWriter(fw);

			writer.write("----------\n");

			// Pour utiliser la LinkedList comme un Stack, il faut
			// l'inverser puisqu'elle "push" les éléments au début de la liste.
			LinkedList<?> listeTexte = modele.getEtats();
			Collections.reverse(listeTexte);

			// On parcourt la linked list pour écrire chaque opération faite.
			for (var texte : listeTexte) {
				try {
					writer.write("----------\n");
					writer.write(texte.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			writer.write("----------\n----------");
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
