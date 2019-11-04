<html>
  <head>
    <title> Human Resources Page </title>
    <style>
      span.values
      {
        font-size: 15px;
      }
      header
      {
        background-color: #666;
        padding : 10px;
        text-align: center;
        font-size: 35px;
        color: white;
      }
      div.top
      {
        margin-top: 50px;
      }
    </style>
  </head>
  <body style="text-align:center">
    <header>
      <h2> Human Resources </h2>
    </header>
    <div class = "top">
      <form action="HrOperation" method = "post" >
        <span class = "values"> Name :</span>
        &nbsp;
        <input type = "text" name = "Name" id = "empName" > <br><br>
        <span class = "values"> Email Id: </span>  &nbsp;
        <input type="email" name = "email" id = "email" ><br><br>
        <span class = "values">Father name : </span>&nbsp;
        <input type = "text" name = "FatherName" id = "fatherName" > <br><br>
        <span class = "values">Registration ID :</span>&nbsp;
        <input type = "text" name = "RegisterNumber" id = "RegisterNum" > <br><br>
        <span class = "values">Date.Of.Birth :</span>&nbsp;
        <input type = "text" name = "DoB" id = "DOB" > <br><br>
        <span class = "values">Blood group :</span>&nbsp;
        <input type = "text" name = "Blood" id = "Blood" > <br><br>
        <span class = "values">Phone number :</span>&nbsp;
        <input type = "text" name = "PhoneNumber" id = "phoneNumber" > <br><br>
        <span class = "values">Password :</span>&nbsp;
        <input type = "text" name = "Password" id = "password" > <br><br>
        <span class = "values">Address :</span>&nbsp;
        <input type = "text" name = "Address" id = "Address" ><br><br>
          <input type="hidden" value="AddEmp" name="methodCall"/>
            <input type = "submit" value = "Add" >
          <br>
      </form>
        <form action = "Hr.jsp" method = "post" >
          <input type = "submit" value = "back" >
        </form>
    </div>
  </body>
</html>
