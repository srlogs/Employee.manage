<html>
  <head>
    <title> index page </title>
  </head>
  <body>
    <%

        String username = request.getRemoteUser();
        session.setAttribute("username", username);
        response.sendRedirect("LoginDetail");
  
      %>
    </body>
  </html>
