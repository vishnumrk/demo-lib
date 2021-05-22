package io.ms.lib;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> demo(@RequestParam @Valid @NotEmpty String date){
        throwException();
        parseDate("");
        return ResponseEntity.ok("Hello World");
    }

    private void parseDate(String s) {
        LocalDate parse = LocalDate.parse(s);
        System.out.println(parse);
    }


    private void throwException() {
        if (true) throw new ApplicationException(ErrorCodeEnum.BAD_REQUEST);
    }
}
