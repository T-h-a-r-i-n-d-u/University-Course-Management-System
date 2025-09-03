
package com.ucms.ucmsapi.assignment.dto;

import jakarta.validation.constraints.Future;

import java.time.Instant;

public class UpdateAssignmentRequest {
    private String title;
    private String description;
    @Future
    private Instant deadline;
    private Integer maxScore;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Instant getDeadline() { return deadline; }
    public void setDeadline(Instant deadline) { this.deadline = deadline; }

    public Integer getMaxScore() { return maxScore; }
    public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }
}
