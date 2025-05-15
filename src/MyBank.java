import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;



public class MyBank 
{
    private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String username = "HR";
    private static final String password = "hr";

    private static String name;
    private static String aadhar_no;
    private static String passkey;

    final static BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String args[])
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("ORACLE jdbc Driver loaded and registered");
        } catch(ClassNotFoundException e){
            System.out.println("class not found");
        }

        try
        {
            final Connection con = DriverManager.getConnection(jdbcURL, username, password);

            Account account = new Account(con,sb);
            account.extractAllAccountNumbers();
            Aadhar aadhar = new Aadhar(con,account,sb);
            Transaction transaction = new Transaction(con,sb);

            while(true)
            {
                System.out.println("\n\n*** WELCOME TO MY BANK ***");
                System.out.println("choose below options");
                System.out.println("------------------------------------------------------");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("\nEnter your choice: ");
                int choice1 = Integer.parseInt(sb.readLine());

                switch(choice1)
                {
                    case 1:
                    {
                        System.out.println("Enter details to Register to Banking System\n");
                        getDetails();
                        aadhar.register(aadhar_no,name,passkey);
                        break;
                    }
                    case 2:
                    {
                        System.out.println("Enter details to login to Banking System\n");
                        getDetails();
                        if(aadhar.login(aadhar_no,passkey))
                        {
                            System.out.println("You have successfully logged in\n");
                            System.out.println("choose below options");
                            System.out.println("------------------------------------------------------");

                            while(true)
                            {
                                double balance = 0.0;
                                System.out.println("1. Create a new Bank Account");
                                System.out.println("2. Open an Existing Bank Account");
                                System.out.print("\nEnter your choice: ");
                                int choice2 = Integer.parseInt(sb.readLine());
                                switch(choice2)
                                {
                                    case 1:
                                    {
                                        System.out.println("Innitial Deposition:->");
                                        balance=Double.parseDouble(sb.readLine());
                                        account.createAccount(aadhar_no,name,balance);
                                        break;
                                    }
                                    case 2:
                                    {
                                        if(!account.getAccount(aadhar_no))
                                        {
                                            System.out.println("No Bank Account exists under the Aadhar no: "+aadhar_no);
                                            break;
                                        }

                                        System.out.println("\nLogin to Your Bank Account\n");
                                        System.out.print("Enter Account Number: ");
                                        String acc_no = sb.readLine();
                                        System.out.println("Enter the pin: ");
                                        int pin = Integer.parseInt(sb.readLine());

                                        if(!account.login(acc_no,pin))
                                        {
                                            System.out.println("Failed to Login to Your Bank Account");
                                            break;
                                        }

                                        while(true)
                                        {
                                            System.out.println("1. Deposite Money");
                                            System.out.println("2. Send Money");
                                            System.out.println("3. Check Balance");
                                            System.out.println("4. Exit");
                                            System.out.print("\nEnter your choice: ");
                                            int choice3 = Integer.parseInt(sb.readLine());
                                            
                                            System.out.print("Enter your Pin: ");
                                            pin = Integer.parseInt(sb.readLine());
                                            if(!account.login(acc_no,pin))
                                            {
                                                System.out.println("Enter the correct pin TRY AGAIN");
                                                continue;
                                            }
                                            switch(choice3)
                                            {
                                                case 1:
                                                {
                                                    System.out.println("Enter amount to deposite: ");
                                                    double amount = Double.parseDouble(sb.readLine());
                                                    account.deposite(acc_no,amount);
                                                    balance = account.getBalance(acc_no);
                                                    break;
                                                }
                                                case 2:
                                                {
                                                    try
                                                    {
                                                        transaction.transfer(acc_no);
                                                    } catch(AccountNotFoundException e1){
                                                        System.out.println(e1);
                                                    } catch(MinimumBalanceException e2){
                                                        System.out.println(e2);
                                                    }
                                                    balance = account.getBalance(acc_no);
                                                    break;
                                                }
                                                case 3:
                                                {
                                                    balance = account.getBalance(acc_no);
                                                    break;
                                                }
                                                case 4:
                                                {
                                                    System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                                                    return;
                                                }
                                                default:
                                                    System.out.println("Current Balance in account: "+acc_no+" is "+"$"+balance);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else System.out.println("User not registered using Aadhar");
                        break;
                    }
                    case 3:
                    {
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                        System.out.println("Exiting System!");
                        return;
                    }
                }
            }
        } catch(SQLException e1){
            System.out.println("SQL exception occured !!!");
        } catch(IOException e2){
            e2.printStackTrace();
        }
    }

    static void getDetails() throws IOException
    {
        System.out.println("Enter your Aadhar Number:-> ");
        aadhar_no = sb.readLine();
        System.out.println("Enter the name:- ");
        name = sb.readLine();
        System.out.println("Enter the PassKey:-> ");
        passkey = sb.readLine();
    }
}
