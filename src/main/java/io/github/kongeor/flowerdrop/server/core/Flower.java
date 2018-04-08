package io.github.kongeor.flowerdrop.server.core;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flowers")
@Getter
@Setter
public class Flower extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "flowerId")
    private List<Watering> waterings = new ArrayList<>();

    @Override
    public String toString() {
        return "Flower{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
