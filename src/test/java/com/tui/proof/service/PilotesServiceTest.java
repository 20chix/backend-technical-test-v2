package com.tui.proof.service;

import com.tui.proof.model.Pilotes;
import com.tui.proof.repository.PilotesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.tui.proof.helper.TestResourceHelper.readObject;
import static java.util.Collections.EMPTY_LIST;
import static org.apache.commons.collections4.IterableUtils.size;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PilotesServiceTest {

    private static final String VALID_PILOTES_ORDER = "valid-pilotes-order.json";


    @Autowired
    private PilotesService pilotesService;

    @Autowired
    private PilotesRepository pilotesRepository;

    @BeforeEach
    void setup() throws Exception {
        pilotesService.addNewPilotesOrder(readPayload(VALID_PILOTES_ORDER));
    }

    @AfterEach
    void tearDown(){
        pilotesRepository.deleteAll();
    }
    @Test
    void givenAValidPilotesOrder_itShouldHaveOnePilotesOrder() throws Exception {

        assertThat(size(pilotesService.getAllPilotes())).isOne();

    }

    @Test
    void givenAValidPilotesOrder_itShouldReturnNothingWhenSearchPilotesOrder() throws Exception {

        assertThat(pilotesService.getPilotesFromSearch("Potato")).isEqualTo(EMPTY_LIST);

    }

    @Test
    void givenAValidPilotesOrder_itShouldReturnPilotesOrderDetailsWhenSearch() throws Exception {

        assertThat(size(pilotesService.getPilotesFromSearch("Hadi"))).isOne();
    }

    @Test
    void givenAValidPilotesOrder_itShouldReturnPilotesOrderDetailsWhenPartialSearch() throws Exception {

        assertThat(size(pilotesService.getPilotesFromSearch("Ha"))).isOne();
    }

    private static Pilotes readPayload(String path) throws Exception {
        return readObject(path, Pilotes.class);
    }

}