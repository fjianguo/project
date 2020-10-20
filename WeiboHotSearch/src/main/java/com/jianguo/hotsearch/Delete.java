package com.jianguo.hotsearch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/Delete")
public class Delete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see Delete#Delete()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Delete#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String myUrl = "jdbc:mysql://localhost:3306/weibo?usaSSl=false&serverTimezone=UTC";
        String name = "root";
        String pwd = "156176pwd";
        String id = request.getParameter("id");
        String data;
        System.out.println(id);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");// 2.引入JDBC驱动器类
            Connection conn = DriverManager.getConnection(myUrl, name, pwd );
            String sql = "DELETE FROM search WHERE s_id=?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, id);
            int rs = stat.executeUpdate();
            if (rs != 0) {
                System.out.println("删除成功");

                data = "删除成功";
                response.setContentType("text/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(data);
            } else {
                System.out.println("删除失败");

                data = "删除失败";
                response.setContentType("text/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(data);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /**
     * @see Delete#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}

