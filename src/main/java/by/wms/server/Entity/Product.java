package by.wms.server.Entity;

import by.wms.server.Entity.Enum.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "length")
    private double length;

    @Column(name = "width")
    private double width;

    @Column(name = "height")
    private double height;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private double price;

    @Column(name = "status")
    private Status status;

    @Column(name = "bestbeforedate")
    private Date bestBeforeDate;

    @ManyToMany(mappedBy = "products")
    private Set<Cell> cells = new HashSet<>();

}
