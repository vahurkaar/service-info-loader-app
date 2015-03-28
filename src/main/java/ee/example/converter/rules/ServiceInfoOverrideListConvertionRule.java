package ee.example.converter.rules;

import ee.example.model.Contact;
import ee.example.model.ServiceInfo;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Implementation of the {@link ee.example.converter.rules.ServiceInfoConvertionRule} abstract implementation.
 * <p>Populates the {@link ee.example.model.ServiceInfo#overrideList} field.</p>
 *
 * @see ee.example.converter.rules.ServiceInfoConvertionRule
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public class ServiceInfoOverrideListConvertionRule extends ServiceInfoConvertionRule {

    private Integer nameLength;
    private Integer phoneNumberLength;
    private Integer listSize;

    public ServiceInfoOverrideListConvertionRule(Integer valueStartPosition, Integer nameLength,
                                                 Integer phoneNumberLength, Integer listSize) {
        super(valueStartPosition, (nameLength + phoneNumberLength) * listSize);
        this.nameLength = nameLength;
        this.phoneNumberLength = phoneNumberLength;
        this.listSize = listSize;
    }

    @Override
    protected String readValue(String serviceInfoString) {
        return super.readValue(serviceInfoString);
    }

    @Override
    protected void populateField(ServiceInfo serviceInfo, String value) {
        if (StringUtils.isNotBlank(value)) {
            List<Contact> contacts = serviceInfo.getOverrideList();

            for (int i = 0; i < listSize; i++) {
                int allPhoneNumbersLength = listSize * phoneNumberLength;
                String name = value.substring(allPhoneNumbersLength + i * nameLength, allPhoneNumbersLength + (i + 1) * nameLength);
                String phoneNumber = value.substring(i * phoneNumberLength, (i + 1) * phoneNumberLength);

                if (StringUtils.isNotBlank(phoneNumber) || StringUtils.isNotBlank(name)) {
                    Contact contact = initializeContact(name, phoneNumber);
                    contacts.add(contact);
                }
            }
        }
    }

    private Contact initializeContact(String name, String phoneNumber) {
        Contact contact = new Contact();
        contact.setName(StringUtils.defaultIfEmpty(StringUtils.trim(name), null));
        contact.setPhoneNumber(StringUtils.defaultIfEmpty(StringUtils.trim(phoneNumber), null));

        return contact;
    }
}
