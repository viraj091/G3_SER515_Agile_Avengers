1. Scrum Simulation Tool

We have made a Java Swing application for simulating Grooming tool.

2. Requirements

- Java 11 or higher
- Maven 3.6+

3. Building and Running

If you are using Maven (Command Line)

To Compile the project - mvn clean compile

Run the application: - mvn exec:java

If you are using VS Code

1. Open the project folder in VS Code
2. Open `src/main/java/com/scrumsim/MainApp.java`
3. Press `F5` or click `Run > Start Debugging`

If you are using IntelliJ IDEA

1. Open the project folder
2. IntelliJ will automatically detect the Maven project
3. Right-click on `MainApp.java` and select `Run 'MainApp.main()'`

If you are using Eclipse

1. File > Import > Existing Maven Projects
2. Select the project folder
3. Right-click on `MainApp.java` > Run As > Java Application


You can log in as Product Owner, Scrum Master and Devloper. 
Use Below combinations of User Names and Passwords - 

Scrum Master - 
User Name - sm
Password - sm123

Product Owner -
User Name - po
Password - po123

Developer -
User Name - dev
Password - dev123

User Name - qa
Password - qa123

User Name - viraj_rathor     | 
password - viraj123 

User Name - gunjan_purohit   
Password - gunjan123

User Name - sairaj_dalvi 
Password - sairaj123

User Name - pranav_irlapale 
password - pranav123

User Name - shreyas_revankar
password - shreyas123

Below is the Project Structure that our team will be using - 

src/main/java/com/scrumsim/
- model/              # Domain models (Story, Member, Team)
- service/            # Business logic services
- navigation/         # Navigation interfaces and implementations
- ui/                 # UI components and factories
- MainApp.java        # Application entry point
