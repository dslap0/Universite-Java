/**
 * The following class is the BankAccount class. This class manages the
 * operations on its balance and print a part of the report. It also contains
 * its number for identification purposes.
 * 
 * @author Nicolas Levasseur & Adrien Charron
 */
public class BankAccount {

    // lastOperation stores the last operation, ready to be printed in report()
    private String lastOperation;
    // number stores the number of the account, for identification purposes
    private String number;

    // This float stores the balance of the account
    private float balance;


    // Getters
    public float getBalance() {
        return balance;
    }

    public String getNumber() {
        return number;
    }

    /**
     * This is the constructor that initialises all parameters according to the
     * argument that are passed to it.
     * 
     * @param transit is the transit of the branch, only useful for
     *                lastOperation.
     * @param number  is the new number of the account.
     * @param bonus   is the bonus given by the bank when a new account is
     *                opened.
     */
    public BankAccount(String transit, String number, float bonus) {

        // We initalize all parameters
        this.number = number;
        balance = bonus;
        lastOperation = "open " + transit + ":" + number + " balance = %.1f$";
        lastOperation =
                String.format(lastOperation, balance).replace(',', '.');
    }

    /**
     * This is the method to deposit an amount to the account.
     * 
     * @param amount is added to the current balance.
     */
    public void deposit(float amount) {

        // We ajust the parameters
        balance += amount;
        lastOperation =
                String.format("deposit %.1f$", amount).replace(',', '.');
    }

    /**
     * This is the method to withdraw an amount from the account, if the
     * balance permits it.
     * 
     * @param amount is subtracted to the current balance.
     */
    public void withdraw(float amount) {

        // We test the result of the subtraction
        if (balance - amount >= 0) {
            // We adjust the parameters
            balance -= amount;
            lastOperation =
                    String.format("withdraw %.1f$", amount).replace(',', '.');
        }
    }

    /**
     * This method prints out the report for the current account, including the
     * account's identification, the balance of the account and its last
     * operation.
     * 
     * @param transit is used to identify the branch the account is in.
     * @return returns the balance of the account, it will be use by the branch
     *         class to calculate the branch total deposit.
     */
    public float report(String transit) {

        // We print the report with the balance and the last operation in the
        // appropriate format
        System.out.println("*** Account " + transit + ":" + number);
        System.out.println(String.format("    Balance = %.1f$", balance)
                .replace(',', '.'));
        System.out.println("    Last operation " + lastOperation);

        return balance;
    }
}
