package mvc;

import javafx.scene.text.Text;

public class Controleur {

	private Modele modele;
	private Text vue1;
	private Text vue2;
	private int iVue;

	public Controleur(Modele m, Text v, Text w) {
		this.modele = m;
		this.vue1 = v;
		this.vue2 = w;
		this.iVue = 1;
	}

	public void inc() {
		this.modele.ajouter(1);
		this.updateText();
	}

	public void dec() {
		this.modele.supprimer(1);
		this.updateText();
	}

	public void dub() {
		this.modele.multiplier(2);
		this.updateText();
	}

	public void div() {
		this.modele.diviser(2);
		this.updateText();
	}

	private void updateText() {
		if (iVue == 1)
			vue1.setText(String.valueOf(this.modele.getValeur()));
		else if (iVue == 2)
			vue2.setText(String.valueOf(this.modele.getValeur()));
	}

	public void changerCompteur(int iCompteur) {
		iVue = iCompteur;
		this.modele.setValeur(iCompteur);
	}
}
