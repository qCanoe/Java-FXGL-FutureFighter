# Java-FXGL-FutureFighter
1.Import the database in mysql, or use your own database
2.Change the information about the database connected to the conn class to ensure that it is
consistent with your own database
public Conn() {
this.host = "localhost";
this.port = 3306;
this.user = "root";
this.password = "123456";
this.database = "mydatabase";
}
3.Open the pom.xml file and reload the project to update maven
4.run the code, input the right username and password
