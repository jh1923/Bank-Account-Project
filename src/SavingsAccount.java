
public class SavingsAccount extends BankAccount
{
	//fields
	private double intRate;
	private final double MIN_BAL;
	private final double MIN_BAL_FEE;
	
	//constructors
	
	//initial balance determined when constructor is called
	public SavingsAccount(String n, double b, double r, double mb, double mbf)
	{
		super(n, b);
		intRate = r;
		MIN_BAL = mb;
		MIN_BAL_FEE = mbf;
	}
	
	//initial balance of zero
	public SavingsAccount(String n, double r, double mb, double mbf)
	{
		this(n, 0, r, mb, mbf);
	}
	
	//methods
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
	public void deposit(double amt)
	{
		if (amt < 0)
			throw new IllegalArgumentException("Error: Amount deposited must be positive. ");
		else
			super.deposit(amt);
	}	
	
	public void transfer(BankAccount other, double amt)
	{
		if (!(other.getName().equals(getName())))
			throw new IllegalArgumentException("Error: Names of accounts are different. ");	
		else if (getBalance()-amt<0)
			throw new IllegalArgumentException("Error: Balance will be negative. ");
		else
			super.transfer(other,amt);
	}
	
	public void addInterest()
	{
		deposit(getBalance() + getBalance()*intRate);
	}
	
	public void endOfMonthUpdate()
	{
		addInterest();
	}
}
