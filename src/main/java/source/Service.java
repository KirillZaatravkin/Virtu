package source;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by кирюха on 24.11.2017.
 */
public class Service {
    private Connection connection;



    public Settings getSetting(int id) {
        connection = DbConnect.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        Settings setting = new Settings();
        try {
            statement = connection.prepareStatement("select * from settings where id=?");
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                setting.setSettings(rs.getString("sett"));
                setting.setSetting_name(rs.getString("sett_name"));
                setting.setId(rs.getInt("id"));
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
        return setting;
    }

    public List<Settings> getAllSettings()
    {
        connection = DbConnect.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Settings> sett =new ArrayList<Settings>();

        try {
            statement = connection.prepareStatement("select * from settings ");

            rs = statement.executeQuery();
            while (rs.next()) {
                Settings setting = new Settings();
                setting.setSettings(rs.getString("sett"));
                setting.setSetting_name(rs.getString("sett_name"));
                setting.setId(rs.getInt("id"));
                sett.add(setting);
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
        return sett;

    }

    public void updateSetting(Settings setting) {
        PreparedStatement ps = null;
        connection = DbConnect.getConnection();

        try {
            ps = connection.prepareStatement("update  settings set sett=? , sett_name=? where id=?");

            ps.setString(1, setting.getSettings());
           ps.setString(2, setting.getSetting_name());
            ps.setInt(3,setting.getId());

            ps.execute();
            System.out.println(setting.getId());

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
