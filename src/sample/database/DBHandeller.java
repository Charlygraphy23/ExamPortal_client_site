package sample.database;
import sample.controller.LoginPageController;
import sample.model.User;

import java.sql.*;

public class DBHandeller {

    private PreparedStatement preparedStatement;

    private Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/paper","root","root");
    }


    public ResultSet getQandA() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM qa");
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getCorrectAnswer(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT ca FROM qa where qid=?");
        preparedStatement.setInt(1,id);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public void setUser(User user) throws SQLException, ClassNotFoundException {
            preparedStatement=getConnection().prepareStatement("INSERT INTO users (firstname,lastname,username,password,mobileno,email,datee,timee,score) VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,user.getfName());
            preparedStatement.setString(2,user.getlName());
            preparedStatement.setString(3,user.getUserName());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setString(5,user.getMobilNo());
            preparedStatement.setString(6,user.getEmail());
            preparedStatement.setString(7,user.getDate());
            preparedStatement.setString(8,user.getTime());
            preparedStatement.setString(9,user.getScore());
            preparedStatement.executeUpdate();
            preparedStatement.close();
    }

    public ResultSet checkUsernameDetails(User user) throws SQLException, ClassNotFoundException {
        int c=0;
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT username FROM users WHERE username=?");
        preparedStatement.setString(1,user.getUserName());
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }
    public ResultSet checkEmailDetails(User user) throws SQLException, ClassNotFoundException {
        int c=0;
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT email FROM users WHERE email=?");
        preparedStatement.setString(1,user.getEmail());
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }
    public ResultSet checkMobileNoDetails(User user) throws SQLException, ClassNotFoundException {
        int c=0;
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT mobileno FROM users WHERE mobileno=?");
        preparedStatement.setString(1,user.getMobilNo());
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getData(User user) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users where username=? AND password=?");
        preparedStatement.setString(1,user.getUserName());
        preparedStatement.setString(2,user.getPassword());
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public void setScore(User user) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE users set score=? where userid=?");
        preparedStatement.setString(1,user.getScore());
        preparedStatement.setInt(2, LoginPageController.loginID);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ResultSet getUserDetails() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users where userid=?");
        preparedStatement.setInt(1,LoginPageController.loginID);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }
}
