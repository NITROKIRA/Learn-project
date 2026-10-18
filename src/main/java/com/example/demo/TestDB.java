import java.sql.Connection;
import java.sql.DriverManager;

public class TestDB
{
    public static void main(String[] args)
    {
        String url = "jdbc:postgresql://127.0.0.1:5432/demo";
        String username = "postgres";
        String password = "0000";

        try
        {
            Connection connection = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

            System.out.println("DATABASE CONNECTION SUCCESSFUL!");

            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}