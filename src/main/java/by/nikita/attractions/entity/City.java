package by.nikita.attractions.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type City.
 */
@Getter
@Setter
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "info")
    private  String info;
}
