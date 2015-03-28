package ee.example.service;

import ee.example.model.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * <p>An implementation of the service info cache service.</p>
 * </p>The service info is cached in an internal cache instance, which is thread-safe. The internal cache is
 * periodically updated by an internal process. The interval is read from a configured properties file.</p>
 *
 * @author Vahur Kaar
 * @since 27.03.2015
 */
@Service
public class ServiceInfoCacheServiceImpl implements ServiceInfoCacheService {

    private static final String CACHE_KEY = "serviceInfo";
    private static final Integer QUERY_INITIAL_INDEX = 1;
    private static final Integer QUERY_MAX_INDEX = 9;

    private final Logger logger = LoggerFactory.getLogger(ServiceInfoServiceImpl.class);

    private Cache serviceInfoCache = new ConcurrentMapCache("ServiceInfoCache");
    private Integer serviceInfoQueryIndex = QUERY_INITIAL_INDEX;
    private ServiceInfoService serviceInfoService;


    /**
     * Spring's scheduled process method, which fetches a service info instance from the legacy system and
     * stores it in the internal cache. It also holds an index state variable,
     * which is used to fetch different service info data at different given times.
     * The index is updated after each iteration.
     *
     * @see ee.example.service.ServiceInfoService#getRemoteServiceInfo(Integer)
     */
    @Scheduled(fixedDelayString = "${remote.serviceInfo.interval}")
    public void updateCacheProcess() {
        try {
            serviceInfoCache.put(CACHE_KEY, serviceInfoService.getRemoteServiceInfo(serviceInfoQueryIndex));
            logger.info(String.format("Loaded %s. service info into cache", serviceInfoQueryIndex));
        } finally {
            incrementIndex();
        }
    }

    private void incrementIndex() {
        if (serviceInfoQueryIndex == QUERY_MAX_INDEX) {
            serviceInfoQueryIndex = QUERY_INITIAL_INDEX;
            return;
        }

        serviceInfoQueryIndex++;
    }

    /**
     * Returns a cached instance of service data.
     */
    @Override
    public ServiceInfo getCachedServiceInfo() {
        if (serviceInfoCache.get(CACHE_KEY) != null) {
            return (ServiceInfo) serviceInfoCache.get(CACHE_KEY).get();
        }

        return null;
    }


    /**
     * An instance of the service info fetching service.
     */
    @Autowired
    public void setServiceInfoService(ServiceInfoService serviceInfoService) {
        this.serviceInfoService = serviceInfoService;
    }

    public Integer getServiceInfoQueryIndex() {
        return serviceInfoQueryIndex;
    }
}
