package ru.job4j.model;

import org.dom4j.rule.Mode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brand_computer")
public class BrandOfComputer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @OneToMany(mappedBy = "brand")
    List<ModelOfComputer> listOfModel = new ArrayList<>();

    public static BrandOfComputer of(String name) {
        BrandOfComputer brand = new BrandOfComputer();
        brand.name = name;
        return brand;
    }

    public String getName() {
        return name;
    }

    public List<ModelOfComputer> getListOfModel() {
        return listOfModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BrandOfComputer that = (BrandOfComputer) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "BrandOfComputer{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
