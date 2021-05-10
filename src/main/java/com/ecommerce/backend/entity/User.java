package com.ecommerce.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Scope("session")
@Table(name="users",schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name ="username",unique = true)
    private String username;
    @Column(name = "password",length = 60)
    private String password;

    @OneToOne
    @JoinColumn(name="store_id",referencedColumnName = "id")
    @JsonIgnore
    private Store store;

    @OneToOne
    @JoinColumn(name="order_id")
    private Order order;



    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;


    public User(String username,String password,String firstName,String lastName){
        this.username=username;
        this.password= password;
        this.firstName = firstName;
        this.lastName = lastName;
    }



    public User() {

    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Order getOrder() {
        if (order == null){
            order = new Order();
        }
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


}
