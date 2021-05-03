package organisme;

import conditionsinitialesinvalides.ConditionsInitialesInvalides;

/**
 * The following class is a factory class that creates immuable instances of
 * carnivores. Each parameter needs to be set before creating a carnivore, and
 * the values specified must be valid, or else the class is gonna throw a
 * ConditionsInitialesInvalides exception message. This class extends
 * UsineAnimal by adding a method to create a new carnivore.
 * 
 * @author: Nicolas Levasseur
 */
public final class UsineCarnivore extends UsineAnimal {
    @Override
    public Carnivore creerOrganisme() throws ConditionsInitialesInvalides {
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
                                + "de créer le carnivore");

        return new Carnivore(nomEspece, besoinEnergie, efficaciteEnergie,
                resilience, fertilite, ageFertilite, energieEnfant,
                debrouillardise, aliments, tailleMaximum);
    }
}
