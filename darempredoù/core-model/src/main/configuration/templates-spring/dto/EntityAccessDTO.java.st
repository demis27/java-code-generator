package $defaultPackageName$.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class $entity.name$AccessDTO  {

    private String id;

    private String uri;

    public $entity.name$AccessDTO(String id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}