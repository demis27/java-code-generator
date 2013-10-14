package $defaultPackageName$.controller;

import $defaultPackageName$.domain.$entity.name$;
import $defaultPackageName$.service.$entity.name$Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
public class $entity.name$Controller {

    private final Logger logger = LoggerFactory.getLogger($entity.name$Controller.class);

    @Autowired
    @Qualifier("$entity.nameLowerFirst$Service")
    private $entity.name$Service $entity.nameLowerFirst$Service;

    @Autowired
    private View jsonView;

	public void set$entity.name$Service($entity.name$Service $entity.nameLowerFirst$Service) {
		this.$entity.nameLowerFirst$Service = $entity.nameLowerFirst$Service;
	}

    @RequestMapping(value = "/$entity.nameLowerFirst$/{id}", method = RequestMethod.GET)
    public ModelAndView get$entity.name$(@PathVariable("id") String id) {
        $entity.nameLowerFirst$Service.get(id);
        return new ModelAndView(jsonView, "data", new $entity.name$());
    }

}