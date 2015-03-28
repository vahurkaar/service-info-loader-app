package ee.example.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * A custom implementation of Jackson's {@link com.fasterxml.jackson.databind.JsonSerializer} interface.
 * This implementation formats the date values based on the Locale, which is stored in
 * {@link org.springframework.context.i18n.LocaleContextHolder}.
 *
 * @author Vahur Kaar
 * @since 27.03.2015
 */
public class LocaleAwareDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        String formattedDate = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT,
                LocaleContextHolder.getLocale()).format(date);
        gen.writeString(formattedDate);
    }

}