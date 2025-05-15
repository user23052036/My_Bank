import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Aadhar
{
    Connection con;
    PreparedStatement pst;
    Account account;
    BufferedReader sb;

    Aadhar(Connection con, Account account, BufferedReader sb)  //constructor
    {
        this.con = con;
        this.account = account;
        this.sb = sb;
    }

    void register(String aadhar_no, String name, String passkey)
    {
        if(available(aadhar_no)) 
            System.out.println("User already registered under the Aadhar Number");
        else
        {
            String update_query = "INSERT INTO Aadhar(aadhar_no,name,passkey) VALUES(?,?,?)";
            try
            {
                pst = con.prepareStatement(update_query);
                pst.setString(1,aadhar_no);
                pst.setString(2,name);
                pst.setString(3, passkey);

                int eff_row = pst.executeUpdate();
                if(eff_row>0) System.out.println("User registered successfully!");
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
            pst = con.prepareStatement(reg_query);
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

    boolean login(String aadhar_no, String passkey)
    {
        String query = "SELECT * FROM  Aadhar where aadhar_no = ? AND PassKey = ?";

        try
        {
            pst = con.prepareStatement(query);
            pst.setString(1,aadhar_no);
            pst.setString(2,passkey);

            ResultSet result = pst.executeQuery();
            if(result.next()) return true;
            else return false;
        } catch(SQLException e){
            System.out.println("Error during login process");
        }
        return false;
    }
}