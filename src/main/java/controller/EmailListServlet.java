package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.User;
import util.MailUtil;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        User user = new User(firstName, lastName, email);

        String to = email;
        String from = "nghao123k4@gmail.com";
        String subject = "Welcome to our email list";
        String body = "Dear " + firstName + ",\n\n"
                + "Thanks for joining our email list. We'll make sure to send\n"
                + "you announcements about new products and promotions.\n\n"
                + "Have a great day and thanks again!\n\n"
                + "Mike Murach & Associates";
        boolean isBodyHTML = false;

        try {
            MailUtil.sendMail(to, from, subject, body, isBodyHTML);
            System.out.println("Email đã được gửi thành công đến " + to);
        } catch (Exception e) {
            System.out.println("Lỗi khi gửi email: " + e.getMessage());
        }

        request.setAttribute("user", user);
        String url = "/success.jsp";

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
