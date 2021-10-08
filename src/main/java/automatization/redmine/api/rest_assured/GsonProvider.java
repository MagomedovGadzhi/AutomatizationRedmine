package automatization.redmine.api.rest_assured;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GsonProvider {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .create();

    private static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            String value = json.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

    }

    private static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            String value = src.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return new JsonPrimitive(value);
        }
    }

}
