<%@page import = "java.io.*"%>
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
      .namebtn
      {
        background-color: white;
        color: black;
        font-size: 18px;
        border: none;

      }
      .accept
      {
        width: 60px;
        background-color: white;
      }
      .accept:hover
      {
        background-color: #228B22;
      }
      .deny
      {

        width: 60px;
        background-color: white;
      }
      .deny:hover
      {
        background-color: #FF0000;
      }
    </style>
  </head>
  <body>
    <% JSONArray jsonarray = (JSONArray) request.getAttribute("Data");%>
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
              <form action = "Views" method="post">
              <input type="hidden" value="<%=j%>" name="namebtns">
              <input type="submit" value="<%=jsonobject.getString("Name")%>" name="namebtn" class="namebtn">
                </form>
                <%String name =(String)  request.getAttribute("name");
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
				  <%
        } }
                  %>
                  </td>
                  <td>
                    <%
                      if(jsonobject.getString("reason").equals("1"))
                      {
                        out.println("password change");
                      }
                      else if(jsonobject.getString("reason").equals("2"))
                      {
                        out.println("update information");
                      }
                    %>
                  </td>
                  <td>
                    <form action="Views" method="post">
                    <input type="hidden" name="check1" value="<%=jsonobject.getString("reason")%>" >
                      <input type="hidden" name="time" value="<%=jsonobject.getString("time")%>" >
                        <input type="hidden" name="accept" value="<%=jsonobject.getString("id")%>">
                          <input type="submit" value="accept" class="accept"/>
                            </form>
                          <br><br>
                            <form action="Views" method="post">
                              <input type="hidden" name="check2" value="<%=jsonobject.getString("reason")%>" >
                                <input type="hidden" name="time" value="<%=jsonobject.getString("time")%>" >
                                  <input type="hidden" name="deny" value="<%=jsonobject.getString("id")%>">
                                    <input type="submit" value="deny" class="deny"/><br><br>
                    </form>
                    </td>
                </tr>
                  <%  }
                %>
              </table>
              <%  } %>
              <form action="Admin.jsp" class="backbutton" >
                <input type="submit" value="Back" name="back" class="button">
                </form>
			  </center>
            </body>
          </html>
