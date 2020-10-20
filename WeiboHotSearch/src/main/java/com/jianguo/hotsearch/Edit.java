package com.jianguo.hotsearch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/Edit")
public class Edit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see Edit#Edit()
     */
    public Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Edit#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String myUrl = "jdbc:mysql://localhost:3306/weibo?usaSSl=false&serverTimezone=UTC";
        String name = "root";
        String pwd = "156176pwd";
        request.setCharacterEncoding("utf-8");
        String rank = request.getParameter("search_rank");
        System.out.println(rank);
        String title = request.getParameter("search_title");
        System.out.println(title);
        String view = request.getParameter("search_view");
        System.out.println(view);

        // 前台传递过来的 是一个 字符串 类型，这里必须把它转换为一个 整数 类型的
        String id = request.getParameter("id");
        int Id = Integer.parseInt(id);
        System.out.println(id);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");// 2.引入JDBC驱动器类
            Connection conn = DriverManager.getConnection(myUrl, name, pwd);
            // 注意下面的sql语句只有一个set
            String sql = "UPDATE search SET s_rank=?,s_title=?,s_view=? WHERE s_id=?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, rank);
            stat.setString(2, title);
            stat.setString(3, view);
            stat.setInt(4, Id);
            int rs = stat.executeUpdate();
            if (rs != 0) {
                System.out.println("更新成功");

                response.setContentType("text/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write("更新成功");
            } else {
                System.out.println("更新失败");

                response.setContentType("text/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write("更新失败");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @see Edit#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}

