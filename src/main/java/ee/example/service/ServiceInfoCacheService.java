package ee.example.service;

import ee.example.model.ServiceInfo;

/**
 * Provides an interface to fetch the cached instances of service info.
 *
 * @author Vahur Kaar
 * @since 27.03.2015
 */
public interface ServiceInfoCacheService {

    /**
     * Returns an instance of service info from an internal cache.
     * @see ee.example.service.ServiceInfoCacheServiceImpl
     */
    ServiceInfo getCachedServiceInfo();

}
