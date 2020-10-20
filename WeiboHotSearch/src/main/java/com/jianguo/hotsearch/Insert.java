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

@WebServlet("/Insert")
public class Insert extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see Insert#Insert()
     */
    public Insert() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Insert#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String myUrl = "jdbc:mysql://localhost:3306/weibo?usaSSl=false&serverTimezone=UTC";
        String name = "root";
        String pwd = "156176pwd";
        request.setCharacterEncoding( "utf-8" );
        String rank = request.getParameter("search_rank");
        String title = request.getParameter("search_title");
        String view = request.getParameter("search_view");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(myUrl, name, pwd );

            String sql = "INSERT INTO search(s_rank,s_title,s_view) VALUES(?,?,?)";
            PreparedStatement stat = conn.prepareStatement(sql);

            stat.setString(1,rank);
            stat.setString(2,title);
            stat.setString(3,view);
            int rs = stat.executeUpdate();
            if (rs == 1) {
                System.out.println("插入成功");

                response.setContentType("text/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write("插入成功");
            } else {
                System.out.println("插入失败");

                response.setContentType("text/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write("插入失败");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * @see Insert#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}

