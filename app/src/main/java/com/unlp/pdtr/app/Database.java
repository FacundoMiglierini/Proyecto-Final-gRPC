package com.unlp.pdtr.app;

import java.time.Instant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.time.ZoneId;
import java.io.FileInputStream;
import java.io.IOException;

public class Database {

    private static Connection connection;

    public Database() {
        connection = null;
        try {
            Properties prop = new Properties();
            try (FileInputStream input = new FileInputStream("config.properties")) {
                prop.load(input);
                String url = prop.getProperty("db.url");
                Properties connectionProps = new Properties();
                connectionProps.put("user", "crate");
                connectionProps.put("password", "");
                connectionProps.put("ssl", false);
                connection = DriverManager.getConnection(url, connectionProps);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            /*
            // create a statement for our connection
            Statement statement = connection.createStatement();
            // Using SHOW TABLES
            ResultSet showTablesResult = statement.executeQuery("SELECT * FROM timeseries");
            while (showTablesResult.next()) {
                System.out.println(showTablesResult.getString(1));
            }
            */
        } catch (SQLException e) {
            System.out.println("Connection failed! Check console for errors.");
            e.printStackTrace();
        }     
    }


    public void closeDatabase() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void writeData(double lat, double lon, String department, String measure, int value, Instant time) {

        if (connection != null) {
            PreparedStatement preparedStatement;
            String sql = "INSERT INTO timeseries (measure, lat, long, department, time, value) VALUES (?, ?, ?, ?, ?, ?)";
            Timestamp timestamp = Timestamp.from(time);
            String timestampString = timestamp.toString();
            try {
                preparedStatement = connection.prepareStatement(sql);

                // Set values for the parameters
                preparedStatement.setString(1, measure);
                preparedStatement.setDouble(2, lat);
                preparedStatement.setDouble(3, lon);
                preparedStatement.setString(4, department);
                preparedStatement.setString(5, timestampString);
                preparedStatement.setInt(6, value);
                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException sqlException) {
                System.out.println("Data writing failed! Check console for errors.");
                sqlException.printStackTrace();
            }

        }

    }


}
