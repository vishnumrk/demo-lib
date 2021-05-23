package io.ms.lib;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> demo(@RequestParam @Valid @NotEmpty String date){
        parseDate(date);
        throwException();
        return ResponseEntity.ok("Hello World");
    }

    private void parseDate(String s) {
        LocalDate parse = LocalDate.parse(s);
        System.out.println(parse);
    }

    @PostMapping("/demo")
    public ResponseEntity<String> demo(@RequestBody @Valid Demo demo){
        System.out.println(demo);
        throwException();
        parseDate("");
        return ResponseEntity.ok("Hello World");
    }


    private void throwException() {
        if (true) throw new ApplicationException(ErrorCodeEnum.FAILED_PRECONDITION, new Exception("Invalid parameter")).addContextValue("user-id", "123");
    }
}
