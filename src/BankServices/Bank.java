package BankServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Bank {

	private String name;

	private List<Account> accounts = new ArrayList<>();
	
	public Bank(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public int createAccount(String name, int date, double initial) {
		Account account = new Account(accounts.size() + 1, name, date, initial);
		accounts.add(account);
		return accounts.size();
	}
	
	public Account deleteAccount(int code, int date) throws InvalidCode {
		Account account = getAccount(code);
		try {
			account.withdraw(date, account.getBalance());
		} catch (InvalidValue e) {
			e.printStackTrace();
		}
		return account;
	}
	
	public Account getAccount(int code) throws InvalidCode {
		if (code < 1 || code > accounts.size()) new InvalidCode();
		return accounts.get(code - 1);
	}

	public void deposit(int code, int date, double value) throws InvalidCode {
		Account account = getAccount(code);
		account.deposit(date, value);
	}

	public void withdraw(int code, int date, double value) throws InvalidCode, InvalidValue {
		Account account = getAccount(code);
		account.withdraw(date, value);
	}
	
	public void transfer(int fromCode, int toCode, int date, double value) throws InvalidCode, InvalidValue {
		withdraw(fromCode, date, value);
		deposit(toCode, date, value);
	}
	
	public double getTotalDeposit() {
		return accounts.stream().mapToDouble(Account::getBalance).sum();
	}
	
	public List<Account> getAccounts() {
		return accounts.stream().filter(account -> account.getBalance() > 0)
				.sorted(Comparator.comparing(Account::getCode).reversed()).toList();
	}
	

	public List<Account> getZeroAccounts() {
		return accounts.stream().filter(account -> account.getBalance() == 0).toList();
	}

	public List<Account> getAccountsByBalance(double low, double high) {
		return accounts.stream().filter(account -> account.getBalance() >= low && account.getBalance() <= high)
				.sorted(Comparator.comparing(Account::getBalance).reversed()).toList();
	}
	
	public long getNumberHigher(double min) {
		return accounts.stream().filter(account -> account.getBalance() >= min).count();
	}

}
