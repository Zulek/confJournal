package crud;/* Copyright � 2015 Oracle and/or its affiliates. All rights reserved. */


import entity.Conference;
import entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


public class EmployeeService {

    public EntityManager em = Persistence.createEntityManagerFactory("COLIBRI").createEntityManager();

    public Employee get(long id){
        return em.find(Employee.class, id);
    }

    public Employee add(Employee emp){
        em.getTransaction().begin();
        Employee empFromDB = em.merge(emp);
        em.getTransaction().commit();
        return empFromDB;
    }

    public void update(Employee emp){
        em.getTransaction().begin();
        em.merge(emp);
        em.getTransaction().commit();
    }

    public List<Employee> getAllEmployees(){
        TypedQuery<Employee> namedQuery = em.createNamedQuery("Employee.getAll", Employee.class);
        return namedQuery.getResultList();
    }


//    public List<Conference> searchEmployeesByName(String name) {
//        Comparator<Conference> groupByComparator = Comparator.comparing(Conference::getName)
//                .thenComparing(Conference::getLastName);
//        List<Conference> result = employeeList
//                .stream()
//                .filter(e -> e.getName().equalsIgnoreCase(name) || e.getLastName().equalsIgnoreCase(name))
//                .sorted(groupByComparator)
//                .collect(Collectors.toList());
//        return result;
//    }
//
//    public Conference getEmployee(long id) throws Exception {
//        Optional<Conference> match
//                = employeeList.stream()
//                .filter(e -> e.getId() == id)
//                .findFirst();
//        if (match.isPresent()) {
//            return match.get();
//        } else {
//            throw new Exception("The model.Employee id " + id + " not found");
//        }
//    }

}
