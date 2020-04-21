package nestexpress.nest.dao;

import java.util.List;

import org.hibernate.Session;

import org.springframework.stereotype.Component;

import nestexpress.nest.entity.Dorm;

@Component
public class DormDAO {

    public DormDAO() {
        super();
    }

    public Session openCurrentSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public Session openCurrentSessionwithTransaction() {
        Session currentSession = HibernateUtil.getSessionFactory().openSession();
        currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession(Session currentSession) {
        currentSession.close();
    }

    public void closeCurrentSessionwithTransaction(Session currentSession) {
        currentSession.getTransaction().commit();
        currentSession.close();
    }

    public Dorm findDormById(int dormId, Session currentSession) {
        Dorm validDorm = (Dorm) currentSession.get(Dorm.class, dormId);

        return validDorm;
    }

    public List<Dorm> getAllDorms(Session currentSession) {
        List<Dorm> dormList = currentSession.createQuery("from Dorm", Dorm.class).list();

        return dormList;
    }
}