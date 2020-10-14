package com.wenance.WenanceChallenge.repository;

import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BitcoinsRepository extends CrudRepository<BitcoinSnapshot, Date> {
}
