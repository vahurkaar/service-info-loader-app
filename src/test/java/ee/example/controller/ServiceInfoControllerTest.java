package ee.example.controller;

import ee.example.configuration.AppConfiguration;
import ee.example.exception.BusinessException;
import ee.example.model.Contact;
import ee.example.model.ServiceInfo;
import ee.example.service.ServiceInfoCacheService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class,
        classes = {AppConfiguration.class})
@WebAppConfiguration
public class ServiceInfoControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ServiceInfoController controller;

    @Mock
    private ServiceInfoCacheService serviceInfoCacheService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        controller.setServiceInfoCacheService(serviceInfoCacheService);
    }

    @Test
    public void testFetchServiceInfoSuccess() throws Exception {
        when(serviceInfoCacheService.getCachedServiceInfo()).thenReturn(prepareServiceInfo());

        mockMvc.perform(get("/getServiceInfo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.index", equalTo(1)))
                .andExpect(jsonPath("$.status", is("status")))
                .andExpect(jsonPath("$.contactPhone", is("phone")))
                .andExpect(jsonPath("$.activeXlService", equalTo(true)))
                .andExpect(jsonPath("$.language", is("lang")))
                .andExpect(jsonPath("$.xlServiceLanguage", is("lang1")))
                .andExpect(jsonPath("$.serviceEndDate", equalTo("neljapäev, 10. oktoober 2013. a 23:23")))
                .andExpect(jsonPath("$.xlServiceStartTime", is("10:00")))
                .andExpect(jsonPath("$.xlServiceEndTime", is("10:11")))
                .andExpect(jsonPath("$.overrideListUsed", equalTo(true)))
                .andExpect(jsonPath("$.overrideList[0].name", is("name")))
                .andExpect(jsonPath("$.overrideList[0].phoneNumber", is("number")));

        verify(serviceInfoCacheService, times(1)).getCachedServiceInfo();
    }

    @Test
    public void testFetchServiceInfoWithAlternativeLocale() throws Exception {
        when(serviceInfoCacheService.getCachedServiceInfo()).thenReturn(prepareServiceInfo());

        mockMvc.perform(get("/getServiceInfo?lang=en"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.serviceEndDate", equalTo("October 10, 2013 11:23 PM")));

        verify(serviceInfoCacheService, times(1)).getCachedServiceInfo();
    }

    @Test
    public void testFetchServiceInfoWithoutOverrideList() throws Exception {
        ServiceInfo serviceInfo = prepareServiceInfo();
        serviceInfo.setOverrideListUsed(false);
        serviceInfo.getOverrideList().clear();
        when(serviceInfoCacheService.getCachedServiceInfo()).thenReturn(serviceInfo);

        mockMvc.perform(get("/getServiceInfo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.overrideListUsed", equalTo(false)))
                .andExpect(jsonPath("$.overrideList", IsEmptyCollection.empty()));

        verify(serviceInfoCacheService, times(1)).getCachedServiceInfo();
    }

    @Test
    public void testFetchServiceInfoWithEmptyValues() throws Exception {
        ServiceInfo serviceInfo = prepareServiceInfo();
        serviceInfo.setXlServiceLanguage(null);
        when(serviceInfoCacheService.getCachedServiceInfo()).thenReturn(serviceInfo);

        mockMvc.perform(get("/getServiceInfo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.xlServiceLanguage", CoreMatchers.nullValue()));

        verify(serviceInfoCacheService, times(1)).getCachedServiceInfo();
    }

    @Test
    public void testFetchServiceInfoWhenExceptionIsThrown() throws Exception {
        when(serviceInfoCacheService.getCachedServiceInfo()).thenThrow(new BusinessException("Error message!"));

        mockMvc.perform(get("/getServiceInfo"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.errorMessage", is("Service info is not available")))
                .andExpect(jsonPath("$.description", is("Error message!")));

        verify(serviceInfoCacheService, times(1)).getCachedServiceInfo();
    }


    private ServiceInfo prepareServiceInfo() throws ParseException {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setIndex(1);
        serviceInfo.setStatus("status");
        serviceInfo.setContactPhone("phone");
        serviceInfo.setActiveXlService(true);
        serviceInfo.setLanguage("lang");
        serviceInfo.setXlServiceLanguage("lang1");
        serviceInfo.setServiceEndDate(new SimpleDateFormat("yyyy.MM.dd HH:mm").parse("2013.10.10 23:23"));
        serviceInfo.setXlServiceStartTime("10:00");
        serviceInfo.setXlServiceEndTime("10:11");
        serviceInfo.setOverrideListUsed(true);

        Contact contact = new Contact();
        contact.setName("name");
        contact.setPhoneNumber("number");
        serviceInfo.getOverrideList().add(contact);

        return serviceInfo;
    }
}