<html>
  <head>
    <title> session invalidate </title>
  </head>
  <body>
    <% session.invalidate();
      
      response.sendRedirect("index.jsp");
      %>
    </body>
  </html>
