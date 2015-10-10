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

    @Modifying
    @Transactional
    @Query(name = UserMeal.DELETE)
    int delete(@Param("id")Integer id, @Param("userId")Integer userId);

    @Query(name = UserMeal.ALL_SORTED)
    List<UserMeal> findAll(@Param("userId")Integer userId);

    @Query(name = UserMeal.GET_BETWEEN)
    List<UserMeal> findAllBetween(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate,
                                  @Param("userId")Integer userId);

    List<UserMeal> findAll(Specification<UserMeal> spec, Sort sort); //Для примера

    List<UserMeal> findAllByUserIdOrderByDateTimeDesc(Integer userId); //Для примера

    UserMeal findOne(Specification<UserMeal> spec); //Для примера
}
