INTRODUCTION

Triage is an application designed for nurses and physicians to manage the patients in and out of an emergency room. After running the app, you will get to the login page and you can login using a valid user name & password combination (user names and passwords can be found in passwords.txt in the asset folder). The app will then log you in and you can test all the features by clicking corresponding buttons.

WHERE TO FIND THE CODE

Our application Triage is inside the app folder of the root folder of repositary. Inside src/* is the com.example.triage package which stores the source codes for the application.

ENHANCEMENT WE MADE

We used SQLite database to store patient data, improved user interface and made the app very convenient to use.

FEATURE LIST

-Nurses and physicians can launch the triage application and log in using a username and password stored in the file passwords.txt.
-Nurses can create new patient records and record individual patient data (name, birth date, and health card number)
-Nurses can record the date and time when a patient has been seen by a doctor.
-Nurses can access a list of patients (name, birth date and health card number) who have not yet been seen by a doctor categorized and ordered by decreasing urgency according to hospital policy.
-Using the health card number, physicians can look up a patient's record, which contains all data recorded about that patient.
-Physicians can record prescription information (name of the medication and instructions) for a given patient. (Notice that this information becomes part of the patient's record.)
- Nurses and physicians can search patients by health card number if the patient is in the patient data.
- Nurses can add a new visit when a patient comes to the emergency room.
- Nurses can add and update vital signs of the patients while they are waiting.
-Nurses can view previous visit records if exist.

