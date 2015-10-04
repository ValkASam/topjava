package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        //@NamedQuery(name = UserMeal.ALL, query = "SELECT m FROM UserMeal m LEFT JOIN FETCH m.user") //не понял зачем (если брать по аналогии с User) делать тут JOIN - весь LAZY коту под хвост
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT m FROM UserMeal m WHERE m.user.id=:user_id ORDER BY m.dateTime DESC"),
        @NamedQuery(name = UserMeal.BETWEEN, query = "SELECT m FROM UserMeal m WHERE m.user.id=:user_id AND m.dateTime BETWEEN :start_date AND :end_date ORDER BY m.dateTime DESC"),
        @NamedQuery(name = UserMeal.BY_ID, query = "SELECT m FROM UserMeal m WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal m WHERE m.id=:id AND m.user.id=:user_id")
})
@Entity
@Table(name = "meals")
public class UserMeal extends BaseEntity {
    public static final String ALL_SORTED = "UserMeal.getAll";
    public static final String BETWEEN = "UserMeal.getBetween";
    public static final String BY_ID = "UserMeal.getById";
    public static final String DELETE = "UserMeal.delete";

    @Column(name = "date_time")
    //@Convert(converter = LocalDateTimePersistenceConverter.class) реализовано через поддержку хибернейтом 5
    protected LocalDateTime dateTime;

    @Column(name = "description")
    @Length(min = 4, max = 50)
    protected String description;

    @Column(name = "calories")
    @NotNull
    @Digits(fraction = 0, integer = 4)
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id") //явно укажем оба поля - универсальней на случай нестандартных имен полей
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public Integer getId() {
        return id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}