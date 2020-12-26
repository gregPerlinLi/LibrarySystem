# **Library System Manager**

## **A commonline based library manage system with Java & MySQL**

## By: gregPerlinLi

## Project Description

### Project requirements

​      Through JavaSE, we develop and design a library management system on the offline environment. In this system, we can realize the functions of library information management and library staff management. The system is oriented to the staff of the library, and it is required to complete the basic management operation in the system.

### Description of the packages:

- **`sql`**: Placing the database files
- **`com.gregperlinli.bean`**: Entity class corresponding to database table one by one (the properties of the class correspond to the keys of the table in the database one by one)
- **`com.gregperlinli.dao`**: **Database Acess Object** Place database or file crud **(create, retrieve, update and delete)** code
- **`com.gregperlinli.service`**: Place the code to process the transaction logic and isolate the Dao layer from the view layer
- **`com.gregperlinli.util`**: Utilities class, place the common code for all projects which has nothing to do with transaction logic
- **`com.gregperlinli.view`**: Place interface related code
- **`com.gregperlinli.junit`**: Using to debug DAO service, if you want to use it, please add the library `JUnit` (Minium version is **5.4**)

### MySQL table structure

- **`User`**: Storge the account data of the user (each user corresponding to a common staff or curator)
- **`CommonStaff`**: Storge the common staff information
- **`Curator`**: Storge the curator information
- **`Book`**: Storage book information
- **`Schedule`**: *Storage the scheduling of the comon staff **(under construction)***



---

## **Preparation**

### Basic requirements：

- **`Java`**: Required version is **15.0** or higher

- **`MySQL`**: Required version is **8.0.22** or higher

### Required external libraries:

- **`Mysql connector java`**: MySQL JDBC driver (Minium version is **8.0.16**)
- **`Druid`**: a database connection pool utility (Minium version is **1.2.4**)
- **`JUnit`**: Code debug utility (**optionl**, Minium version is **5.4**)

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

---

## Functions

### User level and authority

1. Common staff (authority: 10 ~ 90):
    - Query or update the books information
    - Lend or return the books
2. Curator (authority: 100):
    - All functions of common staff
    - Manage the staff information
    - Schedule the staffs (under construction)
3. *Administrator(Under construction):*

### Data interaction

#### Update the data in the database (create, update and delete)

```java
/**
 * @Description  Generic add, delete and update operation (version2.0) (Consider database transaction)
 * @author gregperlinli
 * @param conn connection of database
 * @param sql sql format
 * @param args fill the placeholder(variable)
 *
 * How to output:
 * BaseDAO.update(conn, sql, ...args);
 */
public int update(Connection conn, String sql, Object ...args) throws Exception {  // the length of variable parameters should be equal to the number of placeholders
    PreparedStatement ps = null;
    try {
        // precompiled Statement and return ps instance
        ps = conn.prepareStatement(sql);
        // fill placeholder
        for ( int i = 0; i < args.length; i++ ) {
            // Be careful to the parameter declaration exception
            ps.setObject(i+1, args[i]);
        }
        // perform operation
        return ps.executeUpdate();
    } catch ( Exception e ) {
        e.printStackTrace();
    } finally {
        // close the resource
        JDBCUtills.closeResource(null, ps);
    }
    return 0;
}
```

#### Query the data in the database (retrieve)

##### For one result:

```java
/**
 * @Description Generic query with one object
 *              using PreparedStatement to realize the query operation by different form (version 2.0, consider to transaction)
 * @author gregperlinli
 * @param conn connection of database
 * @param sql sql format
 * @param args fill the placeholder(variable)
 * @return Class or null
 *
 * How to output:
 * ClassName className = getQuery(conn, ClassName.class, sql, ...args);
 */
 public T getQuery(Connection conn, String sql, Object...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            // get the metadata of the result set：ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // get the column of the result set through metadata
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                T t = clazz.newInstance();
                // handle each column in one row of the result set
                for (int i = 0; i < columnCount; i++) {
                    // get the value of the column
                    Object columnValue = rs.getObject(i + 1);
                    // get name of each row
                    String columnName = rsmd.getColumnLabel(i + 1);
                    // give one corresponding "columnName" of the Object "t" assignment to "columnValue" through reflex
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(null, ps, rs);
        }
        return null;
 }
```

##### For Multi Resault:

```java
/**
 * @Description Generic query with multiple object
 *              using PreparedStatement to realize multi query operation by different form (version 2.0, consider to transaction)
 * @author gregperlinli
 * @param conn connection of database
 * @param sql sql format
 * @param args fill the placeholder(variable)
 * @return List or null
 *
 * How to output:
 * List<ClassName> listName = getMultiQuery(Classname.class, sql, ...args);
 *
 * How to print out:
 * listName.forEach(System.out::println);
 */
 public List<T> getMultiQuery(Connection conn, String sql, Object...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            // get the metadata of the result set：ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // get the column of the result set through metadata
            int columnCount = rsmd.getColumnCount();
            // create an aggregate object
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()) {
                T t = clazz.newInstance();
                // handle each column in one row of the result set and assignment the value to object "t"
                for (int i = 0; i < columnCount; i++) {
                    // get the value of the column
                    Object columnValue = rs.getObject(i + 1);
                    // get name of each row
                    String columnName = rsmd.getColumnLabel(i + 1);
                    // give one corresponding "columnName" of the Object "t" assignment to "columnValue" through reflex
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            JDBCUtills.closeResource(null, ps, rs);
        }
        return null;
  }
```

Note: This function are universal, so you can use it in anywhere

