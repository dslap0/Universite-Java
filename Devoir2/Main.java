import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    /**
     * Méthode statique utilitaire permettant de déterminer si une string
     * peut-être convertie en Integer
     *
     * @param s
     * @return True si la string peut être convertie en Integer, false sinon
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Méthode statique utilitaire permettant de déterminer si une string
     * peut-être convertie en Double
     *
     * @param s
     * @return True si la string peut être convertie en Double, false sinon
     */
    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            File myObj = new File("./assets/input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (!line.isEmpty()) {
                    System.out.println("----------");
                    String[] colonne = line.split(" ");
                    String sens = colonne[0];
                    String listeEnString = colonne[1];
                    System.out.println("Sens du tri: " + sens);
                    System.out.println("Liste originale: " + listeEnString);

                    ListeDoublementChainee ldc =
                            new ListeDoublementChainee(sens);

                    /**
                     * Cette fonction doit ajouter tous les elements d'une
                     * liste en string dans la liste doublement chainee. Vous
                     * devez recuperer chaque element de la liste et utiliser
                     * le bon cast sur celui-ci pour ensuite pouvoir comparer
                     * les differents types.
                     * 
                     * @param listeEnString Exemple: '[1,2,3]'
                     */
                    ldc.ajouterListe(listeEnString);

                    /**
                     * Ces fonctions impriment du début vers la fin, ou de la
                     * fin vers le début le contenu de la liste doublement
                     * chainée avec le format présenté dans l'énoncé.
                     * 
                     * @param listeEnString Exemple: '[1,2,3]'
                     */
                    ldc.imprimerListeDuDebut();
                    ldc.imprimerListeDeLaFin();

                    System.out.println("\n----------");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Une erreur est survenue.");
            e.printStackTrace();
        }
    }
}
