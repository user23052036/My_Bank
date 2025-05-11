import java.util.Scanner;
import java.util.HashSet;
import java.util.Random;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Account
{
    Scanner sc;
    Connection con;
    HashSet<String> set = new HashSet<>();

    Account(Connection con, Scanner sc)
    {
        this.con = con;
        this.sc = sc;
    }

    void createAccount(String aadhar_no, String name)
    {
        String acc_no = generateAccountNumber();
        int pin = generatePin();

        String acc_Query = "INSERT INTO Account(acc_no,name,aadhar_no,balance,pin) VALUES(?,?,?,?,?)";
        try
        {
            PreparedStatement pst = con.prepareStatement(acc_Query);
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
    void extractAccountNumbers()
    {
        String query = "SELECT acc_no FROM Account";
        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) set.add(rs.getString("acc_no"));
            
        } catch(SQLException e){
            System.out.println("exception during extraction of account number into set");
            e.getStackTrace();
        }
    }
}