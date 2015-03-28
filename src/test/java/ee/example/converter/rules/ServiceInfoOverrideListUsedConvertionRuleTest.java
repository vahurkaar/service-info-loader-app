package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoOverrideListUsedConvertionRuleTest {

    private ServiceInfoConvertionRule rule = new ServiceInfoOverrideListUsedConvertionRule(44, 1);

    @Test
    public void testOnKasutuses() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555          JII20111111215900001200K");

        Assert.assertTrue(serviceInfo.isOverrideListUsed());
    }

    @Test
    public void testEiOleKasutuses() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555          JII20111111215900001200E");

        Assert.assertFalse(serviceInfo.isOverrideListUsed());
    }

    @Test
    public void testOther() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555          JII20111111215900001200W");

        Assert.assertFalse(serviceInfo.isOverrideListUsed());
    }

    @Test
    public void testEmpty() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555          JII20111111215900001200 ");

        Assert.assertFalse(serviceInfo.isOverrideListUsed());
    }

}