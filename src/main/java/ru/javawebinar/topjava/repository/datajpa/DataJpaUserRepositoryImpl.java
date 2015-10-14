package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */

@Repository
public class DataJpaUserRepositoryImpl implements UserRepository {

    @Autowired
    private ProxyUserRepository proxy;

    @Override
    public User save(User user) {
        return proxy.save(user);
    }

    @Override
    public boolean delete(int id) {
        return proxy.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return proxy.findOne(id);
    }

    @Override
    public User getByEmail(String email) {
        return proxy.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return proxy.findAll(ProxyUserRepository.SORT_NAME_EMAIL);
    }

    //тут, в отличие от реализации для jpa, не можем дергать ленивый прокси-объект
    // (после запроса в proxy и возврата сюда выйдем из контекста сессии) - поэтому уносим в proxy
    @Override
    public Collection<User> getAllWithMeals() {
        return proxy.getAllWithMeals();
    }

    @Override
    public User getWithMeals(int id) {
        return proxy.getWithMeals(id);
    }

    /*
    updateLazy работает без учета аннотаций, соответсвенно, при включенных cascade = CascadeType.ALL и orphanRemoval = true
    можно сохранять упрощенный объект User user (без подвязанного списка еды).
    Дополнительно в комментах для UserServiceTest_Hsqldb_Datajpa.testUpdate()
     */
    @Override
    public User updateLazy(User user) {
        if (proxy.save(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.getRegistered(),
                user.getCaloriesPerDay()) == 0) return null;
        return user;
    }
}
