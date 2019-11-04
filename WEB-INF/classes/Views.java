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
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Array;

public class Views extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      int status = 0;
      response.setContentType("text/html");
      JSONObject jsonobject = new JSONObject();
      String Accept = request.getParameter("accept");
      String Deny = request.getParameter("deny");
      String name = request.getParameter("namebtns");
      String check1 = request.getParameter("check1");
      String check2 = request.getParameter("check2");
      String time = request.getParameter("time");
      if(Accept == null && Deny == null)
      {
        if(name!=null)
        {
          request.setAttribute("name", name);
        }
        ViewOf(request, response);
      }
      if(Accept!=null)
      {
        status = 1;
        jsonobject.put("status", status);
        jsonobject.put("id", Accept);
        jsonobject.put("check", check1);
        jsonobject.put("time", time);
        Validate(jsonobject, response);
        ViewOf(request, response);
        Accept = null;
      }
      if(Deny!=null)
      {
        status = 2;
  			jsonobject.put("status", status);
  			jsonobject.put("id",Deny);
  			jsonobject.put("check",check2);
        jsonobject.put("time", time);
  			Validate(jsonobject, response);
  			ViewOf(request,response);
  			Deny = null;
      }
    }
    catch(Exception e)
    {

    }
  }
  public void ViewOf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter pw =  response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      int i =0, value = 0, value1 =  0, pass = 0, update = 0, flag =0, count = 0;
      String str_val = " ";
      AESAlgorithm a = new AESAlgorithm();
      JSONObject jsonobject = new JSONObject();
      JSONArray jsonarray = new  JSONArray();
      ServletContext sc = this.getServletContext();
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      String query = "SELECT * FROM admintb";
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = stmt.executeQuery(query);
      rs.last();
      int size = rs.getRow();
      rs.beforeFirst();
      String[] s = new String[size];
      while(rs.next())
      {
        if(rs.getString("response").equals("0"))
        {
          jsonobject = new JSONObject();
          jsonobject.put("id", rs.getString("idvalue"));
          jsonobject.put("reqtime", rs.getString("reqtime"));
          jsonobject.put("reason", rs.getString("reason"));
          jsonobject.put("response", rs.getString("response"));
          jsonobject.put("restime", rs.getString("restime"));
          s[i] = jsonobject.getString("reason");
          pw.println(s[i]);
          jsonarray.put(ViewList(jsonobject, response));
          count ++;
        }
        i++;
      }
      if(count == 0)
      {
        request.setAttribute("noreq","noreq");
        RequestDispatcher rd = sc.getRequestDispatcher("/ViewDetail.jsp");
        rd.include(request, response);
      }
      else
      {
        request.setAttribute("check", s);
        request.setAttribute("Data",jsonarray);
        RequestDispatcher rd = sc.getRequestDispatcher("/ViewDetail.jsp");
        rd.include(request, response);
      }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public JSONObject ViewList(JSONObject jsonobject, HttpServletResponse response) throws Exception
  {
    Connection con = null;
    Statement stmt = null;
    JSONObject jsonobj = new JSONObject();
    AESAlgorithm s = new AESAlgorithm();
    PrintWriter pw = response.getWriter();
    try
    {
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      stmt =  con.createStatement();
      String cmpData = "SELECT * FROM company ";
      ResultSet rs = stmt.executeQuery(cmpData);
      while(rs.next())
      {
        if(rs.getString("Id").equals(jsonobject.getString("id")))
        {
          jsonobj = new JSONObject();
          jsonobj.put("id", rs.getString("Id"));
          jsonobj.put("Name", rs.getString("Name"));
          jsonobj.put("FatherName", rs.getString("FatherName"));
          jsonobj.put("PhoneNumber", rs.getString("Phone"));
          jsonobj.put("DateOfBirth", rs.getString("DateofBirth"));
          jsonobj.put("BloodGroup", rs.getString("BloodGroup"));
          jsonobj.put("Address", rs.getString("Address"));
          jsonobj.put("RegistrationId", rs.getString("RegisterNum"));
          jsonobj.put("reason", jsonobject.getString("reason"));
          jsonobj.put("time", jsonobject.getString("reqtime"));
        }
      }
      stmt.close();
      con.close();
    }
    catch(Exception e)
    {

    }
    return jsonobj;
  }
  public void Validate(JSONObject jsonobj, HttpServletResponse response) throws Exception
  {
    Connection con = null;
    Statement stmt = null;
    PrintWriter pw = response.getWriter();
    try
    {
      AESAlgorithm s = new AESAlgorithm();
      Date dates;
      String actime;
      DateFormat dateFormat;
      Calendar cal;
      String qry = " ", res_upd = " ";
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      stmt = con.createStatement();
      String query = "SELECT * FROM admintb";
      ResultSet rs = stmt.executeQuery(query);
      while(rs.next())
      {
        if(rs.getString("idvalue").equals(jsonobj.getString("id")))
        {
          switch(jsonobj.getInt("status"))
          {
            case 1:
              cal = Calendar.getInstance();
              dates = cal.getTime();
              dateFormat = new SimpleDateFormat("hh:mm:ss");
              actime = dateFormat.format(dates);
              res_upd = "UPDATE admintb SET restime = '"+actime+"' WHERE idvalue = '"+jsonobj.getString("id")+"' AND reqtime = '"+jsonobj.getString("time")+"'";
              stmt.executeUpdate(res_upd);
              modify(jsonobj);
              break;
            case 2:
              cal = Calendar.getInstance();
              dates = cal.getTime();
              dateFormat = new SimpleDateFormat("hh:mm:ss");
              actime = dateFormat.format(dates);
              res_upd = "UPDATE admintb SET restime = '"+actime+"' WHERE idvalue = '"+jsonobj.getString("id")+"'AND reqtime = '"+jsonobj.getString("time")+"'";
              stmt.executeUpdate(res_upd);
              modify(jsonobj);
              break;
            default:
              break;
          }
        }
      }
    }
    catch(Exception e)
    {

    }
  }
  public void modify(JSONObject jsonobj) throws Exception
  {
    Connection con = null;
    Statement stmt = null;
    String qry = " ", del = " ";
    Class.forName("org.postgresql.Driver");
    con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
    stmt = con.createStatement();
    String query = "SELECT * FROM admintb";
    ResultSet rs = stmt.executeQuery(query);

    while(rs.next())
    {
      if(jsonobj.getInt("status") == 1)
      {
        if(jsonobj.getString("check").equals("1") && jsonobj.getString("check").equals(rs.getString("reason")))
        {
          qry = "UPDATE admintb SET response = '"+2+"' WHERE idvalue = '"+jsonobj.getString("id")+"' AND reqtime = '"+jsonobj.getString("time")+"'";
          stmt.executeUpdate(qry);
          stmt.close();
          con.close();
        }
        else if(jsonobj.getString("check").equals("2") && jsonobj.getString("check").equals(rs.getString("reason")))
        {
          qry = "UPDATE admintb SET response = '"+2+"' WHERE idvalue = '"+jsonobj.getString("id")+"'AND reqtime = '"+jsonobj.getString("time")+"' ";
          stmt.executeUpdate(qry);
          stmt.close();
          con.close();
        }
      }
      else if(jsonobj.getInt("status") == 2)
      {
        if(jsonobj.getString("check").equals("1") && jsonobj.getString("check").equals(rs.getString("reason")))
        {
          qry = "UPDATE admintb SET  response = '"+3+"' WHERE idvalue = '"+jsonobj.getString("id")+"' AND reqtime = '"+jsonobj.getString("time")+"'";
          stmt.executeUpdate(qry);
          stmt.close();
          con.close();
        }
        else if(jsonobj.getString("check").equals("2") && jsonobj.getString("check").equals(rs.getString("reason")))
        {
          qry = "UPDATE admintb SET  response = '"+3+"' WHERE idvalue = '"+jsonobj.getString("id")+"' AND reqtime = '"+jsonobj.getString("time")+"'";
          stmt.executeUpdate(qry);
          stmt.close();
          con.close();
        }
      }
    }
  }
}
