package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoStatusConvertionRuleTest {

    private ServiceInfoStatusConvertionRule rule = new ServiceInfoStatusConvertionRule(0, 1);

    @Test
    public void testActive() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII");

        Assert.assertEquals("A", serviceInfo.getStatus());
    }

    @Test
    public void testPassive() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "P05512345ta          JII");

        Assert.assertEquals("P", serviceInfo.getStatus());
    }

    @Test
    public void testEmptyValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, " 05512345ta          JII");

        Assert.assertNull(serviceInfo.getStatus());
    }

}