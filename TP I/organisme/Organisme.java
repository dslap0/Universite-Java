package organisme;

/**
 * This method is the parent class for all the organism in the project. All the
 * shared methods are specified here and will later be called by the class Lac
 * in the inherited classes.
 * 
 * @author Adrien Charron
 */
public abstract class Organisme {

    // All the following parameters are needed in order to
    // create a new organism.

    // Specie's name
    protected String nomEspece;

    // The quantity of energy inside the organism, just like health points
    // in a video game, you gain or lose energy and below zero, you die.
    protected double energie;

    // The age of an organism, gains one year each cycle.
    protected int age;

    // Minimal quantity of energy required to maintain itself each cycle
    protected double besoinEnergie;

    // Efficiency in converting raw energy into life energy, is used
    // when there are leftovers in energy at the end of a cycle.
    protected double efficaciteEnergie;

    // The probability to die in a cycle if the needed energy to survive
    // (besoinEnergie) isn't obtained.
    protected double resilience;

    // Probability of reproduction in a cycle if the needed energy
    // (besoinEnergie) is surpassed.
    protected double fertilite;

    // The age at which an organism can start reproducing.
    protected int ageFertilite;

    // The intial energy of a organism when created.
    protected double energieEnfant;


    // Getters for each parameters
    public String getNomEspece() {
        return this.nomEspece;
    }

    public double getEnergie() {
        return energie;
    }

    public int getAge() {
        return age;
    }

    public double getBesoinEnergie() {
        return besoinEnergie;
    }

    public double getEfficaciteEnergie() {
        return efficaciteEnergie;
    }

    public double getResilience() {
        return resilience;
    }

    public double getFertilite() {
        return fertilite;
    }

    public int getAgeFertilite() {
        return ageFertilite;
    }

    public double getEnergieEnfant() {
        return energieEnfant;
    }


    /**
     * Constructor for the class Organism, initializes every parameters 
     * whenever a new organism is created.
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
     * @param ageFertilite      The age at which an organism can start
     *                          reproducing.
     * @param energieEnfant     The intial energy of a organism when created.
     */
    protected Organisme(String nomEspece, double besoinEnergie,
            double efficaciteEnergie, double resilience, double fertilite,
            int ageFertilite, double energieEnfant) {

        // Every parameters are initializes with the arguments specified,
        // expect age and energy, which are set to 0 and energieEnfant 
        // respectively.
        this.nomEspece = nomEspece;
        this.energie = energieEnfant;
        this.age = 0;
        this.besoinEnergie = besoinEnergie;
        this.efficaciteEnergie = efficaciteEnergie;
        this.resilience = resilience;
        this.fertilite = fertilite;
        this.ageFertilite = ageFertilite;
        this.energieEnfant = energieEnfant;

    }

    /**
     * Method defined in the children classes that creates a copy of the
     * current organism.
     * 
     * @return a copy of the current organism
     */
    public abstract Organisme copy();

    /**
     * This method is call by Lac to update the age of the organism each cycle.
     */
    public void augmenteAge() {

        // We update the age of the organism since it's a new cycle, so the
        // organism gains one year.
        age++;

    }

    /**
     * This method adds the energy left at the end of a cycle to the raw energy
     * of an organism at a rate determined by efficaciteEnergie.
     * 
     * @param energieRestante is the energy left at the end of the cycle.
     */
    public void energieAjoutee(double energieRestante) {

        // We add the extra energy to the energy of the organism with the
        // the particular efficiency of the organism.
        energie += efficaciteEnergie * energieRestante;

    }

    /**
     * This method calculates the energy of the organism that will be lost if
     * it gets eaten by an animal.
     * 
     * @param voracite A percentage of the organism that will get eaten,
     *                 determined by the voracity of the animal.
     * @return The energy lost in the process
     */
    public double mangee(double voracite) {

        // The energy lost depends on the efficiency "voracite" of the
        // herbivore.
        double energiePerdue = energie * voracite;
        energie -= energiePerdue;

        return energiePerdue;
    }

    /**
     * This method is called every cycle by the class Lac, in order to
     * determine if an organism dies, reproduces or does nothing.
     * 
     * @param energieAbsorbee is the energy given to the organism from the Sun
     *                        or from eating in the cycle.
     * @return Depending on the ratio between the absorbed energy and the need
     *         in energy, the methods returns the result of "survie",
     *         "reproduction" or 0.
     */
    public int absortionEnergie(double energieAbsorbee) {

        // If the required energy for the cycle isn't obtained, we go in the
        // method "survie", to see if the organism dies.
        if (besoinEnergie > energieAbsorbee)

            return survie(energieAbsorbee);

        // If the required energy is obtained, we go in the method
        // reproduction, to see if the organism creates new organism(s).
        else if (besoinEnergie < energieAbsorbee)

            return reproduction(energieAbsorbee);

        // If the required energy and the energy needed are equals, we do
        // nothing, no child is created and the organism survives.
        else

            return 0;

    }

    /**
     * This method is used to determine if the organism will survive the cycle,
     * since the energy absorbed isn't sufficient.
     * 
     * @param energieAbsorbee is the energy given to the organism from the Sun
     *                        or from eating in the cycle.
     * @return returns 0 if the organism survives the cycle and -1 if it dies.
     */
    public int survie(double energieAbsorbee) {

        // This parameter indicates the energy missing that the organism needed
        // for this cycle.
        double energieManquante = besoinEnergie - energieAbsorbee;

        // We remove the missing energy from the energy of the organism.
        energie -= energieManquante;


        // We test if the organism still has energy, if not, the organism dies.
        if (energie <= 0) {

            return -1;
        }

        // We test the resilience of the organism with the parameter
        // "resilience". We decided not to round up the energy missing and take
        // directly the number with decimal and put it for the exponent of
        // resilience. If the random number is higher than the one resulting
        // from the equation of the resilience, the organism dies.
        if (Math.random() >= Math.pow(resilience, energieManquante)) {
            return -1;
        }

        // The organism went through all the tests and survived.
        return 0;
    }

    /**
     * This method calculates the odds of reproduction for the organism each
     * cycle, since the energy absorbed is superior than the energy needed.
     * 
     * @param energieAbsorbee is the energy given to the organism from the Sun
     *                        or from eating in the cycle.
     * @return returns the number of child resulting from the reproduction.
     */
    public int reproduction(double energieAbsorbee) {

        // We initialize the number of child to 0.
        int nombreEnfant = 0;

        // We test if the organism has reached the age of reproduction, if not,
        // nothing happens.
        if (age < ageFertilite) {

            // We add the extra energy to the energy of the organism with the
            // the particular efficiency of the organism.
            energieAjoutee(energieAbsorbee);
            return nombreEnfant;
        }

        // This parameter indicates the extra energy for the organism this
        // cycle.
        double energieSupp = energieAbsorbee - besoinEnergie;

        // We initialize the energy that will be left at the end of the turn.
        double energieRestante = energieSupp;

        // We initialize the iterable parameter.
        double i = energieSupp;


        // For every unit of extra energy, we test the fertility of the
        // organism.
        while (i >= 1) {
            // We test the probability of creating a new organism, if the the
            // random number is lower than the parameter "fertilite", the
            // organism produces a child. If not, we continue the loop.
            if (Math.random() > fertilite) {
                i--;
                continue;
            }
            
            // The test succeeded so we remove the energy required for a new
            // organism, specified by the parameter "energieEnfant" and we
            // decrease the iterable i.
            energieRestante -= energieEnfant;
            i -= energieEnfant;

            // If the extra energy becomes negative because of the removed
            // energy, we take the energy missing for the creation of an
            // organism directly from the energy of the parent.
            if (i < 0) {
                energie += i;

                // If, with this process, the parent organism's energy become
                // negative, then this organism dies and no child is created
                if (energie < 0)
                    return -1;
            }

            // A new organism is born, we add 1 to the number of child.
            nombreEnfant++;
        }

        // If the remaining energy is positive, we add the extra energy
        // to the energy of the organism with the equation for the efficiency
        // of the organism.
        if (energieRestante > 0)
            energieAjoutee(energieRestante);

        // We return the number of new organisms.
        return nombreEnfant;
    }
}
