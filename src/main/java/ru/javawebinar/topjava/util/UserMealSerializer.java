package ru.javawebinar.topjava.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.javawebinar.topjava.model.UserMeal;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Created by Valk on 12.09.15.
 */
public class UserMealSerializer extends JsonSerializer<UserMeal> {
    @Override
    public void serialize(UserMeal userMeal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", userMeal.getId().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        jsonGenerator.writeStringField("dateTime", userMeal.getDateTime().format(formatter));
        jsonGenerator.writeStringField("description", userMeal.getDescription());
        jsonGenerator.writeNumberField("calories", userMeal.getCalories());
        jsonGenerator.writeEndObject();
    }
}
