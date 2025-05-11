import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;



public class MyBank 
{
    private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String username = "HR";
    private static final String password = "hr";

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
            final Scanner sc = new Scanner(System.in);

            Account account = new Account(con,sc);
            account.extractAccountNumbers();
            Aadhar aadhar = new Aadhar(con,account,sc);

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
                        aadhar.register();
                    }
                    case 2:
                    {
                        if(aadhar.login())
                        {
                            System.out.println("You have successfully logged in to your account");
                            System.out.println("choose below options");
                            System.out.println("------------------------------------------------------");
                            System.out.println("1. Deposite");
                            System.out.println("2. Send");
                            System.out.println("3. Exit");
                            System.out.print("\nEnter your choice: ");
                            int choice2 = sc.nextInt();
                            
                            Transaction tn = new Transaction(con, sc);
                            switch(choice2)
                            {
                                case 1:
                                {}
                                case 2:
                                {}
                                case 3:
                                {}
                            }
                        }
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
}
