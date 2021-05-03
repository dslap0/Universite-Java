package mvc;

/*
 *	Notez que cette classe est completement indépendante de la representation.
 *	On pourraît facilement definir un interface complètement different.
 */
public class Modele {
	private int valeur;
	private int valeur1;
	private int valeur2;

	public Modele() {
		this.valeur1 = 0;
		this.valeur2 = 0;
	}

	public Modele(int v, int w) {
		this.valeur1 = v;
		this.valeur2 = w;
		this.valeur = valeur1;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int iValeur) {
		if (iValeur == 1) {
			valeur2 = valeur;
			valeur = valeur1;
		} else if (iValeur == 2) {
			valeur1 = valeur;
			valeur = valeur2;
		}
	}

	public void ajouter(int montant) {
		valeur += montant;
	}

	public void supprimer(int montant) {
		valeur -= montant;
	}

	public void multiplier(int fois) {
		valeur *= fois;
	}

	public void diviser(int fois) {
		valeur /= fois;
	}
}
