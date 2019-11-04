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
import java.sql.*;

public class EmployeeRequests extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
        int i = 0, flag = 0, c = 0;
        ServletContext sc = this.getServletContext();
        String pswchange = request.getParameter("changePassword");
        String create = request.getParameter("createdb");
          RequestFunction(Integer.parseInt(pswchange), response, request);
          Validate(request, response);
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public void Validate(HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      int flag = 0 ;
      String val = " ";
      String pswchange = request.getParameter("changePassword");
      String idvalue = request.getParameter("password");
      String upd = " ";
      ServletContext sc = this.getServletContext();
      Class.forName("org.postgresql.Driver");
      HttpSession session = request.getSession();
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String query = "SELECT * FROM admintb";
      ResultSet rs = stmt.executeQuery(query);
      while(rs.next())
      {
        if(rs.getString("idvalue").equals(idvalue) && rs.getString("reason").equals(pswchange))
        {
          switch(Integer.parseInt(rs.getString("reason")))
          {
            case 1:
              if(rs.getString("idvalue").equals(idvalue))
              {
                  if(rs.getString("response").equals("0"))
                  {
                    request.setAttribute("Passstatus", "0");
                    request.setAttribute("data",session.getAttribute("data"));
                    RequestDispatcher rd = sc.getRequestDispatcher("/Employee.jsp");
                    rd.forward(request,response);
                  }
                  else if(rs.getString("response").equals("2"))
                  {
                    upd = "UPDATE admintb SET response='"+4+"' WHERE idvalue = '"+idvalue+"' AND reason = '"+pswchange+"'";
                    stmt.executeUpdate(upd);
                    RequestDispatcher rd = sc.getRequestDispatcher("/passwordChange.jsp");
                    rd.forward(request,response);
                  }
                  else if(rs.getString("response").equals("3"))
                  {
                    request.setAttribute("Passstatus", "3");
                    upd = "UPDATE admintb SET response='"+5+"' WHERE idvalue = '"+idvalue+"' AND reason = '"+pswchange+"'";
                    stmt.executeUpdate(upd);
                    request.setAttribute("data",session.getAttribute("data"));
                    RequestDispatcher rd = sc.getRequestDispatcher("/Employee.jsp");
                    rd.forward(request,response);
                  }
                  else
                  {
                    flag = 1;
                  }
              }
              break;
            case 2:
            if(rs.getString("idvalue").equals(idvalue))
            {
                if(rs.getString("response").equals("0"))
                {
                  request.setAttribute("updStatus", "0");
                  request.setAttribute("data",session.getAttribute("data"));
                  RequestDispatcher rd = sc.getRequestDispatcher("/Employee.jsp");
                  rd.forward(request,response);

                }
                else if(rs.getString("response").equals("2"))
                {
                  upd = "UPDATE admintb SET response='"+4+"' WHERE idvalue = '"+idvalue+"' AND reason = '"+pswchange+"'";
                  stmt.executeUpdate(upd);
                  RequestDispatcher rd = sc.getRequestDispatcher("/updateEmp.jsp");
                  rd.forward(request,response);

                }
                else if(rs.getString("response").equals("3"))
                {
                  request.setAttribute("updStatus", "3");
                  upd = "UPDATE admintb SET response='"+5+"' WHERE idvalue = '"+idvalue+"' AND reason = '"+pswchange+"'";
                  stmt.executeUpdate(upd);
                  request.setAttribute("data",session.getAttribute("data"));
                  RequestDispatcher rd = sc.getRequestDispatcher("/Employee.jsp");
                  rd.forward(request,response);

                }
                else
                {
                  flag = 1;
                }
            }
              break;
            default:
              break;
          }
        }
      }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public void RequestFunction(int i, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      pw.println("helloworld");
      int f = 0, flag = 0;
      String upd = " ", strDate = " ";
      Calendar cal = Calendar.getInstance();
      DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
      Date  date=cal.getTime();
      String pswchange = request.getParameter("changePassword");
      String idvalue = request.getParameter("password");
      HttpSession session = request.getSession();
      JSONObject jsonobj = new JSONObject();
      JSONObject jsonobject =(JSONObject) session.getAttribute("data");
      strDate = dateFormat.format(date);
      jsonobj.put("time", strDate);
      jsonobj.put("Name", jsonobject.getString("Name"));
      jsonobj.put("status", pswchange);

      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      DatabaseMetaData dbms = con.getMetaData();
      ResultSet rst = dbms.getTables(null, null, "admintb", null);

      if(rst.next())
      {
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = "SELECT * FROM admintb";
        ResultSet rs = stmt.executeQuery(query);
        rs.last();
        int size = rs.getRow();
        rs.beforeFirst();
        if(size>=1)
        {
          while(rs.next())
          {
            if(rs.getString("idvalue").equals(idvalue) && rs.getString("reason").equals(pswchange) && (Integer.parseInt(rs.getString("response")) <= 3))
            {
              flag = 0;
              break;
            }
            else
            {
              flag = 1;
            }
          }
          if(flag == 1)
          {
            writelist(jsonobj, response, request);
          }
        }
        else
        {
          writelist(jsonobj, response, request);
        }
      }
      else
      {
        writelist(jsonobj, response, request);
      }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public void writelist(JSONObject jsonobj, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException, NumberFormatException
  {
    Connection conn = null;
    Statement stmts = null;
    PrintWriter pw = response.getWriter();
    try
    {
      HttpSession session = request.getSession();
      Class.forName("org.postgresql.Driver");
      conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      DatabaseMetaData dbms = conn.getMetaData();
      ResultSet rs = dbms.getTables(null, null, "admintb", null);
      if(rs.next())
      {
        stmts = conn.createStatement();
        String ins_qry = "INSERT INTO admintb(idvalue, reqtime, reason, response, restime)" + "VALUES ('"+session.getAttribute("id")+"', '"+jsonobj.getString("time")+"', '"+jsonobj.getString("status")+"', '0', '0')";
        stmts.executeUpdate(ins_qry);
        stmts.close();
        conn.close();
      }
      else
      {
        stmts =  conn.createStatement();
        String crt_qry = "CREATE TABLE admintb" + "(idvalue VARCHAR(5), reqtime VARCHAR(20), reason VARCHAR(10), response VARCHAR(10), restime VARCHAR(20))";
        stmts.executeUpdate(crt_qry);
        String ins_data = "INSERT INTO admintb(idvalue, reqtime, reason, response, restime)" + "VALUES ('"+session.getAttribute("id")+"', '"+jsonobj.getString("time")+"', '"+jsonobj.getString("status")+"', '0', '0')";
        stmts.executeUpdate(ins_data);
        stmts.close();
        conn.close();
      }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
}
