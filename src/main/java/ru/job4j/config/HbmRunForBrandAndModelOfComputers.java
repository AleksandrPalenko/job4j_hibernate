package ru.job4j.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.BrandOfComputer;
import ru.job4j.model.ModelOfComputer;

import java.util.ArrayList;
import java.util.List;

public class HbmRunForBrandAndModelOfComputers {
    public static void main(String[] args) {
        List<BrandOfComputer> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            /**
            BrandOfComputer brand = BrandOfComputer.of("Apple");
            session.save(brand);
            ModelOfComputer model = ModelOfComputer.of("MacBook Air", brand);
            ModelOfComputer model1 = ModelOfComputer.of("Mac Pro", brand);
            ModelOfComputer model2 = ModelOfComputer.of("MacBook", brand);
            ModelOfComputer model3 = ModelOfComputer.of("MacBook", brand);
            ModelOfComputer model4 = ModelOfComputer.of("iMac Pro", brand);
            session.save(model);
            session.save(model1);
            session.save(model2);
            session.save(model3);
            session.save(model4);

            list = session.createQuery(
                    "select distinct b from BrandOfComputer b join fetch b.model_computer"
            ).list();
             */
            list = session.createQuery("from BrandOfComputer").list();
            for (BrandOfComputer brand: list) {
                for (ModelOfComputer model: brand.getListOfModel()) {
                    System.out.println(model);
                }
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        /**
        for (ModelOfComputer model : list.get(0).getListOfModel()) {
            System.out.println(model);
        }
         */
    }
}
