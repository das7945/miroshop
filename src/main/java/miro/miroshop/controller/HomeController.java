package miro.miroshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

  // @Slf4j은 lombok을 통해 log를 사용 할 수 있음.
  //  Logger log = LoggerFactory.getLogger(getClass());

  @RequestMapping("/")
  public String home() {
    log.info("home controller");
    return "home";
  }
}
