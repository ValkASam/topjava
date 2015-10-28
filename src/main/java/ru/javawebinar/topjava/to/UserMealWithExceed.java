package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
//варианты отключения сериализации поля + к
//@JsonIgnoreProperties(value = { "exceed" })
//@JsonFilter("filterUserMealWith")
public class UserMealWithExceed {
    protected final Integer id;

    protected final LocalDateTime dateTime;

    protected final String description;

    protected final int calories;

    protected final boolean exceed;

    public UserMealWithExceed() {
        this(null, null, 0, false);
    }

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this(null, dateTime, description, calories, exceed);
    }

    public UserMealWithExceed(Integer id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public Integer getId() {
        return id;
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

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMealWithExceed that = (UserMealWithExceed) o;

        if (calories != that.calories) return false;
        if (exceed != that.exceed) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + calories;
        result = 31 * result + (exceed ? 1 : 0);
        return result;
    }
}
