package young.blaybus.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class BeanStalkDeployController {

    @GetMapping("/health")
    public String healthCheck() { return "Hello World"; }
}
