package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation of the {@link ee.example.converter.rules.ServiceInfoConvertionRule} abstract implementation.
 * <p>Populates the {@link ee.example.model.ServiceInfo#xlServiceLanguage} field.</p>
 *
 * @see ee.example.converter.rules.ServiceInfoConvertionRule
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public class ServiceInfoXlServiceLanguageConvertionRule extends ServiceInfoConvertionRule {

    public ServiceInfoXlServiceLanguageConvertionRule(Integer valueStartPosition, Integer valueLength) {
        super(valueStartPosition, valueLength);
    }

    @Override
    protected void populateField(ServiceInfo serviceInfo, String value) {
        if (StringUtils.isNotBlank(value)) {
            serviceInfo.setXlServiceLanguage(value);
        }
    }
}
