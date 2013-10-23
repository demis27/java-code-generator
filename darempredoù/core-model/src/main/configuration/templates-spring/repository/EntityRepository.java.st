package $defaultPackageName$.repository;

import $defaultPackageName$.domain.$entity.name$;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface $entity.name$Repository extends JpaRepository<$entity.name$, Integer> {

$if(entity.oneToManyRelations)$
    @Query("SELECT entity FROM $entity.name$ entity  $entity.oneToManyRelations:{oneToMany| $if(oneToMany.composition)$ LEFT OUTER JOIN FETCH entity.$oneToMany.associatedEntity.pluralLowerFirst$ $endif$}$ WHERE entity.id = (:id)")
    public $entity.name$ findByIdEagerly(@Param("id") Integer id);
$else$
    @Query("SELECT entity FROM $entity.name$ entity WHERE entity.id = (:id)")
    public $entity.name$ findByIdEagerly(@Param("id") Integer id);
$endif$

}