package $defaultPackageName$.repository;

import $defaultPackageName$.domain.$entity.name$;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface $entity.name$Repository extends JpaRepository<$entity.name$, String> {

$if(entity.oneToManyRelations)$
    @Query("SELECT entity FROM $entity.name$ entity  $entity.oneToManyRelations:{oneToMany| JOIN FETCH entity.$oneToMany.associatedEntity.pluralLowerFirst$ }$ WHERE entity.id = (:id)")
    public $entity.name$ findByIdEagerly(@Param("id") String id);
$else$
    @Query("SELECT entity FROM $entity.name$ entity WHERE entity.id = (:id)")
    public $entity.name$ findByIdEagerly(@Param("id") String id);
$endif$

}