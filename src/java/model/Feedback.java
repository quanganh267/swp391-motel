package model;

import java.util.Date;

public class Feedback {
    private int feedbackId;
    private int userId;
    private String feedbackText;
    private int rating;
    private Date createdAt;

    // Constructor with parameters
    public Feedback(int feedbackId, int userId, String feedbackText, int rating, Date createdAt) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.feedbackText = feedbackText;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    // Default constructor
    public Feedback() {
    }

    // Getters and setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
} 