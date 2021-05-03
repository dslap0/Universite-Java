/**
 * This class is a doubly linked list structure that can support three
 * different types (Integer, Double and String) and sort them in ascending or
 * descending order. The chain can be printed from beginning to the end or the
 * opposite.
 *
 * @author Adrien Charron
 * @author Nicolas Levasseur
 */
public class ListeDoublementChainee implements IListeDoublementChainee {
    /**
     * This class represent a node in a double chained list, containing a
     * pointer to the previous node, a pointer to the next one and the value
     * associated with this node, named item.
     *
     * @author Nicolas Levasseur
     */
    private class Noeud{
        // Reference to the last node in the list
        public Noeud precedent;
        // Item contained in the current node
        public Item<?> item;
        // Reference to the the next node in the list
        public Noeud suivant;

        /**
         * Constructor for a node in a double chained list.
         *
         * @param precedent Reference to the previous node in the list.
         * @param item      Item contained in the node.
         * @param suivant   Reference to the next node in the list.
         */
        Noeud(Noeud precedent, Item<?> item, Noeud suivant) {
            this.precedent = precedent;
            this.item = item;
            this.suivant = suivant;
        }
    }


    // Sens is the order in which the list will be sorted (ascending or
    // descending).
    private final String sens;
    // The first node in the chain.
    private Noeud premier;
    // The last node in the chain.
    private Noeud dernier;


    /**
     * Constructor for the doubly linked list, is called in main to create a
     * new structure.
     * 
     * @param sens is specfied in the input and extracted in the Main class.
     */
    public ListeDoublementChainee(String sens) {
        this.sens = sens;

        // Premier and dernier are initialized to null, since the chain is
        // empty at the beginning.
        this.premier = null;
        this.dernier = null;
    }

    /**
     * This method transforms the String list from the main in a doubly linked
     * chain by creating nodes from the extracted element of the input.
     * 
     * @param listeEnString the list specified in the input and extracted to a
     *                      String in the Main class.
     */
    @Override
    public void ajouterListe(String listeEnString) {
        // We adapt the String to be able to take the elements and transform
        // them into items by removing useless characters between the elements.
        listeEnString = listeEnString.replace("[", "");
        listeEnString = listeEnString.replace("]", "");
        // listeInitiale is the String list cleaned up.
        String[] listeInitiale = listeEnString.split(",");

        // We initialize a temp variable of type Item<?>
        Item<?> temp;

        // We loop through the String list to transform the elements into
        // items.
        for (int i = 0; i < listeInitiale.length; i++) {

            // We initialize a String variable "item" for the current element.
            String item = listeInitiale[i];

            // We look if the element is an integer and create an item
            // consequently.
            if (Main.isInteger(item))
                temp = new Item<>(Integer.parseInt(item));

            // We look if the element is a double and create an item
            // consequently.
            else if (Main.isDouble(item))
                temp = new Item<>(Double.parseDouble(item));

            // Else, the element is a String and we create an item consequently
            else
                temp = new Item<>(item);

            // A node is created with the new item.
            ajouterNoeud(temp);
        }
    }

    /**
     * This method is used to create a node in the doubly linked list. It will,
     * at the same time, sort the list by placing the nodes in order, ascending
     * or descending.
     * 
     * @param item is the item created in the method ajouterListe.
     */
    @Override
    public void ajouterNoeud(Item<?> item) {
        // sensDeTri indicates the order of the chain, -1 if ascending and 1 if
        // descending.
        float sensDeTri = sens.equals("asc") ? -1 : 1;

        // We initialize the variable current to the first node in the chain.
        Noeud current = premier;

        // If the chain is empty, meaning this new node is the first one
        // created, we assign the node to the first and last node.
        if (premier == null) {
            premier = dernier = new Noeud(null, item, null);
            return;
        }

        // We create a loop to look for the correct place to put the new node,
        // until we reach the end of the chain.
        while (current != null) {
            // We look if the next item is greater for an ascending order or if
            // the next item is smaller for a decending order. We use the
            // compareTo method defined in the Item class and our previous
            // variable "sensDeTri".
            if (Math.signum(item.compareTo(current.item)) == sensDeTri) {
                // We intialize the node that will be placed before our new
                // node
                Noeud before = current.precedent;
                // The new node is placed before the "current" node
                current.precedent = new Noeud(before, item, current);
                // If the previous node wasn't the first (therefore null), the
                // previous
                // node has his "suivant" set to the new node.
                if (before != null)
                    before.suivant = current.precedent;
                // else the new node is the first in the chain.
                else
                    premier = current.precedent;
                return;
            }
            // We continue looping to look for the right place to put our new
            // node.
            current = current.suivant;
        }
        // If we reached the end of the chain, the new node is placed at the
        // end.
        Noeud temp = dernier;
        dernier = new Noeud(temp, item, null);
        // the previous last node's next is assigned to the new node.
        temp.suivant = dernier;
    }

    /**
     * This method is used to print the list from beginning to the end.
     */
    @Override
    public void imprimerListeDuDebut() {
        // We initialize a variable to the first node.
        Noeud node = this.premier;

        // We print the first node
        System.out.print(
                "Noeuds du debut vers la fin: " + node.item.getValeur());

        // We loop through the list to print each node in order.
        while (node.suivant != null) {
            node = node.suivant;
            System.out.print("->" + node.item.getValeur());
        }
        System.out.println();
    }

    /**
     * This method is used to print the list from the end to the beginning.
     */
    @Override
    public void imprimerListeDeLaFin() {
        // We initialize a variable to the last node.
        Noeud node = this.dernier;

        // We print the last node
        System.out.print(
                "Noeuds de la fin vers le debut: " + node.item.getValeur());

        // We loop through the list to print each node in order.
        while (node.precedent != null) {
            node = node.precedent;
            System.out.print("->" + node.item.getValeur());
        }
    }
}
