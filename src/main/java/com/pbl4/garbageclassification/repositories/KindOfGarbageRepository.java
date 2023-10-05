package com.pbl4.garbageclassification.repositories;

import com.pbl4.garbageclassification.collections.KindOfGarbage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindOfGarbageRepository extends MongoRepository<KindOfGarbage,String> {
}
