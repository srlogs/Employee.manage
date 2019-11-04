import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.http.*;
import EmployeeManagement.*;
import java.lang.reflect.Method;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Random;
import java.lang.Math;

public class HrOperation extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, NullPointerException
  {
    PrintWriter pw = response.getWriter();
    String methodName = request.getParameter("methodCall");
    try
    {
         Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
         method.invoke(this, (HttpServletRequest)request, (HttpServletResponse)response);
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public void deleteOperation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      String id = request.getParameter("delete");
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      stmt =  con.createStatement();
      String del = "DELETE FROM company WHERE company.id= '"+id+"' ";
      stmt.executeUpdate(del);
      stmt.close();
      con.close();
      Listout(request, response);
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public void Listout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    AESAlgorithm s = new AESAlgorithm();
    ServletContext sc = this.getServletContext();
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      String update = request.getParameter("update");
      JSONObject jsonobject = new JSONObject();
      JSONArray jsonarray = new JSONArray();
      HttpSession session = request.getSession();
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      DatabaseMetaData dbm = con.getMetaData();
      ResultSet rs = dbm.getTables(null, null, "company", null);

      if(rs.next())
      {
        stmt = con.createStatement();
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
          if(update!=null)
          {
            if(update.equals(resultset.getString("Id")))
            {
              request.setAttribute("data", jsonobject);
            }
          }
        }
        stmt.close();
        con.close();
        if(update==null)
        {
          request.setAttribute("EmployeeDetails", jsonarray);
          RequestDispatcher rd = sc.getRequestDispatcher("/listout.jsp");
          rd.include(request, response);
        }
        else
        {
          RequestDispatcher rd = sc.getRequestDispatcher("/updateInfo.jsp");
          rd.include(request, response);
        }
      }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public void AddEmp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    AESAlgorithm s = new AESAlgorithm();
    Algo algo[] = Algo.values();
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      // int x = (Math.Random()*((2-1)+1))+1;
      Random r = new Random();
      String key = " ";
      int x = r.nextInt((3-1)+1)+1;
      pw.println(x);
      String epassword = " ";
      String password = request.getParameter("Password");
      if(x == 1)
      {
        key = s.aeskeygenerate();
        epassword = s.encrypt_aes(password, key);
      }
      else if(x == 2)
      {
        key = s.deskeygenerate();
        epassword = s.encrypt_des(password, key);
      }
      else if(x == 3)
      {
        key = s.Blowfishkeygenerate();
        epassword = s.encrypt_Blowfish(password, key);
      }
      ServletContext sc = this.getServletContext();
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      DatabaseMetaData dbm = con.getMetaData();
      stmt = con.createStatement();
      ResultSet rs = dbm.getTables(null, null, "company", null);

      if(rs.next())
      {

        String query = "INSERT INTO company(Id, Email, Name, Phone, RegisterNum, FatherName, DateofBirth, BloodGroup, Address)" + "VALUES(DEFAULT, '"+request.getParameter("email")+"', '"+request.getParameter("Name")+"', '"+request.getParameter("PhoneNumber")+"', '"+request.getParameter("RegisterNumber")+"', '"+request.getParameter("FatherName")+"', '"+request.getParameter("DoB")+"', '"+request.getParameter("Blood")+"', '"+request.getParameter("Address")+"');";
        stmt.executeUpdate(query);

      }
      else
      {
        String createTb = "CREATE TABLE company" + "(Id SERIAL NOT NULL, Email VARCHAR(20) NOT NULL, Name VARCHAR(50), Phone VARCHAR(50), RegisterNum VARCHAR(5), FatherName VARCHAR(50), DateofBirth VARCHAR(50), BloodGroup VARCHAR(50), Address VARCHAR(50), PRIMARY KEY(Id))";
        stmt.executeUpdate(createTb);
        String query = "INSERT INTO company(Id, Email, Name, Phone, RegisterNum, FatherName, DateofBirth, BloodGroup, Address)" + "VALUES(DEFAULT, '"+request.getParameter("email")+"', '"+request.getParameter("Name")+"', '"+request.getParameter("PhoneNumber")+"', '"+request.getParameter("RegisterNumber")+"', '"+request.getParameter("FatherName")+"', '"+request.getParameter("DoB")+"', '"+request.getParameter("Blood")+"', '"+request.getParameter("Address")+"');";
        stmt.executeUpdate(query);

      }
      ResultSet rst = dbm.getTables(null, null, "pswtable", null);
      if(rst.next())
      {
        String ins_pass = "INSERT INTO pswtable(regno, password, type, key)" + "VALUES(DEFAULT, '"+epassword+"', '"+algo[x-1]+"', '"+key+"')";
        stmt.executeUpdate(ins_pass);

      }
      else
      {
        String createptb = "CREATE TABLE pswtable" + "(regno SERIAL NOT NULL REFERENCES company(Id) ON DELETE CASCADE, password VARCHAR(80), type VARCHAR(20), key VARCHAR(50))";
        stmt.executeUpdate(createptb);
        String ins_npass =  "INSERT INTO pswtable(regno, password, type, key)" + "VALUES(DEFAULT, '"+epassword+"', '"+algo[x-1]+"', '"+key+"')";
        stmt.executeUpdate(ins_npass);
      }
      stmt.close();
      con.close();
      RequestDispatcher rd = sc.getRequestDispatcher("/AddEmployee.jsp");
      rd.forward(request, response);
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public void updateInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    PrintWriter pw = response.getWriter();
    ServletContext sc = this.getServletContext();
    Connection con = null;
    Statement stmt = null;
    try
    {
      HttpSession session = request.getSession();
      String employee = request.getParameter("empupdate");
      String dob = request.getParameter("dob");
      String phone = request.getParameter("phone_number");
      String address = request.getParameter("address");
      String regid = request.getParameter("registrationid");
      String bg = request.getParameter("Blood");
      String fn = request.getParameter("fatherName");
      String id = request.getParameter("idvalue");
      String name = request.getParameter("name");
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      stmt = con.createStatement();
      if(employee == null)
      {

        String upd = "UPDATE company SET Phone = '"+phone+"', RegisterNum = '"+regid+"', FatherName = '"+fn+"', BloodGroup = '"+bg+"', Address = '"+address+"', Name = '"+name+"', DateOfBirth = '"+dob+"' WHERE company.Id ='"+id+"'";
        stmt.executeUpdate(upd);
      }
      else
      {
        String empupd = "UPDATE company SET Phone = '"+phone+"', RegisterNum = '"+regid+"', Name = '"+name+"', FatherName = '"+fn+"', BloodGroup = '"+bg+"', Address = '"+address+"', DateOfBirth = '"+dob+"' WHERE company.Id ='"+id+"'";
        stmt.executeUpdate(empupd);
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
  public enum Algo
  {
    AES, DES, Blowfish;
  }
}
