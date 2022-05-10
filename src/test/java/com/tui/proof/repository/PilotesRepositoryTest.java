package com.tui.proof.repository;

import com.tui.proof.model.Pilotes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.tui.proof.helper.TestResourceHelper.readObject;
import static java.util.Collections.EMPTY_LIST;
import static org.apache.commons.collections4.IterableUtils.size;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PilotesRepositoryTest {

    private static final String VALID_PILOTES_ORDER = "valid-pilotes-order.json";

    @Autowired
    private PilotesRepository pilotesRepository;

    @AfterEach
    void tearDown(){
        pilotesRepository.deleteAll();
    }

    @Test
    void givenAValidPilotesOrder_itShouldHaveOnePilotesOrder() throws Exception {

        pilotesRepository.save(readPayload(VALID_PILOTES_ORDER));

        assertThat(size(pilotesRepository.findAll())).isOne();

    }

    @Test
    void givenAValidPilotesOrder_itShouldReturnNothingWhenSearchPilotesOrder() throws Exception {

        pilotesRepository.save(readPayload(VALID_PILOTES_ORDER));

        assertThat(pilotesRepository.findPilotesOrdersDetails("Potato")).isEqualTo(EMPTY_LIST);

    }

    @Test
    void givenAValidPilotesOrder_itShouldReturnPilotesOrderDetailsWhenSearch() throws Exception {

        pilotesRepository.save(readPayload(VALID_PILOTES_ORDER));

        assertThat(size(pilotesRepository.findPilotesOrdersDetails("Hadi"))).isOne();
    }

    @Test
    void givenAValidPilotesOrder_itShouldReturnPilotesOrderDetailsWhenPartialSearch() throws Exception {

        pilotesRepository.save(readPayload(VALID_PILOTES_ORDER));

        assertThat(size(pilotesRepository.findPilotesOrdersDetails("Ha"))).isOne();
    }

    private static Pilotes readPayload(String path) throws Exception {
        return readObject(path, Pilotes.class);
    }

}