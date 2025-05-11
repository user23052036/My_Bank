import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

class Aadhar
{
    Connection con;
    Account account;
    Scanner sc;

    Aadhar(Connection con, Account account, Scanner sc)  //constructor
    {
        this.con = con;
        this.account = account;
        this.sc = sc;
    }

    void register()
    {
        System.out.println("Enter the aadhar_no:-");
        String aadhar_no = sc.next();
        System.out.println("Enter the name:- ");
        String name = sc.nextLine();
        System.out.println("Enter OPT received:- ");
        int OTP = sc.nextInt();

        if(available(aadhar_no))
            System.out.println("User already registered under the Aadhar number");
        else
        {
            String update_query = "INSERT INTO Aadhar(aadhar_no,name,OTP) VALUES(?,?,?)";
            try
            {
                PreparedStatement pst = con.prepareStatement(update_query);
                pst.setString(1,aadhar_no);
                pst.setString(2,name);
                pst.setInt(3, OTP);

                int eff_row = pst.executeUpdate();
                if(eff_row>0) 
                {
                    System.out.println("User registered successfully!");
                    account.createAccount(aadhar_no,name);
                }
                else System.out.println("registration failed");
                
            } catch(SQLException e){
                System.out.println("");
            }
        }
    }

    boolean available(String aadhar_no)
    {
        String reg_query = "SELECT * from Aadhar where aadhar_no = ?";
        try
        {
            PreparedStatement pst = con.prepareStatement(reg_query);
            pst.setString(1, aadhar_no);

            int eff_row = pst.executeUpdate();
            if(eff_row>0) return true;
            else return false;

        } catch(SQLException e){
            System.out.println("SQL exception during registration cheaking !!!");
            e.printStackTrace();
        }
        return false;
    }

    void login()
    {

    }
}