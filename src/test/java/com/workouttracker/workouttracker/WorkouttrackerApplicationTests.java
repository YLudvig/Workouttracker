package com.workouttracker.workouttracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.workouttracker.workouttracker.DTOs.AuthRequest;
import com.workouttracker.workouttracker.DTOs.AuthResponse;
import com.workouttracker.workouttracker.DTOs.UserDTO;
import com.workouttracker.workouttracker.DTOs.WorkoutRequest;
import com.workouttracker.workouttracker.Util.AuthService;
import com.workouttracker.workouttracker.model.Exercise;
import com.workouttracker.workouttracker.model.Workout;
import com.workouttracker.workouttracker.repository.ExerciseRepository;
import com.workouttracker.workouttracker.repository.UserRepository;
import com.workouttracker.workouttracker.service.WorkoutService;
import com.workouttracker.workouttracker.websocket.Session;
import com.workouttracker.workouttracker.websocket.SessionCodeGenerator;
import com.workouttracker.workouttracker.websocket.SessionRepository;

@SpringBootTest
@Transactional
class WorkouttrackerApplicationTests {

	@Autowired
	private SessionRepository sessionRepository; 

	@Autowired
	private ExerciseRepository exerciseRepository; 

	@Autowired 
	private WorkoutService workoutService; 

	@Autowired
	private AuthService authService; 

	@Autowired
	private UserRepository userRepository; 

	// Testar våra repo metoder för att göra temporära sessioner
	// Här testar vi att hämta en kod som omöjligt finns då våra koder alltid genereras som en string av 6 tecken
	@Test
	void testFindingNonExistentSession(){
		assertNull(sessionRepository.findByCode("KODSOMEJFINNS"));
	}

	// Testar repo metod för att deleta 
	// Deletar här en session som inte finns 
	// Därav bör assertNull passera 
	@Test
	void testDeletingNonExistentSession(){
		assertFalse(sessionRepository.deleteByCode("KODSOMEJFINNS"));
	}

	// Test där vi kollar hela flödet, 
	// så att man kan spara en session, hämta den och sedan deleta den 
	@Test
	void testSaveAndFindAndDeleteSession(){
		// Sparar en mockad session
		Session session = new Session("KOD123", 999L);
		sessionRepository.save(session);

		// Kollar att vi hittar sessionen 
		Session exists = sessionRepository.findByCode("KOD123"); 
		
		assertNotNull(exists);
		assertEquals("KOD123", exists.getSessionCode());

		// Deletear vår session och checkar att det genomfördes korrekt 
		boolean deleted = sessionRepository.deleteByCode(session.getSessionCode()); 
		assertTrue(deleted);
		assertNull(sessionRepository.findByCode("KOD123"));
	}

	// testar att generera en kod och kollar sedan att den inte är null och att den är lika lång som den ska vara 
	@Test
	void testCodeGenerator(){
		int codeLength = 6; 
		String code = SessionCodeGenerator.getSessionCode(codeLength); 

		assertNotNull(code);
		assertEquals(codeLength, code.length());
	}

	// Testar att skapa en workout 
	@Test 
	void createWorkoutTest () {
		WorkoutRequest request = new WorkoutRequest(); 
		request.setUserId(1L); 
		request.setWorkoutName("TESTWORKOUT");
		request.setCompleted(true);

		Exercise exercise = new Exercise(); 
		exercise.setExerciseName("Push-ups");
		request.setExercises(List.of(exercise));

		Workout saved = workoutService.createWorkout(request); 

		// Checkar att det assignats ett workoutid, om det hänt så har det korrekt sparats
		assertNotNull(saved.getWorkoutId());
		assertEquals("TESTWORKOUT", saved.getWorkoutName());

		// Checkar om det sparats i exercises också
		List<Exercise> exercises = exerciseRepository.findByWorkoutId(saved.getWorkoutId()); 
		assertEquals("Push-ups", exercises.get(0).getExerciseName()); 

	}

	// testar att vår registreringsmetod fungerar 
	@Test
	void registerTest(){

		authService.register("test", "testpass", "testmail");
		
		assertTrue(userRepository.existsByUsername("test"));

	}

	// testar att vår inloggning fungerar och att vi får en token tillbaka 
	@Test
	void loginTest() {

		// Behöver registrera en testanvändare temporärt
		authService.register("test", "test123", "test@gmail.com");

		// Mockar upp en inloggningsrequest med samma värden som vi temporärt registrerade en användare 
		AuthRequest loginRequest = new AuthRequest(); 
		loginRequest.setUsername("test");
		loginRequest.setPassword("test123");

		// Testar att logga in 
		AuthResponse response = authService.login(loginRequest);

		// Checkar att vi fick en respons när vi försökte logga in 
		// och sedan kollar vi att vi fick tillbaka vad vi väntade oss
		assertNotNull(response); 
		assertEquals("test", response.getUsername());
		assertNotNull(response.getToken());	

	}

}
