package com.todolist.servlet;

import com.todolist.dao.ItemDAO;
import com.todolist.entity.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addItem")
public class AddItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Item item = new Item(request.getParameter("item"), FindUserServlet.user.getId());
        ItemDAO.INSTANCE.create(item);
        request.getRequestDispatcher("/item").forward(request, response);
    }
}
