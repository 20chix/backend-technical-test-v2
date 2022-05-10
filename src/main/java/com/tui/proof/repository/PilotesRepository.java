package com.tui.proof.repository;

import com.tui.proof.model.Pilotes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilotesRepository
        extends CrudRepository<Pilotes,Long > {

    @Query(value = "SELECT * FROM " +
            "Pilotes WHERE CITY LIKE %?1% " +
            "OR COUNTRY LIKE %?1% " +
            "OR POSTCODE LIKE %?1% " +
            "OR STREET LIKE %?1% " +
            "OR FIRST_NAME LIKE %?1% " +
            "OR LAST_NAME LIKE %?1% " +
            "OR TELEPHONE LIKE %?1% " +
            "OR EMAIL LIKE %?1% " ,
            nativeQuery = true)
    Iterable<Pilotes> findPilotesOrdersDetails(String searchParameter);

}
