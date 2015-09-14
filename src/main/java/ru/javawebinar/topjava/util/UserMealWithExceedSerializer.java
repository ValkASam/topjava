package ru.javawebinar.topjava.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Created by Valk on 12.09.15.
 */
public class UserMealWithExceedSerializer extends JsonSerializer<UserMealWithExceed> {
    @Override
    public void serialize(UserMealWithExceed userMealWithExceed, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", userMealWithExceed.getId().toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        jsonGenerator.writeStringField("dateTime", userMealWithExceed.getDateTime().format(formatter));
        jsonGenerator.writeStringField("description", userMealWithExceed.getDescription());
        jsonGenerator.writeNumberField("calories", userMealWithExceed.getCalories());
        jsonGenerator.writeBooleanField("exceed", userMealWithExceed.isExceed());
        jsonGenerator.writeEndObject();
    }
}
