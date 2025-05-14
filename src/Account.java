import java.util.Scanner;
import java.util.HashSet;
import java.util.Random;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Account
{
    Scanner sc;
    Connection con;
    Statement st;
    PreparedStatement pst;
    ResultSet result;
    HashSet<String> set = new HashSet<>();

    Account(Connection con, Scanner sc)
    {
        this.con = con;
        this.sc = sc;
    }

    void deposite(String acc_no, double amount)
    {
        String query = "UPDATE ACCOUNT SET balance = balance + ? WHERE acc_no = ?";

        try
        {
            pst = con.prepareStatement(query);
            pst.setDouble(1,amount);
            pst.setString(2,acc_no);
            int eff_row = pst.executeUpdate();

            if(eff_row>0) System.out.println("Money Deposited");
            else System.out.println("Failed to deposite Money");

        } catch(SQLException e){
            System.out.println("Error in getting result");
            e.printStackTrace();
        }
    }

    void getBalance(String acc_no, int pin)
    {
        String query = "SELECT balance FROM Account WHERE acc_no = ? AND pin = ?";
        
        try
        {
            pst = con.prepareStatement(query);
            pst.setString(1,acc_no);
            pst.setInt(2,pin);
            result = pst.executeQuery();

            double balance = result.getDouble("balance");
            System.out.println("Current Balance in account: "+acc_no+" is "+"$"+balance);
        } catch(SQLException e){
            System.out.println("Error in getting result");
            e.printStackTrace();
        }
    }

    boolean login(String acc_no, int pin)
    {
        String query = "SELECT acc_no FROM Account WHERE acc_no = ? AND pin = ?";

        try
        {
            pst = con.prepareStatement(query);
            result = pst.executeQuery();
            if(result.next()) return true;
            return false;

        } catch(SQLException e){
            System.out.println("error in login into the account table");
            e.printStackTrace();
        }
        return false;
    }

    void createAccount(String aadhar_no, String name, double balance)
    {
        String acc_no = generateAccountNumber();
        int pin = generatePin();

        String acc_Query = "INSERT INTO Account(acc_no,name,aadhar_no,balance,pin) VALUES(?,?,?,?,?)";
        try
        {
            pst = con.prepareStatement(acc_Query);
            pst.setString(1,acc_no);
            pst.setString(2,name);
            pst.setString(3,aadhar_no);
            pst.setDouble(4,balance);
            pst.setInt(5,pin);

            int eff_row = pst.executeUpdate();
            if(eff_row>0)
            {
                System.out.println("Your new Account Number:-> "+acc_no);
                System.out.println("Your sequirity pin:-> "+pin);
                set.add(acc_no);
            }
            else System.out.println("unable create account in Database");

        } catch(SQLException e){
            System.out.println("EQL EXCEPTION during insering into Account table");
            e.printStackTrace();
        }

    }

    String generateAccountNumber()  //genius CODE
    {
        Random rand = new Random();
        int length = rand.nextInt(11,21);
        StringBuilder sb = new StringBuilder(length);
        sb.append(rand.nextInt(1,9));
        
        do{
            for(int i=1; i<length; i++)
                sb.append(rand.nextInt(10));    
        }while(available(sb.toString()));
        
        return sb.toString();
    }
    boolean available(String acc_no)
    {
        return set.contains(acc_no);
    }

    int generatePin()
    {
        Random rand = new Random();
        return rand.nextInt(9999)+1;
    }

    void extractAllAccountNumbers()
    {
        String query = "SELECT acc_no FROM Account";
        try
        {
            st = con.createStatement();
            result = pst.executeQuery(query);
            while(result.next()) set.add(result.getString("acc_no"));
            
        } catch(SQLException e){
            System.out.println("exception during extraction of account number into set");
            e.getStackTrace();
        }
    }

    boolean getAccount(String aadhar_no)
    {
        int account_counter=1;
        String query = "SELECT acc_no,balance FROM Account WHERE aadhar_no = ?";

        try
        {
            pst = con.prepareStatement(query);
            pst.setString(1,aadhar_no);

            result = pst.executeQuery();
            while(result.next())
            {
                String acc_no = result.getString("acc_no");
                int balance = result.getInt("balance");
                System.out.println("Account "+account_counter+" :->"+acc_no+"(Balance $)"+balance);
                account_counter++;
            }
            System.out.println("-------------------------------------------------------------");
            if(account_counter>1) return true;
            return false;

        } catch(SQLException e){
            System.out.println("Exception in getting account number associated with Aadhar Number");
            e.printStackTrace();
        }
        return false;
    }
}