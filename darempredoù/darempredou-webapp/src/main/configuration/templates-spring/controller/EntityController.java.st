package $defaultPackageName$.controller;

import $defaultPackageName$.domain.$entity.name$;
import $defaultPackageName$.dto.$entity.name$DTO;
import $defaultPackageName$.dto.$entity.name$FullDTO;
import $defaultPackageName$.dto.$entity.name$DTOConverter;
import $defaultPackageName$.service.$entity.name$Service;
import $defaultPackageName$.dto.$entity.name$DTOPage;
import $defaultPackageName$.service.$entity.name$Page;

import $defaultPackageName$.APIError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletResponse;

@Controller
public class $entity.name$Controller {

    private final Logger logger = LoggerFactory.getLogger($entity.name$Controller.class);

    @Autowired
    private $entity.name$Service service;

    @Autowired
    private View jsonView;

    @RequestMapping(value = {"/rest/$entity.nameLowerFirst$/{id}", "/rest/$entity.nameLowerFirst$/{id}/"}, method = RequestMethod.GET)
    @ResponseBody
    public Object get$entity.name$(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        $entity.name$ $entity.nameLowerFirst$ = null;

        try {
            $entity.nameLowerFirst$ = service.getEager(id);
        } catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error");
        }
        if ($entity.nameLowerFirst$ == null) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new APIError("RESOURCE_NOT_FOUND", "Resource #$entity.nameLowerFirst$(" + id + ") not found");
        }
        $entity.name$DTO $entity.nameLowerFirst$DTO = $entity.name$DTOConverter.convert($entity.nameLowerFirst$);

        return $entity.nameLowerFirst$DTO;
    }

    @RequestMapping(value = {"/rest/$entity.nameLowerFirst$/", "/rest/$entity.nameLowerFirst$"}, method = RequestMethod.GET)
    @ResponseBody
    public Object get$entity.name$Page(@RequestParam(value="pageNumber", defaultValue="0") Integer pageNumber,
                                             @RequestParam(value="pageSize", defaultValue="10") Integer pageSize,
                                             HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        $entity.name$Page page = null;

        try {
            page = service.getPage(pageNumber.intValue(), pageSize.intValue());
        } catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error");
        }
        if (page == null) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new APIError("RESOURCE_NOT_FOUND", "Resources #$entity.nameLowerFirst$(" + pageNumber + ", " + pageSize + ") not found");
        }

         httpResponse.setStatus(HttpStatus.OK.value());
         return $entity.name$DTOConverter.convertPage(page);
    }

    @RequestMapping(value = {"/rest/$entity.nameLowerFirst$/{id}", "/rest/$entity.nameLowerFirst$/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete$entity.name$(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        $entity.name$ $entity.nameLowerFirst$;

        try {
            $entity.nameLowerFirst$ = service.getEager(id);
            if ($entity.nameLowerFirst$ == null) {
                httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return new APIError("RESOURCE_NOT_FOUND", "Resource #$entity.nameLowerFirst$(" + id + ") not found");
            }
            else {
                service.delete($entity.nameLowerFirst$);
                httpResponse.setStatus(HttpStatus.OK.value());
                return null;
            }
        } catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error");
        }
    }

    @RequestMapping(value = {"/rest/$entity.nameLowerFirst$/", "/rest/$entity.nameLowerFirst$"}, method = RequestMethod.POST)
    @ResponseBody
    public Object create$entity.name$(@RequestBody $entity.name$FullDTO dto, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        try {
            $entity.name$ $entity.nameLowerFirst$ = $entity.name$DTOConverter.convert(dto);
            service.save($entity.nameLowerFirst$);
            httpResponse.setStatus(HttpStatus.OK.value());
            $entity.name$DTO $entity.nameLowerFirst$DTO = $entity.name$DTOConverter.convert($entity.nameLowerFirst$);
            return $entity.nameLowerFirst$DTO;
        }
        catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return createErrorResponse(new APIError("INTERNAL_ERROR", "Internal error"));
        }
    }

    @RequestMapping(value = {"/rest/$entity.nameLowerFirst$/{id}", "/rest/$entity.nameLowerFirst$/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object update$entity.name$(@PathVariable("id") Integer id, @RequestBody $entity.name$FullDTO dto, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        $entity.name$ $entity.nameLowerFirst$ = null;

        try {
            $entity.nameLowerFirst$ = service.getEager(id);
            if ($entity.nameLowerFirst$ != null) {
                $entity.name$DTOConverter.update$entity.name$(dto, $entity.nameLowerFirst$);
                service.save($entity.nameLowerFirst$);
                httpResponse.setStatus(HttpStatus.OK.value());
                return $entity.name$DTOConverter.convert($entity.nameLowerFirst$);
            }
            else {
                httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return new APIError("RESOURCE_NOT_FOUND", "Resource #$entity.nameLowerFirst$(" + id + ") not found");
            }
        }
        catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return createErrorResponse(new APIError("INTERNAL_ERROR", "Internal error"));
        }
    }

    @RequestMapping(value = {"/rest/userGroup/{id}", "/rest/userGroup/{id}/"}, method = RequestMethod.HEAD)
    @ResponseBody
    public Object getUserGroupExist(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {
         httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

         $entity.name$ $entity.nameLowerFirst$ = null;

         try {
             $entity.nameLowerFirst$ = service.getEager(id);
         } catch (Exception e) {
             logger.error("Internal Error", e);
             httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
             return new APIError("INTERNAL_ERROR", "Internal error");
         }
         if ($entity.nameLowerFirst$ == null) {
             httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
             return new APIError("RESOURCE_NOT_FOUND", "Resource #$entity.nameLowerFirst$(" + id + ") not found");
         }

         return null;
   }

    @RequestMapping(value = {"/rest/$entity.nameLowerFirst$/", "/rest/$entity.nameLowerFirst$"}, method = RequestMethod.HEAD)
    @ResponseBody
    public Object get$entity.name$PageExist(@RequestParam(value="pageNumber", defaultValue="0") Integer pageNumber,
                                             @RequestParam(value="pageSize", defaultValue="10") Integer pageSize,
                                             HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        $entity.name$Page page = null;

        try {
            page = service.getPage(pageNumber.intValue(), pageSize.intValue());
        } catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error");
        }
        if (page == null) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new APIError("RESOURCE_NOT_FOUND", "Resources #$entity.nameLowerFirst$(" + pageNumber + ", " + pageSize + ") not found");
        }

         httpResponse.setStatus(HttpStatus.OK.value());
         return null;
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