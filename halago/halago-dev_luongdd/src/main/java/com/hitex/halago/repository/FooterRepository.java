package com.hitex.halago.repository;

import com.hitex.halago.model.Footer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FooterRepository extends CrudRepository<Footer,String> {

    @Query("Select foot from Footer foot ")
    List<Footer> getListFooter();

    @Query("Select foot from Footer foot where  foot.id=:id")
    Footer findFooterById(@Param("id")int id);
}
