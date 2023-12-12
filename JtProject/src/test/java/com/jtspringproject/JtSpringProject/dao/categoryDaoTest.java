package com.jtspringproject.JtSpringProject.dao;

import com.jtspringproject.JtSpringProject.models.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class categoryDaoTest {

    private categoryDao categoryDao;
    private SessionFactory sessionFactory;
    private Session session;

    @BeforeEach
    void setUp() {
        categoryDao = new categoryDao();
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);

        categoryDao.setSessionFactory(sessionFactory);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void addCategory() {
        String categoryName = "Test Category";
        Category expectedCategory = new Category();
        expectedCategory.setName(categoryName);

        Category actualCategory = categoryDao.addCategory(categoryName);

        verify(session).saveOrUpdate(expectedCategory);
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void getCategories() {
        Query<Category> query = mock(Query.class);
        List<Category> expectedCategories = List.of(new Category(), new Category());

        when(session.createQuery(eq("from Category"), eq(Category.class))).thenReturn(query);
        when(query.list()).thenReturn(expectedCategories);

        List<Category> actualCategories = categoryDao.getCategories();

        verify(session).createQuery(eq("from Category"), eq(Category.class));
        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    void deleteCategory() {
        int categoryId = 1;

        when(session.load(Category.class, categoryId)).thenReturn(new Category());

        boolean result = categoryDao.deleteCategory(categoryId);

        assertTrue(result);
        verify(session).delete(eq(Category.class));
    }

    @Test
    void updateCategory() {
        int categoryId = 1;
        String newName = "Updated Category";

        Category category = new Category();
        category.setId(categoryId);

        when(session.get(Category.class, categoryId)).thenReturn(category);

        Category updatedCategory = categoryDao.updateCategory(categoryId, newName);

        verify(session).update(category);
        assertEquals(newName, updatedCategory.getName());
    }

    @Test
    void getCategory() {
        int categoryId = 1;
        Category expectedCategory = new Category();

        when(session.get(Category.class, categoryId)).thenReturn(expectedCategory);

        Category actualCategory = categoryDao.getCategory(categoryId);

        assertEquals(expectedCategory, actualCategory);
    }
}
