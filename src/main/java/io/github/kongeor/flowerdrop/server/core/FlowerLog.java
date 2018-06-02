package io.github.kongeor.flowerdrop.server.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "flower_drop_log")
@Getter
@Setter
public class FlowerLog extends BaseEntity {

    @Column(name = "description")
    private String description;
}
