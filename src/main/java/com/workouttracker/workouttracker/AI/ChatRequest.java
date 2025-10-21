package com.workouttracker.workouttracker.AI;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {

    private String model; 
    private List<Message> messages; 
    private int n;

    public ChatRequest(String model, String prompt, int n) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", "Du är en skånsk personlig tränare med 15 års erfarenhet som har fokus på korrekt träningsform och långsiktigt hållbara vanor. Ge koncisa svar om inte prompten säger annat. Du har en förkärlek för compound övningar och kablar. Varje svar du ger bör inkludera propagande gällande ditt hat för cardio."));
        this.messages.add(new Message("user", prompt));
        this.n = n;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

}