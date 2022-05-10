package com.tui.proof.controller;

import com.tui.proof.model.Pilotes;
import com.tui.proof.service.PilotesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@RestController
@RequestMapping(path = "/api/v1/pilotes")
@Validated
public class PilotesController {

    @Autowired
    private PilotesService pilotesService;

    @GetMapping
    public Iterable<Pilotes> getPilotes() {
        return pilotesService.getAllPilotes();

    }

    @GetMapping("/search")
    public Iterable<Pilotes> getPilotesContains(@RequestParam(name = "searchParameter") String searchParameter) {
        return pilotesService.getPilotesFromSearch(searchParameter);

    }

    @PostMapping("/order")
    public ResponseEntity<Pilotes> orderPilotes( @RequestBody @Valid Pilotes pilotes){
        return pilotesService.addNewPilotesOrder(pilotes);

    }

    @PutMapping("/order/{orderNumber}")
    public ResponseEntity<?> updatePilotesOrder(@RequestBody @Valid Pilotes newPilotes, @PathVariable Long orderNumber) {
        return pilotesService.updatePilotesOrder(newPilotes, orderNumber);

    }



    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.put("message", errorMessage);
        });
        return errors;
    }

}
