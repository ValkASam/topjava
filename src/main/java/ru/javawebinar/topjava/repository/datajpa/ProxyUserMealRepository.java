package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by Valk on 09.10.15.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer>{
    @Override
    @Transactional
    UserMeal save(UserMeal userMeal);

    @Query(name = UserMeal.GET)
    UserMeal findOne(@Param("id")Integer id, @Param("userId")Integer userId);
    //альтернатива UserMeal findOne(Specification<UserMeal> spec);

    @Modifying
    @Transactional
    @Query(name = UserMeal.DELETE)
    int delete(@Param("id")Integer id, @Param("userId")Integer userId);
    /*альтернатива
    @Transactional
    Integer deleteByIdAndUser_id(Integer id, Integer userId);*/

    @Query(name = UserMeal.ALL_SORTED)
    List<UserMeal> findAll(@Param("userId")Integer userId);
    //альтернатива List<UserMeal> findAllByUserIdOrderByDateTimeDesc(Integer userId);
    //альтернатива List<UserMeal> findAll(Specification<UserMeal> spec, Sort sort);

    @Query(name = UserMeal.GET_BETWEEN)
    List<UserMeal> findAllBetween(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate,
                                  @Param("userId")Integer userId);


    @Query("SELECT um FROM UserMeal um JOIN FETCH um.user u WHERE u.id = :user_id ORDER BY um.dateTime DESC")
    public List<UserMeal> getAllWithUser(@Param("user_id")int userId);
    /*
    Вариант: без @Query
    //реализация в интерфейсе, чтобы оставаться в контексте текущей Session, что необходимо для
    //возможности дергать ленивый прокси-объект
    default public Collection<UserMeal> getAllWithUser(int userId) {
        List<UserMeal> meals = findAll(userId);
        if (!meals.isEmpty()) meals.iterator().next().getUser().getId(); //весь список не нужен - юзер один на всех
        return meals;
    }*/

    @Query("SELECT um FROM UserMeal um JOIN FETCH um.user u WHERE u.id = :user_id AND um.id=:id ORDER BY um.dateTime DESC")
    public UserMeal getByIdWithUser(@Param("id")int id, @Param("user_id")int userId);

}
