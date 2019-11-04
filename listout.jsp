<%@page import = "java.io.*"%>
<%@page import = "org.json.JSONObject"%>
<%@page import = "org.json.JSONArray"%>
<html>
  <head>
    <title> Employee List </title>
    <style>
    table, td, th
    {
      border: 1px solid #ddd;
      padding: 8px;
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
      border-collapse: collapse;
    }
    th
    {
      padding-top: 12px;
      padding-bottom: 12px;
      text-align: center;
      background-color: #4CAF50;
      color: white;
    }
    .button
    {
      background-color: #196F3D;
      color: white;
      padding: 15px 32px;
      text-align: center;
      font-size: 18px;
      border: none;
      width: 100px;
    }
    </style>
  </head>
  <body>
    <% JSONArray jsonarray = (JSONArray) request.getAttribute("EmployeeDetails"); %>
    <header>
      <h2> Human Resources </h2>
    </header>
    <center>
      <table>
        <tr>
          <th>Employee ID</th>
          <th>Employee Name</th>
          <th>phone number</th>
          <th>Register number</th>
          <th>Father name</th>
          <th>Date of Birth</th>
          <th>Blood group</th>
          <th>Address</th>
          <th>Modify</th>
        </tr>
        <% for(int i = 0 ; i<jsonarray.length() ; i++)
          {
            JSONObject jsonobj = jsonarray.getJSONObject(i);
            %>
            <tr>
              <td>
                <% out.println(jsonobj.getString("id")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("name")); %>

              </td>
              <td>
                <% out.println(jsonobj.getString("phone number")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("registerNumber")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("fatherName")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("DoB")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("blood")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("address")); %>
              </td>
              <td>
              <form action="HrOperation" method="post">
                <input type="hidden" name="methodCall" value="deleteOperation" />
                  <input type = "hidden" name = "delete" value="<%=jsonobj.getString("id")%>" >
                    <input type="submit" value="Delete">
                      </form>
                  <form action="HrOperation" method = "post">
                    <input type = "hidden" name = "methodCall" value="Listout" />
                      <input type="hidden" name="update" value="<%=jsonobj.getString("id")%>">
                        <input type="submit" value="update">
                    </form>
              </td>
            </tr>
            <% } %>
          </table>
          <br>
            <br>
              <form action = "Hr.jsp" method="post">
                <input type="submit" value="Back" class="button" >
                </form>
        </center>
      </body>
    </html>
