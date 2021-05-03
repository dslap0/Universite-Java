package organisme;

import java.util.Map;
import java.util.HashMap;
import conditionsinitialesinvalides.ConditionsInitialesInvalides;

/**
 * The following class is an abstract factory class designed so that its
 * children classes create immuable organisms instances (plants, herbivores or
 * carnivores). Each parameter needs to be set before creating an organism and
 * the values specified must be valid, or else the class is gonna throw a
 * ConditionsInitialesInvalides exception message.
 * 
 * @author Nicolas Levasseur
 */
public abstract class UsineOrganisme {
    // Parameters that are used to create a new organism and a parameter that
    // stocks if all parameters have been initialized

    // Specie's name, can be anything except ""
    protected String nomEspece;

    // Minimal quantity of energy required to survive for a tick, can be
    // anything > 0
    protected double besoinEnergie;

    // Needs in energy in each cycle, must be between 0 and 1
    protected double efficaciteEnergie;

    // Probability to die if there's not enough energy for a tick, must be
    // between 0 and 1
    protected double resilience;

    // Probability to create offsprings if its energy permits it, must be
    // between 0 and 1
    protected double fertilite;

    // Age after which the organism can create offsprings, can be anything >= 0
    protected int ageFertilite;

    // Energy that is transfered from the parent to an offspring when it is
    // created, can be anything > 0
    protected double energieEnfant;
    
    // Parameters that stock if the other parameters have been initialized, and
    // if they are not, contains false (we could have made an array for
    // performance, but for readability purposes we instead made a Map)
    protected Map<String, Boolean> parametresInitialises;


    /**
     * Constructor that initialises parametresInitialises to all false
     */
    protected UsineOrganisme() {
        // We initialise parametresInitialises to all false values
        parametresInitialises = new HashMap<String, Boolean>();
        parametresInitialises.put("nomEspece", false);
        parametresInitialises.put("besoinEnergie", false);
        parametresInitialises.put("efficaciteEnergie", false);
        parametresInitialises.put("resilience", false);
        parametresInitialises.put("fertilite", false);
        parametresInitialises.put("ageFertilite", false);
        parametresInitialises.put("energieEnfant", false);
    }

    // Setters that handles the validity domain of the setted variables and
    // throws an error if the specified parameters does not match the validity
    // domain specified (to know the validity domains of each parameters, check
    // the parameter in question)

    public void setNomEspece(String nomEspece)
            throws ConditionsInitialesInvalides {
        // Exception handling if the value we are setting to is not valid
        if (nomEspece.isEmpty())
            throw new ConditionsInitialesInvalides(
                    "Le nom de l'espèce n'est pas spécifié");

        this.nomEspece = nomEspece;
        parametresInitialises.put("nomEspece", true);
    }

    public void setBesoinEnergie(double besoinEnergie)
            throws ConditionsInitialesInvalides {
        // Exception handling if the value we are setting to is not valid
        if (besoinEnergie <= 0)
            throw new ConditionsInitialesInvalides(
                    "Le besoin d'énergie spécifié n'est pas strictement "
                            + "positif");

        this.besoinEnergie = besoinEnergie;
        parametresInitialises.put("besoinEnergie", true);
    }

    public void setEfficaciteEnergie(double efficaciteEnergie)
            throws ConditionsInitialesInvalides {
        // Exception handling if the value we are setting to is not valid
        if (efficaciteEnergie < 0 || efficaciteEnergie > 1)
            throw new ConditionsInitialesInvalides(
                    "L'efficacité énergétique spécifiée n'est pas comprise "
                            + "entre 0 et 1");

        this.efficaciteEnergie = efficaciteEnergie;
        parametresInitialises.put("efficaciteEnergie", true);
    }

    public void setResilience(double resilience)
            throws ConditionsInitialesInvalides {
        // Exception handling if the value we are setting to is not valid
        if (resilience < 0 || resilience > 1)
            throw new ConditionsInitialesInvalides(
                    "La résilience spécifiée n'est pas comprise entre 0 et 1");

        this.resilience = resilience;
        parametresInitialises.put("resilience", true);
    }

    public void setFertilite(double fertilite)
            throws ConditionsInitialesInvalides {
        // Exception handling if the value we are setting to is not valid
        if (fertilite < 0 || fertilite > 1)
            throw new ConditionsInitialesInvalides(
                    "La fertilité spécifiée n'est pas comprise entre 0 et 1");

        this.fertilite = fertilite;
        parametresInitialises.put("fertilite", true);
    }

    public void setAgeFertilite(int ageFertilite)
            throws ConditionsInitialesInvalides {
        // Exception handling if the value we are setting to is not valid
        if (ageFertilite < 0)
            throw new ConditionsInitialesInvalides(
                    "L'âge de fertilité spécifié est négatif");

        this.ageFertilite = ageFertilite;
        parametresInitialises.put("ageFertilite", true);
    }

    public void setEnergieEnfant(double energieEnfant)
            throws ConditionsInitialesInvalides {
        // Exception handling if the value we are setting to is not valid
        if (energieEnfant <= 0)
            throw new ConditionsInitialesInvalides(
                    "L'âge de fertilité spécifié n'est pas strictement "
                            + "positif");

        this.energieEnfant = energieEnfant;
        parametresInitialises.put("energieEnfant", true);
    }


    /**
     * General method that is defined in the final children classes that will
     * create a specific type of organism. It also checks if all of the
     * parameters are initialized.
     * 
     * @return a more specific organisme (ex: a plant or a carnivore)
     * @throws ConditionsInitialesInvalides
     */
    public abstract Organisme creerOrganisme()
            throws ConditionsInitialesInvalides;
}
