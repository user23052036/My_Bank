import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;



public class MyBank 
{
    private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String username = "HR";
    private static final String password = "hr";

    private static String name;
    private static String aadhar_no;
    private static String passkey;

    final static Scanner sc = new Scanner(System.in);

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

            Account account = new Account(con,sc);
            account.extractAllAccountNumbers();
            Aadhar aadhar = new Aadhar(con,account,sc);
            Transaction transaction = new Transaction(con,sc);

            while(true)
            {
                System.out.println("\n\n*** WELCOME TO MY BANK ***");
                System.out.println("choose below options");
                System.out.println("------------------------------------------------------");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("\nEnter your choice: ");
                int choice1 = sc.nextInt();

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
                            
                                System.out.println("1. Create a new Bank Account");
                                System.out.println("2. Open an Existing Bank Account");
                                System.out.print("\nEnter your choice: ");
                                int choice2 = sc.nextInt();
                                switch(choice2)
                                {
                                    case 1:
                                    {
                                        System.out.println("Innitial Deposition:->");
                                        double balance=sc.nextDouble();
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
                                        String acc_no = sc.next();
                                        System.out.println("Enter the pin: ");
                                        int pin = sc.nextInt();
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
                                            int choice3 = sc.nextInt();
                                            
                                            System.out.print("Enter your Pin: ");
                                            pin = sc.nextInt();
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
                                                    int amount = sc.nextInt();
                                                    account.deposite(acc_no,amount);
                                                    account.getBalance(acc_no, pin);
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
                                                    account.getBalance(acc_no, pin);
                                                    break;
                                                }
                                                case 3:
                                                {
                                                    account.getBalance(acc_no,pin);
                                                    break;
                                                }
                                                case 4:
                                                {
                                                    System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                                                    return;
                                                }
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
        } catch(SQLException e){
            System.out.println("SQL exception occured !!!");
        }
    }

    static void getDetails()
    {
        System.out.println("Enter your Aadhar Number:-> ");
        aadhar_no = sc.next();
        System.out.println("Enter the name:- ");
        name = sc.nextLine();
        System.out.println("Enter the PassKey:-> ");
        passkey = sc.next();
    }
}
