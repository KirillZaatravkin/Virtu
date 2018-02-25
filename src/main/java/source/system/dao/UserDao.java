package source.system.dao;

import org.springframework.stereotype.Repository;
import source.DbConnect;
import source.system.model.User;

import java.sql.*;
import java.util.*;


@Repository
public class UserDao {



    public List<User> getAllUsers() {
      Connection  connection = DbConnect.getConnection();
        List<User> users = new ArrayList<User>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from users ");
            while (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("login"));
                user.setId(rs.getInt("id"));
                user.setGroups(rs.getString("groups"));
                user.setReg(rs.getDate("Reg"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        DbConnect.close(connection);
        return users;
    }


    public User getUser(int id) {
        Connection  connection = DbConnect.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = new User();
        try {
            statement = connection.prepareStatement("select * from users where id=? limit 1");
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {

                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setFio(rs.getString("fio"));
                user.setGroups(rs.getString("groups"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        DbConnect.close(connection);
        return user;
    }

    public void addUser(User user) {
        Connection  connection = DbConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("insert into users(login,password,fio,groups) values (?,?,?,?)");

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFio());
            ps.setString(4, user.getGroups());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public User newUser(String password, String login, String fio, String groups, int id)
    {
        User user =new User();
        user.setGroups(groups);
        user.setPassword(password);
        user.setLogin(login);
        user.setFio(fio);
        user.setId(id);
        return user;

    }
    public void updateUser(User user) {

        PreparedStatement ps = null;
        Connection  connection = DbConnect.getConnection();


        try {
            ps = connection.prepareStatement("update  users set password=?, fio=?, login=?,groups=? where id=?");

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getFio());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getGroups());
            ps.setInt(5, user.getId());

            ps.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}