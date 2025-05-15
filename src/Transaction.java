import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//Exception Classes
//----------------------------------------------------------------------------------------------------

class MinimumBalanceException extends Exception
{
    MinimumBalanceException(){super();}

    @Override
    public String toString()
    { return "MINIMUMBALANCE EXCEPTION!";}
}
class AccountNotFoundException extends Exception
{
    AccountNotFoundException(String message)
    {super(message);}

    @Override
    public String toString()
    { return "INVALID CREDENTIALS"+getMessage();}
}
//--------------------------------------------------------------------------------------------------



class Transaction 
{
    Account account;
    PreparedStatement s_pst,r_pst;
    ResultSet result;
    Connection con;
    BufferedReader sb;

    Transaction(Connection con, BufferedReader sb)  //constructor
    {
        this.con = con;
        this.sb = sb;
    }

    //-------------------------------------------------------------------------------------------------------
    
    void validate_sender(String acc_no, double amount) throws MinimumBalanceException
    {
        double balance = account.getBalance(acc_no);
        if(balance-amount<0) throw new MinimumBalanceException();
    }

    void validate_receiver(String acc_no) throws AccountNotFoundException
    {
        String query = "SELECT * FROM Account WHERE acc_no = ?";
        try 
        {
            r_pst = con.prepareStatement(query);
            r_pst.setString(1, acc_no);
            result = r_pst.executeQuery();

            if (!result.next()) 
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
            r_pst = con.prepareStatement(query);
            r_pst.setString(1, acc_no);
            r_pst.setString(2, name);
            result = r_pst.executeQuery();

            if(!result.next())
            {
                String excep_mess = String.format("%d does not match with '%s'",acc_no,name);
                throw new AccountNotFoundException(excep_mess);
            } 

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    void transfer(String acc_no) throws AccountNotFoundException,MinimumBalanceException
    {
        try
        {
            con.setAutoCommit(false); // to mantain atomocity property

            String debit = "update Account set balance = balance - ? where acc_no = ?";
            String cradit = "update Account set balance = balance + ? where acc_no = ?";
            s_pst = con.prepareStatement(debit);
            r_pst = con.prepareStatement(cradit);

            
            System.out.println("--------------:Enter account details of the Receiver:-------------");
            System.out.print("Enter acc number:-");
            String r_acc_no = sc.next();
            sc.nextLine();
            validate_receiver(r_acc_no);

            System.out.print("Enter Name:-");
            String r_name = sc.nextLine();
            validate_receiver(r_acc_no,r_name);

            System.out.println("Enter amount to send: ");
            double s_amount = sc.nextDouble();
            validate_sender(acc_no,s_amount);

            
            s_pst.setDouble(1,s_amount);
            s_pst.setString(2,acc_no);
            r_pst.setDouble(1,s_amount);
            r_pst.setString(2, r_acc_no);

            int eff_row1 = s_pst.executeUpdate();
            int eff_row2 = r_pst.executeUpdate();

            if(eff_row1>0 && eff_row2>0) 
            {
                con.commit();
                System.out.println(".....[[[[Transaction Successull]]]].....");
            }
            else System.out.println("Transaction failed "+s_amount+" reverted back to source");
                
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
}
