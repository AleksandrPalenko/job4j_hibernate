package ru.job4j.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brand_car")
public class BrandOfCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModelOfCar> cars = new ArrayList<>();

    public BrandOfCar() {
    }

    public static BrandOfCar of(String name) {
        BrandOfCar car = new BrandOfCar();
        car.name = name;
        return car;
    }

    public void addModelOfCar(ModelOfCar car) {
        this.cars.add(car);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BrandOfCar that = (BrandOfCar) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "BrandOfCar{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
