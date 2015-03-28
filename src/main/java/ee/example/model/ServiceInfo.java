package ee.example.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ee.example.util.LocaleAwareDateSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service info data object
 *
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public class ServiceInfo implements Serializable {

    private Integer index;
    private String status;
    private String contactPhone;
    private String language;
    @JsonSerialize(using = LocaleAwareDateSerializer.class)
    private Date serviceEndDate;
    private boolean activeXlService;
    private String xlServiceLanguage;
    private String xlServiceStartTime;
    private String xlServiceEndTime;
    private boolean overrideListUsed;
    private List<Contact> overrideList;


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(Date serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public boolean isActiveXlService() {
        return activeXlService;
    }

    public void setActiveXlService(boolean activeXlService) {
        this.activeXlService = activeXlService;
    }

    public String getXlServiceLanguage() {
        return xlServiceLanguage;
    }

    public void setXlServiceLanguage(String xlServiceLanguage) {
        this.xlServiceLanguage = xlServiceLanguage;
    }

    public String getXlServiceStartTime() {
        return xlServiceStartTime;
    }

    public void setXlServiceStartTime(String xlServiceStartTime) {
        this.xlServiceStartTime = xlServiceStartTime;
    }

    public String getXlServiceEndTime() {
        return xlServiceEndTime;
    }

    public void setXlServiceEndTime(String xlServiceEndTime) {
        this.xlServiceEndTime = xlServiceEndTime;
    }

    public boolean isOverrideListUsed() {
        return overrideListUsed;
    }

    public void setOverrideListUsed(boolean overrideListUsed) {
        this.overrideListUsed = overrideListUsed;
    }

    public List<Contact> getOverrideList() {
        if (overrideList == null) {
            overrideList = new ArrayList<Contact>();
        }

        return overrideList;
    }

}
