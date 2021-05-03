import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

import static java.util.stream.Collectors.*;

import organisme.*;

/**
 * This class simulates a lake and the passage of time in the lake. It is
 * centered on the energy transfers between the different oganisms and between
 * the organisms and the Sun. The lake is also in charge of keeping track of
 * its resident, which includes births and deaths.
 * 
 * @author Simon Génier (presumably)
 * @author Adrien Charron
 * @author Nicolas Levasseur
 */
public final class Lac {
    // Solar energy distributed to the plants at each tick
    private final int energieSolaire;
    // All alive plants in the lake
    private final List<Plante> plantes;
    // All alive herbivores in the lake
    private final List<Herbivore> herbivores;
    // All alive carnivores in the lake
    private final List<Carnivore> carnivores;
    // Used to generate random numbers
    private final Random rnd;

    /**
     * Constructor for Lac, sets all its parameters to given values and
     * alimentation herbivore to an empty Map that can be modified later.
     * 
     * @param energieSolaire Solar energy distributed to the plants at each
     *                       tick
     * @param plantes        List of all of the alive plants in the lake
     * @param herbivores     List of all of the alive herbivores in the lake
     */
    public Lac(int energieSolaire, List<Plante> plantes,
            List<Herbivore> herbivores, List<Carnivore> carnivores) {
        this.energieSolaire = energieSolaire;
        this.plantes = plantes;
        this.herbivores = herbivores;
        this.carnivores = carnivores;
        this.rnd = new Random();
    }


    /**
     * We simulate what happens for all of the plants during a tick.
     */
    private void plantTick() {
        // We cycle through the plants and get the total amount of energy
        // shared by the plants
        double sommeEnergie = 0;
        for (Plante plante : plantes)
            sommeEnergie += plante.getEnergie();

        // We give energy to each plant in the plants list according to its
        // current energy level and update their age
        for (int i = 0; i < plantes.size(); i++) {
            plantes.get(i).augmenteAge();

            // This division is safe to make because we would not be in this
            // for loop if sommeEnergie = 0 because the energy of a plant can
            // never be less than 0 and we know we have more than 0 plants if
            // we are in the loop
            int nbrNouvellesPlantes =
                    plantes.get(i).absortionEnergie(plantes.get(i).getEnergie()
                            * energieSolaire / sommeEnergie);


            // If we have a negative number of new plants, we remove the plant
            if (nbrNouvellesPlantes < 0)
                plantes.remove(i);

            // If we have a positive number of new plants, we create new plants
            // until the number is not more than 0
            else
                while (nbrNouvellesPlantes > 0) {
                    plantes.add(plantes.get(i).copy());
                    nbrNouvellesPlantes--;
                }
        }
    }

    /**
     * Method called exclusively in herbivoreTick(), check if the Map
     * alimentationHerbivore needs updating and updates it if needed.
     * 
     * @param nombreRepas            number of time the herbivore eats
     * @param herbivore              the current herbivore that is trying to
     *                               eat
     * @param alimentationHerbivores links the different herbivore species to
     *                               what they can currently eat in the lake.
     */
    private void updateAlimentationHerbivore(int nombreRepas,
            Herbivore herbivore,
            Map<String, List<Plante>> alimentationHerbivores) {
        // We test if we need to update alimentationHerbivores
        if (nombreRepas != 0 && !alimentationHerbivores
                .containsKey(herbivore.getNomEspece())) {

            // We create a list to put all of the aliments available in the
            // lake and we put them in it
            List<Plante> aliments = new ArrayList<Plante>();
            for (int i = 0; i < plantes.size(); i++)
                if (herbivore.getAliments()
                        .contains(plantes.get(i).getNomEspece()))
                    aliments.add(plantes.get(i));

            alimentationHerbivores.put(herbivore.getNomEspece(), aliments);
        }
    }

    /**
     * We simulate what happens for all of the herbivores during a tick.
     */
    private void herbivoreTick() {
        // Map that associates an herbivore's spieces name to a list of all
        // the available and alive plants they can eat
        Map<String, List<Plante>> alimentationHerbivores =
                new HashMap<String, List<Plante>>();

        for (int i = 0; i < herbivores.size(); i++) {
            // Copy of herbivores that is used to call getters, did not want
            // to spend too much time on how Java will handle changes made to
            // this herbivore and will they be reflected on the herbivore in
            // herbivores so we do not modify this herbivore
            final Herbivore herbivore = herbivores.get(i);

            herbivores.get(i).augmenteAge();

            // We find how much times does the herbivore eat
            double voracite[] = herbivore.alimentation();

            updateAlimentationHerbivore(voracite.length, herbivore,
                    alimentationHerbivores);

            // We take all of the available plants to eat for the herbivore
            List<Plante> aliments =
                    alimentationHerbivores.get(herbivore.getNomEspece());

            // We determine how much energy did the herbivore eat and deduct it
            // from the plants
            double energieAbsorbee = 0;
            for (int j = 0; j < voracite.length; j++) {
                if (!aliments.isEmpty()) {
                    int iPlanteMangee = rnd.nextInt(aliments.size());
                    energieAbsorbee +=
                            aliments.get(iPlanteMangee).mangee(voracite[j]);
                }
            }

            int nbrNouveauxHerbivores =
                    herbivores.get(i).absortionEnergie(energieAbsorbee);

            // If we have a negative number of new herbivores, we remove the
            // herbivore
            if (nbrNouveauxHerbivores < 0)
                herbivores.remove(i);

            // If we have a positive number of new herbivores, we create new
            // herbivores
            else
                while (nbrNouveauxHerbivores > 0) {
                    herbivores.add(herbivore.copy());
                    nbrNouveauxHerbivores--;
                } // end while
        } // end for on all herbivores
    }

    /**
     * Method called exclusively in carnivoreTick(), fill the
     * alimentationHerbivores map for the given carnivore.
     * 
     * @param nombreRepas           number of time the carnivore eats
     * @param carnivore             the current carnivore that is trying to eat
     * @param alimentationCarnivore links the list (Herbivore = 0, Carnivore =
     *                              1) and the index in the list of each
     *                              potential prey with itself, and the map
     *                              contains all of the potential preys.
     */
    private void fillAlimentationCarnivore(int nombreRepas,
            Carnivore carnivore, Map<int[], Animal> alimentationCarnivores) {
        // We test if we need to fill alimentationCarnivores
        if (nombreRepas != 0) {
            // We loop on all herbivores to find which should be added to
            // alimentationCarnivores
            for (int i = 0; i < herbivores.size(); i++)
                if (carnivore.getAliments()
                        .contains(herbivores.get(i).getNomEspece())
                        && carnivore.getEnergie() > herbivores.get(i)
                                .getEnergie()) {
                    int x[] = {0, i};
                    alimentationCarnivores.put(x, herbivores.get(i));
                }
            // We loop on all carnivores to find which should be added to
            // alimentationCarnivores
            for (int i = 0; i < carnivores.size(); i++)
                if (carnivore.getAliments()
                        .contains(carnivores.get(i).getNomEspece())
                        && carnivore.getEnergie() > carnivores.get(i)
                                .getEnergie()) {
                    int x[] = {1, i};
                    alimentationCarnivores.put(x, carnivores.get(i));
                }
        }
    }

    /**
     * This method is called exclusively in carnivoreTick() and handles the
     * eating of another animal for a carnivore, returning only the raw energy
     * output of this action.
     * 
     * @param nombreRepas            number of time the carnivore is going to
     *                               eat
     * @param alimentationCarnivores links the list (Herbivore = 0, Carnivore =
     *                               1) and the index in the list of each
     *                               potential prey with itself, and the map
     *                               contains all of the potential preys.
     * @return the energy that is ingested by the carnivore after its killing
     *         spree
     */
    private double nourrirCarnivore(int nombreRepas,
            Map<int[], Animal> alimentationCarnivores) {
        // We initialize the total amount of energy and create two list to help
        // us delete preys that are eaten
        double energieAbsorbee = 0.0;
        List<Integer> listDeListeEtIndiceHerbivores = new ArrayList<Integer>();
        List<Integer> listDeListeEtIndiceCarnivores = new ArrayList<Integer>();

        for (int j = 0; j < nombreRepas; j++)
            if (!alimentationCarnivores.isEmpty()) {
                // We find which aliment will be eaten and we save its
                // corresponding list and index in that list
                List<int[]> keyList =
                        new ArrayList<int[]>(alimentationCarnivores.keySet());
                int[] listeEtIndice = keyList.get(rnd.nextInt(keyList.size()));

                // We update the energy ingested
                energieAbsorbee +=
                        alimentationCarnivores.get(listeEtIndice).mangee(1.0);

                // We remove the eaten prey from the available preys and we add
                // it to the corrresponding list to be deleted later
                alimentationCarnivores.remove(listeEtIndice);

                if (listeEtIndice[0] == 0)
                    listDeListeEtIndiceHerbivores.add(listeEtIndice[1]);
                else if (listeEtIndice[0] == 1)
                    listDeListeEtIndiceCarnivores.add(listeEtIndice[1]);
            }

        // We sort our lists of prey to be deleted in reverse order and we
        // delete them in that order
        Collections.sort(listDeListeEtIndiceHerbivores,
                Collections.reverseOrder());
        Collections.sort(listDeListeEtIndiceCarnivores,
                Collections.reverseOrder());
        for (int listeEtIndiceHerbivores : listDeListeEtIndiceHerbivores)
            herbivores.remove(listeEtIndiceHerbivores);
        for (int listeEtIndiceCarnivores : listDeListeEtIndiceCarnivores)
            carnivores.remove(listeEtIndiceCarnivores);

        return energieAbsorbee;
    }

    /**
     * We simulate what happens for all of the carnivores during a tick.
     */
    private void carnivoreTick() {
        for (int i = 0; i < carnivores.size(); i++) {
            // Copy of the current carnivore that is used to call getters, did
            // not want to spend too much time on how Java will handle changes
            // made to this carnivore and will they be reflected on the
            // carnivore in carnivores so we do not modify this carnivore.
            final Carnivore carnivore = carnivores.get(i);

            // Map that associates all of the available and alive animals that
            // can be eaten by a given carnivore to its corresponding list
            // (herbivores = 0, carnivores = 1) and its indice in that list
            Map<int[], Animal> alimentationCarnivores =
                    new HashMap<int[], Animal>();

            carnivores.get(i).augmenteAge();

            // We find how much times does the carnivore eat
            int nombreRepas = carnivore.rechercheRepas();

            fillAlimentationCarnivore(nombreRepas, carnivore,
                    alimentationCarnivores);

            double energieAbsorbee =
                    nourrirCarnivore(nombreRepas, alimentationCarnivores);

            int nbrNouveauxCarnivores =
                    carnivores.get(i).absortionEnergie(energieAbsorbee);

            // If we have a negative number of new carnivores, we remove the
            // carnivore
            if (nbrNouveauxCarnivores < 0)
                carnivores.remove(i);

            // If we have a positive number of new carnivores, we create new
            // carnivores
            else
                while (nbrNouveauxCarnivores > 0) {
                    carnivores.add(carnivore.copy());
                    nbrNouveauxCarnivores--;
                } // end while
        } // end for on all carnviores
    }

    /**
     * Make the simulation progress by one tick. Calls other, more specific
     * methods to deal with each type of organism.
     */
    public void tick() {
        plantTick();
        herbivoreTick();
        carnivoreTick();
    }

    /**
     * We print a report of the state of each type of organism living in the
     * lake.
     * 
     * @param out Used to print the report to the user
     */
    public void imprimeRapport(PrintStream out) {
        // We know that those usually aren't safe casts to make, but since
        // Plante, Herbivore and Carnivore are all Organisme, this cast
        // shouldn't cause problems, we should just be careful about changing
        // this method or the heritage mechanism for Plante, Herbivore or
        // Carnivore
        imprimeRapportOrganisme(out, (List<Organisme>) (List<?>) plantes,
                "plantes");
        imprimeRapportOrganisme(out, (List<Organisme>) (List<?>) herbivores,
                "herbivores");
        imprimeRapportOrganisme(out, (List<Organisme>) (List<?>) carnivores,
                "carnivores");
    }

    /**
     * General method to print a report for a list of organisms living in the
     * lake. Note that we did not make it, so we will not comment it either.
     * 
     * @param out Used to print the report to the user
     */
    public void imprimeRapportOrganisme(PrintStream out,
            List<Organisme> organismes, String typeOrganisme) {
        var especes =
                organismes.stream().collect(groupingBy(Organisme::getNomEspece,
                        summarizingDouble(Organisme::getEnergie)));
        out.println("Il reste " + especes.size() + " espèces de "
                + typeOrganisme + ".");
        for (var entry : especes.entrySet()) {
            var value = entry.getValue();
            out.printf(
                    "%s: %d individus qui contiennent en tout %.2f unités " +  
                    "d'énergie.\n\n", entry.getKey(), value.getCount(), 
                    value.getSum());
        }
    }
}
