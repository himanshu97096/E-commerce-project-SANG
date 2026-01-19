package com.sangspringproject.SANGSpringProject.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.sangspringproject.SANGSpringProject.models.Category;

@Transactional
@Repository
public class categoryDao {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Transactional
	public Category addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        entityManager.persist(category);
        return category;
    }

	@Transactional
	public List<Category> getCategories() {
        return entityManager.createQuery(
                "SELECT c FROM CATEGORY c", Category.class
        ).getResultList();
    }

	@Transactional
	public Boolean deleteCategory(int id) {
        Category category = entityManager.find(Category.class, id);

        if (category != null) {
            entityManager.remove(category);
            return true;
        }
        return false;
    }

	@Transactional
	public Category updateCategory(int id, String name) {
        Category category = entityManager.find(Category.class, id);

        if (category != null) {
            category.setName(name);
            return entityManager.merge(category); // merge for update
        }
        return null;
    }

	@Transactional
	public Category getCategory(int id) {
        return entityManager.find(Category.class, id);
    }
}
