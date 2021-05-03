package organisme;

import java.util.Set;

/**
 * This is the parent class for all the animal in the project and is inherited
 * from the class Organism. The shared methods for all the animals are
 * specified here and will later be called by the class Lac in the inherited
 * classes.
 *
 * @author Adrien Charron
 */
public abstract class Animal extends Organisme {
    // Parameter that determines the probability of finding
    // something to eat.
    protected double debrouillardise;

    // Specifies the different species an animal can eat.
    protected Set<String> aliments;

    // The maximum size a animal can grow to.
    protected final double tailleMaximum;


    // Getters for each parameters
    public double getDebrouillardise() {
        return debrouillardise;
    }

    public Set<String> getAliments() {
        return aliments;
    }

    public double getTailleMaximum() {
        return tailleMaximum;
    }


    /**
     * Constructor for the class Animal, initializes every parameters whenever
     * a new animal is created.
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
     * @param ageFertilite      The age at which an animal can start
     *                          reproducing.
     * @param energieEnfant     The intial energy of a animal when created.
     * @param debrouillardise   Parameter that determines the probability of
     *                          finding something to eat.
     * @param aliments          Specifies the different species an animal can
     *                          eat.
     * @param tailleMaximum     The maximum size an animal can grow to.
     */
    protected Animal(String nomEspece, double besoinEnergie,
            double efficaciteEnergie, double resilience, double fertilite,
            int ageFertilite, double energieEnfant, double debrouillardise,
            Set<String> aliments, double tailleMaximum) {

        super(nomEspece, besoinEnergie, efficaciteEnergie, resilience,
                fertilite, ageFertilite, energieEnfant);

        this.debrouillardise = debrouillardise;
        this.aliments = aliments;
        this.tailleMaximum = tailleMaximum;
    }


    /**
     * This method adds the energy left at the end of a cycle to the raw energy
     * of an animal at a rate determined by efficaciteEnergie.
     * 
     * @param energieRestante is the energy left at the end of the cycle.
     */
    @Override
    public void energieAjoutee(double energieRestante) {

        // We add the extra energy to the energy of the animal with the
        // the particular efficiency of the animal.
        double energieTemp = energie + efficaciteEnergie * energieRestante;

        // We make sure the energy doesn't exceeds the value of tailleMaximum.
        // If so, we set energy to tailleMaximum.
        energie = energieTemp > tailleMaximum ? tailleMaximum : energie;

    }

    /**
     * This method uses the "debrouillardise" parameter to determine the number
     * of meal an animal will be able to find.
     * 
     * @return The number of meal found.
     */
    public int rechercheRepas() {

        // We initialize the parameter for the number of meal.
        int nombreRepas = 0;

        // We add a meal each time the random number is lower than the
        // debrouillardise parameter.
        while (Math.random() < debrouillardise)
            nombreRepas += 1;

        return nombreRepas;
    }
}
