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
    <%JSONObject jsonobj = (JSONObject) request.getAttribute("data");

    %>
    <form action = "HrOperation" method="post">
      <div class = "items">
        <span class="values">Enter Name :</span>
        <input type="text" name = "name" value="<%=jsonobj.getString("name")%>" />
        <br><br>
      <span class = "values">Enter Phone number :</span>
      <input type="text" name = "phone_number" value="<%=jsonobj.getString("phone number")%>">
      <br><br>
        <span class = "values">Enter address :</span>
        <input type="text" name = "address" value="<%=jsonobj.getString("address")%>">
        <br><br>
          <span class = "values"> Enter registration id :</span>
          <input type = "text" name = "registrationid" value="<%=jsonobj.getString("registerNumber")%>">
          <br><br>
          <span class = "values" >Enter Blood group :</span>
          <input type = "text" name = "Blood" value="<%=jsonobj.getString("blood")%>">
          <br><br>
            <span class="values">Enter Date of Birth :</span>
            <input type="text" name = "dob" value="<%=jsonobj.getString("DoB")%>" />
            <br><br>
            <span class="values">Enter Father name :</span>
            <input type="text" name = "fatherName" value="<%=jsonobj.getString("fatherName")%>" >
            <br><br>
              <input type="hidden" name = "idvalue" value="<%=jsonobj.getString("id")%>" >
            <input type="submit" value="updateInfo" name = "methodCall">
          </div>
            </form>
  </body>
</html>
