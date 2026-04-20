package com.lab.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.lab.db.DBConnection;
import com.lab.util.HashUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AppointmentServlet")
public class AppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pname = request.getParameter("pname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String symptoms = request.getParameter("symptoms");
        String date = request.getParameter("app_date");
        String dept = request.getParameter("department");
        String gender = request.getParameter("gender");
        String time = request.getParameter("app_time");

        // Backend validation
        if (pname == null || pname.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            symptoms == null || symptoms.trim().isEmpty() ||
            date == null || date.trim().isEmpty() ||
            dept == null || dept.trim().isEmpty() ||
            gender == null || gender.trim().isEmpty() ||
            time == null || time.trim().isEmpty()) {

            request.setAttribute("message", "All fields are required.");
            RequestDispatcher rd = request.getRequestDispatcher("appointment.jsp");
            rd.forward(request, response);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            request.setAttribute("message", "Invalid email format.");
            RequestDispatcher rd = request.getRequestDispatcher("appointment.jsp");
            rd.forward(request, response);
            return;
        }

        if (!phone.matches("[0-9]{10}")) {
            request.setAttribute("message", "Phone number must be exactly 10 digits.");
            RequestDispatcher rd = request.getRequestDispatcher("appointment.jsp");
            rd.forward(request, response);
            return;
        }

        // Hash all values after validation
        String hpname = HashUtil.hashValue(pname);
        String hphone = HashUtil.hashValue(phone);
        String hemail = HashUtil.hashValue(email);
        String hsymptoms = HashUtil.hashValue(symptoms);
        String hdate = HashUtil.hashValue(date);
        String hdept = HashUtil.hashValue(dept);
        String hgender = HashUtil.hashValue(gender);
        String htime = HashUtil.hashValue(time);

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            if (con == null) {
                request.setAttribute("message", "Database connection failed.");
                RequestDispatcher rd = request.getRequestDispatcher("appointment.jsp");
                rd.forward(request, response);
                return;
            }

            String sql = "INSERT INTO Appointment (pname, phone, email, symptoms, app_date, department, gender, app_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setString(1, hpname);
            ps.setString(2, hphone);
            ps.setString(3, hemail);
            ps.setString(4, hsymptoms);
            ps.setString(5, hdate);
            ps.setString(6, hdept);
            ps.setString(7, hgender);
            ps.setString(8, htime);

            int row = ps.executeUpdate();

            if (row > 0) {
                request.setAttribute("message", "Saved with hashing!");
            } else {
                request.setAttribute("message", "Failed to save appointment.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("appointment.jsp");
        rd.forward(request, response);
    }
}