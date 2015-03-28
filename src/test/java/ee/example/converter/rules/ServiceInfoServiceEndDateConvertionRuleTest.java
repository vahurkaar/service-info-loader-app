package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class ServiceInfoServiceEndDateConvertionRuleTest {

    private ServiceInfoConvertionRule rule = new ServiceInfoServiceEndDateConvertionRule(24, 12);

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    @Test
    public void testValidDate() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII201412122123A");

        Assert.assertEquals(dateFormat.parse("2014.12.12 21:23"), serviceInfo.getServiceEndDate());
    }

    @Test
    public void testInvalidDate() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII201412121193A");

        Assert.assertNull(serviceInfo.getServiceEndDate());
    }

    @Test
    public void testEmptyValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII            A");

        Assert.assertNull(serviceInfo.getServiceEndDate());

    }
}