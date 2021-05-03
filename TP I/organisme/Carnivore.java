package organisme;

import java.util.Set;

/**
 * The following class is the class called when creating a new carnivore. It
 * also handles the methods for the carnivores called in the class Lac with the
 * method tick. It will determine the action of each carnivore every cycle,
 * whether they reproduce, die or do nothing.
 *
 * @author Adrien Charron
 */
public class Carnivore extends Animal {
    /**
     * Constructor for the class Carnivore, initializes every parameters
     * whenever a new carnivore is created.
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
     * @param ageFertilite      The age at which an carnivore can start
     *                          reproducing.
     * @param energieEnfant     The intial energy of a carnivore when created.
     * @param debrouillardise   Parameter that determines the probability of
     *                          finding something to eat.
     * @param aliments          Specifies the different species an carnivore can
     *                          eat.
     * @param tailleMaximum     The maximum size an carnivore can grow to.
     */
    protected Carnivore(String nomEspece, double besoinEnergie,
            double efficaciteEnergie, double resilience, double fertilite,
            int ageFertilite, double energieEnfant, double debrouillardise,
            Set<String> aliments, double tailleMaximum) {

        super(nomEspece, besoinEnergie, efficaciteEnergie, resilience,
                fertilite, ageFertilite, energieEnfant, debrouillardise,
                aliments, tailleMaximum);
    }

    @Override
    public Carnivore copy() {
        return new Carnivore(nomEspece, besoinEnergie, efficaciteEnergie,
                resilience, fertilite, ageFertilite, energieEnfant,
                debrouillardise, aliments, tailleMaximum);
    }
}
