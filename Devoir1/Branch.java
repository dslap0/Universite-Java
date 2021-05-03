/**
 * The following class is the branch class. It manages the operation on its
 * accounts and takes its directives from the bank class. It also keeps track
 * of the closed accounts and the amounts in those accounts when they were
 * closed. The class also handles and generates a part of the report.
 * 
 * @author Adrien Charron
 */
public class Branch {

    // iAccounts stores the index of the next addition to accounts
    private int iAccounts;
    // closedAccounts stores the number of closed accounts in a branch
    private int closedAccounts;

    // closedAmount stores the amount of money of all the closed accounts
    private float closedAmount;

    // transit identifies the branches of the bank with a unique identification
    private String transit;

    // accounts stores all of the accounts that answers to the branch
    private BankAccount[] accounts;


    // getter for transit
    public String getTransit() {
        return this.transit;
    }

    /**
     * This is the constructor for branch that initialize all parameters of the
     * class.
     */
    public Branch(String transit) {

        // We initialize all parameters
        this.transit = transit;
        accounts = new BankAccount[7];
        iAccounts = 0;
        closedAccounts = 0;
        closedAmount = 0;

    }

    /**
     * This method opens a new account corresponding to a unique number
     * specified in the input and then passed down by the bank class.
     * 
     * @param number is used to identify the account we want to open.
     */
    public void open(String number, float bonus) {

        // We verify if there is already an existing account with the number
        // selected.
        try {
            int i = searchNumber(number);

            // If not, we create the new account
        } catch (IllegalArgumentException e) {

            // We first have to verify that the array still has room for
            // another
            // account, and expand it if it is not the case.
            if (iAccounts >= accounts.length) {
                BankAccount[] temp = accounts.clone();
                accounts = new BankAccount[temp.length + 1];
                System.arraycopy(temp, 0, accounts, 0, temp.length);
            }

            // We create the new account and put it in the array that stores
            // them
            accounts[iAccounts] = new BankAccount(transit, number, bonus);

            // We change the index of the next addition to the array accounts
            iAccounts++;
        }
    }

    /**
     * This closes an account corresponding to a unique number specified in the
     * input and then passed down by the bank class. It also registers the
     * amount left in the account that is about to be close and
     * 
     * @param number is used to identify the account we want to close.
     */
    public void close(String number) {

        // We verify if the account exits
        int i = searchNumber(number);

        // We add the balance to the closed amounts parameter before it is
        // closed
        // to remember it for the report
        closedAmount += accounts[i].getBalance();

        // We copy the accounts array and modify it to close the desired
        // account
        System.arraycopy(accounts, i + 1, accounts, i,
                accounts.length - i - 1);

        // We change the index of the next addition to the array accounts
        iAccounts--;

        // We change the index of closed accounts
        closedAccounts++;
    }

    /**
     * This method asks to a branch (designated by its transit) to deposit an
     * amount in a certain account, designated by its number.
     * 
     * @param number is used to identify the account we want to make a deposit
     *               to.
     * @param amount is passed to the branch bank account method to specify the
     *               amount that is deposed in the account.
     */
    public void deposit(String number, float amount) {

        // We look if the account exists, then we call the deposit method of
        // BankAccount
        int i = searchNumber(number);
        accounts[i].deposit(amount);
    }

    /**
     * This method asks to a branch (designated by its transit) to withdraw an
     * amount from a certain account, designated by its number.
     * 
     * @param number is used to identify the account we want to make a withdraw
     *               from.
     * @param amount is passed to the branch bank account method to specify the
     *               amount that is withdrawn from the account.
     */
    public void withdraw(String number, float amount) {

        // We look if the account exists, then we call the withdraw method of
        // BankAccount
        int i = searchNumber(number);
        accounts[i].withdraw(amount);
    }

    /**
     * This method is used when we want to find a specific account by using its
     * number. The method throws an exception if no accounts have a matching
     * number with the one we are looking for.
     * 
     * @param number is the number of the branch we are looking for.
     * @return the account with the matching number index in the accounts
     *         array.
     */
    private int searchNumber(String number) {

        // We set this variable to an impossible value to detect a account
        // without a match.
        int indiceRecherche = -1;

        // We loop through each created element in branches for a matching
        // account.
        for (int i = 0; i < iAccounts; i++) {
            if (accounts[i].getNumber().equals(number)) {
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

    /**
     * This method asks to each of the branch's account to generate a long
     * report. Those include the current balance of the account and the last
     * operation the account executed. We add all of the account's balance
     * through each iteration and show them at the end of the method.
     * 
     * @return returns the sum of the balance of all the accounts in order for
     *         the Bank class to calculate the bank total deposits.
     */
    public float report() {

        // We initialize the sum of all of the deposed active amounts to 0
        float sum = 0;

        // We print out the first lines before the BankAccount report
        System.out.println("### Branch " + transit + " ###");
        System.out.println("    " + iAccounts + " active accounts.");

        // We loop through the accounts and ask them to generate their reports
        for (int i = 0; i < iAccounts; i++) {
            sum += accounts[i].report(transit);
        }

        // We print the remaining lines with the appropriate format
        System.out.println(String.format("    Total deposits = %.1f$", sum)
                .replace(',', '.'));
        System.out.println("    " + closedAccounts + " closed accounts.");
        System.out.println(String
                .format("    Total closed accounts = %.1f$", closedAmount)
                .replace(',', '.'));
        System.out.println("####################");

        return sum;
    }

    /**
     * This method asks each accounts in a branch to generate the amount
     * available for their users and adds each of these amounts to generate the
     * sum.
     * 
     * @return returns the sum of the amounts in all the accounts of a branch
     */
    public float sum() {

        // We initialize the sum parameter to 0
        float sum = 0;

        // We go through all the accounts and ask them for their current
        // balance
        for (int i = 0; i < iAccounts; i++) {
            sum += accounts[i].getBalance();
        }

        return sum;
    }
}
