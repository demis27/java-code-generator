package $defaultPackageName$.controller;

import $defaultPackageName$.domain.$entity.name$;
import $defaultPackageName$.service.$entity.name$Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class $entity.name$Controller {

    private final Logger logger = LoggerFactory.getLogger($entity.name$Controller.class);

    private $entity.name$Service $entity.nameLowerFirst$Service;


	public void set$entity.name$Service($entity.name$Service $entity.nameLowerFirst$Service) {
		this.$entity.nameLowerFirst$Service = $entity.nameLowerFirst$Service;
	}
}