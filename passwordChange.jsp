<html>
  <head>
    <title>password  change </title>
    <style>
      form.pswbutton
      {
        margin-top: 100px;
      }
      body
      {
        text-align: center;
      }
    </style>
  </head>
  <body >
    <form action = "ChangePassword" method="get" class="pswbutton">
    <span class = "texts"> Enter new password </span>
    <input type="text" name = "new_password" >
      <br><br>
      <span class = "texts">Confirm password</span>
      <input type = "text" name = "confirm_password">
        <br><br>
        <% String s = (String) request.getAttribute("incorrect");
        if(s!=null)
        {
          if(s.equals("0"))
          {
            out.println("password mismatching");
          }
        }
        %>
        <br><br>
        <input type="submit" value="submit" name = "submit">
      </form>
    </body>
  </html>
