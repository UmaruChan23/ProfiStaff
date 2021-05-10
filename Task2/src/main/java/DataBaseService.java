import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


public class DataBaseService {

    public static SessionFactory CreateSessionFactory(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }

    public void add(Student student,Session session){
        session.save(student);
    }

    public List<Student> list(Session session) {
        return session.createQuery("SELECT a FROM Student a", Student.class).getResultList();
    }

    public void delete(Session session, Long id){
        Student student = session.get(Student.class, id);
        session.delete(student);
    }
}
