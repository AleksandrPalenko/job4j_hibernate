package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrdersStore {
    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(OrdersStore.class.getName());

    public OrdersStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Order save(Order order) {
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement(
                     "INSERT INTO orders(name, description, created) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, order.getName());
            pr.setString(2, order.getDescription());
            pr.setTimestamp(3, order.getCreated());
            pr.execute();
            ResultSet id = pr.getGeneratedKeys();
            if (id.next()) {
                order.setId(id.getInt(1));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return order;
    }

    public boolean update(int id, Order order) {
        boolean result = false;
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement(
                     "UPDATE orders SET name = ?, description = ? where id = ? ",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, order.getName());
            pr.setString(2, order.getDescription());
            pr.setInt(3, id);
            result = pr.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    public List<Order> findByName(String name) {
        List<Order> rsl = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement("SELECT * FROM orders where name = ?")) {
            pr.setString(1, name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                rsl.add(new Order(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp(4)
                ));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    public Collection<Order> findAll() {
        List<Order> rsl = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement("SELECT * FROM orders")) {
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    rsl.add(
                            new Order(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("description"),
                                    rs.getTimestamp(4)
                            )
                    );
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    public Order findById(int id) {
        Order rsl = null;
        try (Connection con = pool.getConnection();
             PreparedStatement pr = con.prepareStatement("SELECT * FROM orders WHERE id = ?")) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                rsl = new Order(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp(4)
                );
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }
}