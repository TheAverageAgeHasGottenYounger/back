package young.blaybus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanStalkDeployController {

    @GetMapping("/health")
    public String healthCheck() { return "Hello World"; }
}
