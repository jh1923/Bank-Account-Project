/**
 * 
 * @author Julia Hu
 * SavingsAccount Class
 *
 */
public class SavingsAccount extends BankAccount
{
	//fields
	private double intRate;
	private final double MIN_BAL;
	private final double MIN_BAL_FEE;
	
	//constructors
	/**
	 * creates SavingsAccount with a given balance
	 * @param n name
	 * @param b balance
	 * @param r rate
	 * @param mb minimum balance
	 * @param mbf minimum balance fee
	 */
	public SavingsAccount(String n, double b, double r, double mb, double mbf)
	{
		super(n, b);
		intRate = r;
		MIN_BAL = mb;
		MIN_BAL_FEE = mbf;
	}
	
	/**
	 * creates SavingsAccount where initial balance is zero
	 * @param n name
	 * @param r rate
	 * @param mb minimum balance
	 * @param mbf minimum balance fee
	 */
	public SavingsAccount(String n, double r, double mb, double mbf)
	{
		this(n, 0, r, mb, mbf);
	}
	
	//methods
	/**
	 * withdraws money from SavingsAccount
	 * @param amt the amount of money to be withdrawn
	 */
	public void withdraw(double amt)
	{
		if (getBalance() - amt < 0)
			throw new IllegalArgumentException("Error: Balance cannot go negative. ");
		else
		{
			if (getBalance() < MIN_BAL)
				throw new IllegalArgumentException("Error: Balance is already below minimum balance requirement.");
			else
			{
				if (getBalance() - amt < MIN_BAL)
					super.deposit(-MIN_BAL_FEE);
				super.withdraw(amt);
			}
		}
	}
	
	/**
	 * deposits money into SavingsAccount
	 * @param amt the amount of money to be deposited
	 */
	public void deposit(double amt)
	{
		if (amt < 0)
			throw new IllegalArgumentException("Error: Amount deposited must be positive. ");
		else
			super.deposit(amt);
	}	
	
	/**
	 * transfers money from one account to another
	 * @param other BankAccount that is being deposited into
	 * @param amt amount of money being transferred from SavingsAccount
	 */
	public void transfer(BankAccount other, double amt)
	{
		if (!(other.getName().equals(getName())))
			throw new IllegalArgumentException("Error: Names of accounts are different. ");	
		else if (getBalance()-amt<0)
			throw new IllegalArgumentException("Error: Balance will be negative. ");
		else
			super.transfer(other,amt);
	}
	
	/**
	 * calculates and adds interest to SavingsAccount
	 */
	public void addInterest()
	{
		deposit(getBalance() + getBalance()*intRate);
	}
	
	/**
	 * updates at end of month by adding interest to account
	 */
	public void endOfMonthUpdate()
	{
		addInterest();
	}
}
