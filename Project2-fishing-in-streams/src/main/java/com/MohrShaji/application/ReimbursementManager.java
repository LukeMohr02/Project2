package com.MohrShaji.application;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.MohrShaji.util.HibernateUtil;
import com.MohrShaji.model.Reimbursement;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class ReimbursementManager {
    static StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    static Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
    static SessionFactory factory = meta.getSessionFactoryBuilder().build();

    public Reimbursement createReimbursement(float amount, Timestamp submitted, Timestamp resolved, String description, int author,
                                   int resolver, int status_id, int type_id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();


        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setAmount(amount);
        reimbursement.setSubmitted(submitted);
        reimbursement.setResolved(resolved);
        reimbursement.setDescription(description);
        reimbursement.setAuthor(author);
        reimbursement.setResolver(resolver);
        reimbursement.setStatus_id(status_id);
        reimbursement.setType_id(type_id);
        session.save(reimbursement);
        tx.commit();
        System.out.println("Reimbursement saved");
        session.close();

        return reimbursement;
    }


    public List<Reimbursement> listReimbursements() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;


        tx = session.beginTransaction();
        List reimbursements = session.createQuery("from Reimbursement").list();

        tx.commit();


        session.close();

        return reimbursements;
    }

    public Reimbursement updateReimbursement(Integer id, float amount, int resolver) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Reimbursement reimbursement = (Reimbursement) session.get(Reimbursement.class, id);
            reimbursement.setAmount(amount);
            reimbursement.setResolver(resolver);
            session.update(reimbursement);
            tx.commit();
            return reimbursement;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }

        return null;
    }


    public Reimbursement deleteReimbursement(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Reimbursement reimbursement = (Reimbursement) session.get(Reimbursement.class, id);
            session.delete(reimbursement);
            tx.commit();
            return reimbursement;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    public Reimbursement getById(int id) {
        Session session = null;
        Reimbursement reimbursement = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            reimbursement = session.get(Reimbursement.class, id);
            Hibernate.initialize(reimbursement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return reimbursement;

    }


    public List<Reimbursement> getByUserId(int author) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;


        tx = session.beginTransaction();
        List reimbursement = session.createQuery("from Reimbursement where author = :author").setParameter("author",author).list();
        List<Reimbursement> result = new ArrayList<>();

        for (Iterator iterator = reimbursement.iterator();
            iterator.hasNext(); ) {
            Reimbursement re = (Reimbursement) iterator.next();
            result.add(re);
        }
        tx.commit();

        session.close();

        return result;
    }
}
