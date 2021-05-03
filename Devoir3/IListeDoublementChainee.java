public interface IListeDoublementChainee {
    public void ajouterListe(String listeEnString);

    public void ajouterNoeud(Item<?> item);

    public String imprimerListeDuDebut();

    public String imprimerListeDeLaFin();
}
