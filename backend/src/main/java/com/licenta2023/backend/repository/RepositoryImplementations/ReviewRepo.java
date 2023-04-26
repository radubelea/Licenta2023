package com.licenta2023.backend.repository.RepositoryImplementations;

import com.licenta2023.backend.domain.Review;
import com.licenta2023.backend.domain.User;
import com.licenta2023.backend.repository.RepositoryInterfaces.IReviewRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepo implements IReviewRepo {
    private SessionFactory sessionFactory;

    public ReviewRepo() {
        this.sessionFactory = SessionFactoryClass.getSessionFactory();
    }

    @Override
    public Review findOne(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Review review= session.createQuery(" from Review where id=:id", Review.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return review;
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<Review> findAll() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Review> list = session.createQuery("from Review", Review.class).list();
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
    public void save(Review entity) {
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
                Review review= session.createQuery("from Review where id=:id", Review.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(review);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Review entity) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Review review = (Review) session.load( Review.class, entity.getID());
                review.setText(entity.getText());
                review.setRating(entity.getRating());
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public List<Review> getReviewsByUser(Long user){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Review> list = session.createQuery("from Review R where R.reviewBy.id=:user", Review.class)
                        .setParameter("user", user)
                        .list();
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
    public List<Review> getReviewsByMovie(Integer movie){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Review> list = session.createQuery("from Review where movieId=:movie", Review.class)
                        .setParameter("movie", movie)
                        .list();
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
    public Review findByUserAndMovie(Long user, Integer movie) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Review review= session.createQuery(" from Review R where R.reviewBy.id=:user and R.movieId=:movie", Review.class)
                        .setParameter("user", user)
                        .setParameter("movie", movie)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return review;
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }
}
