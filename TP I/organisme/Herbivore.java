package organisme;

import java.util.Set;

/**
 * The following class is the class called when creating a new herbivore. It
 * also handles the methods for the herbivores called in the class Lac with the
 * method tick. It will determine the action of each herbivore every cycle,
 * whether they reproduce, die or do nothing.
 *
 * @author Adrien Charron
 */
public class Herbivore extends Animal {

    // The minimal efficiency at which the herbivore can eat a plant.
    private final double voraciteMin;

    // The maximal efficiency at which the herbivore can eat a plant.
    private final double voraciteMax;



    // Getters for each parameters
    public double getVoraciteMin() {
        return voraciteMin;
    }

    public double getVoraciteMax() {
        return voraciteMax;
    }


    /**
     * Constructor for the class Herbivore, initializes every parameters
     * whenever a new herbivore is created.
     * 
     * @param nomEspece         Specie's name
     * @param besoinEnergie     Minimal quantity of energy required to maintain
     *                          itself each cycle
     * @param efficaciteEnergie Efficiency in converting raw energy into life
     *                          energy, is used when there are leftovers in
     *                          energy at the end of a cycle.
     * @param resilience        The probability to die in a cycle if the needed
     *                          energy to survive (besoinEnergie) isn't
     *                          obtained.
     * @param fertilite         Probability of reproduction in a cycle if the
     *                          needed energy (besoinEnergie) is surpassed.
     * @param ageFertilite      The age at which an herbivore can start
     *                          reproducing.
     * @param energieEnfant     The intial energy of a herbivore when created.
     * @param debrouillardise   Parameter that determines the probability of
     *                          finding something to eat.
     * @param voraciteMin       The minimal efficiency at which the herbivore
     *                          can eat a plant.
     * @param voraciteMax       The maximal efficiency at which the herbivore
     *                          can eat a plant.
     * @param aliments          Specifies the different species an herbivore
     *                          can eat.
     * @param tailleMaximum     The maximum size an herbivore can grow to.
     */
    protected Herbivore(String nomEspece, double besoinEnergie,
            double efficaciteEnergie, double resilience, double fertilite,
            int ageFertilite, double energieEnfant, double debrouillardise,
            double voraciteMin, double voraciteMax, Set<String> aliments,
            double tailleMaximum) {

        super(nomEspece, besoinEnergie, efficaciteEnergie, resilience,
                fertilite, ageFertilite, energieEnfant, debrouillardise,
                aliments, tailleMaximum);

        this.voraciteMin = voraciteMin;
        this.voraciteMax = voraciteMax;
    }


    @Override
    public Herbivore copy() {
        return new Herbivore(nomEspece, besoinEnergie, efficaciteEnergie,
                resilience, fertilite, ageFertilite, energieEnfant,
                debrouillardise, voraciteMin, voraciteMax, aliments,
                tailleMaximum);
    }

    /**
     * This method sets the efficiency at which the herbivore consumes a plant
     * for each of his meals.
     * 
     * @return An array of the voracity for each meal the herbivore manages to
     *         get.
     */
    public double[] alimentation() {
        // Number of meal to be eaten.
        int nombreRepas = rechercheRepas();

        // We initialize the array of voracity
        double voracite[] = new double[nombreRepas];

        // We take a random number between the minimum and maximum voracity
        // for each meal.
        for (int i = 0; i < nombreRepas; i++)
            voracite[i] = Math.random()
                    * ((voraciteMax - voraciteMin) + voraciteMin);

        return voracite;
    }
}
