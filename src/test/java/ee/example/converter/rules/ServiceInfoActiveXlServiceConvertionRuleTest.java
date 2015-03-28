package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoActiveXlServiceConvertionRuleTest {

    private ServiceInfoConvertionRule rule = new ServiceInfoActiveXlServiceConvertionRule(21, 1);

    @Test
    public void testJah() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555          JEI");

        Assert.assertTrue(serviceInfo.isActiveXlService());
    }

    @Test
    public void testEi() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555          EEI");

        Assert.assertFalse(serviceInfo.isActiveXlService());
    }

    @Test
    public void testOther() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555          WEI");

        Assert.assertFalse(serviceInfo.isActiveXlService());
    }

    @Test
    public void testEmpty() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555           EI");

        Assert.assertFalse(serviceInfo.isActiveXlService());
    }
}