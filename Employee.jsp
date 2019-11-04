<%@page import = "java.io.*"%>
<%@page import = "org.json.JSONObject"%>

<html>
  <head>
    <title> Employee page </title>
    <style>
    header
    {
      background-color: #666;
      padding : 10px;
      text-align: center;
      font-size: 35px;
      color: white;
    }
    table
    {
      width: 50%;
      text-align: center;
      margin-top: 40px;
    }
    table, td
    {
      border: 1px solid #ddd;
      padding: 8px;
    }
    table
    {
      margin-top: 50px;
      text-align: center;
      border-collapse: collapse;
      margin-left: 350px;
    }
    div.emp
    {
      text-align: center;
    }
    </style>
  </head>
  <body>
    <header>
      <h2> Employee </h2>
    </header>
    <% JSONObject jsonobj =(JSONObject) session.getAttribute("data"); %>
    <div class ="emp" >
      <table>
        <tr>
          <td>Name</td>
          <td><% out.println(jsonobj.getString("Name")); %></td>
        </tr>
        <tr>
          <td>Father name</td>
          <td><%  out.println(jsonobj.getString("Father_name")); %></td>
        </tr>
        <tr>
          <td>Register number</td>
          <td><%  out.println(jsonobj.getString("Register_number")); %></td>
        </tr>
        <tr>
          <td>phone number</td>
          <td><%  out.println(jsonobj.getString("phone_number")); %></td>
        </tr>
        <tr>
          <td>Date_of_Birth</td>
          <td><%  out.println(jsonobj.getString("Date_of_Birth")); %></td>
        </tr>
        <tr>
          <td>Blood group</td>
          <td><%  out.println(jsonobj.getString("Blood")); %></td>
        </tr>
        <tr>
          <td>Address</td>
          <td><%  out.println(jsonobj.getString("address")); %></td>
        </tr>
      </table>
      <br><br>
    </div>
    <div style="text-align:center">
      <form action = "EmployeeRequests" method = "post">
        <input type="hidden" value="<%=session.getAttribute("id")%>" name="password"/>
        <input type = "hidden" value = "1" name = "changePassword"/>
          <input type="hidden" value="one" name="createdb"/>
        <input type = "submit" value = "change Password" name = "Password" >

      </form>

      <% String i = (String) request.getAttribute("Passstatus");
          String j = (String) request.getAttribute("updStatus");
        %>
          <%
          if(i!=null)
          {
            if(i.equals("0"))
            {
              out.println("Requested for password change");
            }
            else if(i.equals("1"))
            {
              out.println("You are already requested");
            }
            else if(i.equals("3"))
            {
              out.println("rejected");
            }
          }
          %>
      <form action = "EmployeeRequests" method = "post">
        <input type="hidden" value="<%=session.getAttribute("id")%>" name="password"/>
        <input type="hidden" value="one" name="createdb"/>
        <input type = "hidden" value = "2" name = "changePassword"/>
       <input type = "submit" value = "update information" name = "updateInfo">
      </form>
      <%
      if(j!=null)
      {
        if(j.equals("0"))
        {
          out.println("Requested for update info");
        }
        else if(j.equals("1"))
        {
          out.println("You are already requested");
        }
        else if(j.equals("3"))
        {
          out.println("rejected");
        }
      }
      %>
      <form action="session.jsp" >
        <input type="submit" name="signout" value="Sign out">
        </form>
    </div>
  </body>
</html>
