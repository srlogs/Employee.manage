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

public class ViewHistory extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      Info info[] = Info.values();

      JSONObject jsonobj = new JSONObject();
      JSONArray jsonarray = new JSONArray();
      ServletContext sc = this.getServletContext();
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      String query = "SELECT * FROM admintb";
      stmt = con.createStatement(
      ResultSet.TYPE_SCROLL_INSENSITIVE,
      ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = stmt.executeQuery(query);
      rs.last();
      int size = rs.getRow();
      rs.beforeFirst();
      if(size<1)
      {
        request.setAttribute("noreq","noreq");
        RequestDispatcher rd = sc.getRequestDispatcher("/viewHistory.jsp");
        rd.include(request, response);
      }
      else
      {
        while(rs.next())
        {
          if(rs.getString("response").equals("4") || rs.getString("response").equals("5") || rs.getString("response").equals("2") ||  rs.getString("response").equals("3"))
          {
            jsonobj = new  JSONObject();
            jsonobj.put("id", rs.getString("idvalue"));
            jsonobj.put("rqtime", rs.getString("reqtime"));
            jsonobj.put("reason", info[Integer.parseInt(rs.getString("reason"))-1].toString());
            if(rs.getString("response").equals("4") || rs.getString("response").equals("5"))
            {
                jsonobj.put("status", info[Integer.parseInt(rs.getString("response"))-2].toString());
            }
            else if(rs.getString("response").equals("2") ||  rs.getString("response").equals("3"))
            {
              jsonobj.put("status", info[Integer.parseInt(rs.getString("response"))].toString());
            }
            jsonobj.put("rstime", rs.getString("restime"));
            jsonobj.put("Name", data(jsonobj.getString("id")));
            jsonarray.put(jsonobj);
          }
        }
        request.setAttribute("Data", jsonarray);
        RequestDispatcher rd = sc.getRequestDispatcher("/viewHistory.jsp");
        rd.forward(request, response);
      }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public String data(String id) throws Exception
  {
    Connection con = null;
    Statement stmt = null;
    String name = " ";
    Class.forName("org.postgresql.Driver");
    con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
    String query = "SELECT name FROM company where id = '"+id+"'";
    stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    while(rs.next())
    name = rs.getString("Name");
    return name;
  }
  public enum Info
  {
    password_change, update_information, Accepted, Rejected;
  }
}
