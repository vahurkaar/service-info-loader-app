package ee.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller, which redirects the user to the index page
 *
 * @author Vahur Kaar
 * @since 28.03.2015
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public RedirectView index() {
        return new RedirectView("/index.html");
    }

}
