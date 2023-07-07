package com.hitex.halago.repository;

import com.hitex.halago.model.BookKolsLanguage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookKolsLanguageRepository extends CrudRepository<BookKolsLanguage,String> {
    @Query("Select lang from BookKolsLanguage lang where lang.idBookKols=:id and lang.languageKey=:language")
    BookKolsLanguage findBookKolsLanguage(@Param("id")int id,
                                        @Param("language")String language);
}
