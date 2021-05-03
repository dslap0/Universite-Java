package organisme;

import conditionsinitialesinvalides.ConditionsInitialesInvalides;

/**
 * The following class is a factory class that creates immuable instances of
 * plants. Each parameter needs to be set before creating a plant, and the
 * values specified must be valid, or else the class is gonna throw a
 * ConditionsInitialesInvalides exception message. This class extends
 * UsineOrganisme by implementing creerOrganisme() into a method to create
 * plants. We just use the default constructor, so no need to define another
 * one.
 * 
 * @author Nicolas Levasseur
 */
public final class UsinePlante extends UsineOrganisme {
    @Override
    public Plante creerOrganisme() throws ConditionsInitialesInvalides {
        // Exception handling if all parameters are not initialized
        for (Boolean initialise : parametresInitialises.values())
            if (!initialise)
                throw new ConditionsInitialesInvalides(
                        "Tous les paramètres n'ont pas étés initialisés avant "
                                + "de créer la plante");

        return new Plante(nomEspece, besoinEnergie, efficaciteEnergie,
                resilience, fertilite, ageFertilite, energieEnfant);
    }
}
