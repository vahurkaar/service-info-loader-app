package ee.example.converter;

import ee.example.configuration.AppConfiguration;
import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.InputStream;
import java.text.SimpleDateFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {AppConfiguration.class})
@WebAppConfiguration
public class ServiceInfoConverterTest {

    @Autowired
    private ServiceInfoConverter serviceInfoConverter;


    @Test
    public void testConvert() throws Exception {
        InputStream inputStream = getClass().getResource("/data1.txt").openStream();

        ServiceInfo serviceInfo = serviceInfoConverter.convert(inputStream);

        Assert.assertEquals("A", serviceInfo.getStatus());
        Assert.assertEquals("0551234569", serviceInfo.getContactPhone());
        Assert.assertTrue(serviceInfo.isActiveXlService());
        Assert.assertEquals("I", serviceInfo.getLanguage());
        Assert.assertEquals("E", serviceInfo.getXlServiceLanguage());
        Assert.assertEquals("2011.10.23 23:59", new SimpleDateFormat("yyyy.MM.dd HH:mm").format(serviceInfo.getServiceEndDate()));
        Assert.assertEquals("08:00", serviceInfo.getXlServiceStartTime());
        Assert.assertEquals("12:00", serviceInfo.getXlServiceEndTime());
        Assert.assertTrue(serviceInfo.isOverrideListUsed());
        Assert.assertEquals(2, serviceInfo.getOverrideList().size());
        Assert.assertEquals("Jaan Juurikas", serviceInfo.getOverrideList().get(0).getName());
        Assert.assertEquals("0551111111", serviceInfo.getOverrideList().get(0).getPhoneNumber());
        Assert.assertEquals("Peeter", serviceInfo.getOverrideList().get(1).getName());
        Assert.assertEquals("0509999999", serviceInfo.getOverrideList().get(1).getPhoneNumber());
    }
}