package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_002.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void dropTableOrders() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        pool.getConnection().prepareStatement("drop table orders").executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndUpdateThisNameANdThisDescription() {
        OrdersStore store = new OrdersStore(pool);
        Order one = store.save(Order.of("name", "description"));
        Order two = Order.of("java", "junior");
        assertTrue(store.update(one.getId(), two));
        assertThat(store.findById(one.getId()).getName(), is(two.getName()));

    }

    @Test
    public void whenSaveOrderAndFindThisByName() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("java", "junior"));
        store.save(Order.of("Java", "middle"));
        List<Order> name = store.findByName("Java");
        assertThat(name.get(0).getName(), is("Java"));
    }

    @Test
    public void whenSaveOrderAndFindThisById() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("java", "junior"));
        store.save(Order.of("Java", "middle"));
        Order id = store.findById(1);
        assertThat(id.getName(), is("java"));
    }
}