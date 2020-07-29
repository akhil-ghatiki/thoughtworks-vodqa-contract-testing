package com.example.consumer_two;

import static org.junit.Assert.assertEquals;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslRootValue;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  private SampleServiceTwo sampleServiceTwo;

  @Rule
  public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("provider_one",
      HOST_NAME, PORT, this);

  @Pact(consumer = "consumer_two")
  public RequestResponsePact createPactForGetString(PactDslWithProvider builder)
      throws JsonProcessingException {

    String expectedString = "contract test for second consumer";
    String expectedStringJson = objectMapper.writeValueAsString(expectedString);

    PactDslRootValue pactDslResponse = new PactDslRootValue();
    pactDslResponse.setValue(expectedStringJson);

    Map<String,String> headers = new HashMap();
    headers.put("Content-Type","text/plain");

    return builder
        .given("test GET string")
        .uponReceiving("GET request for string")
        .path("/vodqa_shot")
        .method(HttpMethod.GET.name())
        .willRespondWith()
        .status(HttpStatus.OK.value())
        .headers(headers)
        .body(pactDslResponse)
        .toPact();
  }

  @Test
  @PactVerification(value = "provider_one", fragment = "createPactForGetString")
  public void testConsumerGetRequestToString() {
    String expectedString = "\"contract test for second consumer\"";

    String resultString = sampleServiceTwo.getString();
    assertEquals(expectedString, resultString);
  }

}
