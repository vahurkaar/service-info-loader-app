package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation of the {@link ee.example.converter.rules.ServiceInfoConvertionRule} abstract implementation.
 * <p>Populates the {@link ee.example.model.ServiceInfo#xlServiceStartTime} field.</p>
 *
 * @see ee.example.converter.rules.ServiceInfoConvertionRule
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public class ServiceInfoXlServiceStartTimeConvertionRule extends ServiceInfoConvertionRule {

    public ServiceInfoXlServiceStartTimeConvertionRule(Integer valueStartPosition, Integer valueLength) {
        super(valueStartPosition, valueLength);
    }

    @Override
    protected void populateField(ServiceInfo serviceInfo, String value) {
        if (StringUtils.isNotBlank(value)) {
            serviceInfo.setXlServiceStartTime(value.substring(0, 2) + ":" + value.substring(2));
        }
    }
}
