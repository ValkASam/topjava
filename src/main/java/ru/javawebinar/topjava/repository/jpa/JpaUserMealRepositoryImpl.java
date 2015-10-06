package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            /*userMeal.setUser(new User());
            userMeal.getUser().setId(userId);*/
            User ref = em.getReference(User.class, userId); //подумал и согласен, что такой вариант кошерней
            userMeal.setUser(ref);
            em.persist(userMeal);
            return userMeal;
        } else {
            /*if (em.createNamedQuery(UserMeal.UPDATE) //традиционный вариант - такое уже было. Испытаем новенькое
                    .setParameter("dateTime", userMeal.getDateTime())
                    .setParameter("description", userMeal.getDescription())
                    .setParameter("calories", userMeal.getCalories())
                    .setParameter("id", userMeal.getId())
                    .setParameter("user_id", userId)
                    .executeUpdate()
                    == 0) return null;
            return userMeal;
            */
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaUpdate<UserMeal> query = builder.createCriteriaUpdate(UserMeal.class);
            Root<UserMeal> meal = query.from(UserMeal.class);
            query.set("dateTime", userMeal.getDateTime());
            query.set("description", userMeal.getDescription());
            query.set("calories", userMeal.getCalories());
            Predicate predicate = builder.and(
                    builder.equal(meal.get("id"), userMeal.getId()),
                    builder.equal(meal.get("user"), userId)
            );
            query.where(builder.and(predicate));
            if (em.createQuery(query).executeUpdate()==0) return null;
            return userMeal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        /*return em.createNamedQuery(UserMeal.DELETE, UserMeal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;*/
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaDelete<UserMeal> query = builder.createCriteriaDelete(UserMeal.class);
        Root<UserMeal> meal = query.from(UserMeal.class);
        Predicate predicate = builder.and(
                builder.equal(meal.get("id"), id),
                builder.equal(meal.get("user"), userId)
        );
        query.where(builder.and(predicate));
        return em.createQuery(query).executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        try {
            return em.createNamedQuery(UserMeal.BY_ID, UserMeal.class)
                    .setParameter("id", id)
                    .setParameter("user_id", userId)
                    .getSingleResult();
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.BETWEEN, UserMeal.class)
                .setParameter("user_id", userId)
                .setParameter("start_date", startDate)
                .setParameter("end_date", endDate)
                .getResultList();
    }
}