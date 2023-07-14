package com.springcrud.user.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="Users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @NotNull
    @Size(min=2, message="Name must be at least 2 characters long")

    //valido solo per questo esercizio: nome senza spazi e senza caratteri
    @Pattern(regexp = "[A-Za-z]+",
    		 message = "Name must not contain numbers")    
/*
    //valido in generale: nome con spazi, ma senza caratteri
    @Pattern(regexp = "^[A-Za-z.-]+(\\s*[A-Za-z.-]+)*$",
    		message = "Name must not contain numbers")   
*/ 
    private String name;
    
    @Pattern(regexp = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
             + "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
             + "(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9]"
             + "(?:[A-Za-z0-9-]*[A-Za-z0-9])?",
             message = "Email format not valid")
   @NotNull
   @Column(unique=true)
   private String email;
    
    public User() {
        this.name = "";
        this.email = "";
    }
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + '}';
    }
}
