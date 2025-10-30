# Workouttracker Backend

Detta är backenden till ett workout projekt. 

Den finns deployad, länken bifogad i botten av denna readme. 

I denna appen så kan man som registrerad användare skapa träningspass och köra dem själv eller med vänner. 

Man kan även köpa träningspass genom vår stripeintegration. 

# Techstack 
- Spring Boot
- Java
- Stripe
- Websocket

Appen går att köra lokalt om man skapar en .env fil och däri placera: 
- SECRET_KEY_OPENAI 
- JWT_SECRET_KEY
- STRIPE_SECRET_KEY

Behöver då själv hämta stripenyckel från stripe och nyckel från openai samt generera en jwt secret key. 

Backenden är byggd med Spring Boot Java och nyttjar en MySQL databas. Projektet nyttjar även websocket och Stripe för att ha premium funktionalitet och för att kunna synka träningspass mellan användare så att man kan träna ihop. 

Backenden skapas genom att skapa en MySQL databas lokalt på sin dator och ändra värdena för: 
- spring.datasource.url=jdbc:mysql://localhost:3306/workouttracker
- spring.datasource.username=workouttracker
- spring.datasource.password=workouttracker
till motsvarande värden på sin MySQL databas. 

Förslagsvis kan man skapa användare och databasen i sin lokala MySQL genom att köra följande kommandon i MySQL i terminalen eller MySQL Workbench: 
- CREATE DATABASE workouttracker; (Detta skapar databasen)
- CREATE USER 'workouttracker'@'localhost' IDENTIFIED BY 'workouttracker'; (Detta skapar en användare för localhost med samma användarnamn och lösenord, enbart ok på localhost)
- GRANT ALL PRIVILEGES ON workouttracker.* TO 'workouttracker'@'localhost'; (Detta ger behörigheter till ens nyskapade användare att göra allting i databsen man skapade)
- FLUSH PRIVILEGES; (Detta applicerar ovan ändring)

Efter man har konfigurerat korrekta värden för databas så kan backenden enkelt startas genom att köra mvn spring-boot:run om man har maven installerat globalt. Alternativt ./mvnw spring-boot:run om man bara har det lokalt. 

Frontend repo: https://github.com/YLudvig/workouttrackerfrontend

Deployad sida: 
