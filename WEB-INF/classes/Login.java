import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.realm.GenericPrincipal;
import java.util.logging.Logger;
import EmployeeManagement.*;
import java.sql.*;

public class Login extends RealmBase {
    private static final Logger log = Logger.getLogger(Login.class.getName());
    private String username;
    private String password;

    @Override
    public Principal authenticate(String username, String password) {
        AESAlgorithm s = new AESAlgorithm();
        this.username = username;
        this.password = password;
        Connection con = null;
        Statement stmt = null;
        int flag = 0;
        try
        {
          String psw = " ";
          Class.forName("org.postgresql.Driver");
          con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
          stmt = con.createStatement();
          String query = "SELECT company.Email, pswtable.password, pswtable.type, pswtable.key FROM  company, pswtable WHERE company.Id = pswtable.regno";
          ResultSet resultset = stmt.executeQuery(query);
          while(resultset.next())
          {
            if(resultset.getString("type").equals("AES"))
            {
              psw = s.decrypt_aes(resultset.getString("password"), resultset.getString("key"));
            }
            else if(resultset.getString("type").equals("DES"))
            {
              psw = s.decrypt_des(resultset.getString("password"), resultset.getString("key"));
            }
            else if(resultset.getString("type").equals("Blowfish"))
            {
              psw = s.decrypt_Blowfish(resultset.getString("password"), resultset.getString("key"));
            }
            if(username.equals(resultset.getString("Email")) && password.equals(psw))
            {
              return getPrincipal(username);
            }
          }
          stmt.close();
          con.close();
        }
        catch(Exception e)
        {}
        return null;
    }
    @Override
    protected String getName() {
        return username;
    }

    @Override
    protected String getPassword(String username) {
        return password;
    }

    @Override
    protected Principal getPrincipal(String strings) {
        List<String> roles = new ArrayList<String>();
        roles.add("user");
        log.info("Realm: "+this);
        Principal principal = new GenericPrincipal(strings, password,roles);
        log.info("Principal: "+principal);
        return principal;
    }
}
