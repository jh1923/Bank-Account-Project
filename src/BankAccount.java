
public abstract class BankAccount 
{
	//fields
	private static int nextAccNum;
	private String name;
	private int acctNum;
	private double balance;
	
	//constructors
	BankAccount(String n)
	{
		name = n;
		acctNum = nextAccNum;
		nextAccNum++;
	}
	
	BankAccount(String n, double b)
	{
		name = n;
		acctNum = nextAccNum;
		nextAccNum++;
		balance  = b;
	}
	
	//methods
	public void deposit(double amt)
	{
		balance+=amt;
	}
	
	public void withdraw(double amt)
	{
		if (amt<0)
			throw new IllegalArgumentException("Amount withdrawn must be positive");
		else
			balance-=amt;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public int getAcctNum()
	{
		return acctNum;
	}
	
	public abstract void endOfMonthUpdate();
	
	public void transfer(BankAccount other, double amt)
	{
		withdraw(amt);
		other.deposit(amt);
	}
	
	public String toString()
	{
		return "\nAccount Number: "+acctNum+"\tName: "+name+"\tBalance: $"+balance+"";
	}
}

