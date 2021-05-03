import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Cette classe permet de trier une série de listes dans un fichier et de
 * passer ces listes triées à un serveur sur un port quelconque.
 * 
 * @author Nicolas Levasseur
 * @author Michalis Famelis
 */
public class LeClient {
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
		// On utilise le try with resource pour s'assurer que Java ferme le
		// socket peu importe les erreurs captées dans le programme.
		try (
				Socket monSocket = new Socket("127.0.0.1", Port.NUMBER)) {
			System.out.println(String
					.format("Demarrage du client sur port %d.", Port.NUMBER));

			// On crée un writer sur le canal du socket crée précédemment
			ObjectOutputStream writer =
					new ObjectOutputStream(monSocket.getOutputStream());

			try {
				File myFile = new File("./assets/input.txt");

				// On utilise le try with resource pour que Java ferme le
				// scanner peu importe les erreurs produites
				try (
						Scanner myReader = new Scanner(myFile)) {
					while (myReader.hasNextLine()) {
						String line = myReader.nextLine();
						if (!line.isEmpty()) {
							// String décrivant les conditions initiales et
							// faisant un peu de mise en page
							String toPass = "----------\n";
							String[] colonne = line.split(" ");
							String sens = colonne[0];
							String listeEnString = colonne[1];
							toPass += "Sens du tri: " + sens + "\n";
							toPass +=
									"Liste originale: " + listeEnString + "\n";

							writer.writeObject(toPass);

							// On crée et on trie la liste doublement chainée
							ListeDoublementChainee ldc =
									new ListeDoublementChainee(sens);
							ldc.ajouterListe(listeEnString);

							writer.writeObject(ldc);

							// On refait un peu de mise en page
							toPass = "----------\n";
							writer.writeObject(toPass);
						}
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("Le fichier n'a pas été trouvé.");
				e.printStackTrace();
			}
			// Le compilateur nous laisse fermer le writer sans passer par un
			// try with resource, donc cette formulation semble correcte.
			writer.close();

		} catch (ConnectException x) {
			System.out.println("Connexion impossible sur port " + Port.NUMBER
					+ ": pas de serveur.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
