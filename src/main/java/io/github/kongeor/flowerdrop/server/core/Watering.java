package io.github.kongeor.flowerdrop.server.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "waterings")
@Getter
@Setter
public class Watering extends BaseEntity {

    @Column(name = "flower_id")
    private Integer flowerId;

    private String notes;
}
