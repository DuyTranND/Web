package web.controler;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import web.dao.impl.UserDaoImpl;
import web.models.UserModel;

public class Login_controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward to the registration form
        RequestDispatcher rd = req.getRequestDispatcher("/view/login_form.html");
        rd.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        
        String username = req.getParameter("user_name");
        String password = req.getParameter("password");

        UserDaoImpl userDao = new UserDaoImpl();
        UserModel user = userDao.findByUsername(username);  // Check if the user exists by username
        
       
        
        if (user != null && user.getPassword().equals(password)) {
            req.setAttribute("user", username);
            RequestDispatcher rd = req.getRequestDispatcher("view/home_page.jsp");
            rd.forward(req, resp);
        } else {
            req.setAttribute("error", "Invalid username or password.");
            RequestDispatcher rd = req.getRequestDispatcher("/view/login_form.html");
            rd.forward(req, resp);
        }
    }
        
    
}
