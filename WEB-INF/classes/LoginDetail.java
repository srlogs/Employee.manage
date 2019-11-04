import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.*;

public class LoginDetail extends HttpServlet
{
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    Connection con = null;
    PrintWriter pw = response.getWriter();
    Statement stmt = null;
    try
    {
      JSONObject jsonobject = new JSONObject();
      JSONArray jsonarray = new JSONArray();
      ServletContext sc = this.getServletContext();
      HttpSession session = request.getSession();
      // if(session == null)
      // {
      //   response.sendRedirect("index.jsp");
      // }
      // else
      // {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
        stmt = con.createStatement();
        String username = (String) session.getAttribute("username");
        String query = "SELECT * FROM company WHERE Email = '"+username+"'";
        ResultSet resultset = stmt.executeQuery(query);
        while(resultset.next())
        {
          jsonobject.put("id",resultset.getString("id"));
          jsonobject.put("Name", resultset.getString("Name"));
          jsonobject.put("Father_name", resultset.getString("FatherName"));
  				jsonobject.put("Register_number", resultset.getString("RegisterNum"));
  				jsonobject.put("phone_number", resultset.getString("Phone"));
  				jsonobject.put("Date_of_Birth", resultset.getString("DateofBirth"));
  				jsonobject.put("Blood", resultset.getString("BloodGroup"));
  				jsonobject.put("address", resultset.getString("Address"));
          request.setAttribute("data",jsonobject);
  				session.setAttribute("data",jsonobject);
  				session.setAttribute("id", resultset.getString("id"));
        }
        RequestDispatcher rd = sc.getRequestDispatcher("/Employee.jsp");
        rd.forward(request, response);
      // }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
}
