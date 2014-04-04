import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * Created by Марат on 04.04.2014.
 */
public class RegFormServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name.contains("-") || name.equals("")) {
            PrintWriter pw = response.getWriter();
            pw.print("Error!");
            doGet(request, response);
        } else {
            response.sendRedirect("/success");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String name = request.getParameter("name");
        pw.print("<form action='/' method ='POST'>");
        pw.print("<input type='text' name='name' value='"+
                (name==null?"":name) +
                "'/>");
        pw.print("</input>");
        pw.print("<input type='submit' value='send'/>");
        pw.print("</form>");
    }
}
