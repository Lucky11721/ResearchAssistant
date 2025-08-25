package com.research.assistant.controller;

import com.research.assistant.researchService.ResearchService;
import com.research.assistant.researchrequest.ResearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/research")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ResearchController {

  private ResearchService researchService;
    @PostMapping("/process")
    public ResponseEntity<String> processContent(@RequestBody ResearchRequest researchRequest){
    String result = researchService.processContent(researchRequest);
      return ResponseEntity.ok(result);
  }
}
