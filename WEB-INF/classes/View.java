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

public class View extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      int status = 0, i = 0;
  		response.setContentType("text/html");
      JSONObject jsonobject = new JSONObject();
      String Accept = request.getParameter("accept");
      String Deny = request.getParameter("deny");
      String name = request.getParameter("namebtns");
      String check1 = request.getParameter("check1");
  		String check2 = request.getParameter("check2");
      String Time =  request.getParameter("Time");

        if(Accept==null && Deny==null)
        {
          if(name!=null)
    			{
    			request.setAttribute("name",name);
    			}
          Viewof(request, response);
        }

        if(Accept!=null)
        {
          status = 1;
          jsonobject.put("status", status);
          jsonobject.put("id", Accept);
          jsonobject.put("check", check1);
          jsonobject.put("Time", Time);
          Validate(jsonobject);
          Viewof(request, response);
          Accept = null;
        }

        if(Deny!=null)
  		  {
  			status = 2;
  			jsonobject.put("status", status);
  			jsonobject.put("id",Deny);
  			jsonobject.put("check",check2);
        jsonobject.put("Time", Time);
  			Validate(jsonobject);
  			Viewof(request,response);
  			Deny = null;
  		  }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }

  public void Viewof(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter pw = response.getWriter();
    try
     {
      int i = 0;
      Connection con = null;
      Statement stmt = null;

      JSONObject jsonobject = new JSONObject();
      JSONArray jsonarray = new  JSONArray();
      ServletContext sc = this.getServletContext();
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");

      String query = "SELECT * FROM company";
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      stmt.executeQuery(query);
      ResultSet rs = stmt.executeQuery(query);
      rs.last();
      int size = rs.getRow();
      rs.beforeFirst();
      if(size<1)
      {
        request.setAttribute("noreq","noreq");
        RequestDispatcher rd = sc.getRequestDispatcher("/ViewDetail.jsp");
        rd.include(request, response);
      }
      else
      {
        String[] s = new String[size];
        while(rs.next())
        {
          jsonobject =  new JSONObject();
          jsonobject.put("id", rs.getString("idvalue"));
          jsonobject.put("check", rs.getString("Value"));
          jsonobject.put("Time", rs.getString("Time"));
          s[i] = jsonobject.getString("check");
          jsonarray.put(ViewList(jsonobject));
          i++;
        }
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
  public JSONObject ViewList(JSONObject jsonobject) throws Exception
  {
    Connection con = null;
    Statement stmt = null;
    JSONObject jsonobj = new JSONObject();
    AESAlgorithm s = new AESAlgorithm();
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
          jsonobj.put("Password", s.decrypt(rs.getString("Password")));
          jsonobj.put("DateOfBirth", rs.getString("DateofBirth"));
          jsonobj.put("BloodGroup", rs.getString("BloodGroup"));
          jsonobj.put("Address", rs.getString("Address"));
          jsonobj.put("RegistrationId", rs.getString("RegisterNum"));
          jsonobj.put("Time", jsonobject.getString("Time"));
        }
      }
    }
    catch(Exception e)
    {}
    return jsonobj;
  }
  public void Validate(JSONObject jsonobj) throws  Exception
  {
    Connection con = null;
    Statement stmt = null;
    try
    {
      AESAlgorithm s = new AESAlgorithm();
      Date dates;
      String actime;
      DateFormat dateFormat;
      Calendar cal;

      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      stmt = con.createStatement();
      String query = "SELECT * FROM company";
      ResultSet rs = stmt.executeQuery(query);
      while(rs.next())
      {
        if(rs.getString("Id").equals(jsonobj.getString("id")))
        {
          switch(jsonobj.getInt("status"))
          {
            case 1:
              modify(jsonobj);
              cal = Calendar.getInstance();
              dates = cal.getTime();
              dateFormat = new SimpleDateFormat("hh:mm:ss");
              actime = dateFormat.format(dates);
              jsonobj.put("actime", actime);
              Viewallwrite(jsonobj);
              break;
            case 2:
              modify(jsonobj);
              cal = Calendar.getInstance();
              dates =  cal.getTime();
              dateFormat = new SimpleDateFormat("hh:mm:ss");
              actime = dateFormat.format(dates);
              jsonobj.put("actime", actime);
              Viewallwrite(jsonobj);
              break;
          default:
            break;
          }
        }
      }
    }
    catch(Exception e)
    {}
  }

  public void modify(JSONObject jsonobj) throws Exception
  {
    Connection con = null;
    Statement stmt = null;
    String qry = " ", del = " ";
    Class.forName("org.postgresql.Driver");
    con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      stmt = con.createStatement();
      if(jsonobj.getInt("status") == 1)
      {
        if(jsonobj.getString("check").equals("1"))
        {
          qry = "UPDATE company SET passstatus = '"+2+"' WHERE id = '"+jsonobj.getString("id")+"' ";
          stmt.executeUpdate(qry);
          del = "DELETE FROM admintb WHERE value = '"+jsonobj.getString("check")+"'AND  idvalue = '"+jsonobj.getString("id")+"' ";
          stmt.executeUpdate(del);
          stmt.close();
          con.close();
        }
        else if(jsonobj.getString("check").equals("2"))
        {
          qry = "UPDATE company SET updstatus = '"+2+"' WHERE id = '"+jsonobj.getString("id")+"' ";
          stmt.executeUpdate(qry);
          del = "DELETE FROM admintb WHERE value = '"+jsonobj.getString("check")+"'AND  idvalue = '"+jsonobj.getString("id")+"' ";
          stmt.executeUpdate(del);
          stmt.close();
          con.close();
        }
      }
      else if(jsonobj.getInt("status") == 2)
      {
        if(jsonobj.getString("check").equals("1"))
        {
          qry = "UPDATE company SET passstatus = '"+3+"' WHERE id = '"+jsonobj.getString("id")+"' ";
          stmt.executeUpdate(qry);
          del = "DELETE FROM admintb WHERE value = '"+jsonobj.getString("check")+"' AND  idvalue = '"+jsonobj.getString("id")+"'";
          stmt.executeUpdate(del);
          stmt.close();
          con.close();
        }
        else if(jsonobj.getString("check").equals("2"))
        {
          qry = "UPDATE company SET updstatus = '"+3+"' WHERE id = '"+jsonobj.getString("id")+"' ";
          stmt.executeUpdate(qry);
          del = "DELETE FROM admintb WHERE value = '"+jsonobj.getString("check")+"' AND  idvalue = '"+jsonobj.getString("id")+"'";
          stmt.executeUpdate(del);
          stmt.close();
          con.close();
        }
      }
  }

  public void Viewallwrite(JSONObject jsonobj)
  {
    Connection con = null;
    Statement stmt = null;
    try
    {
      String checkF = " ", status = " ", ins = " ";
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      DatabaseMetaData dbm = con.getMetaData();
      ResultSet rs = dbm.getTables(null, null, "history", null);
      if(rs.next())
      {
        stmt = con.createStatement();
        ins = "INSERT INTO history(ids, requesttime, fields, choice, responsetime)" + "VALUES('"+jsonobj.getString("id")+"', '"+jsonobj.getString("Time")+"', '"+0+"', '"+0+"', '"+jsonobj.getString("actime")+"')";
        stmt.executeUpdate(ins);
        if(jsonobj.getString("check").equals("1"))
        {
          checkF = "UPDATE history SET fields = '"+1+"' WHERE requesttime = '"+jsonobj.getString("Time")+"' AND ids = '"+jsonobj.getString("id")+"' AND responsetime = '"+jsonobj.getString("actime")+"'";
          stmt.executeUpdate(checkF);
        }
        else if(jsonobj.getString("check").equals("2"))
        {
          checkF = "UPDATE history SET fields = '"+0+"' WHERE requesttime = '"+jsonobj.getString("Time")+"'AND ids = '"+jsonobj.getString("id")+"' AND responsetime = '"+jsonobj.getString("actime")+"'";
          stmt.executeUpdate(checkF);
        }
        if(jsonobj.getInt("status") == 1)
        {
          status = "UPDATE history SET choice = '"+2+"' WHERE requesttime = '"+jsonobj.getString("Time")+"'AND ids = '"+jsonobj.getString("id")+"' AND responsetime = '"+jsonobj.getString("actime")+"'";
          stmt.executeUpdate(status);
        }
        else if(jsonobj.getInt("status") == 2)
        {
          status = "UPDATE history SET choice = '"+3+"' WHERE requesttime = '"+jsonobj.getString("Time")+"'AND ids = '"+jsonobj.getString("id")+"' AND responsetime = '"+jsonobj.getString("actime")+"'";
          stmt.executeUpdate(status);
        }
        stmt.close();
        con.close();
      }
      else
      {
        stmt = con.createStatement();
        String createTb = "CREATE TABLE history" + "(ids INTEGER REFERENCES company(Id) ON DELETE CASCADE , requesttime VARCHAR(20), fields VARCHAR(20), choice VARCHAR(10), responsetime VARCHAR(20))";
        stmt.executeUpdate(createTb);
          ins = "INSERT INTO history(ids, requesttime, fields, choice, responsetime)" + "VALUES('"+jsonobj.getString("id")+"', '"+jsonobj.getString("Time")+"', '"+0+"', '"+0+"', '"+jsonobj.getString("actime")+"')";
        stmt.executeUpdate(ins);
        if(jsonobj.getString("check").equals("1"))
        {
          checkF = "UPDATE history SET fields = '"+1+"' WHERE requesttime = '"+jsonobj.getString("Time")+"'AND ids = '"+jsonobj.getString("id")+"' AND responsetime = '"+jsonobj.getString("actime")+"'";
          stmt.executeUpdate(checkF);
        }
        else if(jsonobj.getString("check").equals("2"))
        {
          checkF = "UPDATE history SET fields = '"+0+"' WHERE requesttime = '"+jsonobj.getString("Time")+"'AND ids = '"+jsonobj.getString("id")+"' AND responsetime = '"+jsonobj.getString("actime")+"'";
          stmt.executeUpdate(checkF);
        }
        if(jsonobj.getInt("status") == 1)
        {
          status = "UPDATE history SET choice = '"+2+"' WHERE requesttime = '"+jsonobj.getString("Time")+"'AND ids = '"+jsonobj.getString("id")+"' AND responsetime = '"+jsonobj.getString("actime")+"'";
          stmt.executeUpdate(status);
        }
        else if(jsonobj.getInt("status") == 2)
        {
          status = "UPDATE history SET choice = '"+3+"' WHERE requesttime = '"+jsonobj.getString("Time")+"'AND ids = '"+jsonobj.getString("id")+"' AND responsetime = '"+jsonobj.getString("actime")+"'";
          stmt.executeUpdate(status);
        }
        stmt.close();
        con.close();
      }
    }
    catch(Exception e)
    {}
  }
}
