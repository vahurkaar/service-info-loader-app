package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the {@link ee.example.converter.rules.ServiceInfoConvertionRule} abstract implementation.
 * <p>Populates the {@link ee.example.model.ServiceInfo#activeXlService} field.</p>
 *
 * @see ee.example.converter.rules.ServiceInfoConvertionRule
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public class ServiceInfoActiveXlServiceConvertionRule extends ServiceInfoConvertionRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInfoActiveXlServiceConvertionRule.class);

    public ServiceInfoActiveXlServiceConvertionRule(Integer valueStartPosition, Integer valueLength) {
        super(valueStartPosition, valueLength);
    }

    @Override
    protected void populateField(ServiceInfo serviceInfo, String value) {
        if (StringUtils.isNotBlank(value)) {
            if (value.equals("J")) {
                serviceInfo.setActiveXlService(true);
            } else if (value.equals("E")) {
                serviceInfo.setActiveXlService(false);
            } else {
                LOGGER.warn("Unsupported XL service activation indicator! [Allowed values are 'J' and 'E']");
            }
        }
    }
}
