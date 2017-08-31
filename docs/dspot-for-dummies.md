### Environment Set-up
1. [Install Java 8](https://www.java.com/en/download/help/download_options.xml)
1. [Install GIT](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
1. [Install Maven](https://maven.apache.org/install.html)

### Clone DSpot and create jar

1. From the root folder, clone the project:
```
git clone https://github.com/STAMP-project/dspot.git
```
1. Access to the dspot project directory
```
cd dspot
```
1. Create DSpot jar (eg `target/dspot-1.0.0-jar-with-dependencies.jar`)
```
mvn package -DskipTests
```

### Clone and Compile Maven Project (DHELL)

1. From the root folder, clone the project
```
git clone https://github.com/STAMP-project/dhell.git
```
1. Access to the project directory
```
cd dhell
```
1.  Compile application and tests, and run tests:
```
mvn clean package
```

### Execute DSpot

1. From the root folder copy the configuration to the file dhell/dspot.properties
```
#relative path to the project root from dspot project
project=../dhell
#relative path to the source project from the project properties
src=src/main/java/
#relative path to the test source project from the project properties
testSrc=src/test/java
#java version used
javaVersion=8
# (optional) path to the output folder, default to "output_diversify"
outputDirectory=dspot-out/
```
1. Execute DSpot
```
cd dspot
java -jar target/dspot-1.0.0-jar-with-dependencies.jar  -p ../dhell/dspot.properties
```