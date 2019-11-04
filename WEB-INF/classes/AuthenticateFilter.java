import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class AuthenticateFilter implements Filter
{
  private ServletContext context;
  public void init(FilterConfig fConfig) throws ServletException
  {
    this.context = fConfig.getServletContext();
    this.context.log("filter class started");
  }
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    HttpSession session = req.getSession();
    PrintWriter pw = res.getWriter();
    String uri = req.getRequestURI();
    this.context.log("Requested Resource::"+uri);
    this.context.log("session username is "+session.getAttribute("username"));

    if(session.getAttribute("username") == null || uri.endsWith(".jsp"))
    {
      this.context.log("session username is "+session.getAttribute("username"));
      res.sendRedirect("welcomePage.jsp");
    }
    else
    {
      chain.doFilter(request, response);
    }
  }
  public void destroy() {

	}
}
