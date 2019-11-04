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

public class Listout extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    AESAlgorithm s = new AESAlgorithm();
    PrintWriter pw = response.getWriter();
    ServletContext sc = this.getServletContext();
    Connection con = null;
    Statement stmt = null;

    try
    {
      JSONObject jsonobject = new JSONObject();
      JSONArray jsonarray = new JSONArray();
      HttpSession session = request.getSession();
      String delete = request.getParameter("delete");

      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      DatabaseMetaData dbm = con.getMetaData();
      ResultSet rs = dbm.getTables(null, null, "company", null);

      if(rs.next())
      {
        stmt = con.createStatement();

        if(delete!=null)
        {
          String del = "DELETE FROM company WHERE company.id= '"+delete+"' ";
          stmt.executeUpdate(del);
        }

        String query = "SELECT * FROM company";
        ResultSet resultset = stmt.executeQuery(query);

        while(resultset.next())
        {
          jsonobject = new JSONObject();
          jsonobject.put("id", resultset.getString("Id"));
          jsonobject.put("name", resultset.getString("Name"));
          jsonobject.put("phone number", resultset.getString("Phone"));
          jsonobject.put("registerNumber", resultset.getString("RegisterNum"));
          jsonobject.put("fatherName", resultset.getString("FatherName"));
          jsonobject.put("DoB", resultset.getString("DateofBirth"));
          jsonobject.put("blood", resultset.getString("BloodGroup"));
          jsonobject.put("address", resultset.getString("Address"));
          jsonarray.put(jsonobject);
        }

        stmt.close();
        con.close();

        request.setAttribute("EmployeeDetails", jsonarray);
        RequestDispatcher rd = sc.getRequestDispatcher("/listout.jsp");
        rd.include(request, response);
      }
    }
    catch (Exception e)
    {
        pw.println(e);
    }
  }
}
