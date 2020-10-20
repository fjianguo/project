package com.jianguo.hotsearch;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/DB")
public class DB extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see DB#DB()
     */
    public DB() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see DB#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String myUrl = "jdbc:mysql://localhost:3306/weibo?usaSSl=false&serverTimezone=UTC";
        String name = "root";
        String pwd = "156176pwd";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(myUrl, name, pwd);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM search");
            List<PeBean> list = new ArrayList<>();
            while (rs.next()) {
                int Id = rs.getInt("s_id");
                String Rank = rs.getString("s_rank");
                String Title = rs.getString("s_title");
                String View = rs.getString("s_view");
                PeBean pebean = new PeBean(Id, Rank, Title, View);
                list.add(pebean);
            }
            Gson gson = new Gson();
            String json = gson.toJson(list);
            System.out.println(json);

            // 将JSON字符串作为响应数据返回
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(json);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * @see DB#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}

