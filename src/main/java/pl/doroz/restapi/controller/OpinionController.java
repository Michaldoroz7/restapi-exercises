package pl.doroz.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.doroz.restapi.entity.Opinion;
import pl.doroz.restapi.service.OpinionService;

import java.util.List;

@RestController("/opinions")
@AllArgsConstructor
public class OpinionController {

    private final OpinionService opinionService;


    @GetMapping("/id")
    public ResponseEntity<Opinion> getOpinionById(@RequestParam Long id) {
        return ResponseEntity.ok(opinionService.getOpinionById(id).get());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Opinion>> getEmployeeOpinions(@PathVariable Long employeeId) {
        return ResponseEntity.ok(opinionService.getEmployeeOpinions(employeeId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Opinion>> getAllOpinions() {
        return ResponseEntity.ok(opinionService.getAllOpinions());
    }
}
