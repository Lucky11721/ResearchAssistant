package com.research.assistant.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {
    @JsonProperty("candidates")
    private List<Candidate> candidateList;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Candidate {
        private Content content;

        public Content getContent() {
            return content;
        }
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content{
        private List<Part> parts;

        public List<Part> getParts() {
            return parts;
        }
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Part{
        private String text;

        public String getText() {
            return text;
        }
    }

    public List<Candidate> getCandidateList() {
        return candidateList;

    }

    public void setCandidateList(List<Candidate> candidateList) {
        this.candidateList = candidateList;
    }

    @Override
    public String toString() {
        return "GeminiResponse{" +
                "candidateList=" + candidateList +
                '}';
    }
}
