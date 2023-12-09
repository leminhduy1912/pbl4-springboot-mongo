package com.pbl4.garbageclassification.repositories;


import com.pbl4.garbageclassification.collections.Garbage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarbageRepository extends MongoRepository<Garbage,String> {
    long countByKindOfGarbage(String kindOfGarbage);
    long countByClassOfGarbage(String classOfGarbage);
    long count();
}
