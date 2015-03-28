package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of the {@link ee.example.converter.rules.ServiceInfoConvertionRule} abstract implementation.
 * <p>Populates the {@link ee.example.model.ServiceInfo#serviceEndDate} field.</p>
 *
 * @see ee.example.converter.rules.ServiceInfoConvertionRule
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public class ServiceInfoServiceEndDateConvertionRule extends ServiceInfoConvertionRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInfoServiceEndDateConvertionRule.class);
    private static final String DATE_FORMAT = "yyyyMMddHHmm";

    public ServiceInfoServiceEndDateConvertionRule(Integer valueStartPosition, Integer valueLength) {
        super(valueStartPosition, valueLength);
    }

    @Override
    protected void populateField(ServiceInfo serviceInfo, String value) {
        if (StringUtils.isNotBlank(value)) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                dateFormat.setLenient(false);

                Date dateValue = dateFormat.parse(value);
                serviceInfo.setServiceEndDate(dateValue);
            } catch (ParseException e) {
                LOGGER.warn("Failed to parse service end date value from input!", e);
            }
        }
    }
}
