package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate specific <code>PreparedStatements</code>,
 * to execute the specific SQL Statements.
 */
public class UserDao extends DaoImp<User> {

    /**
     * The constructor initiates an object of <code>UserDao</code> and passes the connection to its super class.
     *
     * @param connection Object of <code>Connection</code> to execute the SQL-statements.
     */
    public UserDao(Connection connection) {
        super(connection);
    }

    /**
     * Generates a <code>PreparedStatement</code> to persist the given object of <code>User</code>.
     *
     * @param user Object of <code>User</code> to persist.
     * @return <code>PreparedStatement</code> to insert the given user.
     */
    @Override
    protected PreparedStatement getCreateStatement(User user) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO users (firstname, surname, phoneNumber, permissions, hashedpassword) " +
                    "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getPermissions());
            preparedStatement.setString(5, user.getHashedPassword());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query a user by a given user id (uid).
     *
     * @param uid User id to query.
     * @return <code>PreparedStatement</code> to query the user.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long uid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM users WHERE uid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, uid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
    public User getUserByUsername(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            final String SQL = "SELECT * FROM users WHERE firstname = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getInstanceFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * Maps a <code>ResultSet</code> of one user to an object of <code>User</code>.
     *
     * @param result ResultSet with a single row. Columns will be mapped to an object of class <code>User</code>.
     * @return Object of class <code>User</code> with the data from the resultSet.
     */
    @Override
    protected User getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new User(
                result.getLong("uid"),
                result.getString("firstname"),
                result.getString("surname"),
                result.getString("phoneNumber"),
                result.getString("permissions"),
                result.getString("hashedPassword"));
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all users.
     *
     * @return <code>PreparedStatement</code> to query all users.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM users";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Maps a <code>ResultSet</code> of all users to an <code>ArrayList</code> of <code>User</code> objects.
     *
     * @param result ResultSet with all rows. The Columns will be mapped to objects of class <code>User</code>.
     * @return <code>ArrayList</code> with objects of class <code>User</code> of all rows in the
     * <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        while (result.next()) {
            User user = new User(
                    result.getLong("uid"),
                    result.getString("firstname"),
                    result.getString("surname"),
                    result.getString("phoneNumber"),
                    result.getString("permissions"),
                    result.getString("hashedPassword"));
            list.add(user);
        }
        return list;
    }

    /**
     * Generates a <code>PreparedStatement</code> to update the given user, identified
     * by the id of the user (uid).
     *
     * @param user User object to update.
     * @return <code>PreparedStatement</code> to update the given user.
     */
    @Override
    protected PreparedStatement getUpdateStatement(User user) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "UPDATE users SET " +
                            "firstname = ?, " +
                            "surname = ?, " +
                            "phoneNumber = ?, " +
                            "permissions = ?, " +
                            "hashedPassword = ? " +
                            "WHERE uid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getPermissions());
            preparedStatement.setString(5, user.getHashedPassword());
            preparedStatement.setLong(6, user.getUid());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to delete a user with the given id.
     *
     * @param uid Id of the user to delete.
     * @return <code>PreparedStatement</code> to delete user with the given id.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long uid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM users WHERE uid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, uid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
}