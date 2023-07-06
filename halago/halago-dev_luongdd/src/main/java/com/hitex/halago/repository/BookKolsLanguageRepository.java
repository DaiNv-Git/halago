package com.hitex.halago.repository;

import com.hitex.halago.model.BookKolsLanguage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookKolsLanguageRepository extends CrudRepository<BookKolsLanguage,String> {
    @Query("Select lang from BookKolsLanguage lang where lang.idBookKols=:id and lang.languageKey=:language")
    BookKolsLanguage findBookKolsLanguage(@Param("id")int id,
                                        @Param("language")String language);

    @Query("Select lang from BookKolsLanguage lang where lang.idBookKols=:id")
    List<BookKolsLanguage> findBookKolsLanguageV2(@Param("id")int id);
}
