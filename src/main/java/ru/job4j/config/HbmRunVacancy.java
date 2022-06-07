package ru.job4j.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Candidate;
import ru.job4j.model.DbOfVacancy;
import ru.job4j.model.Vacancy;

public class HbmRunVacancy {
    public static void main(String[] args) {
        Candidate rsl = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

           /**
             Candidate one = Candidate.of("Ivan", 1.1, 1000);
             Candidate two = Candidate.of("Alex", 3.1, 3000);
             Candidate three = Candidate.of("Anna", 0.1, 500);
             Candidate four = Candidate.of("Ivan", 1.5, 1500);

             Vacancy vacancy1 = Vacancy.of("middle java dev", "spring, kafka, docker");
             Vacancy vacancy2 = Vacancy.of("junior java dev", "sql, stream API, Spring Boot");

             DbOfVacancy db = DbOfVacancy.of("Developer");

             db.getVacancies().add(vacancy1);
             db.getVacancies().add(vacancy2);

             one.setDb(db);
             two.setDb(db);
             three.setDb(db);
             four.setDb(db);

             session.save(vacancy1);
             session.save(vacancy2);

             session.save(db);

             session.save(one);
             session.save(two);
             session.save(three);
             session.save(four);


            rsl = session.createQuery("select distinct c from Candidate c"
                            + "join fetch c.vacancy_db d"
                            + "join fetch d.vacancies v"
                            + "where c.id = :cId", Candidate.class)
                    .setParameter("cId", 1).uniqueResult();
*/

            rsl = session.createQuery("select distinct c from Candidate c"
                            + " join fetch c.db d"
                            + " join fetch d.vacancies v"
                            + " where c.id = :cId", Candidate.class)
                    .setParameter("cId", 1).uniqueResult();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(rsl);
    }
}

