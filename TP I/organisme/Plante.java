package organisme;

/**
 * The following class is the class called when creating a new plant. It also
 * handles the methods for the plants called in the class Lac with the method
 * tick. It will determine the action of each plant every cycle, whether they
 * reproduce, die or do nothing.
 *
 * @author Adrien Charron
 */
public final class Plante extends Organisme {


    /**
     * Constructor for the class Plant, initializes every parameters whenever a
     * new plant is created.
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
     * @param ageFertilite      The age at which an plant can start
     *                          reproducing.
     * @param energieEnfant     The intial energy of a plant when created.
     */
    protected Plante(String nomEspece, double besoinEnergie,
            double efficaciteEnergie, double resilience, double fertilite,
            int ageFertilite, double energieEnfant) {

        super(nomEspece, besoinEnergie, efficaciteEnergie, resilience,
                fertilite, ageFertilite, energieEnfant);

    }

    @Override
    public Plante copy(){
        return new Plante(nomEspece, besoinEnergie, efficaciteEnergie,
                resilience, fertilite, ageFertilite, energieEnfant);
    }

}
