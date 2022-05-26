package ru.job4j.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "model_car")
public class ModelOfCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Name;

    public ModelOfCar() {
    }

    public static ModelOfCar of(String name) {
        ModelOfCar car = new ModelOfCar();
        car.Name = name;
        return car;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelOfCar that = (ModelOfCar) o;
        return id == that.id && Objects.equals(Name, that.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Name);
    }

    @Override
    public String toString() {
        return "ModelOfCar{"
                + "id=" + id
                + ", Name='" + Name + '\''
                + '}';
    }
}
