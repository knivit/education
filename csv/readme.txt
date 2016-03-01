# Building

mvn clean package


# Using

1) Given number of files (2) will be generated to the current folder
java -jar target/csv-generator-1.0.jar . 2 10000 WebStats.xml

2) The same as 1) but that will be repeated 3 times (each time is within 60 sec interval)
java -jar target/csv-generator-1.0.jar . 2 10000 WebStats.xml 3 60
