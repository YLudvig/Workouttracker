package com.workouttracker.workouttracker.websocket;

// När skapar session behöver enbart hostens userid och träningspassets id skickas för att skapa en session 
// Med detta så kan vi skapa en session för den workouten 
public class CreateSessionRequest {
    public Long hostUserId; 
    public Long workoutId; 
}
