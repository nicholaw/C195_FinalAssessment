#C195-QAM1 Final Assessment: Appointment Scheduler

This application serves as the final assessment for WGU's C195: Advanced Java Concepts course.

This application allows a user to add or edit customers to the database of a hypothetical international corporation 
and edit or schedule appointments for those customers. The application can also generate reports regarding how many 
appointments of each type are scheduled for each month. The hypothetical corporation has offices in Arizona, New York,
Montreal, and the United Kingdom, and therefore offers services in English and French across multiple time zones.

###Author: 
Nicholas Warner
nwarn17@wgu.edu

###Version:    
0.9.0 
July, 13, 2021

###IDE:
IntelliJ IDEA 2021.1.3 (Community Edition)
Build #IC-211.7628.21
June 29, 2021

###Resources:
JDK 11.0.9
JavaFX 11.0.2
MySQL-Connector-Java 8.0.23

##Directions For Use:
1. Log on to the application be entering your user credentials in the login screen (For the purposes of the final assessment username: 'test'  password: 'test'  will grant access to the application).
2. Add a new customer to the database by selecting the 'Add' button at the bottom of the customer overview screen and filling out the customer's information on the following form.
3. Delete a customer from the database by selecting a customer from the tableview on the customer overview screen and selecting the 'Delete' button. NOTE: you will not be able to delete the customer if they have any outstanding appointments.
4. Edit a customer by selecting a customer from the tableview on the customer overview screen and selecting the 'Edit' button. This will take you to a form where you may edit the customer's information.
5. Schedule an appointment for a customer by selecting a customer from the tableview on the customer overview screen and selecting the 'View Appointments' button. On the next screen, select the 'Schedule Appointment' button and fill out the form with the appointment information.
6. Edit a customer's appointment by selecting a customer from the tableview on the customer overview screen, selecting the 'View Appointments' button, selecting the appointment you would like to edit from the tableview on the following appointments overview screen, and then selecting the 'Edit Appointment' button. This will take you to a form where you may edit the appointment's information.
7. To view a report of the all the appointments scheduled for each month, select the 'Generate Report' button on the customer overview screen.

Reports are listed on a month-to-month basis and show how many appointments of each type and in each location are scheduled for the selected month.