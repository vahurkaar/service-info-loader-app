package ee.example.controller;

import ee.example.model.Error;
import ee.example.model.ServiceInfo;
import ee.example.service.ServiceInfoCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * A REST controller, which returns the latest cached instance of service metadata info.
 *
 * @author Vahur Kaar
 * @since 25.03.2015
 */
@RestController
public class ServiceInfoController {

    private static final String NOT_AVAILABLE_MSG = "Service info is not available";

    private ServiceInfoCacheService serviceInfoCacheService;


    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleException(Exception e) {
        return new Error(NOT_AVAILABLE_MSG, e.getMessage());
    }

    @RequestMapping(value = "/getServiceInfo")
    public ServiceInfo getServiceInfo() {
        return serviceInfoCacheService.getCachedServiceInfo();
    }


    @Autowired
    public void setServiceInfoCacheService(ServiceInfoCacheService serviceInfoCacheService) {
        this.serviceInfoCacheService = serviceInfoCacheService;
    }
}
