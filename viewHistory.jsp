<%@page import = "java.io.*"%>
<%@page import = "org.json.JSONObject"%>
<%@page import = "org.json.JSONArray"%>
<html>
  <head>
    <title> History </title>
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
    <% JSONArray jsonarray = (JSONArray) request.getAttribute("Data"); %>
    <header>
      <h2> History </h2>
    </header>
    <center>
      <% String rec = (String) request.getAttribute("noreq");
        if(rec!=null)
          out.println("** There are no records **");
        else {
          %>
      <table>
        <tr>
          <th>Employee Id</th>
          <th>Employee Name</th>
          <th>Request time</th>
          <th>Reason</th>
          <th>Status</th>
          <th>Response time</th>
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
                <% out.println(jsonobj.getString("Name")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("rqtime")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("reason")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("status")); %>
              </td>
              <td>
                <% out.println(jsonobj.getString("rstime")); %>
              </td>
            </tr>
            <% } %>
          </table>
          <br>
            <br>
              <form action = "Admin.jsp" method="post">
                <input type="submit" value="Back" class="button" >
                </form>
                <% } %>
        </center>
      </body>
    </html>
