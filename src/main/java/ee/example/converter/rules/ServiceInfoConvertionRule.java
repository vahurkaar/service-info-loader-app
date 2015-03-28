package ee.example.converter.rules;

import ee.example.model.ServiceInfo;

/**
 * <p>An abstract implementation of a conversion rule, which is used to populate a given
 * {@link ee.example.model.ServiceInfo} instance.</p>
 *
 * @see ee.example.converter.ServiceInfoConverter
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public abstract class ServiceInfoConvertionRule {

    private Integer valueStartPosition;
    private Integer valueLength;

    public ServiceInfoConvertionRule(Integer valueStartPosition, Integer valueLength) {
        this.valueStartPosition = valueStartPosition;
        this.valueLength = valueLength;
    }

    /**
     * Takes the service info string representation, parses the appropriate string value based on
     * the start position and length input parameters and populates the appropriate
     * service info object field.
     *
     * @param serviceInfo service object instance, which is populated
     * @param serviceInfoString the service info string representation,
     *                          which is going to be read for the appropriate value
     */
    public void applyRule(ServiceInfo serviceInfo, String serviceInfoString) {
        String value = readValue(serviceInfoString);
        populateField(serviceInfo, value);
    }


    protected abstract void populateField(ServiceInfo serviceInfo, String value);

    /**
     * Reads the appropriate string value from the service info string representation based
     * on the start position and length input parameters.
     *
     * @param serviceInfoString the service info string representation, which is going to be read for the appropriate value
     * @return A string value, which is going to be assigned to the service info data object.
     * In case one of the parameters (start position or length) is out of bounds from the input string, then {@code null} is returned.
     */
    protected String readValue(String serviceInfoString) {
        if (valueStartPosition < serviceInfoString.length() &&
                valueStartPosition + valueLength <= serviceInfoString.length()) {
            return serviceInfoString.substring(valueStartPosition, valueStartPosition + valueLength);
        }

        return null;
    }

}
