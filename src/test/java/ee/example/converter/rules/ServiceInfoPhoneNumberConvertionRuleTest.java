package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoPhoneNumberConvertionRuleTest {

    private ServiceInfoConvertionRule rule = new ServiceInfoPhoneNumberConvertionRule(1, 20);

    @Test
    public void testConvertValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII");

        Assert.assertEquals("0551234555", serviceInfo.getContactPhone());
    }

    @Test
    public void testNonNumber() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A05512345ta          JII");

        Assert.assertEquals("05512345ta", serviceInfo.getContactPhone());
    }

    @Test
    public void testEmptyValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A                    JII");

        Assert.assertNull(serviceInfo.getContactPhone());
    }
}