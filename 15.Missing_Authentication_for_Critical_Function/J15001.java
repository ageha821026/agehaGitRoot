public class J15001 {
    private String toPerson  = "";

    public void bad(String accountNumber, double balance) {
	BankAccount account = new BankAccount();
	account.setAccountNumber(accountNumber);
	account.setToPerson(toPerson);
        
	account.setBalance(balance);
	AccountManager.send(account);

	// ...
    }

    // ...
}

class BankAccount {
    private String accountNumber;
    private String toPerson;
    private double balance;

    public void setAccountNumber(String accNum) {
	accountNumber = accNum;
    }

    public void setToPerson(String person) {
	toPerson = person;
    }

    public void setBalance(double bal) {
	balance = bal;
    }
}

class AccountManager {
    public static void send(BankAccount account) {
	// ...
    }
}
