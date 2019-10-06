package com.app.service;

import com.app.model.User;
import com.app.sql.QueryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    public UserServiceImpl() throws Exception {
        createUserTableIfNotExist();
    }

    @Override
    public void createUserTableIfNotExist() throws Exception {
        String query = "CREATE TABLE IF NOT EXISTS USERS " +
                "(ID SERIAL PRIMARY KEY, FIRST_NAME varchar(100) NOT NULL, " +
                "LAST_NAME varchar(100) NOT NULL)";
        QueryExecutor.executeQuery(query);
    }

    @Override
    public void addUser(User user) throws Exception {
        String query = String.format("INSERT INTO USERS(FIRST_NAME,LAST_NAME) "
                + "VALUES('%s','%s')", user.getFirstName(), user.getLastName());
        QueryExecutor.executeQuery(query);
    }

    @Override
    public User getUser(int id) throws Exception {
        String query = String.format("SELECT * FROM USERS WHERE id=%d",id);
        List<Map<String, Object>> list = QueryExecutor.executeQueryWithResults(query);
        if (list.isEmpty()) {
            return null;
        }
        Map<String, Object> row = list.get(0);
        User user = new User();
        user.setId((int)row.get("id"));
        user.setFirstName(row.get("first_name").toString());
        user.setLastName(row.get("last_name").toString());
        return user;
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USERS";
        List<Map<String, Object>> rows = QueryExecutor.executeQueryWithResults(query);
        if (rows.isEmpty()) {
            return null;
        }
        rows.stream().forEach(row -> {
            int id = (int)row.get("id");
            String firstName = row.get("first_name").toString();
            String lastName = row.get("last_name").toString();
            users.add(new User(firstName, lastName, 0, 0, id));
        });
        return users;
    }

    @Override
    public void updateUser(int id, User user) {

    }

    @Override
    public void deleteUser(int id) {

    }

}
