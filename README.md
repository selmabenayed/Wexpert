# Wexpert
This is the README.TXT

STMPBenchmarkProject is a simple web java application that use:
1-primefaces5+jsf as fronted
2-managedbeans
3-hibernate
4-mysqlserver
5-maven
6-Junit4
7-Glassfish 4 (but we can run it with other server like Tomcate but Tomcate needs to add in its lib folder all the jars of JSF librairies 
here is a link http://www.horstmann.com/bigj2/tomcat-jsf.html
)
8-Netbeans 7

The application is implemented the SMTP JAVAMAIL API and improve the following issues:

    1-	The application MUST work with an SMTP service with username/pasword autentication
    2-	The application MUST measure min/max/avg time of message delivery
    3-	The application MUST measure the impact of the size of the message
    4-	The application MAY evaluate the impact of sending multiple messages on the same SMTP connection


I tried to implement a web app in order to see interfaces in front of the user that expalin all the issues(optionally)

I attached with the application a JAVA test packages with Junit in order to test all the functionalities of the applications
without running it on a server.

The entities/models of the applications are composed of:
1- The UserEntity:  the concerning user to send messages throw SMTP, it contians:
    - firstName, lastname
    - login is unique
    - email and password for the account SMTP which must be correct values as your real mail.
2-the SMTPProperties: the properties of SMTP server
3-SMTPMessage: the SMTP Message that describe the date, the number of messages send in the given date attached with the concerning user

The application will automaticaly create the database structure, and to access to the application, we must create a user(there is a registration page for that)

Also we must put all the properties of SMTP server in SMTPproperties table: 

    - mail.smtp.auth = true
    - mail.smtp.starttls.enable = true
    - mail.smtp.host = smtp.gmail.com
    - mail.smtp.port = 587
    - mail_user and mail_password are setted autmaticly after login (equal to the email and password of the connectedUser)


The application can send a message with a list of attached Files, the files are staticaly located in ressources/files , we can after add functionnality to attach a file

The script of the data base is in ressource/files under web pages folder called scripte_smtp.sql,the scripte containes
some data, only it needs to update the given mail_user (the email of the user), mail_password(the password of the email and the user) 

The web application works with a full Server like Glassfish that support JSF (Optional functionnality for the task)

---------------------------------------------------
In order to test the application without Glassfish, we can use the class testSendEmail located in the package test
----------------------------------------------------

testSendEmail is Test class with Junit, throw it we can:

    - create the user
    - create all the properties
    - sendMessage with different method 
    - measure min/max/avg time of message delivery : calculated by the method getMinMaxAvgMessageDelivery in SMTPMessageRepository
    - measure the impact of the size of the message : the size rendred in the map of sendEmail method : when attached very big file
        the size of the message will be higher
    - evaluate the impact of sending multiple messages on the same SMTP connection : i implemented the method sendEmailRepeatdly for send SMPT message
     in repeated way and the sendEmail with only one time

In order to test the sendEmail, if using gmail , it is better to disable the option for sucured application using this link
https://www.google.com/settings/security/lesssecureapps


application.properties is a file that contains the persistence configuration for hibernate with mysql driver

log4j.properties for logger issues

spring-security.xml for authentication of the application



