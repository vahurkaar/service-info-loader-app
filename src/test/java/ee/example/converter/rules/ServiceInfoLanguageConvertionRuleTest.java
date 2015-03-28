package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoLanguageConvertionRuleTest {

    private ServiceInfoConvertionRule rule = new ServiceInfoLanguageConvertionRule(22, 1);

    @Test
    public void testEnglish() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII");

        Assert.assertEquals("I", serviceInfo.getLanguage());
    }

    @Test
    public void testEstonian() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JEI");

        Assert.assertEquals("E", serviceInfo.getLanguage());
    }

    @Test
    public void testEmptyValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          J I");

        Assert.assertNull(serviceInfo.getLanguage());
    }
}