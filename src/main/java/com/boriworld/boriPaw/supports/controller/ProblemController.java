package com.boriworld.boriPaw.supports.controller;

import com.boriworld.boriPaw.common.constant.ProblemDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    @GetMapping("/supports/problems/{type}")
    public ResponseEntity getProblem(@PathVariable String type) {

        ProblemDefinition problemDefinition = Arrays.stream(ProblemDefinition.values())
                .filter(problem -> problem.getType().equals(type))
                .findFirst()
                .orElse(ProblemDefinition.NOT_FOUND_PROBLEM);

        return ResponseEntity
                .ok()
                .body(new ResponseDto(problemDefinition.getTitle(), problemDefinition.getExplanation()));
    }


    record ResponseDto(String title, String explanation) {
    }

}
