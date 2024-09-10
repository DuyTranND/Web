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

public class Register_controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/view/register_form.html");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        // Retrieve form parameters
        String user = req.getParameter("user_name");
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String password = req.getParameter("password");

        // Create a new UserDaoImpl object to interact with the database
        UserDaoImpl userDao = new UserDaoImpl();

        try {
            // Check if the username already exists
            UserModel existingUser = userDao.findByUsername(user);

            if (existingUser != null) {
                // Username exists, set an error message and forward back to registration form
                req.setAttribute("errorMessage", "Username already exists. Please choose another one.");
                RequestDispatcher rd = req.getRequestDispatcher("/view/register_form.html");
                rd.forward(req, resp);
            } else {
                // Username is available, proceed with registration
                UserModel newUser = new UserModel(user, email, fullname, "null", password);

                // Insert user into the database
                userDao.insert(newUser);

                
                resp.sendRedirect("login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "An error occurred. Please try again.");
            RequestDispatcher rd = req.getRequestDispatcher("/view/register_form.html");
            rd.forward(req, resp);
        }
    }
}
