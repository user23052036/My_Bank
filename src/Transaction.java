import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//Exception Classes
//----------------------------------------------------------------------------------------------------

class MinimumBalanceException extends Exception
{
    MinimumBalanceException(){super();}

    public String toString()
    {
        return "MINIMUMBALANCE EXCEPTION!";
    }
}
class AccountNotFoundException extends Exception
{
    AccountNotFoundException(String message)
    {super(message);}

    public String toString()
    {
        return "INVALID CREDENTIALS"+getMessage();
    }
}
//--------------------------------------------------------------------------------------------------



class Transaction 
{
    Connection con;
    Scanner sc;

    Transaction(Connection con, Scanner sc)  //constructor
    {
        this.con = con;
        this.sc = sc;
    }

    //-------------------------------------------------------------------------------------------------------


    void validate_receiver(String acc_no) throws AccountNotFoundException
    {
        String query = "SELECT * FROM Account WHERE acc_no = ?";
        try 
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, acc_no);
            ResultSet rs = pst.executeQuery();

            if (!rs.next()) 
            {
                String excep_mess = String.format("%d Not Found In DATABASE!",acc_no);
                throw new AccountNotFoundException(excep_mess);
            } 

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    void validate_receiver(String acc_no, String name) throws AccountNotFoundException
    {
        String query = "SELECT * FROM Account WHERE acc_no = ? AND UPPER(name) = UPPER(?)";
        try 
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, acc_no);
            pst.setString(2, name);
            ResultSet rs = pst.executeQuery();

            if(!rs.next())
            {
                String excep_mess = String.format("%d does not match with '%s'",acc_no,name);
                throw new AccountNotFoundException(excep_mess);
            } 

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    void transfer() throws AccountNotFoundException,MinimumBalanceException
    {
        try
        {
            con.setAutoCommit(false); 

            String debit = "update Account set balance = balance - ? where acc_no = ?";
            String cradit = "update Account set balance = balance + ? where acc_no = ?";
            PreparedStatement s_pst = con.prepareStatement(debit);
            PreparedStatement r_pst = con.prepareStatement(cradit);

            while(true)
            {
                System.out.println("--------------:Enter account details of the Receiver:-------------");
                System.out.print("Enter acc number:-");
                String r_acc_no = sc.next();
                sc.nextLine();
                validate_receiver(r_acc_no);

                System.out.print("Enter Name:-");
                String r_name = sc.nextLine();
                validate_receiver(r_acc_no,r_name);

                
                s_pst.setInt(1, s_amount);
                s_pst.setInt(2, s_acc_no);
                r_pst.setInt(1, s_amount);
                r_pst.setInt(2, r_acc_no);

                try
                {
                    s_pst.executeUpdate();
                    r_pst.executeUpdate();
                    con.commit();

                    System.out.println(".....[[[[Transaction Successull]]]].....");
                    System.out.println("money send "+s_amount+" from "+s_name+" to "+r_name);
                    System.out.println("current balance on "+s_name+" is "+(s_balance-s_amount));
                    System.out.println("current balance on "+r_name+" is "+(r_balance+s_amount));
                    
                } catch(SQLException e){

                    con.rollback();
                    System.out.println("Transaction has failed!");
                }

                System.out.print("Want to perform any more transaction(y/Y)?:-");
                String choice = sc.next();
                if(choice.toUpperCase().equals("N")) break;
            }
            sc.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
}
