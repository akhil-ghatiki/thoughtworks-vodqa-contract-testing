package com.example.consumer_one;

import static org.junit.Assert.assertEquals;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslRootValue;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import au.com.dius.pact.model.matchingrules.MatchingRule;
import au.com.dius.pact.model.matchingrules.RegexMatcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class samplePact {

  private static final String HOST_NAME = "localhost";
  private static final int PORT = 8088;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private SampleService sampleService;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("provider_one",
      HOST_NAME, PORT, this);

  @Pact(consumer = "consumer_one")
  public RequestResponsePact createPactForGetLastUpdatedTimestamp(PactDslWithProvider builder)
      throws JsonProcessingException {

    ZonedDateTime dateTime = ZonedDateTime.parse("2019-06-05T00:00:00.111+00:00");
    String lastUpdatedTimeStamp = objectMapper.writeValueAsString(dateTime);

    String dateTimeRegex = "^(?:[1-9]\\d{3}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1\\d|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[1-9]\\d(?:0[48]|[2468][048]|[13579][26])|(?:[2468][048]|[13579][26])00)-02-29)T(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d.\\d{3}(?:Z|[+-][01]\\d:[0-5]\\d)$";
    MatchingRule matchingRule = new RegexMatcher(dateTimeRegex, lastUpdatedTimeStamp);

    PactDslRootValue pactDslResponse = new PactDslRootValue();
    pactDslResponse.setValue(lastUpdatedTimeStamp);
    pactDslResponse.setMatcher(matchingRule);

    Map<String,String> headers = new HashMap();
    headers.put("Content-Type","application/json");

    return builder
        .given("test GET lastUpdatedTimestamp")
        .uponReceiving("GET request for datetime")
        .path("/vodqa")
        .method(HttpMethod.GET.name())
        .willRespondWith()
        .status(HttpStatus.OK.value())
        .headers(headers)
        .body(pactDslResponse)
        .toPact();
  }

  @Test
  @PactVerification(value = "provider_one", fragment = "createPactForGetLastUpdatedTimestamp")
  public void testConsumerGetRequestToOffsetService() {
    ZonedDateTime dateTime = ZonedDateTime.parse("2019-06-05T00:00:00.111+00:00")
        .withZoneSameInstant(ZoneId.of("UTC"));

    ZonedDateTime timestamp = sampleService.getUpdatedTime();
    assertEquals(dateTime, timestamp);
  }

}
