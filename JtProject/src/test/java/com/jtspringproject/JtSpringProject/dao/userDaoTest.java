package com.jtspringproject.JtSpringProject.dao;

import com.jtspringproject.JtSpringProject.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Collections;
import javax.persistence.NoResultException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserDaoTest {

    private userDao userDao;
    private SessionFactory sessionFactory;
    private Session session;

    @BeforeEach
    void setUp() {
        userDao = new userDao();
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);

        userDao.setSessionFactory(sessionFactory);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void getAllUsers() {
        Query<User> query = mock(Query.class);
        List<User> expectedUsers = List.of(new User(), new User());

        when(session.createQuery(eq("from User"), eq(User.class))).thenReturn(query);
        when(query.list()).thenReturn(expectedUsers);

        List<User> actualUsers = userDao.getAllUser();

        verify(session).createQuery(eq("from User"), eq(User.class));
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void saveUser() {
        User user = new User();

        User savedUser = userDao.saveUser(user);

        verify(session).saveOrUpdate(user);
        assertEquals(user, savedUser);
    }

    @Test
    void getUserByUsernameAndPassword() {
        String username = "testUser";
        String password = "testPassword";

        Query<User> query = mock(Query.class);
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setPassword(password);

        when(session.createQuery(eq("from User where username = :username"), eq(User.class))).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedUser);

        User actualUser = userDao.getUser(username, password);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUserByUsernameAndPasswordNoResult() {
        String username = "nonexistentUser";
        String password = "testPassword";

        Query<User> query = mock(Query.class);

        when(session.createQuery(eq("from User where username = :username"), eq(User.class))).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);

        User actualUser = userDao.getUser(username, password);

        assertNull(actualUser.getId());
        assertNull(actualUser.getUsername());
        assertNull(actualUser.getPassword());
    }

    @Test
    void updateUser() {
        User user = new User();

        User updatedUser = userDao.updateUser(user);

        verify(session).update(eq(User.class.getName()), eq(user));
        assertEquals(user, updatedUser);
    }

    @Test
    void updateUserException() {
        User user = new User();
        doThrow(new RuntimeException("Some error")).when(session).update(eq(User.class.getName()), eq(user));

        assertThrows(RuntimeException.class, () -> userDao.updateUser(user));
    }
}
