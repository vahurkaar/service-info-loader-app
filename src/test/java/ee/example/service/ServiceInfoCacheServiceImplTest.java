package ee.example.service;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class ServiceInfoCacheServiceImplTest {

    @InjectMocks
    private ServiceInfoCacheServiceImpl serviceInfoCacheService = new ServiceInfoCacheServiceImpl();

    @Mock
    private ServiceInfoService serviceInfoService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateCache() throws Exception {
        when(serviceInfoService.getRemoteServiceInfo(anyInt())).thenReturn(prepareServiceInfo());

        serviceInfoCacheService.updateCacheProcess();

        ServiceInfo cachedServiceInfo = serviceInfoCacheService.getCachedServiceInfo();
        Assert.assertNotNull(cachedServiceInfo);
        Assert.assertEquals("status", cachedServiceInfo.getStatus());
    }

    @Test
    public void testGetServiceInfoFromEmptyCache() throws Exception {
        ServiceInfo cachedServiceInfo = serviceInfoCacheService.getCachedServiceInfo();
        Assert.assertNull(cachedServiceInfo);
    }

    @Test
    public void testIndexGetsReset() throws Exception {
        for (int i = 0; i < 9; i++) {
            serviceInfoCacheService.updateCacheProcess();
        }

        Assert.assertEquals(new Integer(1), serviceInfoCacheService.getServiceInfoQueryIndex());
    }


    private ServiceInfo prepareServiceInfo() throws ParseException {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setStatus("status");
        return serviceInfo;
    }
}