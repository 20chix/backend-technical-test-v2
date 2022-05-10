package com.tui.proof.service;

import com.tui.proof.model.Pilotes;
import com.tui.proof.repository.PilotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class PilotesService {

    private final PilotesRepository pilotesRepository;

    @Autowired
    public PilotesService(PilotesRepository pilotesRepository) {
        this.pilotesRepository = pilotesRepository;
    }

    public ResponseEntity<Pilotes> addNewPilotesOrder(Pilotes pilotes)  {
        return ResponseEntity.ok(pilotesRepository.save(pilotes));
    }

    public Iterable<Pilotes> getAllPilotes() {
        return pilotesRepository.findAll();
    }

    public Iterable<Pilotes> getPilotesFromSearch(String searchParameter) {
        return pilotesRepository.findPilotesOrdersDetails(searchParameter);
    }

    public ResponseEntity<?> updatePilotesOrder(Pilotes newPilotes, Long orderNumber) {
        return  pilotesRepository.findById(orderNumber)
                .map(pilotes -> {
                    if(pilotes.getCanUpdateOrder()){
                        pilotes.setNumberOfPilotes(newPilotes.getNumberOfPilotes());
                        pilotes.setStreet(newPilotes.getStreet());
                        pilotes.setPostcode(newPilotes.getPostcode());
                        pilotes.setCity(newPilotes.getCity());
                        pilotes.setCountry(newPilotes.getCountry());
                        pilotes.setFirstName(newPilotes.getFirstName());
                        pilotes.setLastName(newPilotes.getLastName());
                        pilotes.setTelephone(newPilotes.getTelephone());
                        pilotes.setEmail(newPilotes.getEmail());

                        return ResponseEntity.ok(pilotesRepository.save(pilotes));
                    }
                    return  ResponseEntity.status(BAD_REQUEST).body("Unable to update order after 5 minutes");
                })
                .orElseGet(() ->ResponseEntity.status(BAD_REQUEST).body("Unable to order number: " + orderNumber));
    }


}