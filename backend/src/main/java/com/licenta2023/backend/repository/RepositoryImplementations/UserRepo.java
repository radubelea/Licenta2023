package com.licenta2023.backend.repository.RepositoryImplementations;

import com.licenta2023.backend.domain.User;
import com.licenta2023.backend.repository.RepositoryInterfaces.IUserRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepo implements IUserRepo {
    private SessionFactory sessionFactory;

    public UserRepo() {
        this.sessionFactory = SessionFactoryClass.getSessionFactory();
    }

    @Override
    public User findOne(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                User user= session.createQuery(" from User where id=:id", User.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return user;
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<User> list = session.createQuery("from User", User.class).list();
                tx.commit();
                return list;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public void save(User entity) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            }catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Long id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                User user= session.createQuery("from User where id=:id", User.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(user);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(User entity) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                User user = (User) session.load( User.class, entity.getID());
                user.setName(entity.getName());
                user.setPassword(entity.getPassword());
                user.setAge(entity.getAge());
                user.setEmail(entity.getEmail());
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public User findOneByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                User user= session.createQuery(" from User where email=:email", User.class)
                        .setParameter("email", email)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return user;
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }
}
