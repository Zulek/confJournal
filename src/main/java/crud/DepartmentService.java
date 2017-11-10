package crud;/* Copyright � 2015 Oracle and/or its affiliates. All rights reserved. */


import entity.Department;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;


public class DepartmentService {

    public EntityManager em = Persistence.createEntityManagerFactory("COLIBRI").createEntityManager();

    public Department get(long id){
        return em.find(Department.class, id);
    }

    public Department add(Department dep){
        em.getTransaction().begin();
        Department depFromDB = em.merge(dep);
        em.getTransaction().commit();
        return depFromDB;
    }

    public void update(Department dep){
        em.getTransaction().begin();
        em.merge(dep);
        em.getTransaction().commit();
    }

    public List<Department> getAllDepartments(){
        TypedQuery<Department> namedQuery = em.createNamedQuery("Department.getAll", Department.class);
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
