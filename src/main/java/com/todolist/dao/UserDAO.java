package com.todolist.dao;

import com.todolist.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum UserDAO {
    INSTANCE;

    public List<User> getAllWithoutIdAndPassword() throws ExceptionDAO {
        String query = "SELECT login, firstName, lastName FROM User";
        ArrayList<User> listUsers = new ArrayList<>();
        try (Connection connection = DBConnection.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                listUsers.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ExceptionDAO("Can`t get user.", e);
        }
        return listUsers;
    }

    public List<String> getAllLogins() throws ExceptionDAO {
        String query = "SELECT login FROM User";
        ArrayList<String> listLogins = new ArrayList<>();
        try (Connection connection = DBConnection.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                listLogins.add(login);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ExceptionDAO("Can`t get all user`s logins.", e);
        }
        return listLogins;
    }

    public User getByLoginAndPassword(String login, String password) throws ExceptionDAO {
        String query = "SELECT id, login, password, firstName, lastName FROM User WHERE login = ? AND password = ?";
        User user;
        try (Connection connection = DBConnection.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
            } catch (SQLException sqle) {
                throw new ExceptionDAO("Can`t get user by login and password.", sqle);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ExceptionDAO("Can`t connect to the database.", e);
        }
        return user;
    }

    public void create(User user) throws ExceptionDAO {
        String query = "INSERT INTO User (login, password, firstName, lastName) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ExceptionDAO("Can`t create user", e);
        }
    }
}
