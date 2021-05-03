package organisme;

import conditionsinitialesinvalides.ConditionsInitialesInvalides;

/**
 * The following class is a factory class that creates immuable instances of
 * herbivores. Each parameter needs to be set before creating an herbivore, and
 * the values specified must be valid, or else the class is gonna throw a
 * ConditionsInitialesInvalides exception message. This class extends
 * UsineAnimal by adding a few specific parameters for herbivores and their
 * setters, adding the corresponding MapEntries to the parametreInitialises map
 * and adding a method to create a new herbivore.
 * 
 * @author: Nicolas Levasseur
 */
public final class UsineHerbivore extends UsineAnimal {
    // Parameters that are used specifically to create a new herbivore

    // Minimal proportion of an aliment energy that will be consumed, must be
    // between 0 and 1 and be smaller than voraciteMax
    private double voraciteMin;

    // Maximal proportion of an aliment energy that will be consumed, must be
    // between 0 and 1 and be bigger than voraciteMin
    private double voraciteMax;


    /**
     * Constructor that initialises all parameters to values we can work with
     * and set parameteresInitialises to all false.
     */
    public UsineHerbivore() {
        // We set voraciteMin and voraciteMax to values that will not interfere
        // with each other when they are set for the first time
        voraciteMin = 0;
        voraciteMax = 1;

        // We add some new parameters to parametresInitialises that we set to
        // false too
        parametresInitialises.put("voraciteMin", false);
        parametresInitialises.put("voraciteMax", false);
    }


    // Specific setters for herbivores that also check the validity domain of
    // the parameters we are trying to set (to know the validity domains of
    // each parameters, check the parameter in question)

    public void setVoraciteMin(double voraciteMin)
            throws ConditionsInitialesInvalides {
        if (voraciteMin < 0 || voraciteMin > 1 || voraciteMin > voraciteMax)
            throw new ConditionsInitialesInvalides(
                    "La voracité minimale spécifiée n'est pas comprise entre "
                            + "0 et 1 ou est supérieure à la voracité "
                            + "maximale");

        this.voraciteMin = voraciteMin;
        parametresInitialises.put("voraciteMin", true);
    }

    public void setVoraciteMax(double voraciteMax)
            throws ConditionsInitialesInvalides {
        if (voraciteMax < 0 || voraciteMax > 1 || voraciteMin > voraciteMax)
            throw new ConditionsInitialesInvalides(
                    "La voracité maximale spécifiée n'est pas comprise entre "
                            + "0 et 1 ou est inférieure à la voracité " 
                            + "minimale");

        this.voraciteMax = voraciteMax;
        parametresInitialises.put("voraciteMax", true);
    }

    
    @Override
    public Herbivore creerOrganisme() throws ConditionsInitialesInvalides {
        // We check if tailleMaximum has been initialized and if it was not, 
        // we set its value to the default value
        if (!parametresInitialises.get("tailleMaximum")) {
            tailleMaximum = energieEnfant * 10;
            parametresInitialises.put("tailleMaximum", true);
        }
        // Exception handling if all parameters are not initialized
        for (Boolean initialise : parametresInitialises.values())
            if (!initialise)
                throw new ConditionsInitialesInvalides(
                        "Tous les paramètres n'ont pas étés initialisés avant "
                                + "de créer l'herbivore");

        return new Herbivore(nomEspece, besoinEnergie, efficaciteEnergie,
                resilience, fertilite, ageFertilite, energieEnfant,
                debrouillardise, voraciteMin, voraciteMax, aliments,
                tailleMaximum);
    }
}
