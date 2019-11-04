%@page import = "java.io.*"%>
<%@page import = "org.json.JSONObject"%>
<%@page import = "org.json.JSONArray"%>
<html>
  <head>
    <title> Requests page </title>
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
        text-align: center;
        width: 100%;
      }
      table.bigtable
      {
        width: 100%;
        border-collapse: collapse;
        margin-top: 50px;
      }
      table.smalltable
      {
        width: 100%;
      }
      form.backbutton
      {
        margin-top: 50px;
      }
      table.bigtable th
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
    <% String[] check = (String[]) request.getAttribute("check");
    JSONArray jsonarray = (JSONArray) request.getAttribute("Data");%>
    <header>
      <h2> Admin </h2>
    </header>
    <center>
      <% String m = (String) request.getAttribute("noreq");
        if(m!=null)
        {
          out.println("*** There are no requests ***");
        }
        else
        {
        %>
      <table class="bigtable">
        <th>Employee ID</th>
        <th>Employee Name</th>
        <th>Reason</th>
        <th>Status</th>
          <tr>
            <% for(int j=0;j<jsonarray.length();j++)
              {
                JSONObject jsonobject = jsonarray.getJSONObject(j);
                %>
            <td>
              <% out.println(jsonobject.getString("id")); %>
            </td>
            <td>
              <form action = "View" method="post">
              <input type="submit" value="<%=j%>" name="namebtn">
                </form>
                <% String name =  request.getParameter("name");
                if(name!=null)
                {
                  if(name.equals(Integer.toString(j)))
                  {
                  %>
            <table class = "smalltable">

                  <tr>
                    <td>Name</td>
                      <td> <% out.println(jsonobject.getString("Name")); %></td>
                    </tr>
                    <tr>
                      <td>Father Name </td>
                        <td><% out.println(jsonobject.getString("FatherName")); %></td>
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
                      <td>Blood group </td>
                        <td> <% out.println(jsonobject.getString("BloodGroup")); %></td>
                    </tr>
                    <tr>
                      <td>Address</td>
                        <td><% out.println(jsonobject.getString("Address")); %></td>
                    </tr>

                  </table>
                  <% } }
                  %>
                  </td>
                  <td>
                    <% if(check[j]!=null)
                    {
                      if(check[j].equals("1"))
                      {
                        out.println("password change");
                      }
                      if(check[j].equals("2"))
                      {
                        out.println("update information");
                      }
                    }
                    %>
                  </td>
                  <td>
                    <form action="View" method="post">
                      <input type="hidden" name="accept" value="<%=check[j]%>"/>
                        <input type="submit" value="accept"/>
                        </form>
                          <br><br>
                            <form action="View" method="post">
                            <input type="hidden" name="deny" value="<%=check[j]%>"/>
                              <input type="submit" value="deny"/><br><br>
                    </form>
                    </td>
                </tr>
                  <% } %>
              </table>
              <% } %>
              <form action="Admin.jsp" class="backbutton" >
                <input type="submit" value="Back" name="back" class="button">
                </form>

            </body>
          </html>
