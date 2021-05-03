import java.io.Serializable;

/**
 * This class is used to compare strings, integers and doubles together. The
 * item created contains only its own value (can be of type String, Integer or
 * Double) and can be compared to other items (and to instances of String,
 * Integer or Double).
 * 
 * @author Nicolas Levasseur
 */
public final class Item<T extends Serializable>
        implements Comparable<Object>, Serializable {
    // Value of the item
    private T valeur;
    // Used for the serializable interface, set to an arbitrary value
    static final long serialVersionUID = 1L;


    /**
     * Generic constructor for an item.
     * 
     * @param item This parameter will be encapsulated in valeur.
     */
    public Item(T item) {
        valeur = item;
    }


    // Getter
    public T getValeur() {
        return valeur;
    }


    @Override
    public int compareTo(Object item) {
        // If we are comparing two items, we need to get the value of the
        // second item before doing the actual comparaison
        if (item instanceof Item) {
            return compareTo(((Item<?>) item).getValeur());
        }
        // If either of the compared object is a string we have to compare
        // their string value.
        else if (item instanceof String || valeur instanceof String)
            return valeur.toString().compareTo(item.toString());

        // Only if both of the compared objects are integers can we compare
        // them safely (without losing precision) using two Integers.
        else if (item instanceof Integer && valeur instanceof Integer) {
            return ((Integer) valeur).compareTo((Integer) item);
        }

        // If we got here, then the two compared objects are numbers and at
        // least one of these numbers is a Double.
        else
            return (int) Math.signum(((Number) valeur).doubleValue()
                    - ((Number) item).doubleValue());
    }
}
