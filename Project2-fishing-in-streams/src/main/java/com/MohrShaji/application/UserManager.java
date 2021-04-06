package com.MohrShaji.application;


import java.util.List;

import com.MohrShaji.util.HibernateUtil;
import com.MohrShaji.model.User;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class UserManager {

    static StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    static Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
    static SessionFactory factory = meta.getSessionFactoryBuilder().build();

    public User createUser(String username, String password, String firstname, String lastname, String email, int role_id) {


        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setRole_id(role_id);

        session.save(user);
        t.commit();
        System.out.println("Saved Entity");
        session.close();
        return user;
/*
        try {
            tx = session.beginTransaction();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setRole_id(role_id);
            userID = (Integer) session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userID;*/
    }


    public List<User> listUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        tx = session.beginTransaction();
        List users = session.createQuery("from User").list();
        tx.commit();

        session.close();

        return users;
    }

    public User updateUser(Integer userID, String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, userID);
            user.setUsername(username);
            session.update(user);
            tx.commit();
            return user;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }


    public User deleteUser(Integer userID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, userID);
            session.delete(user);
            tx.commit();
            return user;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public User getByUserId(int id) {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = session.get(User.class, id);
            Hibernate.initialize(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        System.out.println(user);
        return user;

    }

    @Deprecated
    public User getByUsername(String username) {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            org.hibernate.query.Query query= session.createQuery("from User where username=:username");
            query.setParameter("username", username);
            return (User) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }
}