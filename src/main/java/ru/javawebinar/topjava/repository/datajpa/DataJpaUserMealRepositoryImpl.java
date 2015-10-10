package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository {

    @Autowired
    ProxyUserMealRepository proxy;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            User user = new User();
            user.setId(userId);
            userMeal.setUser(user);
            return proxy.save(userMeal);
        } else {
            UserMeal meal =  proxy.findOne(userMeal.getId(), userId);
            if (meal == null) return null;
            userMeal.setUser(meal.getUser());
            return proxy.save(userMeal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.delete(id, userId) != 0;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        /*
        вариант
        return proxy.findAllByUserIdOrderByDateTimeDesc(userId);

        будет работать и так (если на вход надо дать объект)
        return proxy.findAllByUserOrderByDateTimeDesc(user);
        при, соответсвенно:
        List<UserMeal> findAllByUserOrderByDateTimeDesc(User user);

        МИНУС: запрос будет строиться через left join user
        (все самогенерирующиеся запросы стремятся к завязыванию таблиц, не моможет и
        findAllByUser_IdOrderByDateTimeDesc(userId))
        */

        //более экономный вриант (без left join user)
        //(метод хотя и не родной, но запрос не самогенерирующися, а указан через @Query, в котором нет связи таблиц)
        return proxy.findAll(userId);


        /*аналогично (без left join user), но излишне навороченный:
        return proxy.findAll(new Specification<UserMeal>() {
            @Override
            public Predicate toPredicate(Root<UserMeal> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("user"), userId);
            }
        }
        , new Sort(Sort.Direction.DESC, "dateTime"));
        */

    }

    @Override
    public UserMeal get(int id, int userId) {
        /*
        вариант
        return proxy.findOneByIdAndUserId(id, userId);
        МИНУС: запрос будет строиться через left join user
        */

        //более экономный вриант (без left join user)
        return proxy.findOne(id, userId);

        /*аналогично (без left join user), но излишне навороченный:
        return proxy.findOne(new Specification<UserMeal>() {
            @Override
            public Predicate toPredicate(Root<UserMeal> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), id),
                        criteriaBuilder.equal(root.get("user"), userId)
                );
            }
        });
        */
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.findAllBetween(startDate, endDate, userId);
    }
}
