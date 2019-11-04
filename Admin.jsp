<html>
  <head>
    <title> Hr page </title>
    <style>
      header
      {
        background-color: #666;
        padding : 10px;
        text-align: center;
        font-size: 35px;
        color: white;
      }
      nav
      {
        float: left;
        width: 15%;
        height: 450px;
        background: #F5B041;
        padding: 20px;
      }
      section
      {
        margin-top: 50px;
        text-align: center;
        font-size: 25px;
        color: brown;
      }
      .button
      {
        background-color: #F6A932;
        color: brown;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        font-size: 25px;
        border: none;
        width :250px;
    }
    </style>
  </head>
  <body>
    <header>
      <h2> Admin </h2>
    </header>
    <section>
      <form action = "Views" method = "post">
        <input type="submit" value="List out" class="button">
      </form>
      <form action = "ViewHistory" method = "post">
        <input type="submit" value="View History" class="button">
      </form>
      <form action="index.jsp" method = "post">
        <input type="submit" value="back" class="button">
        </form>
    </section>

  </body>
</html>
