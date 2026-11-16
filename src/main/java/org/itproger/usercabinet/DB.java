package org.itproger.usercabinet;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DB {
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "itproger_javafx";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public String[] getUser(String login) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, login, email FROM users WHERE login = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, login);
        ResultSet res = prSt.executeQuery();

        if (res.next()) {
            return new String[]{
                    String.valueOf(res.getInt("id")),
                    res.getString("login"),
                    res.getString("email")
            };
        }
        return null;
    }

    public boolean isLoginExists(String newLogin, int currentUserId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM users WHERE login = ? AND id != ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, newLogin);
        prSt.setInt(2, currentUserId);
        ResultSet res = prSt.executeQuery();
        return res.next();
    }

    public void updateUser(int id, String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE users SET login = ?, email = ?, password = ? WHERE id = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, login);
        prSt.setString(2, email);
        prSt.setString(3, md5String(password));
        prSt.setInt(4, id);
        prSt.executeUpdate();
    }

    public static String md5String(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }
}
