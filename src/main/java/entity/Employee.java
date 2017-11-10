package entity;/* Copyright © 2015 Oracle and/or its affiliates. All rights reserved. */

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "employees")
@NamedQuery(name = "Employee.getAll", query = "SELECT c from Employee c")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id", unique = true, nullable = false)
    private long id;

    @Column(name = "name", length = 32)
    private String name;

    @Column(name = "surname", length = 32)
    private String surname;

    @Column(name = "patronomyc", length = 32)
    private String patronomyc;

    @Column(name = "birthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name="depid")
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employees", targetEntity = Conference.class)
    private Set<Conference> conferences = new HashSet<>(0);

    private static final AtomicLong counter = new AtomicLong(100);

    public Employee(){}

    public Employee(String _name, String _surname, String _patronomyc, Date _birthdate, Department _department){
        name = _name;
        surname = _surname;
        patronomyc = _patronomyc;
        birthDate = _birthdate;
        department = _department;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPatronomyc() {
        return patronomyc;
    }

    public void setPatronomyc(String patronomyc) {
        this.patronomyc = patronomyc;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
    }

    @Override
    public String toString() {
        return surname + " " + name.substring(0,1) + "." + patronomyc.substring(0,1) + ".";
    }

}
