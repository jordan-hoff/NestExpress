package nestexpress.nest.dao;

import java.util.List;

import org.hibernate.Session;

import org.springframework.stereotype.Component;

import nestexpress.nest.entity.Image;

@Component
public class ImageDAO {

    public ImageDAO() {
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

    @SuppressWarnings("unchecked")
    public List<Image> getAllImages(Session currentSession) {
        List<Image> images = currentSession.createQuery("from Image").list();
        return images;
    }
}