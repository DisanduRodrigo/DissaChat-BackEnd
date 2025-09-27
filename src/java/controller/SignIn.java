/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.User;
import entity.User_Status;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@MultipartConfig
@WebServlet(name = "SignIn", urlPatterns = {"/SignIn"})
public class SignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("success", false);

        JsonObject requestJson = gson.fromJson(request.getReader(), JsonObject.class);
        String mobile = requestJson.get("mobile").getAsString();
        String password = requestJson.get("password").getAsString();
       
       

        if (mobile.isEmpty()) {
            //mobile number is empty
            responseJson.addProperty("message", "Please fill Your Mobile Number");
        }   else if (!Validations.isMobileNumberValid(mobile)) {
            //invalid mobile Number 
            responseJson.addProperty("message", "Invalid mobile number");
        }else if (password.isEmpty()) {
            //password is blank
            responseJson.addProperty("message", "Please Fill Your Password");
        } else if (!Validations.isPasswordValid(password)) {
            responseJson.addProperty("message", "Password must include at least one Uppercaser letter,"
            +"number, special character and be at least eight characters long");

        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();

            //search mobile number and password
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("mobile", mobile));
            criteria1.add(Restrictions.eq("password", password));

            if (!criteria1.list().isEmpty()) {
                //user found
                 User user = (User) criteria1.uniqueResult();
                 
                 responseJson.addProperty("success",true);
                 responseJson.addProperty("message","Sign In Success");
                 responseJson.add("user", gson.toJsonTree(user));

            } else {

                //user  not found
                   responseJson.addProperty("message","Invalid Credentials");

              
            }

            session.close();

        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseJson));

    }

}
