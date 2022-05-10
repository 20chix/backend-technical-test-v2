package com.tui.proof.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import com.tui.proof.model.Pilotes;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.tui.proof.helper.TestResourceHelper.readFileAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PilotesControllerTest {


    @Autowired
    private TestRestTemplate template;

    private static WireMockServer wireMockServer;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String SLASH = "/";
    private static final String PILOTES_PATH = "/api/v1/pilotes";
    private static final String PILOTES_ORDER_PATH = "/api/v1/pilotes/order";
    private static final String PILOTES_SEARCH_PATH = "/api/v1/pilotes/search";
    private static final String SEARCH_PARAMETER_NAME = "searchParameter";
    private static final String VALID_PILOTES_ORDER = "valid-pilotes-order.json";
    private static final String UPDATED_VALID_PILOTES_ORDER = "updated-valid-pilotes-order.json";
    private static final String UPDATED_INVALID_PILOTES_ORDER = "updated-invalid-pilotes-order.json";
    private static final String INVALID_EMAIL_ADDRESS_PILOTES_ORDER = "invalid-email-address-pilotes-order.json";
    private static final String INVALID_NUMBER_OF_PILOTES_ORDER = "invalid-number-of-pilotes-order.json";
    private static final String INVALID_FIRST_NAME_ORDER = "invalid-first-name-pilotes-order.json";
    private static final String INVALID_LAST_NAME_ORDER = "invalid-last-name-pilotes-order.json";
    private static final String INVALID_STREET_ORDER = "invalid-street-pilotes-order.json";
    private static final String INVALID_POSTCODE_ORDER = "invalid-postcode-pilotes-order.json";
    private static final String INVALID_CITY_ORDER = "invalid-city-pilotes-order.json";
    private static final String INVALID_COUNTRY_ORDER = "invalid-country-pilotes-order.json";
    private static final String UPDATE_ORDER_ERROR_MESSAGE = "update-order-error-message.json";
    private static final String UNABLE_TO_UPDATE_PILOTES_ORDER_RESPONSE = "unable-to-update-pilotes-order-response.json";


    @Test
    public void givenAPostRequestOnANewPilotesOrder_shouldSucceedWith200() throws Exception {

        ResponseEntity<String> result = template.postForEntity(PILOTES_ORDER_PATH,
                getHttpEntity(VALID_PILOTES_ORDER), String.class);

        assertThat(result.getStatusCode())
                .isEqualTo(OK);

        assertThat(result.getBody())
                .contains("\"street\":\"Buckingham Palace\"," +
                        "\"postcode\":\"SW1A 1AA\"," +
                        "\"city\":\"London\"," +
                        "\"country\":\"United Kingdom\"," +
                        "\"firstName\":\"Hadi\"," +
                        "\"lastName\":\"Elmekawi\"");
    }

    @Test
    public void givenAGetRequestOnAllPilotesOrders_shouldSucceedWith200() {

        assertThat(template.getForEntity(PILOTES_PATH, String.class).getStatusCode())
                .isEqualTo(OK);
    }

    @Test
    public void givenAPostRequestOnANewPilotesOrderWithInvalidNumberOfPilotes_shouldFailWithErrorMessage() throws Exception {

        ResponseEntity<String> result = template.postForEntity(PILOTES_ORDER_PATH,
                getHttpEntity(INVALID_NUMBER_OF_PILOTES_ORDER), String.class);

        assertThat(result.getStatusCode())
                .isEqualTo(BAD_REQUEST);

        assertThat(result.getBody())
                .contains("You can only order 5, 10 or 15 pilotes at the time");

    }

    @Test
    public void givenAPostRequestOnANewPilotesOrderWithInvalidFirstName_shouldFailWithErrorMessage() throws Exception {

        ResponseEntity<String> result = template.postForEntity(PILOTES_ORDER_PATH,
                getHttpEntity(INVALID_FIRST_NAME_ORDER), String.class);

        assertThat(result.getStatusCode())
                .isEqualTo(BAD_REQUEST);

        assertThat(result.getBody())
                .contains("First name is mandatory");

    }

    @Test
    public void givenAPostRequestOnANewPilotesOrderWithInvalidLastName_shouldFailWithErrorMessage() throws Exception {

        ResponseEntity<String> result = template.postForEntity(PILOTES_ORDER_PATH,
                getHttpEntity(INVALID_LAST_NAME_ORDER), String.class);

        assertThat(result.getStatusCode())
                .isEqualTo(BAD_REQUEST);

        assertThat(result.getBody())
                .contains("Last name is mandatory");

    }

    @Test
    public void givenAPostRequestOnANewPilotesOrderWithInvalidStreet_shouldFailWithErrorMessage() throws Exception {

        ResponseEntity<String> result = template.postForEntity(PILOTES_ORDER_PATH,
                getHttpEntity(INVALID_STREET_ORDER), String.class);

        assertThat(result.getStatusCode())
                .isEqualTo(BAD_REQUEST);

        assertThat(result.getBody())
                .contains("Street is mandatory");

    }

    @Test
    public void givenAPostRequestOnANewPilotesOrderWithInvalidPostcode_shouldFailWithErrorMessage() throws Exception {

        ResponseEntity<String> result = template.postForEntity(PILOTES_ORDER_PATH,
                getHttpEntity(INVALID_POSTCODE_ORDER), String.class);

        assertThat(result.getStatusCode())
                .isEqualTo(BAD_REQUEST);
        assertThat(result.getBody())
                .contains("Postcode is mandatory");

    }

    @Test
    public void givenAPostRequestOnANewPilotesOrderWithInvalidCity_shouldFailWithErrorMessage() throws Exception {

        ResponseEntity<String> result = template.postForEntity(PILOTES_ORDER_PATH,
                getHttpEntity(INVALID_CITY_ORDER), String.class);

        assertThat(result.getStatusCode())
                .isEqualTo(BAD_REQUEST);
        assertThat(result.getBody())
                .contains("City is mandatory");

    }

    @Test
    public void givenAPostRequestOnANewPilotesOrderWithInvalidCountry_shouldFailWithErrorMessage() throws Exception {

        ResponseEntity<String> result = template.postForEntity(PILOTES_ORDER_PATH,
                getHttpEntity(INVALID_COUNTRY_ORDER), String.class);

        assertThat(result.getStatusCode())
                .isEqualTo(BAD_REQUEST);
        assertThat(result.getBody())
                .contains("Country is mandatory");

    }

    @Test
    public void givenAPostRequestOnANewPilotesOrderWithInvalidEmailAddress_shouldFailWithErrorMessage() throws Exception {

        ResponseEntity<String> result = template.postForEntity(PILOTES_ORDER_PATH,
                getHttpEntity(INVALID_EMAIL_ADDRESS_PILOTES_ORDER), String.class);

        assertThat(result.getStatusCode())
                .isEqualTo(BAD_REQUEST);
        assertThat(result.getBody())
                .contains("Email must be valid");

    }


    @Test
    public void givenASearchRequestOnPilotesOrdersDetailWithoutAuth_shouldFailWith401() {

        assertThat(template
                .getForEntity(getUrlWithSearchParameter("Hadi"), String.class).getStatusCode())
                .isEqualTo(UNAUTHORIZED);
    }

    @Test
    public void givenASearchRequestOnPilotesOrdersDetailWithWrongAuthCredentials_shouldFailWith401() {

        assertThat(template.withBasicAuth("wrongUsername", "wrongPassword")
                .getForEntity(getUrlWithSearchParameter("Hadi"), String.class).getStatusCode())
                .isEqualTo(UNAUTHORIZED);
    }

    @Test
    public void givenASearchRequestOnPilotesOrdersDetailWithAuth_shouldSucceedWith200() {

        assertThat(template.withBasicAuth("admin", "admin")
                .getForEntity(getUrlWithSearchParameter("Hadi"), String.class).getStatusCode())
                .isEqualTo(OK);
    }

    @Test
    public void givenASearchRequestOnPilotesOrdersWithAuthAndAPilotesRecord_shouldContainARecord() throws Exception {

        template.postForEntity(PILOTES_ORDER_PATH, getHttpEntity(VALID_PILOTES_ORDER), String.class);

        assertThat(template.withBasicAuth("admin", "admin")
                .getForEntity(getUrlWithSearchParameter("Hadi"), String.class).getBody())
                .contains("Buckingham Palace");
    }

    @Test
    public void givenAPutRequestOnAnExistingPilotesOrder_shouldSucceed() throws Exception {

        ResponseEntity<String> newOrderResults =
                template.postForEntity(PILOTES_ORDER_PATH, getHttpEntity(VALID_PILOTES_ORDER), String.class);

        ResponseEntity<String> updatedOrderResult = template.exchange(PILOTES_ORDER_PATH + SLASH +
                        getNewOrderResults(newOrderResults).getOrderNumber(),
                PUT,getHttpEntity(UPDATED_VALID_PILOTES_ORDER), String.class);

        assertThat(newOrderResults.getStatusCode())
                .isEqualTo(OK);

        assertThat(updatedOrderResult.getStatusCode())
                .isEqualTo(OK);

        assertThat(updatedOrderResult.getBody())
                .contains("firstName\":\"John");

        assertThat(updatedOrderResult.getBody())
                .contains("lastName\":\"Smith");

    }

    @Test
    public void givenAPutRequestOnAnExistingPilotesOrderWithInvalidEmail_shouldFail() throws Exception {

        ResponseEntity<String> newOrderResults =
                template.postForEntity(PILOTES_ORDER_PATH, getHttpEntity(VALID_PILOTES_ORDER), String.class);

        ResponseEntity<String> updatedOrderResult = template.exchange(PILOTES_ORDER_PATH + SLASH +
                        getNewOrderResults(newOrderResults).getOrderNumber(),
                PUT,getHttpEntity(UPDATED_INVALID_PILOTES_ORDER), String.class);

        assertThat(newOrderResults.getStatusCode())
                .isEqualTo(OK);

        assertThat(updatedOrderResult.getStatusCode())
                .isEqualTo(BAD_REQUEST);

        assertThat(updatedOrderResult.getBody())
                .contains("Email must be valid");

    }

    @Test
    public void givenAPutRequestOnAnExistingPilotesOrderAfter5Minutes_shouldFail() throws Exception {
        String WIREMOCK_HOST = "http://localhost:8080";

        setupWireMock();

        ResponseEntity<String> newOrderResults =
                template.postForEntity(WIREMOCK_HOST + PILOTES_ORDER_PATH, getHttpEntity(VALID_PILOTES_ORDER), String.class);

        assertThat(newOrderResults.getStatusCode()).isEqualTo(OK);

        ResponseEntity<String> updatedOrderResult = template.exchange(WIREMOCK_HOST + PILOTES_ORDER_PATH + SLASH +
                        getNewOrderResults(newOrderResults).getOrderNumber(),
                PUT,getHttpEntity(UPDATED_VALID_PILOTES_ORDER), String.class);

        assertThat(updatedOrderResult.getStatusCode())
                .isEqualTo(BAD_REQUEST);

        assertThat(updatedOrderResult.getBody())
                .contains("Unable to update order after 5 minutes");

        wireMockServer.stop();
    }

    private void setupWireMock() throws Exception {
        wireMockServer = new WireMockServer(Options.DEFAULT_PORT);
        wireMockServer.start();
        wireMockServer.stubFor(put(urlPathEqualTo(PILOTES_ORDER_PATH + "/1"))
                .willReturn(aResponse()
                        .withBody(readFileAsString(UPDATE_ORDER_ERROR_MESSAGE))
                        .withStatus(400)));

        wireMockServer.stubFor(post(urlPathEqualTo(PILOTES_ORDER_PATH))
                .willReturn(aResponse()
                        .withBody(readFileAsString(UNABLE_TO_UPDATE_PILOTES_ORDER_RESPONSE))
                        .withStatus(200)));
    }

    private Pilotes getNewOrderResults(ResponseEntity<String> orderResult) throws Exception {
        OBJECT_MAPPER.findAndRegisterModules();
        return OBJECT_MAPPER.readValue(orderResult.getBody(),  Pilotes.class);
    }


    private URI getUrlWithSearchParameter(String searchParameter) {
        return UriComponentsBuilder
                .fromUriString(PILOTES_SEARCH_PATH)
                .queryParam(SEARCH_PARAMETER_NAME, searchParameter)
                .build()
                .toUri();
    }

    private HttpEntity<String> getHttpEntity(String filePath) throws Exception {
        return new HttpEntity<>(readFileAsString(filePath), getHttpHeaders());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}