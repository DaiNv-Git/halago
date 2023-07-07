package com.hitex.halago.repository;

import com.hitex.halago.model.BookKols;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KolsRepository extends CrudRepository<BookKols,String> {

    @Query("Select kols from BookKols kols where kols.id=:id")
    BookKols findBookKolsById(@Param("id") int id);

}
