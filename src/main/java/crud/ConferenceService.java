package crud;/* Copyright � 2015 Oracle and/or its affiliates. All rights reserved. */


import com.mysql.jdbc.StringUtils;
import entity.Conference;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConferenceService {

    public EntityManager em = Persistence.createEntityManagerFactory("COLIBRI").createEntityManager();

    public Conference get(long id){
        return em.find(Conference.class, id);
    }

    public Conference add(Conference con){
        em.getTransaction().begin();
        Conference conFromDB = em.merge(con);
        em.getTransaction().commit();
        return conFromDB;
    }

    public void update(Conference conference){
        em.getTransaction().begin();
        em.merge(conference);
        em.getTransaction().commit();
    }

    public List<Conference> getAllConferences(){
        TypedQuery<Conference> namedQuery = em.createNamedQuery("Conference.getAll", Conference.class);
        return namedQuery.getResultList();
    }

    public List getFilteredConferences(
            String theme,
            String fromDate,
            String toDate,
            String department,
            String participant
    ){
        StringBuffer queryBuffer = new StringBuffer();
        Map<String,Object> columns = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (!StringUtils.isNullOrEmpty(theme)) {
            queryBuffer.append(" AND c.theme LIKE :theme");
            columns.put("theme",theme);
        }
        if (!StringUtils.isNullOrEmpty(department)) {
            queryBuffer.append(" AND department_id=:department");
            columns.put("department",department);
        }
        if (!StringUtils.isNullOrEmpty(fromDate)) {
            try {
                Date from = dateFormat.parse(fromDate);
                queryBuffer.append(" AND c.date>=:fromDate");
                columns.put("fromDate",from);
            }
            catch(ParseException ignored){}
        }
        if (!StringUtils.isNullOrEmpty(toDate)) {
            try {
                Date to = dateFormat.parse(toDate);
                queryBuffer.append(" AND c.date<=:toDate");
                columns.put("toDate",to);
            }
            catch(ParseException ignored){}
        }
        if (!StringUtils.isNullOrEmpty(participant)) {
            queryBuffer.append(" AND (responsible_employee_id=:participant")
                    .append(" OR conference_id IN (SELECT cc.id FROM Conference cc inner join cc.employees e WHERE e.id=:participant))");
            columns.put("participant",participant);
        }

        String queryString = "SELECT c FROM Conference c";
        if (queryBuffer.length()!=0)
             queryString += " WHERE " + queryBuffer.substring(4);
        Query query = em.createQuery(queryString);
        if (columns.size()>0) {
            for (Map.Entry<String, Object> entry : columns.entrySet())
            {
                query.setParameter(entry.getKey(),entry.getValue());
            }
        }
        return query.getResultList();

    }

}
