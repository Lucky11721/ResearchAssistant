package com.research.assistant.researchService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.researchrequest.ResearchRequest;
import com.research.assistant.response.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;



@Service
public class ResearchService {
    @Value("${gemini.api.url}")
    private String geminiurl;
    @Value("${gemini.api.key}")
    private String geminikey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    public ResearchService(WebClient.Builder builder, ObjectMapper objectMapper) {
        this.webClient = builder.build();
        this.objectMapper = objectMapper;
    }

    public String processContent(ResearchRequest researchRequest){
        // build the prompt
        String prompt = buildPrompt(researchRequest);

// Build request body for Gemini API
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of(
                                "parts", new Object[] {
                                        Map.of("text", prompt)
                                }
                        )
                }
        );

        String response = webClient.post()
                .uri(geminiurl + "?key=" + geminikey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // parse the response

        //return response
        return extractTextFromResponse(response);

    }

private String extractTextFromResponse(String response){
        try {
            GeminiResponse geminiResponse = objectMapper.readValue(response, GeminiResponse.class);
            if (geminiResponse.getCandidateList() != null && geminiResponse.getCandidateList().isEmpty() == false) {
                GeminiResponse.Candidate firstCandidate = geminiResponse.getCandidateList().get(0);
                if (firstCandidate.getContent() != null && firstCandidate.getContent().getParts().isEmpty() == false) {
                    return firstCandidate.getContent().getParts().get(0).getText();
                }
            }
            return "No content found in response";
        }
        catch(Exception e){
            return "Error Parsing " + e.getMessage();
        }
}

    private String buildPrompt(ResearchRequest researchRequest) {
        StringBuilder prompt = new StringBuilder();

        switch (researchRequest.getOperations()) {
            case "Summarize":
                prompt.append("Provide a clear and concise summary of the following text:\n\n");
                break;

            case "Suggest":
                prompt.append("Based on the content, suggest related topics:\n\n");
                break;

            default:
                throw new IllegalArgumentException("Unknown Operation: " + researchRequest.getOperations());
        }

        prompt.append(researchRequest.getContent());
        return prompt.toString();
    }
}
