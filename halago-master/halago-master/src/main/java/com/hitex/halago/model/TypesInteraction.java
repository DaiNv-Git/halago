package com.hitex.halago.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "types_interaction")
public class TypesInteraction {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name_interaction")
    private String nameInteraction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameInteraction() {
        return nameInteraction;
    }

    public void setNameInteraction(String nameInteraction) {
        this.nameInteraction = nameInteraction;
    }
}
