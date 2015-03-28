package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoXlServiceStartTimeConvertionRuleTest {

    private ServiceInfoConvertionRule rule = new ServiceInfoXlServiceStartTimeConvertionRule(36, 4);

    @Test
    public void testValidValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII20111111215900001200");

        Assert.assertEquals("00:00", serviceInfo.getXlServiceStartTime());
    }

    @Test
    public void testEmptyValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII201111112159    1200");

        Assert.assertNull(serviceInfo.getXlServiceStartTime());
    }

}