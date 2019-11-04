<%@page import = "java.io.*"%>
<%@page import = "org.json.JSONObject"%>
<%@page import = "org.json.JSONArray"%>
<html>
  <head>
    <title> Requests Page</title>
    <style>
      table, td, th
      {
        border: 1px solid black;
      }
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
        margin-top: 50px;
        text-align: center;
      }
    </style>
  </head>
  <body>
  <% JSONArray jsonarray = (JSONArray) request.getAttribute("Data"); %>
  <header>
    <h2> Admin </h2>
  </header>
  <center>
    <table>
    <% for(int i = 0 ; i<jsonarray.length();)
      {
        JSONObject jsonobject = jsonarray.getJSONObject(i);
        %>
      <tr>
        <td>Name</td>
        <td> <% out.println(jsonobject.getString("Name")); %></td>
        </tr>
        <tr>
        <td>Father Name </td>
        <td><% out.println(jsonobject.getString("FatherName"));
        </tr>
        <tr>
        <td>Registration Number</td>
        <td><% out.println(jsonobject.getString("RegistrationId"));%></td>
        </tr>
        <tr>
        <td>Phone number</td>
        <td><% out.println(jsonobject.getString("PhoneNumber"));%></td>
        </tr>
        <tr>
        <td>Date of Birth</td>
        <td><% out.println(jsonobject.getString("DateOfBirth")); %></td>
        </tr>
        <tr>
        <td> Password </td>
        <td><% out.println(jsonobject.getString("Password")); %></td>
        </tr>
        <tr>
        <td>Blood group </td>
        <td> <% out.println(jsonobject.getString("BloodGroup")); %></td>
        </tr>
        <tr>
        <td>Address</td>
        <td><% out.println(jsonobject.getString("Address")); %></td>
        </tr>
        <% }
        %>
        </table>
      </center>
      <form action = "View" method="post">
        <input type="submit" value="Accept" name="accept">
          <input type="submit" value="Deny" name="Deny">
      </form>
      <form action="Admin.jsp" method="post">
        <input type="submit" value="Back">
      </form>
  </body>
</html>
