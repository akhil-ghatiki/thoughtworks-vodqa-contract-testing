package com.example.provider_one;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class SampleController {

  @GetMapping("/vodqa")
  public ZonedDateTime getZonedDateTime(){
    Date date = new Date();
    final ZoneId id = ZoneId.systemDefault();
    return ZonedDateTime.ofInstant(date.toInstant(), id);
  }

  @GetMapping("/vodqa_shot")
  public String getString(){
    return "\"contract test for second consumer\"";
  }

}
