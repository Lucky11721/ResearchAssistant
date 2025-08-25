package com.research.assistant.researchrequest;


import lombok.Data;

@Data
public class ResearchRequest {

    private String content;
    private String operations;

    public String getContent() {
        return content;
    }

    public String getOperations() {
        return operations;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "ResearchRequest{" +
                "content='" + content + '\'' +
                ", operations='" + operations + '\'' +
                '}';
    }
}
