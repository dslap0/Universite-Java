package organisme;

import java.util.Set;
import java.util.HashSet;

import conditionsinitialesinvalides.ConditionsInitialesInvalides;

/**
 * The following class is an abstract factory class designed so that its
 * children classes creates immuable instances of animals (herbivores or
 * carnivores). Each parameter needs to be set before creating an animal, and
 * the values specified must be valid, or else the class is gonna throw a
 * ConditionsInitialesInvalides exception message. This class extends
 * UsineOrgansime by adding a few specific parameters for animals and their
 * setters and adding the corresponding MapEntries to parametreInitialises.
 * 
 * @author: Nicolas Levasseur
 */
public abstract class UsineAnimal extends UsineOrganisme {
    // Parameters that are used specifically to create a new animal

    // Probability to eat, must be between 0 and 1
    protected double debrouillardise;
    
    // All of the things that the organism can feed upon, cannot be null
    protected Set<String> aliments;

    // Maximum quantity of energy an animal can have, must be superior to the
    // energy of a newborn animal, setted by default to energieEnfant * 10
    protected double tailleMaximum;


    /**
     * Constructor that initialises parametresInitialises to all false
     */
    protected UsineAnimal() {
        // We create an empty set for aliment so that we can add aliments to it
        // after
        aliments = new HashSet<String>();

        // We add some new parameters to parametresInitialises that we set to
        // false too
        parametresInitialises.put("debrouillardise", false);
        parametresInitialises.put("aliments", false);
        parametresInitialises.put("tailleMaximum", false);
    }


    // Specific setters for animals that also check the validity domain of
    // the parameters we are trying to set (to know the validity domains of
    // each parameters, check the parameter in question)
    public void setDebrouillardise(double debrouillardise)
            throws ConditionsInitialesInvalides {
        if (debrouillardise < 0 || debrouillardise > 1)
            throw new ConditionsInitialesInvalides(
                    "La débrouillardise spécifiée n'est pas comprise entre 0 "
                            + "et 1");

        this.debrouillardise = debrouillardise;
        parametresInitialises.put("debrouillardise", true);
    }

    public void addAliment(String aliment)
            throws ConditionsInitialesInvalides {
        if (aliment.isEmpty())
            throw new ConditionsInitialesInvalides(
                    "L'aliment n'est pas spécifié");

        this.aliments.add(aliment);
        parametresInitialises.put("aliments", true);
    }

    public void setTailleMaximum(double tailleMaximum)
            throws ConditionsInitialesInvalides {
        if (tailleMaximum < energieEnfant)
            throw new ConditionsInitialesInvalides(
                    "La taille maximale est plus petite que la taille d'un "
                            + "nouvel enfant");

        this.tailleMaximum = tailleMaximum;
        parametresInitialises.put("tailleMaximum", true);
    }
}
