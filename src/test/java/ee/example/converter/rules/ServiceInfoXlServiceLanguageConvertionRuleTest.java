package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoXlServiceLanguageConvertionRuleTest {

    private ServiceInfoConvertionRule rule = new ServiceInfoXlServiceLanguageConvertionRule(23, 1);

    @Test
    public void testEnglish() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JII");

        Assert.assertEquals("I", serviceInfo.getXlServiceLanguage());
    }

    @Test
    public void testEstonian() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JIE");

        Assert.assertEquals("E", serviceInfo.getXlServiceLanguage());
    }

    @Test
    public void testEmptyValue() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();
        rule.applyRule(serviceInfo, "A0551234555          JE ");

        Assert.assertNull(serviceInfo.getXlServiceLanguage());
    }

}