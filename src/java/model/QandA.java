/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author anhdq
 */
public class QandA {
    private int qaId;
    private int guestId;
    private String guestName;
    private String email;
    private String question;
    private String answer;
    private Date createdAt;

    // Constructor
    public QandA() {
    }

    public QandA(int qaId, int guestId, String guestName, String email, String question, String answer, Date createdAt) {
        this.qaId = qaId;
        this.guestId = guestId;
        this.guestName = guestName;
        this.email = email;
        this.question = question;
        this.answer = answer;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getQaId() {
        return qaId;
    }

    public void setQaId(int qaId) {
        this.qaId = qaId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
