package com.example.uidesign;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DbUtil {

    private Connection connection;
    //public static ArrayList<EnviromentHomeClass> typeLocationList;
    private String types,location,time;
    private double   temp,devSerialNumber,ph,rongJy,zhuD,gaoMengSY, cod,bod5,anDan,total_lin,total_dan,shiYou,yLSu,yuL,dandao,yingdu,sedu,xiaosuanyan,xuanfuwu;

    //连接数据库
    public Connection getSQLConnection(String ip, String user, String pwd, String db) throws Exception
    {
        Connection con = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");//固定写法
            //一定要在AndroidManifest.xml中加入socket权限，不然会包权限错误
            /*
              <uses-permission android:name="android.permission.INTERNET" />
              <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
              <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
             */
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":1433/" + db + ";charset=utf8", user, pwd);//ip:数据库的IP  db：数据库名字 user:数据库用户名   pwd：用户名对应密码
            //con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":1433/" + db + ";useunicode=true;characterEncoding=UTF-8", user, pwd);
            //con = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.219.140:1433/smm","sa","123zxcZXC");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }
    //登陆
    public String QuerySQL(String name,String pass)
    {
        String result = "";
        try
        {
            //要想连接数据库必须导入相应的包在libs中（jtds-1.2.7.jar和sqljdbc.jar）
            connection= getSQLConnection("192.168.219.140","sa", "123zxcZXC", "smm");//连接数据库
            String sql = "select username from smm.dbo.UserInfo where username=? and userpwd=?"; //查询语句
            PreparedStatement stat = connection.prepareStatement(sql);//得到PreparedStatement对象
            stat.setString(1, name);//给占位符设置上内容
            stat.setString(2, pass);
            ResultSet rs = stat.executeQuery();//执行查询语句
            while (rs.next())//判断是否查询出数据
            {
                result= "1" ;
            }
            rs.close();
            connection.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            result += "查询数据异常!" + e.getMessage();
        }

        return result;
    }


}
