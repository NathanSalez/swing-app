# Swing dummy app

## Prerequisites
* Java 11, Intellij Idea.
* Maven configured.
* Git.
### Description
This application allows user to register and connect through a desk application.<br/>
### APIs used
* Optional
* Lombok
* Swing
* JDBC & H2
* Jasypt
* JUnit
### Design patterns
* Singleton
* Abstract Factory
* DAO
* Multitier (view => service => dao)
* Template method
### Project management
* editorconfig
* git
* Maven
## Install the application
1. Clone the project into your computer :
```bash
git clone https://github.com/NathanSalez/swing-dummy-app.git
```
2. Import the project by clicking on the file **pom.xml** in Intellij Idea.
3. Go to Main.java and type Ctrl+Maj+F10 to launch the application.
## Configure the application
By default, the application uses a JDBC connexion to interact with an embedded database.<br/>
#### Change the persistence type
To change the persistence type used by the application, go to UserService.java and change the following line :
```java
userDao = DaoFactory.getDaoFactory(PersistenceType.JDBC).getUserDao();
```
To use JPA, set the persistence type to JPA.
#### Change the database connexion
Go to **resources/jdbc-dao.properties** and change the fields as you want.<br/>
The field **sql** is optional and contains the path to a sql script, which will be executed at the beginning of the application.
#### Change the log system
Go to **resources/log4j2.properties** and change the fields as you want.<br/>
If you want to see the logs in a file : please check https://howtodoinjava.com/log4j2/log4j2-properties-example/
