package com.ecommerce.backend.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="privilege",schema = "public")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "privilege_id")
    private Long id;

    @Column(name = "name")

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(final String name) {
        super();
        this.name = name;
    }

    public Privilege() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }
}