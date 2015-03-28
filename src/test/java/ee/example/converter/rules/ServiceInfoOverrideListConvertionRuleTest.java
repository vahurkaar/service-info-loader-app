package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoOverrideListConvertionRuleTest {

    // new ServiceInfoOverrideListConvertionRule(startIndex, nameLength, phoneNumberLength, size)
    private ServiceInfoConvertionRule rule = new ServiceInfoOverrideListConvertionRule(45, 20, 15, 8);

    @Test
    public void testMultipleContacts() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555          JII20111111215900001200K0552212211     0506669999     0545654321                                                                                Rein Ratas          Mati Ratas                                                                                                                                  ");

        Assert.assertEquals(3, serviceInfo.getOverrideList().size());
        Assert.assertEquals("Rein Ratas", serviceInfo.getOverrideList().get(0).getName());
        Assert.assertEquals("0552212211", serviceInfo.getOverrideList().get(0).getPhoneNumber());
        Assert.assertEquals("Mati Ratas", serviceInfo.getOverrideList().get(1).getName());
        Assert.assertEquals("0506669999", serviceInfo.getOverrideList().get(1).getPhoneNumber());
        Assert.assertNull(serviceInfo.getOverrideList().get(2).getName());
        Assert.assertEquals("0545654321", serviceInfo.getOverrideList().get(2).getPhoneNumber());
    }

    @Test
    public void testNoContacts() throws Exception {
        ServiceInfo serviceInfo = new ServiceInfo();

        rule.applyRule(serviceInfo, "A0551234555          JII20111111215900001200K                                                                                                                                                                                                                                                                                        ");

        Assert.assertEquals(0, serviceInfo.getOverrideList().size());
    }
}