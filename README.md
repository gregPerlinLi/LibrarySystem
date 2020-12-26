# **Library System Manager**

## **A commonline based library manage system with Java & MySQL**

## By: gregPerlinLi

## Project Description

### Project requirements

​        A library management system is designed under the offline environment by Java & MySQL, in which the functions of library information management and library staff management can be realized

### Description of the packages:

- **`sql`**: Placing the database files
- **`com.gregperlinli.bean`**: Entity class corresponding to database table one by one (the properties of the class correspond to the keys of the table in the database one by one)
- **`com.gregperlinli.dao`**: **Database Acess Object** Place database or file crud **(check, read, update and delete)** code
- **`com.gregperlinli.service`**: Place the code to process the transaction logic and isolate the Dao layer from the view layer
- **`com.gregperlinli.util`**: Utilities class, place the common code for all projects which has nothing to do with transaction logic
- **`com.gregperlinli.view`**: Place interface related code
- **`com.gregperlinli.junit`**: Using to debug DAO service, if you want to use it, please add the library `JUnit` (Minium version is **5.4**)

### MySQL table structure

- **`User`**: Storge the account data of the user (each user corresponding to a common staff or curator)
- **`CommonStaff`**: Storge the common staff information
- **`Curator`**: Storge the curator information
- **`Book`**: Storage book information
- **`Schedule`**: Storage the scheduling of the comon staff (under construction)



---

## **Preparation**

### Basic requirements：

- **`Java`**: Required version is **15.0** or higher

- **`MySQL`**: Required version is **8.0.22** or higher

### Required external libraries:

- **`Mysql connector java`** MySQL JDBC driver (Minium version is **8.0.16**)
- **`Druid`** a database connection pool utility (Minium version is **1.2.4**)
- **`JUnit`** Code debug utility (**optionl**, Minium version is **5.4**)

### Environment configuration

- Run `.sql/LibrarySystem.sql` to import the database

### Configurate database:

JDBC configure

```properties
# get basic information

# login username
user=librarySystemManager

# login password
password=123456

# the url of the database
url=jdbc:mysql://localhost:3306/LibrarySystem?rewriteBatchedStatements=true

# database driver
driverClass=com.mysql.cj.jdbc.Driver
```

Druid configure

```properties
# Provide four basic information to get the connection
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/LibrarySystem?rewriteBatchedStatements=true
username=librarySystemManager
password=123456

# The basic information to manage the database connection pool
initialSize=10
maxActive=10
```



