package com.miliancode.carblog.dao;

import com.miliancode.carblog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class UserDAOImpl implements UserDAO{

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> theQuery = entityManager.createQuery("FROM User order by id", User.class);

        return theQuery.getResultList();
    }

    @Override
    public List<User> findByUsername(String username) {
        TypedQuery<User> theQuery = entityManager.createQuery("FROM User WHERE username=:theData", User.class);

        theQuery.setParameter("theData", username);

        return theQuery.getResultList();
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public boolean checkIfExist(String email) {
        TypedQuery<Boolean> query = entityManager.createQuery("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email", Boolean.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public String correctPassword(String email) {
        TypedQuery<String> theQuery = entityManager.createQuery("SELECT password FROM User WHERE email=:theData", String.class);

        theQuery.setParameter("theData", email);

        return theQuery.getSingleResult();
    }
}
