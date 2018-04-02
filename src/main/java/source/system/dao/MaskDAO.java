package source.system.dao;

import org.springframework.stereotype.Repository;
import source.DbConnect;
import source.system.model.Mask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by кирюха on 29.03.2018.
 */

@Repository
public class MaskDAO {
    public List<Mask> getAllMask() {
        Connection connection = DbConnect.getConnection();
        List<Mask> masks = new ArrayList<Mask>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from mask order by title ");
            while (rs.next()) {
                Mask mask = new Mask();
                mask.setMask(rs.getString("mask"));
                mask.setTitle(rs.getString("title"));
                mask.setId(rs.getInt("id"));
                masks.add(mask);
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
        return masks;
    }

    public Mask getMask(int id) {
        Connection connection = DbConnect.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        Mask mask = new Mask();
        try {
            statement = connection.prepareStatement("select * from mask where id=? limit 1");
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {

                mask.setTitle(rs.getString("title"));
                mask.setMask(rs.getString("mask"));
                mask.setId(rs.getInt("id"));

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
        return mask;
    }

    public void addMask(Mask mask) {
        Connection connection = DbConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("insert into mask(mask, title) values (?,?)");

            ps.setString(1, mask.getMask());
            ps.setString(2, mask.getTitle());

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

    public Mask newMask(String mask, String title, int id) {
        Mask masks = new Mask();
        masks.setMask(mask);
        masks.setTitle(title);
        masks.setId(id);
        return masks;

    }


    public void updateMask(Mask mask) {

        PreparedStatement ps = null;
        Connection connection = DbConnect.getConnection();


        try {
            ps = connection.prepareStatement("update  mask set mask=?, title=? where id=?");

            ps.setString(1, mask.getMask());
            ps.setString(2, mask.getTitle());
            ps.setInt(3, mask.getId());
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
