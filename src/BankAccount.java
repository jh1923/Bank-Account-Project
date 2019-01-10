/**
 * 
 * @author Julia Hu
 * BankAccount Class
 *
 */

public abstract class BankAccount 
{
	//fields
	private static int nextAccNum = 1;
	private String name;
	private int acctNum;
	private double balance;
	
	//constructors
	/**
	 * creates BankAccount where initial balance is 0
	 * @param n name
	 */
	BankAccount(String n)
	{
		name = n;
		acctNum = nextAccNum;
		nextAccNum++;
		balance = 0;
	}
	/**
	 * creates BankAccount with a given balance
	 * @param n name
	 * @param b balance
	 */
	BankAccount(String n, double b)
	{
		name = n;
		acctNum = nextAccNum;
		nextAccNum++;
		balance  = b;
	}
	
	//methods
	/**
	 * deposits money into BankAccount
	 * @param amt the amount of money to be deposited
	 */
	public void deposit(double amt)
	{
		balance+=amt;
	}
	
	/**
	 * withdraws money from BankAccount
	 * @param amt the amount of money to be withdrawn
	 */
	public void withdraw(double amt)
	{
		if (amt<0)
			throw new IllegalArgumentException("Amount withdrawn must be positive");
		else
			balance-=amt;
	}
	
	/**
	 * returns the name associated with the account
	 * @return name of account holder
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * returns the current balance of the BankAccount
	 * @return balance of BankAccount
	 */
	public double getBalance()
	{
		return balance;
	}
	
	/**
	 * returns the account number of the BankAccount
	 * @return account number of BankAccount
	 */
	public int getAcctNum()
	{
		return acctNum;
	}
	
	public abstract void endOfMonthUpdate();
	
	/**
	 * transfers money from one account to another
	 * @param other BankAccount that is being deposited into
	 * @param amt amount of money being transferred from current BankAccount
	 */
	public void transfer(BankAccount other, double amt)
	{
		withdraw(amt);
		other.deposit(amt);
	}
	
	/**
	 * returns account number, name, and balance in a String
	 * @return information about the account
	 */
	public String toString()
	{
		return "\nAccount Number: "+acctNum+"\tName: "+name+"\tBalance: $"+balance+"";
	}
}

