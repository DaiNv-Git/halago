package com.hitex.halago.repository;

import com.hitex.halago.model.ChannelInteraction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelInteractionRepository extends CrudRepository<ChannelInteraction,String> {
    @Query("Select channel from ChannelInteraction channel")
    List<ChannelInteraction> listChannel();
}
