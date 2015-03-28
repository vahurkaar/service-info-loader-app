package ee.example.service;

import ee.example.exception.BusinessException;
import ee.example.model.ServiceInfo;

/**
 * Provides an interface to fetch service info from an external resource.
 *
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public interface ServiceInfoService {

    /**
     * Fetches an instance of service info from a remote resource.
     *
     * @param queryIndex index, which is used to make queries to different remote service endpoints.
     */
    ServiceInfo getRemoteServiceInfo(Integer queryIndex) throws BusinessException;

}
