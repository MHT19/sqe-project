package com.jtspringproject.JtSpringProject.dao;

import com.jtspringproject.JtSpringProject.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductDaoTest {

    private productDao productDao;
    private SessionFactory sessionFactory;
    private Session session;

    @BeforeEach
    void setUp() {
        productDao = new productDao();
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);

        productDao.setSessionFactory(sessionFactory);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void getProducts() {
        Query<Product> query = mock(Query.class);
        List<Product> expectedProducts = List.of(new Product(), new Product());

        when(session.createQuery(eq("from Product"), eq(Product.class))).thenReturn(query);
        when(query.list()).thenReturn(expectedProducts);

        List<Product> actualProducts = productDao.getProducts();

        verify(session).createQuery(eq("from Product"), eq(Product.class));
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void addProduct() {
        Product product = new Product();

        Product savedProduct = productDao.addProduct(product);

        verify(session).save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    void getProduct() {
        int productId = 1;
        Product expectedProduct = new Product();

        when(session.get(Product.class, productId)).thenReturn(expectedProduct);

        Product actualProduct = productDao.getProduct(productId);

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void updateProduct() {
        Product product = new Product();

        Product updatedProduct = productDao.updateProduct(product);

        verify(session).update(eq(Product.class.getName()), eq(product));
        assertEquals(product, updatedProduct);
    }

    @Test
    void deleteProduct() {
        int productId = 1;

        when(session.load(Product.class, productId)).thenReturn(new Product());

        boolean result = productDao.deletProduct(productId);

        assertTrue(result);
        verify(session).delete(eq(Product.class));
    }
}
