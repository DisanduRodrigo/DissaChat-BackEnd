/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author DisanduRodrigo
 */
@WebServlet(name = "LoadSettings", urlPatterns = {"/LoadSettings"})
public class LoadSettings extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();

        JsonObject responseJson = new JsonObject();
        Session session = HibernateUtil.getSessionFactory().openSession();

        String userId = request.getParameter("id");
        
 User user = (User) session.get(User.class, Integer.parseInt(userId));
 
//        String logged_user_id = request.getParameter("logged_user_id");
//        String mobile = request.getParameter("mobile");
//        String firstName = request.getParameter("firstName");
//        String lastName = request.getParameter("lastName");

        Criteria criteria1 = session.createCriteria(User.class);

        List<User> otherUserList = criteria1.list();
         
        System.out.println(user.getFirst_name());
        System.out.println(user.getLast_name());
        System.out.println(user.getMobile());
        System.out.println(user.getRegistered_date_time());
        System.out.println(user.getUser_Status());
      
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseJson));
    }

}
