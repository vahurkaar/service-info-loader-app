package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoXlServiceEndTimeConvertionRuleTest {

    private ServiceInfoConvertionRule rule = new ServiceInfoXlServiceEndTimeConvertionRule(40, 4);

    @Test
    public void testValidValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII20111111215900001200");

        Assert.assertEquals("12:00", serviceInfo.getXlServiceEndTime());
    }

    @Test
    public void testEmptyValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII2011111121590000    ");

        Assert.assertNull(serviceInfo.getXlServiceEndTime());
    }

}