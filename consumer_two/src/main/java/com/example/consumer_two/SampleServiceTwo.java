package com.example.consumer_two;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Slf4j
@Service
public class SampleServiceTwo {


  public String getString() {

    return restTemplate().getForObject("http://localhost:8088/vodqa_shot",String.class);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
