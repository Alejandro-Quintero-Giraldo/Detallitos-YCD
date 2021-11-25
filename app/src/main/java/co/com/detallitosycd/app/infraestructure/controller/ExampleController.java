package co.com.detallitosycd.app.infraestructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping("/response")
    public ResponseEntity<Object> responseEntity(){
        return ResponseEntity.ok("OK XD");
    }
}
