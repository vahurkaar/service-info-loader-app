package ee.example.service;

import ee.example.converter.ServiceInfoConverter;
import ee.example.exception.BusinessException;
import ee.example.model.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * <p>An implementation of the service info service interface.</p>
 *
 * @author Vahur Kaar
 * @since 25.03.2015
 */
@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {

    private String serviceInfoFileUrl;
    private ServiceInfoConverter serviceInfoConverter;

    /**
     * <p>The service info is fetched from a remote location over the web.
     * The service info is initially in a string representation</p>
     *
     * <p>ABBB..CDEFFF.., where</p>
     *
     * <li>A: ‘A’ktiivne/’P’passivne (1 character)
     * <li>BBB..: telefoninumber (20 characters)
     * <li>C: XL-lisateenus, ‘J’ah/’E’i (1 character)
     * <li>D: 1. keel (1 character) E=Eesti, I=Inglise
     * <li>E: XL-lisateenuse keel (1 character)
     * <li>FFF...: teenuse lõpukuupäev (8 characters, formaat YYYYMMDD)
     * <li>GGG...: teenuse lõpu kellaaeg (ttmm) (4 characters)
     * <li>HHH...: XL teenuse aktiveerumisaeg 1 (ttmm) (4 characters)
     * <li>III...: XL teenuse lõpuaeg (ttmm) (4 characters)
     * <li>J: override nimekiri;‘K’asutuses/’E’i ole kasutuses (1 character)
     * <li>8*KKK..: telefoninumbrid (15 characters * 8 = 120 characters)
     * <li>8*LLL..: nimed (20 characters * 8 = 160 characters)
     *
     * <p>The string format is converted to the internal {@link ee.example.model.ServiceInfo} data strtructure.</p>
     *
     * @param queryIndex index, which is used to make queries to different remote service endpoints.
     * @see ee.example.converter.ServiceInfoConverter
     * @return service info data object
     * @throws BusinessException
     */
    @Override
    public ServiceInfo getRemoteServiceInfo(Integer queryIndex) throws BusinessException {
        Assert.notNull(serviceInfoFileUrl, "Remote service info url must not be null!");

        InputStream serviceInfoInputStream = getRemoteFileInputStream(queryIndex);
        return prepareServiceInfo(queryIndex, serviceInfoInputStream);
    }

    private ServiceInfo prepareServiceInfo(Integer queryIndex, InputStream serviceInfoInputStream) {
        ServiceInfo serviceInfo = serviceInfoConverter.convert(serviceInfoInputStream);
        serviceInfo.setIndex(queryIndex);
        return serviceInfo;
    }

    private InputStream getRemoteFileInputStream(Integer queryIndex) throws BusinessException {
        String serviceInfoUrl = MessageFormat.format(serviceInfoFileUrl, queryIndex);
        try {
            URL remoteFileUrl = new URL(serviceInfoUrl);
            return remoteFileUrl.openStream();
        } catch (MalformedURLException e) {
            throw new BusinessException(String.format("The URL '%s' is not in the proper format!", serviceInfoUrl), e);
        } catch (IOException e) {
            throw new BusinessException(String.format("Failed to fetch service info from remote file '%s'", serviceInfoUrl), e);
        }
    }


    /**
     * The location of the remote service, where the service info is fetched
     */
    @Value("${remote.serviceInfo.url}")
    public void setServiceInfoFileUrl(String serviceInfoFileUrl) {
        this.serviceInfoFileUrl = serviceInfoFileUrl;
    }

    /**
     * A converter, which is used to convert the fetched service info into the
     * inner {@link ee.example.model.ServiceInfo} format
     * @see ee.example.converter.ServiceInfoConverter
     */
    @Autowired
    public void setServiceInfoConverter(ServiceInfoConverter serviceInfoConverter) {
        this.serviceInfoConverter = serviceInfoConverter;
    }
}
