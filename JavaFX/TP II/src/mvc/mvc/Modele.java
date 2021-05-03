package mvc;

import java.util.LinkedList;

/**
 * Classe qui représente la structrure logique du programme compteur. Cette
 * structure est totalement indépendante de l'interface graphique utilisée pour
 * la représenter.
 * 
 * @author Nicolas Levasseur
 */
public class Modele {
    /**
     * Classe qui représente l'état à un certain moment du programme. Ces états
     * sont immuables et servent principalement seulement à contenir des
     * données telle qu'un titre et des valeurs de compteurs.
     * 
     * @author Nicolas Levasseur
     */
    private class Etat {
        /* Constantes pour la fabrication des etiquettes */
        private static final String DEBUT = "DEBUT";
        public static final String ADD = "ADDITION COMPTEUR";
        public static final String SOUS = "SOUSTRACTION COMPTEUR";
        public static final String MULT = "MULTIPLICATION COMPTEUR";
        public static final String DIV = "DIVISION COMPTEUR";
        public static final String G = " GAUCHE";
        public static final String D = " DROIT";
        public static final String FIN = "FIN";

        // Étiquette pour désigner l'état
        public final String etiquette;
        // Valeur du compteur de gauche
        public final int valeur1;
        // Valeur du compteur de droite
        public final int valeur2;


        /**
         * Constructeur vide qui est seulement appelé au début du programme. Il
         * représente le premier état possible pour l'application.
         */
        public Etat() {
            etiquette = DEBUT;
            valeur1 = 0;
            valeur2 = 0;
        }

        /**
         * Constructeur normal pour un état quelconque du programme qui
         * intialise tous les paramètres.
         * 
         * @param etiquette Identification du changement apporté par rapport au
         *                  dernier état.
         * @param valeur1   Valeur du compteur de gauche.
         * @param valeur2   Valeur du compteur de droite.
         */
        public Etat(String etiquette, int valeur1, int valeur2) {
            this.etiquette = etiquette;
            this.valeur1 = valeur1;
            this.valeur2 = valeur2;
        }

        @Override
        public String toString() {
            return etiquette + "\nRESULTAT COMPTEUR GAUCHE " + valeur1
                    + "\nRESULTAT COMPTEUR DROIT " + valeur2 + "\n";
        }
    }

    // Pile des états du programme, parfois aussi utilisée comme une liste.
    // On a choisi une liste chainée puisqu'elle implémente l'interface Deque,
    // qui est la nouvelle version du Stack en java (le Stack ne devrait plus
    // être utilisé si on se fie à la documentation)
    private LinkedList<Etat> etats;
    // Curseur sur les etats qui permet de savoir on est rendu où dans la ligne
    // du temps
    private int curseur;
    // Indice de la valeur modifiable en ce moment, peut être 1 ou 2
    private int iValeur;

    /**
     * Constructeur pour un modèle quelconque qui contiendra la logique.
     */
    public Modele() {
        etats = new LinkedList<>();
        iValeur = 1;
        etats.push(new Etat());
        curseur = 0;
    }

    /**
     * Accesseur (getter) pour la valeur1 de l'état courant.
     * 
     * @return La valeur qu'on modifie présentement.
     */
    public int getValeur1() {
        return etats.get(curseur).valeur1;
    }

    /**
     * Accesseur (getter) pour la valeur1 de l'état courant.
     * 
     * @return La valeur qu'on modifie présentement.
     */
    public int getValeur2() {
        return etats.get(curseur).valeur2;
    }

    /**
     * Accesseur (getter) pour la liste chainée d'états. On donne une version
     * modifiée de la pile d'états. Cette version est identique à la vraie, à
     * la différence près qu'on a ajouté un dernier état final à la pile .
     * 
     * @return Une copie de la pile d'états du modèle avec un état final ajouté
     *         à la fin.
     */
    LinkedList<Etat> getEtats() {
        LinkedList<Etat> temp = new LinkedList<>(etats);
        temp.subList(0, curseur).clear();
        temp.push(
                new Etat(Etat.FIN, temp.peek().valeur1, temp.peek().valeur2));
        return temp;
    }

    /**
     * Passeur (setter) de valeur pour iValeur, nous permet de changer le
     * compteur courant si la valeur passée est valide.
     * 
     * @param iValeur Nouvelle valeur du iValeur de la classe
     */
    public void setIValeur(int iValeur) {
        if (iValeur == 1 || iValeur == 2)
            this.iValeur = iValeur;
    }

    /**
     * Met à jour la pile d'états et le curseur sur celle-ci. Cette méthode
     * doit être appelée avant tout changement sur la liste d'états.
     */
    private void metAJourEtats() {
        if (curseur != 0) {
            etats.subList(0, curseur).clear();
            curseur = 0;
        }
    }

    /**
     * Méthode utilisée pour ajouter un certain montant au compteur courant.
     * 
     * @param montant Montant qui sera ajouté au compteur courant.
     */
    public void additionner(int montant) {
        metAJourEtats();
        if (iValeur == 1)
            etats.push(new Etat(Etat.ADD + Etat.G,
                    etats.peek().valeur1 + montant, etats.peek().valeur2));
        else if (iValeur == 2)
            etats.push(new Etat(Etat.ADD + Etat.D, etats.peek().valeur1,
                    etats.peek().valeur2 + montant));
    }

    /**
     * Méthode utilisée pour enlever un certain montant au compteur courant.
     * 
     * @param montant Montant qui sera enlevé au compteur courant.
     */
    public void soustraire(int montant) {
        metAJourEtats();
        if (iValeur == 1)
            etats.push(new Etat(Etat.SOUS + Etat.G,
                    etats.peek().valeur1 - montant, etats.peek().valeur2));
        else if (iValeur == 2)
            etats.push(new Etat(Etat.SOUS + Etat.D, etats.peek().valeur1,
                    etats.peek().valeur2 - montant));
    }

    /**
     * Méthode utilisée pour multiplier le compteur courant par un certain
     * nombre de fois.
     * 
     * @param fois Nombre qui sera multiplié au compteur courant.
     */
    public void multiplier(int fois) {
        metAJourEtats();
        if (iValeur == 1)
            etats.push(new Etat(Etat.MULT + Etat.G,
                    etats.peek().valeur1 * fois, etats.peek().valeur2));
        else if (iValeur == 2)
            etats.push(new Etat(Etat.MULT + Etat.D, etats.peek().valeur1,
                    etats.peek().valeur2 * fois));
    }

    /**
     * Méthode utilisée pour diviser le compteur courant par un certain nombre
     * de fois. On change aussi la liste d'états interne si besoin et on ajuste
     * le compteur.
     * 
     * @param fois Nombre qui sera divisé au compteur courant.
     */
    public void diviser(int fois) {
        metAJourEtats();
        if (iValeur == 1)
            etats.push(new Etat(Etat.DIV + Etat.G, etats.peek().valeur1 / fois,
                    etats.peek().valeur2));
        else if (iValeur == 2)
            etats.push(new Etat(Etat.DIV + Etat.D, etats.peek().valeur1,
                    etats.peek().valeur2 / fois));
    }

    /**
     * Incrémente le curseur par 1 si possible.
     */
    public void incCurs() {
        curseur += curseur + 1 < etats.size() ? 1 : 0;
    }

    /**
     * Décrémente le curseur par 1 si possible.
     */
    public void decCurs() {
        curseur -= curseur - 1 >= 0 ? 1 : 0;
    }
}
