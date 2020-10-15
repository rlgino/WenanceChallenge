package com.wenance.WenanceChallenge.repository;

import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BitcoinsRepository extends CrudRepository<BitcoinSnapshot, Integer> {
    @Query("select a from BitcoinSnapshot a where a.creationDateTime <= :creationDateTime")
    List<BitcoinSnapshot> findAllWithCreationDateTimeBefore(
            @Param("creationDateTime") Date creationDateTime);
}
