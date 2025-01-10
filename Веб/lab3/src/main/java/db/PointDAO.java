package db;

import db.models.PointModel;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PointDAO {
    private Session session;
    public PointDAO(){
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }
    public void save(PointModel point) {
        Transaction tx1 = session.beginTransaction();
        session.save(point);
        tx1.commit();
    }


    public List<PointModel> findAll() {
        List<PointModel> points = (List<PointModel>)  session.createQuery("From PointModel ").list();
        return points;
    }
}
