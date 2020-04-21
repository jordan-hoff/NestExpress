package nestexpress.nest.services;

import org.springframework.stereotype.Service;
import org.hibernate.Session;

import nestexpress.nest.entity.Dorm;
import nestexpress.nest.dao.DormDAO;

import java.util.List;

@Service
public class DormService {

    private static DormDAO dormDao;

    public DormService() {
        dormDao = new DormDAO();
    }

    public Dorm findDormById(int dormId) {
        Session currentSession = dormDao.openCurrentSession();
        Dorm dorm = dormDao.findDormById(dormId, currentSession);
        dormDao.closeCurrentSession(currentSession);
        return dorm;
    }

    // Gets the list of all the dorms.
    public List<Dorm> getAllDorms() {
        Session currentSession = dormDao.openCurrentSession();
        List<Dorm> dormList = dormDao.getAllDorms(currentSession);
        dormDao.closeCurrentSession(currentSession);
        return dormList;
    }
}