package com.sangspringproject.SANGSpringProject.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sangspringproject.SANGSpringProject.models.Product;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class productDao {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Product> getProducts() {
	    return entityManager
	            .createQuery("FROM PRODUCT", Product.class)
	            .getResultList();
	}
	
	@Transactional
	public Product addProduct(Product product) {
	    entityManager.persist(product);   // NEW entity
	    return product;
	}
	
	@Transactional
	public Product getProduct(int id) {
	    return entityManager.find(Product.class, id);
	}
	
	@Transactional
	public Product updateProduct(Product product) {
	    return entityManager.merge(product);
	}
	
	@Transactional
	public Boolean deleteProduct(int id) {

	    Product product = entityManager.find(Product.class, id);

	    if (product != null) {
	        entityManager.remove(product);
	        return true;
	    }
	    return false;
	}
}
