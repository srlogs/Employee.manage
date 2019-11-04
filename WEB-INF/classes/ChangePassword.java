import java.io.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.*;
import javax.servlet.http.*;
import EmployeeManagement.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Random;
import java.lang.Math;

public class ChangePassword extends HttpServlet
{
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      Algo algo[] = Algo.values();
      Random r = new Random();
      String key = " ", epassword  = " ";
      int x = r.nextInt((3-1)+1)+1;
      AESAlgorithm s = new AESAlgorithm();
      HttpSession session = request.getSession();
      ServletContext sc = this.getServletContext();


        String newpsw = request.getParameter("new_password");
    		String cnfpsw = request.getParameter("confirm_password");

        if(newpsw.equals(cnfpsw))
    		{
          if(x == 1)
          {
            key = s.aeskeygenerate();
            epassword = s.encrypt_aes(newpsw, key);
          }
          else if(x == 2)
          {
            key = s.deskeygenerate();
            epassword = s.encrypt_des(newpsw, key);
          }
          else if(x == 3)
          {
            key = s.Blowfishkeygenerate();
            epassword = s.encrypt_Blowfish(newpsw, key);
          }
          Class.forName("org.postgresql.Driver");
          con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
          DatabaseMetaData dbm = con.getMetaData();
          ResultSet rs = dbm.getTables(null, null, "pswtable", null);
          if(rs.next())
          {
            stmt = con.createStatement();
            String query = "UPDATE pswtable SET password = '"+epassword+"', key = '"+key+"', type='"+algo[x-1]+"' WHERE regno='"+session.getAttribute("id")+"'";
            stmt.executeUpdate(query);
            stmt.close();
            con.close();
            RequestDispatcher rd = sc.getRequestDispatcher("/Employee.jsp");
    				rd.include(request, response);
          }
        }
        else
        {
          request.setAttribute("incorrect", "0");
          RequestDispatcher rd = sc.getRequestDispatcher("/passwordChange.jsp");
          rd.include(request, response);
        }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public enum Algo
  {
    AES, DES, Blowfish;
  }
}
