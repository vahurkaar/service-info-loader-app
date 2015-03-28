package ee.example.converter.rules;

import ee.example.model.ServiceInfo;
import org.junit.Assert;
import org.junit.Test;

public class ServiceInfoConvertionRuleTest {

    @Test
    public void testReadValueWhenStartIndexExceedsLength() throws Exception {
        ServiceInfoConvertionRule rule = new MockServiceInfoConvertionRule(5, 10);
        String value = rule.readValue("12312");
        Assert.assertNull(value);
    }

    @Test
    public void testReadValueWhenReadableValueExceedsInputLength() throws Exception {
        ServiceInfoConvertionRule rule = new MockServiceInfoConvertionRule(1, 5);
        String value = rule.readValue("12312");
        Assert.assertNull(value);
    }

    class MockServiceInfoConvertionRule extends ServiceInfoConvertionRule {

        public MockServiceInfoConvertionRule(Integer valueStartPosition, Integer valueLength) {
            super(valueStartPosition, valueLength);
        }

        @Override
        protected void populateField(ServiceInfo serviceInfo, String value) {
            // do nothing...
        }
    }
}