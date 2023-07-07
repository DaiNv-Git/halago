package com.hitex.halago.repository;

import com.hitex.halago.model.IntroduceLanguage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroduceLanguageRepository extends CrudRepository<IntroduceLanguage,String> {
    @Query("Select lang from IntroduceLanguage lang where lang.idIntroduce=:id and lang.languageKey=:language")
    IntroduceLanguage findIntroLanguage(@Param("id")int id,
                                        @Param("language")String language);
}
