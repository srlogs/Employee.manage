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

public class UpdateEmp extends HttpServlet
{
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter pw = response.getWriter();
    ServletContext sc = this.getServletContext();
    Connection con = null;
    Statement stmt = null;
    try
    {
        HttpSession session = request.getSession();
          String name = request.getParameter("name");
          String phone = request.getParameter("phone_number");
          String address = request.getParameter("address");
          String regid = request.getParameter("registrationid");
          String bg = request.getParameter("Blood");
          String dob = request.getParameter("dob");
          String fn = request.getParameter("fatherName");
          Class.forName("org.postgresql.Driver");
          con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
          DatabaseMetaData dbm = con.getMetaData();
          ResultSet rs = dbm.getTables(null, null, "company", null);
          if(rs.next())
          {
            stmt = con.createStatement();
            if(phone != null && address != null && regid != null && bg != null && fn != null)
            {
              String upd = "UPDATE company SET Name = '"+name+"', Phone = '"+phone+"', RegisterNum = '"+regid+"', DateofBirth = '"+dob+"', FatherName = '"+fn+"', BloodGroup = '"+bg+"', Address = '"+address+"' WHERE company.Id ='"+session.getAttribute("id")+"'";
              stmt.executeUpdate(upd);
            }
          }
          stmt.close();
          con.close();
          RequestDispatcher rd = sc.getRequestDispatcher("/welcomePage.jsp");
          rd.include(request, response);

    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
}
