package entity;/* Copyright © 2015 Oracle and/or its affiliates. All rights reserved. */

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.*;

@Entity
@Table(name = "conferences")
@NamedQuery(name = "Conference.getAll", query = "SELECT c from Conference c")
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "conference_id", unique = true, nullable = false)
    private long id;

    @Column(name = "theme", length = 32)
    private String theme;

    @OneToOne
    private Employee responsible;

    @OneToOne
    private Department department;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToMany(targetEntity = Employee.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "conference_employee",
            joinColumns = { @JoinColumn(name = "conference_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "employee_id", nullable = false, updatable = false) })
    private Set<Employee> employees = new HashSet<>(0);

    private static final AtomicLong counter = new AtomicLong(100);

    public Conference(){}

    public Conference(String _theme, Employee _responsible, Department _department, Date _date, Set<Employee> _employees) {
        theme = _theme;
        responsible = _responsible;
        department = _department;
        date = _date;
        employees = _employees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setResponsible(Employee responsible) {
        this.responsible = responsible;
    }

    public Employee getResponsible() {
        return responsible;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return theme;
    }


}
