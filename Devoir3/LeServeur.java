import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Cette classe est un serveur qui attend les commande de la classe LeCLient
 * pour s'exécuter. Le serveur va imprimer dans un fichier output.txt les
 * listes doublement chainées ordonnées à partir des données envoyées par le
 * client.
 *
 * @author Adrien Charron
 * @author Michalis Famelis
 */
public class LeServeur {
	/**
	 * Cette méthode sert à écrire dans un fichier output.txt les listes
	 * doublement chainées envoyées par la classe LeClient selon les
	 * spécifications du devoir2.
	 * 
	 * @param d est la liste doublement chainée recueillie du stream.
	 * @throws IOException est l'exception nécessaire au FileWriter.
	 */
	private static void faireDesChoses(IListeDoublementChainee d)
			throws IOException {

		// On crée un FileWriter pour écrire les données à la fin de notre
		// fichier
		// output.txt.
		FileWriter fw = new FileWriter("output.txt", true);

		// On écrit les données dans notre fichier grâce à un BufferedWriter.
		try (
				BufferedWriter writer = new BufferedWriter(fw)) {
			// On utilise les méthodes de la classe ListeDoublementChainee
			// pour imprimer les liste du début à la fin et inversement.
			writer.write(d.imprimerListeDuDebut());
			writer.write(d.imprimerListeDeLaFin());

			// On vide le BufferedWriter.
			writer.flush();
		}
	}

	/**
	 * Cette méthode overload la précédente méthode du même nom pour écrire des
	 * String dans notre fichier output.txt à partir des données envoyées par
	 * LeClient.
	 * 
	 * @param s est le String recueilli du stream.
	 * @throws IOException est l'exception nécessaire au FileWriter.
	 */
	private static void faireDesChoses(String s) throws IOException {

		// On crée un FileWriter pour écrire les données à la fin de notre
		// fichier output.txt.
		FileWriter fw = new FileWriter("output.txt", true);

		// On écrit les données dans notre fichier grâce à un BufferedWriter.
		try (
				BufferedWriter writer = new BufferedWriter(fw)) {
			writer.write(s);
			// On vide le BufferedWriter.
			writer.flush();
		}
	}

	public static void main(String[] args) {
		try (
				ServerSocket server = new ServerSocket(Port.NUMBER, 1)) {
			System.out.println(String
					.format("Demarrage du serveur sur port %d.", Port.NUMBER));

			Socket client = server.accept();
			System.out.println(
					"Un client ayant l'addresse " + client.getInetAddress()
							+ " a connecté sur port " + client.getLocalPort());

			InputStream clientStream = client.getInputStream();

			// On crée un ObjectInputStream à partir du stream envoyé par le
			// client.
			ObjectInputStream objectReader =
					new ObjectInputStream(clientStream);

			try {
				while (true) {
					try {

						// Le stream est désérialisé ici.
						Object obj = objectReader.readObject();

						// Si les données envoyées sont de type String, on
						// transtype l'object pris du stream en string et on
						// appelle la méthode faireDesChoses correspondante.
						if (obj instanceof String) {
							String unString = (String) obj;
							faireDesChoses(unString);
						}
						// Sinon, l'object est une liste doublement chainée et
						// on le cast ainsi, puis on appelle la méthode
						// faireDesChoses correspondante.
						else {
							IListeDoublementChainee uneListe =
									(IListeDoublementChainee) obj;
							faireDesChoses(uneListe);
						}

					} catch (ClassNotFoundException e) {
						System.err.println(
								"L'objet lu n'etait pas une instance de la classe attendue.");
					}

					// Code mort pour que le compilateur ne se plaigne pas du
					// while(true).
					if (false)
						break;
				}
			} catch (EOFException eof) {
				/*
				 * Rien à faire ici. Le EOFException signifie juste que
				 * l'objectReader n'arrive pas à recevoir plus d'objets par
				 * clientStream. Cela nous permet de sortir du boucle while
				 * (true) quand il n'y a plus d'objets à deserialiser.
				 */
				System.out.println("Plus des choses à lire. Au revoir.");
			}

			objectReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
