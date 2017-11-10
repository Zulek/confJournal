package entity;/* Copyright © 2015 Oracle and/or its affiliates. All rights reserved. */

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "departments")
@NamedQuery(name = "Department.getAll", query = "SELECT c from Department c")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", length = 32)
    private String name;

    private static final AtomicLong counter = new AtomicLong(100);

    public Department(){}

    public Department(String _name){
        name = _name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
