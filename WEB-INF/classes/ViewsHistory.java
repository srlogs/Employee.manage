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
import java.sql.*;

public class ViewsHistory extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      Info info[] = Info.values();
      String reqt_val = " ", rest_val = " ", sth_val = " ", rsh_val = " ";
      JSONObject jsonobj = new JSONObject();
      JSONArray jsonarray = new JSONArray();
      ServletContext sc = this.getServletContext();
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      String query = "SELECT * FROM company";
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while(rs.next())
      {
        Array arr_reqt = rs.getArray("reqtime");
        Array arr_rest = rs.getArray("restime");
        Array arr_sth = rs.getArray("stshistory");
        Array arr_rsh = rs.getArray("rshistory");
        String[] str_reqt = (String[])arr_reqt.getArray();
        String[] str_rest = (String[])arr_rest.getArray();
        String[] str_sth = (String[])arr_sth.getArray();
        String[] str_rsh = (String[])arr_rsh.getArray();
        rsh_val = str_rsh[0];
        if(!rsh_val.equals("0"))
        {
          for(int i =0;i<str_sth.length;i++)
          {
            jsonobj = new JSONObject();
            jsonobj.put("id", rs.getString("id"));
            jsonobj.put("rqtime", str_reqt[i]);;
            jsonobj.put("reason", info[Integer.parseInt(str_sth[i])-1].toString());
            jsonobj.put("status", info[Integer.parseInt(str_rsh[i])-1].toString());
            jsonobj.put("rstime", str_rest[i]);
            jsonobj.put("Name", rs.getString("Name"));
            jsonarray.put(jsonobj);
          }
        }
      }
      request.setAttribute("Data", jsonarray);
      RequestDispatcher rd = sc.getRequestDispatcher("/viewHistory.jsp");
      rd.forward(request, response);
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public enum Info
  {
    password_change, upd_Information, Accepted, Rejected;
  }
}
