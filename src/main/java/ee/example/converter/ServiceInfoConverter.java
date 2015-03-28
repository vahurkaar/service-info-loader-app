package ee.example.converter;

import ee.example.converter.rules.ServiceInfoConvertionRule;
import ee.example.exception.BusinessException;
import ee.example.model.ServiceInfo;
import org.springframework.core.convert.converter.Converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <p>A {@link Converter} implementation, which takes the binary file content from the legacy system as
 * an {@link InputStream} and converts it to the {@link ServiceInfo} Java object.</p>
 *
 * <p>The converting process envolves the use of conversion rules
 * ({@link ee.example.converter.rules.ServiceInfoConvertionRule}). Each rule is evaluated and the service
 * info object is updated and populated as a result.</p>
 *
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public class ServiceInfoConverter implements Converter<InputStream, ServiceInfo> {

    private List<ServiceInfoConvertionRule> convertionRules;


    @Override
    public ServiceInfo convert(InputStream inputStream) {
        String serviceInfoString = getServiceInfoStringFromStream(inputStream);

        ServiceInfo serviceInfo = new ServiceInfo();

        for (ServiceInfoConvertionRule rule : convertionRules) {
            rule.applyRule(serviceInfo, serviceInfoString);
        }

        return serviceInfo;
    }

    private String getServiceInfoStringFromStream(InputStream inputStream) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new BusinessException("Failed to read service info from file!", e);
        }
    }


    /**
     * A list of rules that are used to convert the input file to the service info data object.
     * @see ee.example.converter.rules.ServiceInfoConvertionRule
     */
    public void setConvertionRules(List<ServiceInfoConvertionRule> convertionRules) {
        this.convertionRules = convertionRules;
    }
}
