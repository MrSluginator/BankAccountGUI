import java.time.LocalDateTime;
import java.util.ArrayList;

public class BankAccount {
	private long accountNumber;
	private String accountName;
	private double balance;
	private long accountID;
	private double withdrawalFee;
	private double annualInterestRate;
	private static long nextAccountNumber = 1000000;
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private ArrayList<Transaction> selectedTransactions = new ArrayList<Transaction>();
	
	public BankAccount(String AccountName) {
		this(AccountName, 0);
	}
	
	public BankAccount(String AccountName, double Balance) {
		this(AccountName, Balance, 0, 0);	
	}
	
	public BankAccount(String AccountName, double Balance, double WithdrawalFee, double AnnualInterestRate) {
		accountName = AccountName;
		balance = Balance;
		withdrawalFee = WithdrawalFee;
		annualInterestRate = AnnualInterestRate;
		accountNumber = nextAccountNumber;
		nextAccountNumber++;
		accountID = accountNumber;
	}
	
	public String truncateString(String string) {
		int maxLength = 20;
		if(string.length() > maxLength) {
		    string = string.substring(0, maxLength);
		}
		return string;
	}

	public String getAccountName() {
		return truncateString(this.accountName);
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public double getWithdrawalFee() {
		return this.withdrawalFee;
	}
	
	public double getAnnualInterestRate() {
		return this.annualInterestRate;
	}
	
	public long getAccountID() {
		return this.accountID;
	}
	
	public static long getNextAccountID() {
		return nextAccountNumber;
	}
	
	public void setWithdrawalFee(double withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}
	
	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}
	
	public void deposit(double amount) {
		deposit(amount, "");
	}
	
	public void deposit(double amount, String transactionDescription) {
		deposit(LocalDateTime.now(), amount, transactionDescription);
		
	}
	
	public void deposit(LocalDateTime dateTime, double amount, String transactionDescription) {
		balance += amount;
		Transaction newTransaction = new Transaction(dateTime, amount, transactionDescription);
		insertTransaction(newTransaction);
	}
	
	
	public void withdraw(double amount) {
		withdraw(amount, "");
	}
	
	public void withdraw(double amount, String transactionDescription) {
		withdraw(LocalDateTime.now(), amount, transactionDescription);
	}
	
	public void withdraw(LocalDateTime dateTime, double amount, String transactionDescription) {
		balance -= (amount + withdrawalFee);
		Transaction newTransaction = new Transaction(dateTime, amount, transactionDescription);
		insertTransaction(newTransaction);
	}
	
	public void insertTransaction(Transaction newTransaction) {
		if (transactionList.size() == 0) {
			transactionList.add(newTransaction);
		}
		else {
			for (int i = 0; i <= transactionList.size(); i++) {
				if (i == transactionList.size()) {
					transactionList.add(i, newTransaction);
					break;
				}
				else if (transactionList.get(i).getTransactionTime().isAfter(newTransaction.getTransactionTime())) {
					transactionList.add(i, newTransaction);
					break;
				}
			}	
		}
	}
	
	public boolean isOverDrawn() {
		if (balance < 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addAnnualInterest() {
		if (isOverDrawn() == false) {
			balance += balance*(annualInterestRate/100);
		}
		else {}
	}
	
	public String toString() {
		if (balance < 0) {
			return String.format("BankAccount: name = '%s'; balance = ($%.2f)", accountName, balance*-1);
		}
		else {
		return String.format("BankAccount: name = '%s'; balance = $%.2f", accountName, balance);
		}
	}

	public ArrayList<Transaction> getTransactions(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		//clear selected transactions
		if (selectedTransactions.size() > 0) {
			for (int i = 0; i < selectedTransactions.size(); i = 0) {
				selectedTransactions.remove(i);
			}
		}
		
		for (int i = 0; i < transactionList.size(); i++) {
			if (endDateTime == null | startDateTime == null) {
				if (startDateTime != null) {
					if (transactionList.get(i).getTransactionTime().isAfter(startDateTime) | transactionList.get(i).getTransactionTime().isEqual(startDateTime) ) {
						selectedTransactions.add(transactionList.get(i));
					}
					else {}
				}
				else if (endDateTime != null) {
					if (transactionList.get(i).getTransactionTime().isBefore(endDateTime) | transactionList.get(i).getTransactionTime().isEqual(endDateTime)) {
						selectedTransactions.add(transactionList.get(i));
					}
				}
				else {
					selectedTransactions.add(transactionList.get(i));
				}
				
			}
			else {
				if ((transactionList.get(i).getTransactionTime().isAfter(startDateTime) | transactionList.get(i).getTransactionTime().isEqual(startDateTime)) 
						&& (transactionList.get(i).getTransactionTime().isBefore(endDateTime) | transactionList.get(i).getTransactionTime().isEqual(endDateTime))) {
					selectedTransactions.add(transactionList.get(i));
				}
				else {}
			}
		}
		return selectedTransactions;
	}

	

	
}