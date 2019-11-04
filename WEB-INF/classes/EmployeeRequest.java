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

public class EmployeeRequest extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter pw = response.getWriter();
    Connection con = null;
    Statement stmt = null;
    try
    {
      int i = 0;
      String query = " ";
      response.setContentType("text/html");
      ServletContext sc = this.getServletContext();
      HttpSession session = request.getSession();
      String pswchange = request.getParameter("changePassword");
      String updinfo = request.getParameter("updateInfo");
      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
      stmt = con.createStatement();
      if(pswchange!=null)
      {
        i = 1;
        query = "UPDATE company SET status[1] = '"+1+"' WHERE id = '"+session.getAttribute("id")+"'";
        stmt.executeUpdate(query);
        stmt.close();
        con.close();
      }
      if(updinfo!=null)
      {
        i = 2;
        query = "UPDATE company SET status[2] = '"+2+"' WHERE id = '"+session.getAttribute("id")+"'";
        stmt.executeUpdate(query);
        stmt.close();
        con.close();
      }
      int c = RequestFunction(i, request, response);
      if(c == 1)
      {
  	    request.setAttribute("data",session.getAttribute("data"));
        RequestDispatcher rd = sc.getRequestDispatcher("/Employee.jsp");
        rd.forward(request,response);
      }
  	  if(c == 2)
  	  {
  		    RequestDispatcher rd = sc.getRequestDispatcher("/passwordChange.jsp");
  		    rd.forward(request,response);
  	  }
  	  else if(c == 3)
  	  {
  		  RequestDispatcher rd = sc.getRequestDispatcher("/updateEmp.jsp");
  	  	rd.forward(request,response);
  	  }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
  }
  public int RequestFunction(int i, HttpServletRequest request, HttpServletResponse response) throws IOException
  {
    Connection con = null;
    Statement stmt = null;
    PrintWriter pw = response.getWriter();
    try
    {
        int f = 0, u = 0, pass = 0;
        String upd = " ", get_val = " ", get_his = " ";
        JSONObject jsonobj = new JSONObject();
        HttpSession session = request.getSession();
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/employee","postgres","password");
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY);
        String get_first_val = "SELECT reqtime FROM company";
        ResultSet resultset = stmt.executeQuery(get_first_val);
        while(resultset.next())
        {
            Array array = resultset.getArray("reqtime");
            String[] strarray = (String[]) array.getArray();
            get_val = strarray[0];
        }
        String get_sts_val = "SELECT stshistory FROM company";
        ResultSet rstset = stmt.executeQuery(get_sts_val);
        while(rstset.next())
        {
            Array stsarray = rstset.getArray("stshistory");
            String[] strsarray = (String[]) stsarray.getArray();
            get_his = strsarray[0];
        }
        switch(i)
        {
          case 1:
            String psw = "SELECT status FROM company WHERE company.Id = '"+session.getAttribute("id")+"' ";
            ResultSet rsp = stmt.executeQuery(psw);
            while(rsp.next())
            {
              Array arrp = rsp.getArray("status");
              Integer[] intarrp = (Integer[]) arrp.getArray();
              pass = intarrp[2];
            }

              if(pass == 0)
              {
                request.setAttribute("Passstatus", "0");
                Calendar cal = Calendar.getInstance();
                Date date=cal.getTime();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                String strDate = dateFormat.format(date);
                upd = "UPDATE company SET status[3] = '"+1+"' WHERE company.Id = '"+session.getAttribute("id")+"' ";
                stmt.executeUpdate(upd);
                if(get_val.equals("0") && get_his.equals("0"))
                {
                  String one_time_val = "UPDATE company SET reqtime[1] = '"+strDate+"', stshistory[1]='"+1+"' WHERE company.Id = '"+session.getAttribute("id")+"' ";
                  stmt.executeUpdate(one_time_val);
                }
                else
                {
                  String reqtimeupd = "UPDATE company SET reqtime = array_append(reqtime, '"+strDate+"'), stshistory = array_append(stshistory, '"+1+"') WHERE company.Id = '"+session.getAttribute("id")+"' ";
                  stmt.executeUpdate(reqtimeupd);
                }
                stmt.close();
                return 1;
              }
              else if(pass == 2)
              {
                upd = "UPDATE company SET status[3] = '"+0+"', status[1]='"+0+"' WHERE company.Id = '"+session.getAttribute("id")+"' ";
                stmt.executeUpdate(upd);
                stmt.close();
                return 2;
              }
              else if(pass == 3)
              {
                request.setAttribute("Passstatus", "3");
                upd = "UPDATE company SET status[3] = '"+0+"', status[1]='"+0+"' WHERE company.Id = '"+session.getAttribute("id")+"' ";
                stmt.executeUpdate(upd);
                stmt.close();
                return 1;
              }
              else if(pass == 1)
              {
                request.setAttribute("Passstatus", "1");
                return 1;
              }
            break;
          case 2:
            String upds = "SELECT status FROM company WHERE company.Id = '"+session.getAttribute("id")+"' ";
            ResultSet rsu = stmt.executeQuery(upds);
            while(rsu.next())
            {
              Array arr = rsu.getArray("status");
              Integer[] intarr= (Integer[])arr.getArray();
              u = intarr[3];
            }
              if(u == 0)
              {
                request.setAttribute("updStatus", "0");

                Calendar cal = Calendar.getInstance();
                Date date=cal.getTime();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                String strDate = dateFormat.format(date);
                upd = "UPDATE company SET status[4] = '"+1+"', stshistory[1]='"+1+"' WHERE company.Id = '"+session.getAttribute("id")+"' ";
                stmt.executeUpdate(upd);
                if(get_val.equals("0") && get_his.equals("0"))
                {
                  String one_time_val = "UPDATE company SET reqtime[1] = '"+strDate+"' WHERE company.Id = '"+session.getAttribute("id")+"' ";
                  stmt.executeUpdate(one_time_val);
                }
                else
                {
                  String reqtimeupd = "UPDATE company SET reqtime = array_append(reqtime, '"+strDate+"'), stshistory = array_append(stshistory, '"+2+"') WHERE company.Id = '"+session.getAttribute("id")+"' ";
                  stmt.executeUpdate(reqtimeupd);
                }
                stmt.close();
                return 1;
              }
              else if(u == 2)
              {
                upd = "UPDATE company SET status[4] = '"+0+"', status[2]='"+0+"' WHERE company.Id = '"+session.getAttribute("id")+"' ";
                stmt.executeUpdate(upd);
                stmt.close();
                return 3;
              }
              else if(u == 3)
              {
                request.setAttribute("updStatus", "3");
                upd = "UPDATE company SET status[4] = '"+0+"', status[2]='"+0+"' WHERE company.Id = '"+session.getAttribute("id")+"' ";
                stmt.executeUpdate(upd);
                stmt.close();
                return 1;
              }
              else if(u == 1)
              {
                request.setAttribute("updStatus", "1");
                return 1;
              }
            break;
          default:
            break;
        }
    }
    catch(Exception e)
    {
      pw.println(e);
    }
    return 0;
  }
}
