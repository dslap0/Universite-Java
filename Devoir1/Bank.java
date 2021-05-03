/**
 * The following class is the Bank class. This class interprets the
 * instructions that are given by the user, handles exceptions, manages the
 * operations on its branches and has a part in printing the report. It also
 * contains the global bonus for all of the accounts, since the bonus isn't
 * branch dependent. The only way to modify anything in the bank is to pass a
 * command to processTransaction.
 * 
 * @author Nicolas Levasseur
 */
public class Bank {

    // iBranches stores the index of the next addition to branches
    private int iBranches;

    // bonus stores the amount given to a new account when it is created
    private float bonus;

    // branches stores all of the branches that answers to the bank
    private Branch[] branches;


    /**
     * This is the empty constructor for bank that initialize all parameters of
     * the class.
     */
    public Bank() {
        // We initialize all parameters
        bonus = 0;
        branches = new Branch[5];
        iBranches = 0;
    }

    /**
     * This method handles all of the possible transactions the bank might have
     * to deal with. All of the others bank method are called from here. We
     * also handle exceptions here, by ignoring them.
     * 
     * @param command is a string that contains instructions for the bank to
     *                execute.
     */
    public void processTransaction(String command) {
        try {
            // We separate the command line that was given into its components
            String[] words = command.split(" ");

            // We test which command was entered and we handle it
            switch (words[0]) {
                case "build":
                    build(words[1]);
                    break;
                case "dismantle":
                    dismantle(words[1]);
                    break;
                case "open":
                    open(words[1], words[2]);
                    break;
                case "close":
                    close(words[1], words[2]);
                    break;
                case "deposit":
                    deposit(words[1], words[2], Float.parseFloat(words[3]));
                    break;
                case "withdraw":
                    withdraw(words[1], words[2], Float.parseFloat(words[3]));
                    break;
                case "bonus":
                    bonus(Float.parseFloat(words[1]));
                    break;
                case "report":
                    report();
                    break;
                case "short-report":
                    shortReport();
                    break;
                default:
                    // We do not have to do anything if the command is not
                    // correct
            } // end switch
        } catch (Exception e) {
            // We ignore the exception
        }
    }

    /**
     * This method builds a new bank branch with a transit number and puts it
     * in the branches array.
     * 
     * @param transit is used to identify the new bank branch we want to
     *                create.
     */
    private void build(String transit) {
        try {
            int i = searchTransit(transit);
        } catch (IllegalArgumentException e) {
            // We first have to verify that the array still has room for
            // another
            // branch, and expand it if it is not the case.
            if (iBranches >= branches.length) {
                Branch[] temp = branches.clone();
                branches = new Branch[temp.length + 1];
                System.arraycopy(temp, 0, branches, 0, temp.length);
            }
            // We create the new branch and put it in the array that stores
            // them
            branches[iBranches] = new Branch(transit);
            // We change the index of the next addition to the array branches
            iBranches++;
        }
    }

    /**
     * This method dismantles the bank branch that corresponds to the specified
     * transit number by removing it from the branches array.
     * 
     * @param transit is used to identify the bank branch we want to erase.
     */
    private void dismantle(String transit) {

        int i = searchTransit(transit);
        System.arraycopy(branches, i + 1, branches, i,
                branches.length - i - 1);
        iBranches--;
    }

    /**
     * This method asks to a branch (designated by its transit) to open an
     * account corresponding to a number that is passed between the methods.
     * 
     * @param transit is used to identify the bank branch we want to add an
     *                account to.
     * @param number  is passed to the bank branch method to identify the
     *                account we want to open.
     */
    private void open(String transit, String number) {
        int i = searchTransit(transit);
        branches[i].open(number, bonus);
    }

    /**
     * This method asks to a branch (designated by its transit) to close an
     * account corresponding to a number that is passed between the methods.
     * 
     * @param transit is used to identify the bank branch we want to remove an
     *                account from.
     * @param number  is passed to the bank branch method to identify the
     *                account we want to close.
     */
    private void close(String transit, String number) {
        int i = searchTransit(transit);
        branches[i].close(number);
    }

    /**
     * This method asks to a branch (designated by its transit) to deposit an
     * amount in a certain account, designated by its number.
     * 
     * @param transit is used to identify the bank branch we want to deposit
     *                to.
     * @param number  is passed to the bank branch method to identify the
     *                account we want to deposit to.
     * @param amount  is passed to the bank branch method to specify the amount
     *                that is deposed in the account.
     */
    private void deposit(String transit, String number, float amount) {
        if (amount > 0) {
            int i = searchTransit(transit);
            branches[i].deposit(number, amount);
        }
    }

    /**
     * This method asks to a branch (designated by its transit) to withdraw an
     * amount from a certain account, designated by its number.
     * 
     * @param transit is used to identify the bank branch we want to withdraw
     *                from.
     * @param number  is passed to the bank branch method to identify the
     *                account we want to withdaw from.
     * @param amount  is passed to the bank branch method to specify the amount
     *                that is withdrawn from the account.
     */
    private void withdraw(String transit, String number, float amount) {
        if (amount > 0) {
            int i = searchTransit(transit);
            branches[i].withdraw(number, amount);
        }
    }

    /**
     * This method sets the bonus when you open a new account by modifying the
     * bank's private parameter bonus. This function is kind of a setter for
     * bonus, but it is a private method. If the amount is negative, the bonus
     * stays the same.
     * 
     * @param amount is the new amount the bonus is being set to.
     */
    private void bonus(float amount) {
        bonus = amount >= 0 ? amount : bonus;
    }

    /**
     * This method asks to each of the bank's branch to generate a long report.
     * Those include the number of active accounts, the sum of all of the
     * active account available amount, the number of closed accounts and the
     * sum of the amounts in the closed accounts. We add all of the deposed
     * available amounts through each iteration and show them at the end of the
     * method.
     */
    private void report() {
        // We initialize the sum of all of the deposed active amounts to 0
        float sum = 0;
        // We print the header
        System.out.println("+++ Bank Report +++");

        // We loop through the accounts and ask them to generate their reports
        for (int i = 0; i < iBranches; i++) {
            sum += branches[i].report();
        }

        // We print the footer
        System.out.println(String.format("Bank total deposits = %.1f$", sum)
                .replace(',', '.'));
        System.out.println("-------------------");
    }

    /**
     * This method asks to each of the bank's branch to generate a sum of their
     * deposed available amounts and shows them at the end of the method.
     */
    private void shortReport() {
        // We initialize the sum of all of the deposed active amounts to 0
        float sum = 0;

        // We print the header
        System.out.println("+++ Bank Report +++");

        // We loop through the accounts and ask them to generate their sums
        for (int i = 0; i < iBranches; i++) {
            sum += branches[i].sum();
        }

        // We print the footer
        System.out.println(String.format("Bank total deposits = %.1f$", sum)
                .replace(',', '.'));
        System.out.println("-------------------");
    }

    /**
     * This method is used when we want to find a specific branch by using its
     * transit. The method throws an exception if no branches have a matching
     * transit with the one we are looking for.
     * 
     * @param transit is the transit of the branch we are looking for.
     * @return the branch with the matching transit index in the branches
     *         array.
     */
    private int searchTransit(String transit) {
        // We set this variable to an impossible value to detect a transit
        // without a match.
        int indiceRecherche = -1;

        // We loop through each created element in branches for a matching
        // transit.
        for (int i = 0; i < iBranches; i++) {
            if (branches[i].getTransit().equals(transit)) {
                indiceRecherche = i;
                break;
            }
        }

        // If the value of the variable is unchanged, we throw an exception
        if (indiceRecherche == -1) {
            throw new IllegalArgumentException();
        }
        // Else we return the index we were looking for
        else {
            return indiceRecherche;
        }
    }
}
