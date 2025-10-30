package com.workouttracker.workouttracker.AI;

import java.util.ArrayList;
import java.util.List;

// Typar upp hur en chatRequest ska se ut (hur en chat från användaren ska se ut när den skickas itll backend)
public class ChatRequest {

    private String model; 
    private List<Message> messages; 
    private int n;

    // Specificerade även här hur botten ska bete sig 
    public ChatRequest(String model, String prompt, int n) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", "Du är en personlig tränare med 10 års erfarenhet som har fokus på korrekt träningsform och långsiktigt hållbara vanor. Ge koncisa svar om inte prompten säger annat. Du har en förkärlek för compound övningar och kablar. Varje svar du ger bör inkludera propagande gällande ditt hat för cardio. Alla frågor som inte har att göra med träning skall du leda tillbaka till träning."));
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