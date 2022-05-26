package ru.job4j.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.BrandOfCar;
import ru.job4j.model.Candidate;
import ru.job4j.model.ModelOfCar;

import java.util.ArrayList;
import java.util.List;


public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<ModelOfCar> model = new ArrayList<>();
            model.add(ModelOfCar.of("Vesta"));
            model.add(ModelOfCar.of("Largus"));
            model.add(ModelOfCar.of("Priora"));
            model.add(ModelOfCar.of("Granta"));
            model.add(ModelOfCar.of("Xray"));
            model.forEach(session::save);

            BrandOfCar brand = BrandOfCar.of("Lada");
            for (int i = 1; i < model.size(); i++) {
                brand.addModelOfCar(session.load(ModelOfCar.class, i));
            }
            session.save(brand);

            /**
             Candidate one = Candidate.of("Ivan", 1.1, 1000);
             Candidate two = Candidate.of("Alex", 3.1, 3000);
             Candidate three = Candidate.of("Anna", 0.1, 500);
             Candidate four = Candidate.of("Ivan", 1.5, 1500);
             session.save(one);
             session.save(two);
             session.save(three);
             session.save(four);
             System.out.println(two + " " + three + " " + four);
             session.getTransaction().commit();
             session.close();

             Query query = session.createQuery(
             "update Candidate c set c.name = :newName, c.experience = :newExperience, "
             + " c.salary = :newSalary where c.id = :fId"
             );
             query.setParameter("newName", "Ivan");
             query.setParameter("newExperience", 3.3);
             query.setParameter("newSalary", 3200);
             query.setParameter("fId", 1);
             query.executeUpdate();
             Query querySelectAll = session.createQuery("from Candidate");
             for (Object st : querySelectAll.list()) {
             System.out.println(st);
             }
             Query querySelectId = session.createQuery("from Candidate s where s.id = :fId");
             querySelectId.setParameter("fId", 4);
             System.out.println(querySelectId.uniqueResult());
             Query querySelectByName = session.createQuery("from Candidate s where s.name = :fName");
             querySelectByName.setParameter("fName", "Anna");
             System.out.println(querySelectByName.getResultList());
             /**
             session.createQuery("delete from Candidate where id = :fId")
             .setParameter("fId", 1)
             .executeUpdate();
             */
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
