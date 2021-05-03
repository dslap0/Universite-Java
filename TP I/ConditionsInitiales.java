import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.util.ArrayList;
import java.util.List;
import organisme.*;

import conditionsinitialesinvalides.ConditionsInitialesInvalides;

/**
 * Les conditions initiales de la simulation sont stockées dans des fichier
 * XML. Le rôle de cette classe est de lire de tels fichier.
 *
 * On utilise une classe nomée XMLEventReader. Ce n'est pas la façon la plus
 * facile de lire du XML, mais elle a l'avantage de faire partie du JDK, donc
 * il n'y a pas de dépendances à ajouter à votre projet. Elle fonctionne comme
 * un curseur dans le document: on lui demande le prochain évènement, qui peut
 * être une ouverture de tag, des caractères, etc.
 * 
 * @author Simon Génier
 * @author Nicolas Levasseur
 */
public final class ConditionsInitiales {
    private final XMLEventReader reader;

    public ConditionsInitiales(XMLEventReader reader) {
        this.reader = reader;
    }


    /**
     * Method used to initialize the specifics parameters of a herbivore. We
     * throw a new ConditionsInitialesInvalides if an attribute isn't valid for
     * a herbivore.
     * 
     * @param name  Name of the attributes in the XML file.
     * @param usine Factory used to create organisms.
     * @throws ConditionsInitialesInvalides
     */
    private void nextHerbivore(String name, UsineHerbivore usine)
            throws ConditionsInitialesInvalides {
        // We basically continue the previous switch
        switch (name) {
            case "voraciteMin" -> usine
                    .setVoraciteMin(this.nextDouble("voraciteMin"));
            case "voraciteMax" -> usine
                    .setVoraciteMax(this.nextDouble("voraciteMax"));
            default -> throw new ConditionsInitialesInvalides(
                    "attribut \"" + name + "\" invalide pour un herbivore");
        }
    }


    /**
     * Method used to initialize the shared parameters of an animal, which
     * includes herbivores and carnivores. We throw a new
     * ConditionsInitialesInvalides if an attribute isn't valid for an animal.
     * 
     * @param name  Name of the attributes in the XML file.
     * @param usine Factory used to create organisms.
     * @throws ConditionsInitialesInvalides
     */
    private void nextAnimal(String name, UsineAnimal usine)
            throws ConditionsInitialesInvalides {
        // We basically continue the previous switch
        switch (name) {
            case "debrouillardise" -> usine
                    .setDebrouillardise(this.nextDouble("debrouillardise"));
            case "aliments" -> usine.addAliment(this.nextString("aliments"));
            case "tailleMaximum" -> usine
                    .setTailleMaximum(this.nextDouble("tailleMaximum"));
            default -> {
                if (usine instanceof UsineHerbivore) {
                    nextHerbivore(name, (UsineHerbivore) usine);
                } else
                    throw new ConditionsInitialesInvalides("attribut \"" + name
                            + "\" invalide pour un carnivore");
            }
        }
    }


    /**
     * Method used to initialize the shared parameters of an organism, which
     * includes every type of instances created in this project. We throw a new
     * ConditionsInitialesInvalides if an attribute isn't valid for an
     * organism.
     * 
     * @param usine Factory used to create organisms.
     * @throws ConditionsInitialesInvalides
     */
    private void nextOrganisme(UsineOrganisme usine)
            throws ConditionsInitialesInvalides {
        while (true) {
            var event = this.nextEventIgnoringWhitespace();
            if (event.isStartElement()) {
                var startElement = event.asStartElement();
                var name = startElement.getName().getLocalPart();
                switch (name) {
                    case "nomEspece" -> usine
                            .setNomEspece(this.nextString("nomEspece"));
                    case "besoinEnergie" -> usine.setBesoinEnergie(
                            this.nextDouble("besoinEnergie"));
                    case "efficaciteEnergie" -> usine.setEfficaciteEnergie(
                            this.nextDouble("efficaciteEnergie"));
                    case "resilience" -> usine
                            .setResilience(this.nextDouble("resilience"));
                    case "fertilite" -> usine
                            .setFertilite(this.nextDouble("fertilite"));
                    case "ageFertilite" -> usine
                            .setAgeFertilite(this.nextInt("ageFertilite"));
                    case "energieEnfant" -> usine.setEnergieEnfant(
                            this.nextDouble("energieEnfant"));
                    default -> {
                        if (usine instanceof UsineAnimal) {
                            nextAnimal(name, (UsineAnimal) usine);
                        } else
                            throw new ConditionsInitialesInvalides(
                                    "attribut \"" + name
                                            + "\" invalide pour une plante");
                    }
                }
                this.skipEndTag();
            } else if (event.isEndElement()) {
                return;
            }
        }
    }

    /**
     * General method to initialize a list of specialized organisms
     *
     * @param startElement XMLEventReader starts element.
     * @param usine        Factory used to create organisms.
     * @param organismes   List to which we add the created organisms.
     * @throws ConditionsInitialesInvalides
     */
    private void initialisationOrganisme(StartElement startElement,
            UsineOrganisme usine, List<Organisme> organismes)
            throws ConditionsInitialesInvalides {
        var quantityAttribute =
                startElement.getAttributeByName(new QName("quantite"));
        var quantity = Integer.parseInt(quantityAttribute.getValue());
        this.nextOrganisme(usine);
        for (int i = 0; i < quantity; i++) {
            organismes.add(usine.creerOrganisme());
        }
    }



    public Lac nextLac() throws ConditionsInitialesInvalides {
        // On cherche le début du document.
        while (true) {
            var event = this.nextEvent();
            if (event.isStartElement()) {
                var startElement = event.asStartElement();
                var name = startElement.getName().getLocalPart();
                if (!name.equals("lac")) {
                    throw new ConditionsInitialesInvalides(
                            "je m'attendais au tag \"lac\", mais j'ai eu \""
                                    + name + "\"");
                }
                break;
            } else if (!event.isProcessingInstruction()
                    && !event.isStartDocument()) {
                throw new ConditionsInitialesInvalides(
                        "élément invalide: " + event);
            }
        }


        Integer energieSolaire = null;
        List<Plante> plantes = new ArrayList<>();
        List<Herbivore> herbivores = new ArrayList<>();
        // La liste des carnivores a été ajouté
        List<Carnivore> carnivores = new ArrayList<>();


        while (this.reader.hasNext()) {
            var event = this.nextEvent();
            if (event.isStartElement()) {
                var startElement = event.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "energieSolaire" -> {
                        energieSolaire = this.nextInt("energieSolaire");
                        this.skipEndTag();
                    }
                    case "plante" -> {
                        initialisationOrganisme(startElement,
                                new UsinePlante(),
                                (List<Organisme>) (List<?>) plantes);
                    }
                    case "herbivore" -> {
                        initialisationOrganisme(startElement,
                                new UsineHerbivore(),
                                (List<Organisme>) (List<?>) herbivores);
                    }
                    // Cas pour les carnivores a été ajouté de la même manière
                    // que les classes Plante et Herbivore.
                    case "carnivore" -> {
                        initialisationOrganisme(startElement,
                                new UsineCarnivore(),
                                (List<Organisme>) (List<?>) carnivores);
                    }
                }
            } else if (event.isEndElement()) {
                // C'est nécessairement le tag fermant </lac>, si le document
                // est bien formé.
                break;
            }
        }


        if (energieSolaire == null) {
            throw new ConditionsInitialesInvalides(
                    "energieSolaire non spécifiée");
        }

        return new Lac(energieSolaire, plantes, herbivores, carnivores);
    }


    private String nextString(String context)
            throws ConditionsInitialesInvalides {
        var characters = this.nextCharacters(context);
        return characters.getData().trim();
    }

    private int nextInt(String context) throws ConditionsInitialesInvalides {
        return Integer.parseInt(this.nextString(context));
    }

    private double nextDouble(String context)
            throws ConditionsInitialesInvalides {
        return Double.parseDouble(this.nextString(context));
    }

    private Characters nextCharacters(String context)
            throws ConditionsInitialesInvalides {
        var event = this.nextEvent();
        if (event.isCharacters()) {
            return event.asCharacters();
        } else {
            throw new ConditionsInitialesInvalides(
                    "je m'attendais à avoir des caractères dans " + context);
        }
    }

    private void skipEndTag() throws ConditionsInitialesInvalides {
        var event = this.nextEventIgnoringWhitespace();
        if (!event.isEndElement()) {
            throw new ConditionsInitialesInvalides(
                    "je m'attendais à la fin d'un tag, mais j'ai eu des " 
                    + "données: " + event);
        }
    }

    private XMLEvent nextEventIgnoringWhitespace()
            throws ConditionsInitialesInvalides {
        while (true) {
            var event = this.nextEvent();
            if (event.isCharacters() && event.asCharacters().isWhiteSpace()) {
                continue;
            }
            return event;
        }
    }

    private XMLEvent nextEvent() throws ConditionsInitialesInvalides {
        try {
            return this.reader.nextEvent();
        } catch (XMLStreamException e) {
            throw new ConditionsInitialesInvalides(
                    "le document se termine avant la fin", e);
        }
    }
}
