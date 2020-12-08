package com.jianguo.hot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HotDemo {
    public void HotDemo() {

        String myUrl = "jdbc:mysql://localhost:3306/weibo?usaSSl=false&serverTimezone=UTC";
        String name = "root";
        String pwd = "156176pwd";
        String sql = "INSERT INTO search(s_rank,s_title,s_view,s_time) VALUES(?,?,?,?)";
        String delete = "DELETE from search where s_time=?";
        String url = "https://s.weibo.com/top/summary/summary?cate=realtimehot";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(myUrl, name, pwd);

            Document document = Jsoup.connect(url).get();

            Elements r = document.select("td[class='td-01 ranktop']");
            String[] rank = r.eachText().toArray(new String[0]);

            Elements t = document.select("td[class='td-02'] a");
            String[] title = t.eachText().toArray(new String[0]);

            Elements v = document.select("span");
            String[] view = v.eachText().toArray(new String[0]);

            Date times = new Date();
            SimpleDateFormat df = new SimpleDateFormat("mm");
            String time = df.format(times);

            PreparedStatement deleteSql = connection.prepareStatement(delete);
            deleteSql.setString(1, time);
            deleteSql.executeUpdate();

            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 0; i < rank.length; i++) {
                statement.setString(1, rank[i]);
                statement.setString(2, title[i + 1]);
                statement.setString(3, view[i]);
                statement.setString(4, time);
                statement.executeUpdate();
            }
            connection.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
