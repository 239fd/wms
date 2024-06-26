package by.wms.server.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "cell")
public class Cell {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rack_id")
    private Rack rack;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cell_has_product",
            joinColumns = { @JoinColumn(name = "cell_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id")}
    )
    @JsonManagedReference
    private Set<Product> products = new HashSet<>();

}
