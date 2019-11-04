<%@page import = "org.json.JSONObject"%>
<html>
  <head>
    <title> update information </title>
    <style>
      span.values
      {
        font-size: 20px;
      }
      div.items
      {
        text-align: center;
        margin-top: 100px;
      }
    </style>
  </head>
  <body>
    <%JSONObject jsonobj = (JSONObject) session.getAttribute("data");

    %>
    <form action = "UpdateEmp" method="get">
      <div class = "items">
        <span class="values">Enter Name :</span>
        <input type="text" name = "name" value="<%=jsonobj.getString("Name")%>" />
        <br><br>
      <span class = "values">Enter Phone number :</span>
      <input type="text" name = "phone_number" value="<%=jsonobj.getString("phone_number")%>">
      <br><br>
        <span class = "values">Enter address :</span>
        <input type="text" name = "address" value="<%=jsonobj.getString("address")%>">
        <br><br>
          <span class = "values"> Enter registration id :</span>
          <input type = "text" name = "registrationid" value="<%=jsonobj.getString("Register_number")%>">
          <br><br>
          <span class = "values" >Enter Blood group :</span>
          <input type = "text" name = "Blood" value="<%=jsonobj.getString("Blood")%>">
          <br><br>
            <span class="values">Enter Date of Birth :</span>
            <input type="text" name = "dob" value="<%=jsonobj.getString("Date_of_Birth")%>" />
            <br><br>
            <span class="values">Enter Father name :</span>
            <input type="text" name = "fatherName" value="<%=jsonobj.getString("Father_name")%>" />
            <br><br>
              <input type="hidden" name = "idvalue" value="<%=session.getAttribute("id")%>" >
              <input type = "hidden" name = "empupdate" value ="emp"/>
            <input type="submit" value="updateInfo" name = "methodCall">
          </div>
            </form>
  </body>
</html>
