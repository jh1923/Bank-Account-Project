/**
 * @author Julia Hu
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class BankAccountMain 
{
	//method that checks if input is a number
	private static boolean isNumeric(String str)
	{
		try
		{
			Double.parseDouble(str);
			return true;
		}
		catch(IllegalArgumentException e)
		{
			return false;
		}
	}
	
	//returns the BankAccount with a given account number
	private static BankAccount getAccount(ArrayList<BankAccount> accounts, int num)
	{
		for (BankAccount account : accounts)
		{
			if (account.getAcctNum() == num)
				return account;
		}
		return null;
	}
	//returns the BankAccount with a given account holder name
	private static ArrayList<BankAccount> getAccounts(ArrayList<BankAccount> accounts, String name)
	{
		ArrayList<BankAccount> accountsName = new ArrayList<BankAccount>();
		for (BankAccount account : accounts)
			if ((""+account.getName()).equals(name))
				accountsName.add(account);
		return accountsName;
	}
	
	
	//main method		
	public static void main(String[] args) 
	{
		//ArrayList of bank accounts
		ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
		
		//Creates new scanner object
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		//local variables
		int OVER_DRAFT_FEE = 15;
		double RATE = .0025;
		double TRANSACTION_FEE = 1.5;
		int MIN_BAL = 300;
		int MIN_BAL_FEE = 10;
		int FREE_TRANSACTIONS = 10;
		
		boolean continueProgram = true;
		while (continueProgram)
		{	
			System.out.print("\n=========\n Options \n=========\n Add an account: Type \"account\" \n Make a Transaction: Type \"transaction\" \n Terminate the Program: Type \"terminate\" \n");
			String response = in.next().toLowerCase();
			in.nextLine();
			
			//creates new account
			if (response.equals("account"))
			{
				boolean continueAccountCreation = true;
				BankAccount newAccount = null;
				//basic information about account: name
				System.out.print("What is the name of the account holder? ");
				String name = in.nextLine();
				
				//basic information about account: initial balance
				System.out.print("Does the account have an initial balance? ");
				String balance = "";
				String balanceResponse = in.next();
				in.nextLine();
				
				//runs while user does not input either "yes" or "no"
				while (!(balanceResponse.equals("yes")) && !(balanceResponse.equals("no")))
				{
					System.out.print("Error: input must either be \"yes\" or \"no\": ");
					balanceResponse = in.next();
					in.nextLine();
				}
				if (balanceResponse.equals("yes"))
				{
					do
					{
						System.out.print("Enter the initial balance: $");
						balance = in.next();
						in.nextLine();
						if (!isNumeric(balance))
							System.out.print("Error: input must be a number.\n");
					} while (!isNumeric(balance));
				}
				while (continueAccountCreation)
				{	
					//determines account type
					System.out.print("Is the account a checking or savings account? ");
					String accountType = in.next();
					in.nextLine();
					
					//creates new checking account
					if (accountType.equals("checking"))
					{
						if (balanceResponse.equals("yes"))
						{
							newAccount = (new CheckingAccount(name, Double.parseDouble(balance), OVER_DRAFT_FEE, TRANSACTION_FEE, FREE_TRANSACTIONS));
							accounts.add(newAccount);
						}
						else
						{
							newAccount = (new CheckingAccount(name, OVER_DRAFT_FEE, TRANSACTION_FEE, FREE_TRANSACTIONS));
							accounts.add(newAccount);
						}
						continueAccountCreation = false;
					}
					
					//creates new savings account
					else if (accountType.equals("savings"))
					{
						if (balanceResponse.equals("yes"))
						{
							newAccount = new SavingsAccount(name, Double.parseDouble(balance), RATE, MIN_BAL, MIN_BAL_FEE);
							accounts.add(newAccount);
						}
						else
						{
							newAccount = new SavingsAccount(name, RATE, MIN_BAL, MIN_BAL_FEE);
							accounts.add(newAccount);
						}
						continueAccountCreation = false;
					}
					else
						System.out.print("\nInvalid response. Please respond \"checking\" or \"savings\". \n\n");
					
					if (!(newAccount == null))
							System.out.print("\nNew account added. " + newAccount.toString() + "\tAccount Type: " + accountType.substring(0,1).toUpperCase() + accountType.substring(1) + "\n");
				}
			}
			else if (response.equals("transaction"))
			{
				//performs transaction type
				boolean continueTransaction = true;
				while (continueTransaction)
				{
					//determines transaction type
					System.out.print("\n===========\nTransaction\n===========\n Make a Deposit: Type \"deposit\"\n Make a Withdrawl: Type \"withdraw\"\n Make a Transfer: Type \"transfer\"\n Get Account Numbers: Type \"numbers\"\n Exit Transaction Screen: Type \"exit\"\n");
					String transactionResponse = in.next();
					in.nextLine();
					
					switch(transactionResponse)
					{
						case "deposit":
						{
							System.out.print("\n=======\nDeposit\n=======");
							boolean continueDeposit = true;
							while (continueDeposit)
							{
								//determines account number of account being transacted
								BankAccount currentAccount = null;
								int currentAccountNum;
								boolean continueAccNum = true;
								
								while(continueAccNum)
								{
									System.out.print("\nEnter the account number: ");
									try
									{
										currentAccountNum = in.nextInt();
										currentAccount = getAccount(accounts, currentAccountNum);	
									}
									catch (InputMismatchException e)
									{
										System.out.println("Error: input must be an integer. ");
									}
									catch (Exception e) 
									{
										System.out.println(e.getMessage());
								    }
									finally
									{
										in.nextLine();
										if (currentAccount == null)
										{
											System.out.print("Account not found. \nOptions:\n Re-enter account number: Type \"number\"\n Search numbers by name: Type \"name\"\n Exit Deposit Screen: Type \"exit\"\n");
											String accNumResponse = in.next().toLowerCase();
											in.nextLine();
											if (accNumResponse.equals("name"))
											{
												System.out.print("Enter the account holder's name: ");
												String name = in.nextLine();
												ArrayList<BankAccount> accountsList = getAccounts(accounts, name);
												if (accountsList.size() == 0)
												{
													System.out.print("Error: No accounts found. Returning to Transaction screen.");
													continueAccNum = false;
													continueDeposit = false;
												}
												else
												{
													System.out.print("\nAccounts found:");
													for (BankAccount account : accountsList)
													{
														System.out.print(account.toString());
														if (account instanceof SavingsAccount) 
														       System.out.print("\tAccount type: Savings"); 
												        else if (account instanceof CheckingAccount)
												           System.out.print("\tAccount type: Checking");
													}
													System.out.print("\n");
													continueDeposit = false;
												}
											}
											else if (accNumResponse.equals("exit"))
											{
												continueAccNum = false;
												continueDeposit = false;
											}
											else if (!accNumResponse.equals("number") && (!accNumResponse.equals("name")))
												System.out.print("Invalid input. ");
										}
										else 
										{
											System.out.print("Account found. ");
											continueAccNum = false;
										}			
									}
								}
								if (currentAccount != null)
								{
									//prints information about account
									System.out.print(currentAccount.toString()+"\tAccount type: ");
									if (currentAccount instanceof SavingsAccount) 
								       System.out.println("Savings\n"); 
							        else if (currentAccount instanceof CheckingAccount)
							           System.out.println("Checking\n");
									//asks for amount to deposit
									System.out.print("How much would you like to deposit? $");
									try
									{
										//carries out deposit method for account
										double amt = in.nextDouble();
										in.nextLine();
										currentAccount.deposit(amt);
										System.out.print("New balance: $" + currentAccount.getBalance() + "\n");
										continueDeposit = false;
									}
									catch (InputMismatchException e)
									{
										System.out.println("Error: input must be a number. ");
										in.nextLine();
									}
									catch (Exception e)
									{
										System.out.println(e.getMessage());
									}
								}
							}
							break;
						}
						
						case "withdraw":
						{
							System.out.print("\n========\nWithdraw\n========");
							boolean continueWithdraw = true;
							while (continueWithdraw)
							{
								//determines account number of account being transacted
								BankAccount currentAccount = null;
								int currentAccountNum;
								boolean continueAccNum = true;
								
								while(continueAccNum)
								{
									System.out.print("\nEnter the account number: ");
									try
									{
										currentAccountNum = in.nextInt();
										currentAccount = getAccount(accounts, currentAccountNum);	
									}
									catch (InputMismatchException e)
									{
										System.out.println("Error: input must be an integer. ");
									}
									catch (Exception e) 
									{
										System.out.println(e.getMessage());
								    }
									finally
									{
										in.nextLine();
										if (currentAccount == null)
										{
											System.out.print("Account not found. \nOptions:\n Re-enter account number: Type \"number\"\n Search numbers by name: Type \"name\"\n Exit Deposit Screen: Type \"exit\"\n");
											String accNumResponse = in.next().toLowerCase();
											in.nextLine();
											if (accNumResponse.equals("name"))
											{
												System.out.print("Enter the account holder's name: ");
												String name = in.nextLine();
												ArrayList<BankAccount> accountsList = getAccounts(accounts, name);
												if (accountsList.size() == 0)
												{
													System.out.print("Error: No accounts found. Returning to Transaction screen.");
													continueAccNum = false;
													continueWithdraw = false;
												}
												else
												{
													System.out.print("\nAccounts found:");
													for (BankAccount account : accountsList)
													{
														System.out.print(account.toString());
														if (account instanceof SavingsAccount) 
														       System.out.print("\tAccount type: Savings"); 
												        else if (account instanceof CheckingAccount)
												           System.out.print("\tAccount type: Checking");
													}
													System.out.print("\n");
													continueWithdraw = false;
												}
											}
											else if (accNumResponse.equals("exit"))
											{
												continueAccNum = false;
												continueWithdraw = false;
											}
											else if (!accNumResponse.equals("number") && (!accNumResponse.equals("name")))
												System.out.print("Invalid input. ");
										}
										else 
										{
											System.out.print("Account found. ");
											continueAccNum = false;
										}			
									}
								}
								if (currentAccount != null)
								{
									//prints information about account
									System.out.print(currentAccount.toString()+"\tAccount type: ");
									if (currentAccount instanceof SavingsAccount) 
								       System.out.println("Savings\n"); 
							        else if (currentAccount instanceof CheckingAccount)
							           System.out.println("Checking\n");
									//asks for amount to withdraw
									System.out.print("How much would you like to withdraw? $");
									try
									{
										//carries out withdraw method for account
										double amt = in.nextDouble();
										in.nextLine();
										currentAccount.withdraw(amt);
										System.out.print("New balance: $" + currentAccount.getBalance() + "\n");
										continueWithdraw = false;
									}
									catch (InputMismatchException e)
									{
										System.out.println("Error: input must be a number. ");
										in.nextLine();
									}
									catch (Exception e)
									{
										System.out.println(e.getMessage());
									}
								}
							}
							break;
						}
						case "transfer":
						{
							System.out.print("\n========\nTransfer\n========");
							boolean continueTransfer = true;
							while (continueTransfer)
							{
								BankAccount withdrawAccount = null;
								BankAccount depositAccount = null;
								int currentAccountNum;
								boolean continueAccNum = true;
								boolean continueName = true;
								while (continueName)
								{
									System.out.print("\nEnter the accounts holder's name: ");
									String name = in.nextLine();
									ArrayList<BankAccount> accountsList = getAccounts(accounts, name);
									if (accountsList.size() == 0)
									{
										System.out.print("Error: Holder does not exist. Returning to Transactions screen. ");
										continueTransfer = false;
										continueName = false;
										continueAccNum = false;
									}
									else
									{
										System.out.print("\nAccounts found:");
										for (BankAccount account : accountsList)
										{
											System.out.print(account.toString());
											if (account instanceof SavingsAccount) 
											       System.out.print("\tAccount type: Savings"); 
									        else if (account instanceof CheckingAccount)
									           System.out.print("\tAccount type: Checking");
										}
										System.out.print("\n");
										continueName = false;
									}
								}
								//runs twice: sets account being withdrawn from and account being deposited into
								while(continueAccNum)
									for (int i=0; i<=1; i++)
									{
										BankAccount currentAccount = null;
										System.out.print("\nEnter the account number of the account to");
										if (i==0)
											System.out.print(" withdraw from: ");
										else if (i==1)
											System.out.print(" deposit into: ");
										try
										{
											currentAccountNum = in.nextInt();
											currentAccount = getAccount(accounts, currentAccountNum);	
										}
										catch (InputMismatchException e)
										{
											System.out.println("Error: input must be an integer. ");
										}
										catch (Exception e) 
										{
											System.out.println(e.getMessage());
									    }
										finally
										{
											in.nextLine();
											if (currentAccount == null)
											{
												System.out.print("Account not found.\n\nOptions:\n Search numbers by name: Type \"name\"\n Exit Transfer Screen: Type \"exit\"\n");
												String accNumResponse = in.next().toLowerCase();
												in.nextLine();
												
												if (accNumResponse.equals("number"))
													i = 3;
												else if (accNumResponse.equals("name"))
												{
													continueAccNum = false;
													i = 3;
												}
												else if (accNumResponse.equals("exit"))
												{
													continueTransfer = false;
													continueName = false;
													continueAccNum = false;
													i = 3;
												}
												else
												{
													System.out.print("Invalid input. Returning to Transfer Screen.\n========\nTransfer\n========");
													continueAccNum = false;
													i = 3;
												}
											}
											else 
											{
												System.out.print("\nAccount found. " + currentAccount.toString());
												if (currentAccount instanceof SavingsAccount) 
												       System.out.print("\tAccount type: Savings"); 
										        else if (currentAccount instanceof CheckingAccount)
										           System.out.print("\tAccount type: Checking");
												if (i==0)
													withdrawAccount = currentAccount;
												else if (i==1)
													depositAccount = currentAccount;
												continueAccNum = false;
											}	
										}
									}	
								if (!(withdrawAccount==null) && !(depositAccount==null))
								{
									System.out.print("\nHow much would you like to transfer? $");
									try
									{
										//carries out transfer method for accounts
										double amt = in.nextDouble();
										in.nextLine();
										withdrawAccount.transfer(depositAccount, amt);;
										System.out.print("\nNew balances:" + depositAccount.toString());
										if (depositAccount instanceof SavingsAccount) 
										       System.out.print("\tAccount type: Savings"); 
								        else if (depositAccount instanceof CheckingAccount)
								           System.out.print("\tAccount type: Checking");
										System.out.print(withdrawAccount.toString());
										if (withdrawAccount instanceof SavingsAccount) 
										       System.out.print("\tAccount type: Savings"); 
								        else if (withdrawAccount instanceof CheckingAccount)
								           System.out.print("\tAccount type: Checking");
										continueTransfer = false;
									}
									catch (InputMismatchException e)
									{
										System.out.println("Error: input must be a number. ");
										in.nextLine();
									}
									catch (Exception e)
									{
										System.out.println(e.getMessage() + "Returning to Transactions screen.\n");
										continueTransfer = false;
									}
								}
							}
							break;
						}
						case "numbers":
						{
							System.out.print("\n=======\nNumbers\n=======\nEnter the account holder's name: ");
							String name = in.nextLine();
							ArrayList<BankAccount> accountsList = getAccounts(accounts, name);
							if (accountsList.size() == 0)
								System.out.print("No accounts found. ");
							else
							{
								System.out.print("\nAccounts found:");
								for (BankAccount account : accountsList)
								{
									System.out.print(account.toString());
									if (account instanceof SavingsAccount) 
									       System.out.print("\tAccount type: Savings"); 
							        else if (account instanceof CheckingAccount)
							           System.out.print("\tAccount type: Checking");
								}
							}
							break;
						}
						case "exit":
						{
							continueTransaction = false;
							System.out.print("Returning to Options screen. ");
						}
						default:
						{
							System.out.print("Invalid Response. ");
						}
					}
				}
			}
			else if (response.equals("terminate"))
			{
				System.out.print("k see ya");
				continueProgram = false;
			}
			else
				System.out.print("\nInvalid response. ");
		}
	}
}
	

