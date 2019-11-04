<html>
  <head>
    <title> Welcome page </title>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:600&display=swap" rel="stylesheet">
      <link href="https://fonts.googleapis.com/css?family=Open+Sans&display=swap" rel="stylesheet">
    <style>
      div.size
      {
        padding-top: 50px;
        padding-left: 50px;
        padding-right: 50px;
        padding-bottom: 50px;
        background-color:  #f7f2f1;
        text-align: center;
        margin: 100px 200px 100px 200px;
        box-shadow: 5px 5px  #cdc8c6;
        /* border-width: 5px;
        border-color:  #cdc8c6; */
      }
      div.title
      {
        font-size: 25px;
        text-align: center;
        font-family: 'Montserrat', sans-serif;
        color:  #a25747;
      }
      span.values
      {
        font-size: 15px;
        font-family: 'Open Sans', sans-serif;
      }
      div.button
      {
        text-align: center;
        font-family: 'Montserrat', sans-serif;
        color: lightblue;
        font-size: 10px;
      }
      input[type=submit]
      {
        font-family: 'Open Sans', sans-serif;
        background-image: linear-gradient( #a6dbf9 , #41b4f5,   #2fb3fe );
        font-size: 15px;
        border-width: 1px;
        border-color: black;
        width: 285px;
        padding-top: 3px;
        padding-bottom: 3px;
      }
      input:hover[type=submit]
      {
        background-color:  #61a1c6;
        color: white;
      }
      body
      {
        background-image: linear-gradient( #b4e1fa , #41b4f5,   #b4e1fa );
      }
      a
      {
        font-family: 'Open Sans', sans-serif;
      }
      input[type=text]
      {
        border: none;
        border-bottom: 1px solid black;
        background-color:  #f7f2f1;
      }
      input[type=password]
      {
        border: none;
        border-bottom: 1px solid black;
        background-color:  #f7f2f1;
      }
      input:hover[type=text]
      {
        border-bottom: 1px solid blue;
      }
      input:hover[type=password]
      {
        border-bottom: 1px solid blue;
      }
    </style>
  </head>
  <body>
    <%
        String i =(String) session.getAttribute("value");
          request.setAttribute("value","correct");
    %>
    <div class = "size">
      <form action = "j_security_check" method = "post">
        <div class = "title"> Employee Login </div>
        <br><br><br>
        <span class = "values">Enter username : </span>
        &nbsp;
        <input type = "text" name = "j_username" id = "username" >
        <br><br>
        <span class = "values" >Enter password : </span>
        &nbsp;
        <input type = "password" name = "j_password" id = "password" >
        <br><br><br>
        <%
          if(i != null)
          {
              if(i.equals("incorrect"))
              {
                  out.println("incorrect password");
              }
          }
        %>
        <div class = "button">
          <input type = "submit" value = "Login" >
        </div>
      </form>
      <br> <br>
      <div class = "nextPage">
        <a href = "Hr.jsp">Enter as Human Resources Manager </a>
        <br><br>&nbsp;&nbsp; or <br><br>
        <a href = "Admin.jsp">Enter as Admin </a>
      </div>
    </div>
  </body>
</html>
