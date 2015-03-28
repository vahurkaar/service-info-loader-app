package ee.example.service;

import ee.example.converter.ServiceInfoConverter;
import ee.example.exception.BusinessException;
import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ServiceInfoServiceImplTest {

    @InjectMocks
    private ServiceInfoServiceImpl serviceInfoService = new ServiceInfoServiceImpl();

    @Mock
    private ServiceInfoConverter serviceInfoConverter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        serviceInfoService.setServiceInfoFileUrl("http://www.google.com");
    }

    @Test
    public void testGetRemoteServiceInfo() throws Exception {
        when(serviceInfoConverter.convert(any(InputStream.class)))
                .thenReturn(new ServiceInfo());

        ServiceInfo serviceInfo = serviceInfoService.getRemoteServiceInfo(1);
        Assert.assertNotNull(serviceInfo);
        Assert.assertEquals(new Integer(1), serviceInfo.getIndex());
    }

    @Test
    public void testGetRemoteServiceInfoWithMalformedUrl() throws Exception {
        serviceInfoService.setServiceInfoFileUrl("random");

        try {
            serviceInfoService.getRemoteServiceInfo(1);
        } catch (BusinessException e) {
            Assert.assertEquals("The URL 'random' is not in the proper format!", e.getMessage());
            return;
        }

        Assert.fail("Service url was malformed! An exception should have been thrown!");
    }

    @Test
    public void testGetRemoteServiceInfoWithInvalidFile() throws Exception {
        serviceInfoService.setServiceInfoFileUrl("http://people.proekspert.ee/ak/123123123xcv.txt");

        try {
            serviceInfoService.getRemoteServiceInfo(1);
        } catch (BusinessException e) {
            Assert.assertEquals("Failed to fetch service info from remote file 'http://people.proekspert.ee/ak/123123123xcv.txt'", e.getMessage());
            return;
        }

        Assert.fail("The file should not have been accessible! An exception should have been thrown!");
    }

    @Test
    public void testGetRemoteServiceWithEmptyRemoteFileUrl() throws Exception {
        serviceInfoService.setServiceInfoFileUrl(null);

        try {
            serviceInfoService.getRemoteServiceInfo(1);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Remote service info url must not be null!", e.getMessage());
            return;
        }

        Assert.fail("Remote service info was null! An exception should have been thrown!");
    }
}