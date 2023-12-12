package com.jtspringproject.JtSpringProject.dao;

import com.jtspringproject.JtSpringProject.models.Cart;
import com.mysql.cj.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class cartDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private cartDao cartDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setSessionFactory() {
        // Already covered by Mockito annotations
    }

    @Test
    void addCart() {
        Cart cart = new Cart();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        cartDao.addCart(cart);
        verify(session).save(cart);
    }

    @Test
    void getCarts() {
        String hql = "SELECT DISTINCT c FROM CART c JOIN FETCH c.products";
        org.hibernate.Query<Cart> query = (org.hibernate.Query<Cart>) mock(Query.class);
        List<Cart> expectedCarts = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(hql, Cart.class)).thenReturn((org.hibernate.query.Query<Cart>) query);
        when(query.getResultList()).thenReturn(expectedCarts);

        List<Cart> actualCarts = cartDao.getCarts();

        verify(session).createQuery(hql, Cart.class);
        assertEquals(expectedCarts, actualCarts);
    }

    @Test
    void updateCart() {
        Cart cart = new Cart();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        cartDao.updateCart(cart);
        verify(session).update(cart);
    }

    @Test
    void deleteCart() {
        Cart cart = new Cart();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        cartDao.deleteCart(cart);
        verify(session).delete(cart);
    }

    @Test
    void getCart() {
        int id = 1;
        String hql = "FROM Cart WHERE id = :id";
        org.hibernate.Query<Cart> query = (org.hibernate.Query<Cart>) mock(Query.class);
        Cart expectedCart = new Cart();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(eq(hql), eq(Cart.class))).thenReturn((org.hibernate.query.Query<Cart>) query);
        when(query.setParameter(eq("id"), eq(id))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(expectedCart);

        Cart actualCart = cartDao.getCart(id);

        verify(session).createQuery(eq(hql), eq(Cart.class));
        verify(query).setParameter(eq("id"), eq(id));
        assertEquals(expectedCart, actualCart);
    }
}
