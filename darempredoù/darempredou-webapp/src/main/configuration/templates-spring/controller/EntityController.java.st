package $defaultPackageName$.controller;

import $defaultPackageName$.domain.$entity.name$;
import $defaultPackageName$.dto.$entity.name$DTO;
import $defaultPackageName$.dto.$entity.name$DTOConverter;
import $defaultPackageName$.service.$entity.name$Service;
import $defaultPackageName$.APIError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
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
    private $entity.name$Service service;

    @Autowired
    private View jsonView;

    @RequestMapping(value = "/rest/$entity.nameLowerFirst$/{id}", method = RequestMethod.GET)
    public ModelAndView get$entity.name$(@PathVariable("id") Integer id) {
        $entity.name$ $entity.nameLowerFirst$ = null;

        try {
            $entity.nameLowerFirst$ = service.getEager(id);
        } catch (Exception e) {
            return createErrorResponse(new APIError("INTERNAL_ERROR", "Internal error"));
        }
        if ($entity.nameLowerFirst$ == null) {
            return createErrorResponse(new APIError("RESOURCE_NOT_FOUND", "Resource #$entity.nameLowerFirst$(" + id + ") not found"));
        }
        $entity.name$DTO $entity.nameLowerFirst$DTO = $entity.name$DTOConverter.convert($entity.nameLowerFirst$);

        return new ModelAndView(jsonView, "data",$entity.nameLowerFirst$DTO);
    }

    @RequestMapping(value = "/rest/$entity.nameLowerFirst$/", method = RequestMethod.GET)
    public ModelAndView get$entity.name$Page(@DefaultValue("10") @QueryParam("page") int pageNumber,
                                               @DefaultValue("0") @QueryParam("offset") int offset) {
        $entity.name$Page page = null;

        service.getPage(pageNumber, offset);

        $entity.name$DTOPage dtoPage = $entity.name$DTOConverter.convertPage(page);

        return new ModelAndView(jsonView, "data",dtoPage);
    }

    private ModelAndView createErrorResponse(APIError error) {
        return new ModelAndView(jsonView, "error", error);
    }

    public void setService($entity.name$Service service) {
        this.service = service;
    }

    public void setJsonView(View jsonView) {
        this.jsonView = jsonView;
    }
}