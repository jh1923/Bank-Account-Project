/**
 * 
 * @author Julia Hu
 * CheckingAccount Class
 *
 */
public class CheckingAccount extends BankAccount
{
	//fields
	final double OVER_DRAFT_FEE;
	final double TRANSACTION_FEE;
	final double FREE_TRANS;
	private int numTransactions;
	
	//constructors
	/**
	 * creates CheckingAccount with a given balance
	 * @param n name
	 * @param b balance
	 * @param odf overdraft fee
	 * @param tf transfer fee
	 * @param freeTrans number of free transactions
	 */
	public CheckingAccount(String n, double b, double odf, double tf, int freeTrans)
	{
		super(n, b);
		OVER_DRAFT_FEE = odf;
		TRANSACTION_FEE = tf;
		FREE_TRANS = freeTrans;
	}
	/**
	 * creates CheckingAccount with a balance of 0
	 * @param n name
	 * @param odf overdraft fee
	 * @param tf transfer fee
	 * @param freeTrans number of free transactions
	 */
	public CheckingAccount(String n, double odf, double tf, int freeTrans)
	{
		this(n, 0, odf, tf, freeTrans);
	} 
	
	//methods
	/**
	 * deposits money into CheckingAccount
	 * @param amt the amount of money to be deposited
	 */
	public void deposit(double amt)
	{
		if (amt<0)
			throw new IllegalArgumentException("Error: Amount deposited must be positive. ");
		if (FREE_TRANS < numTransactions)
			super.deposit(-TRANSACTION_FEE);
		else
		{
			super.deposit(amt);
			numTransactions++;
		}
	}
	
	/**
	 * withdraws money from SavingsAccount
	 * @param amt the amount of money to be withdrawn
	 */
	public void withdraw(double amt)
	{
		if (getBalance() <= 0)
			throw new IllegalArgumentException("Error: Balance is negative or zero. ");
		else
		{
			numTransactions++;
			super.withdraw(amt);
			if (getBalance() - amt < 0)
				super.deposit(-OVER_DRAFT_FEE);		
			if (FREE_TRANS < numTransactions)
				super.deposit(-TRANSACTION_FEE);
		}
	}
	
	/**
	 * transfers money from one account to another
	 * @param other BankAccount that is being deposited into
	 * @param amt amount of money being transferred from SavingsAccount
	 */
	public void transfer(BankAccount other, double amt)
	{
		if (!(other.getName().equals(getName())))
			throw new IllegalArgumentException("Error: Names are different. ");	
		else if (getBalance() - amt < 0)
			throw new IllegalArgumentException("Error: Balance will be negative. ");
		else
			super.transfer(other,amt);
	}
	
	/**
	 * updates at end of the month by resetting number of transactions performed to zero
	 */
	public void endOfMonthUpdate()
	{
		numTransactions = 0;
	}
}
