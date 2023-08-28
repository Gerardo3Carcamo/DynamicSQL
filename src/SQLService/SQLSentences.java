/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQLService;

import DatabaseConnection.ConnectionDatabases;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gerardo Rodrigo Chávez Cárcamo
 * @param <T> Class T has a Generic Class (Model) to do a CRUD (Create, Read,
 * Update and Delete) in a Database Oracle
 */
public class SQLSentences<T> {

    private Connection cn;
    private Class<T> clazz;

    public SQLSentences(Class<T> clazz) {
        this.clazz = clazz;
    }

    public SQLSentences() {

    }

    /**
     *
     * @param totalData
     * @param valueToInsert
     * @param separator
     * @return
     * 
     * @since Dynamic SQL 1.0
     */
    private String BuildString(int totalData, String valueToInsert, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < totalData; i++) {
            if (i < totalData - 1) {
                sb.append(valueToInsert).append(separator);
            } else {
                sb.append(valueToInsert);
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param columns
     * @param valuesToInsert
     * @return
     * 
     * @since Dynamic SQL 1.0
     */
    private String BuildStringUpdateOrDelete(String[] columns, String operator, String andOrSentence) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (i < columns.length - 1) {
                sb.append(columns[i]).append(operator).append("? ").append(andOrSentence).append(" ");
            } else {
                sb.append(columns[i]).append(operator).append("? ");
            }

        }
        return sb.toString();
    }
    
    /**
     * 
     * @param columns
     * @param operator
     * @param andOrSentence
     * @return 
     * 
     * @since Dynamic SQL 1.0
     */
    private String BuildStringUpdateOrDelete(String[] columns, String operator, String[] andOrSentence) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (i < columns.length - 1) {
                sb.append(columns[i]).append(operator).append("? ").append(andOrSentence[i]).append(" ");
            } else {
                sb.append(columns[i]).append(operator).append("? ");
            }

        }
        return sb.toString();
    }

    /**
     * This method used to differentiate data types to set into
     * PreparedStatement
     *
     * @param pst
     * @param objects array of objects
     * @throws SQLException
     * 
     * @since Dynamic SQL 1.0
     */
    private void Differentiate(PreparedStatement pst, Object[] objects) throws SQLException {
        int i = 1;
        for (Object o : objects) {
            switch (o.getClass().getName()) {
                case "Z":
                    pst.setBoolean(i, (boolean) o);
                    break;
                case "B":
                    pst.setByte(i, (byte) o);
                    break;
                case "D":
                    pst.setDouble(i, (double) o);
                    break;
                case "F":
                    pst.setFloat(i, (float) o);
                    break;
                case "I":
                    pst.setInt(i, (int) o);
                    break;
                case "S":
                    pst.setShort(i, (short) o);
                    break;
                default:
                    pst.setString(i, (String) o);
                    break;
            }
            i++;
        }
    }

    private void Differentiate(PreparedStatement pst, Object[] objectsUpdate, Object[] objectsConditionals) throws SQLException {
        int i = 1;
        for (Object o : objectsUpdate) {
            switch (o.getClass().getName()) {
                case "Z":
                    pst.setBoolean(i, (boolean) o);
                    break;
                case "B":
                    pst.setByte(i, (byte) o);
                    break;
                case "D":
                    pst.setDouble(i, (double) o);
                    break;
                case "F":
                    pst.setFloat(i, (float) o);
                    break;
                case "I":
                    pst.setInt(i, (int) o);
                    break;
                case "S":
                    pst.setShort(i, (short) o);
                    break;
                default:
                    pst.setString(i, (String) o);
                    break;
            }
            i++;
        }
        for (Object o : objectsConditionals) {
            switch (o.getClass().getName()) {
                case "Z":
                    pst.setBoolean(i, (boolean) o);
                    break;
                case "B":
                    pst.setByte(i, (byte) o);
                    break;
                case "D":
                    pst.setDouble(i, (double) o);
                    break;
                case "F":
                    pst.setFloat(i, (float) o);
                    break;
                case "I":
                    pst.setInt(i, (int) o);
                    break;
                case "S":
                    pst.setShort(i, (short) o);
                    break;
                default:
                    pst.setString(i, (String) o);
                    break;
            }
            i++;
        }
    }

    /**
     *
     * @param query SQL Sentence to get a List of generic Class T (Model). <br>
     * <p>
     * <strong>Example:</strong></p><br>
     * <strong>Database Information:</strong><br>
     * <table  border="1">
     *  <tr>
     *      <th style="text-align:center;">ID</th>
     *      <th style="text-align:center;">Name</th>
     *      <th style="text-align:center;">Country</th>
     *      <th style="text-align:center;">Phone Number</th>
     *  </tr>
     *  <tr>
     *      <td style="text-align:center;">1</td>
     *      <td style="text-align:center;">Liliana Gonzalez</td>
     *      <td style="text-align:center;">México</td>
     *      <td style="text-align:center;">+52 844xxxxxxx</td>
     *  </tr>
     *  <tr>
     *      <td style="text-align:center;">2</td>
     *      <td style="text-align:center;">Nahomi Tovar</td>
     *      <td style="text-align:center;">México</td>
     *      <td style="text-align:center;">+52 844xxxxxxx</td>
     *  </tr>
     * </table>
     * <p>
     * Your class T it must have the following attributes:</p>
     * <ul>
     *  <li>ID</li>
     *  <li>Name</li>
     *  <li>Country</li>
     *  <li>Phone Number</li>
     * </ul>
     * <p>
     * Your class must include all the get and set methods of all the
     * attributes.
     * </p>
     * @param connection You've the possibility to create a new instance of
     * Connection class included into a this library or create your own
     * connetion
     * @return Return a List of Generic class T (Model)
     * @throws SQLException throw this exception if happens any SQL error
     * @throws InstantiationException throw this exception if happens any
     * Instantiation error
     * @throws IllegalAccessException throw this exception if happens any
     * Illegal Access error
     * @throws NoSuchFieldException throw this exception if happens any No Such
     * Field error
     * 
     * @since Dynamic SQL 1.0
     */
    public List<T> DynamicGetListMethod(String query, Connection connection) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<T> lista = new ArrayList<>();
        cn = connection;
        Statement statement = cn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();
        while (resultSet.next()) {
            T obj = clazz.newInstance();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metadata.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(obj, columnValue);
            }
            lista.add(obj);
        }
        resultSet.close();
        statement.close();
        cn.close();
        return lista;
    }

    /**
     * Method to dynamically insert all data into a specific table.
     *
     * @param table Name of the table to which the data will be inserted.
     * @param optionalColumns If you only want to insert certain data, you need
     * to specify which columns you want to insert the data into.<br>
     * This param has to be a string separate by ",".<br>
     * <strong>Example:</strong><br>
     * <p>
     * column_1, column_2, column_3,...,column_n</p>
     * @param objects array of objects to insert into the table.<br>
     * <strong>Example:</strong>
     * <p>
     * Your table contains the next columns:</p>
     * <ul>
     *  <li>ID (Not Null)</li>
     *  <li>Name (Not Null)</li>
     *  <li>Address (Not Null)</li>
     *  <li>Phone Number (Nullable)</li>
     *  <li>Email (Nullable)</li>
     * </ul>
     * <p>
     * If you only wants insert ID, Name and Address, you have to declare the
     * param Optional Columns.</p>
     * @param cn You've the possibility to create a new instance of Connection
     * class included into a this library or create your own connetion.
     * @return if don't throws any Exception, has return true.
     * @throws SQLException throw this exception if happens any SQL error
     * 
     * @since Dynamic SQL 1.0
     */
    public boolean DynamicInsertMethod(String table, String optionalColumns, Object[] objects, Connection cn) throws SQLException {
        this.cn = cn;
        optionalColumns = (optionalColumns.length() > 0 && optionalColumns.contains(",")) ? optionalColumns : "";
        PreparedStatement pst = this.cn.prepareStatement("INSERT INTO " + table + "(" + optionalColumns + ")" + " VALUES (" + BuildString(objects.length, "?", ","));
        Differentiate(pst, objects);
        pst.executeQuery();
        this.cn.close();
        return true;
    }

    /**
     *
     * @param table Name of table of database
     * @param columsToUpdate All columns that you want Update
     * @param objectsToInsertInColumnsToUpdate All values to be set to previously declared columns
     * @param columnsCondition These are the columns which will serve as a condition for data updating
     * @param conditionalValues These are the values that will be used as a condition for updating the
     * columns, these must be ordered as the columns were declared
     * @param cn You've the possibility to create a new instance of Connection
     * class included into a this library or create your own connetion.
     * @return Count of all rows updated 
     * @throws SQLException throw this exception if happens any SQL error
     * 
     * @since Dynamic SQL 1.0
     */
    public int DynamicUpdateMethod(String table,
            String[] columsToUpdate,
            Object[] objectsToInsertInColumnsToUpdate,
            String[] columnsCondition,
            Object[] conditionalValues,
            Connection cn) throws SQLException {
        this.cn = cn;
        String query = "Update " + table + " set " + BuildStringUpdateOrDelete(columsToUpdate, "=", ",")
                + " Where " + BuildStringUpdateOrDelete(columnsCondition, "=", "");
        PreparedStatement pst = this.cn.prepareStatement(query);
        Differentiate(pst, objectsToInsertInColumnsToUpdate, conditionalValues);
        int rowsAffected = pst.executeUpdate();
        this.cn.close();
        return rowsAffected;
    }
    
    /**
     * 
     * @param table Name of table of database
     * @param columsToUpdate All columns that you want Update
     * @param objectsToInsertInColumnsToUpdate All values to be set to previously declared columns
     * @param columnsCondition These are the columns which will serve as a condition for data updating
     * @param conditionalValues These are the values that will be used as a condition for updating the
     * columns, these must be ordered as the columns were declared
     * @param andOrSentence this param allows operations "and"-"or" to use in conditional SQL Sentences.
     * this parameter needs to be declared with a length of -1 than the length of the columnsCondition parameter.<br>
     * <strong>Example:</strong><br>
     * columnsCondition = ["col_1","col_2","col_3","col_4","col_5"]<br>
     * andOrSentence = ["and", "or", "and", "and"]
     *  <table  border="1">
     *      <tr>
     *          <th style="text-align:center;">Param</th>
     *          <th style="text-align:center;">Length</th>
     *      </tr>
     *      <tr>
     *          <td style="text-align:center;">columnsCondition</td>
     *          <td style="text-align:center;">5</td>
     *      </tr>
     *      <tr>
     *          <td style="text-align:center;">andOrSentence</td>
     *          <td style="text-align:center;">4</td>
     *      </tr>
     *  </table>
     * @param cn You've the possibility to create a new instance of Connection
     * class included into a this library or create your own connetion.
     * @return Count of all rows updated 
     * @throws SQLException throw this exception if happens any SQL error
     * 
     * @since Dynamic SQL 1.0
     */
    public int DynamicUpdateMethod(String table,
            String[] columsToUpdate,
            Object[] objectsToInsertInColumnsToUpdate,
            String[] columnsCondition,
            Object[] conditionalValues,
            String[] andOrSentence,
            Connection cn) throws SQLException {
        this.cn = cn;
        String query = "Update " + table + " set " + BuildStringUpdateOrDelete(columsToUpdate, "=", ",")
                + " Where " + BuildStringUpdateOrDelete(columnsCondition, "=", andOrSentence);
        PreparedStatement pst = this.cn.prepareStatement(query);
        Differentiate(pst, objectsToInsertInColumnsToUpdate, conditionalValues);
        int rowsAffected = pst.executeUpdate();
        this.cn.close();
        return rowsAffected;
    }

    /**
     * 
     * @param table Name of table
     * @param conditionalColumns These are the columns which will serve as a condition for data deleting
     * @param conditionalValues These are the values that will be used as a condition for deleting data
     * @param cn You've the possibility to create a new instance of Connection
     * class included into a this library or create your own connetion.
     * @return Count of all rows updated 
     * @throws SQLException throw this exception if happens any SQL error
     * 
     * @since Dynamic SQL 1.0
     */
    public boolean DynamicDeleteMethod(String table, String[] conditionalColumns, Object[] conditionalValues, Connection cn) throws SQLException {
        this.cn = cn;
        String query = "Delete from " + table + " Where " + BuildStringUpdateOrDelete(conditionalColumns, "=", "");
        System.out.println(query);
        PreparedStatement pst = this.cn.prepareStatement(query);
        Differentiate(pst, conditionalValues);
        pst.executeUpdate();
        this.cn.close();
        return true;
    }
    
    /**
     * 
     * @param table Name of table
     * @param conditionalColumns These are the columns which will serve as a condition for data deleting
     * @param conditionalValues These are the values that will be used as a condition for deleting data
     * @param andOrSentence this param allows operations "and"-"or" to use in conditional SQL Sentences
     * @param cn You've the possibility to create a new instance of Connection
     * class included into a this library or create your own connetion.
     * @return Count of all rows updated 
     * @throws SQLException throw this exception if happens any SQL error
     * 
     * @since Dynamic SQL 1.0
     */
    public boolean DynamicDeleteMethod(String table, String[] conditionalColumns, Object[] conditionalValues, String[] andOrSentence, Connection cn) throws SQLException {
        this.cn = cn;
        String query = "Delete from " + table + " Where " + BuildStringUpdateOrDelete(conditionalColumns, "=", andOrSentence);
        PreparedStatement pst = this.cn.prepareStatement(query);
        Differentiate(pst, conditionalValues);
        pst.executeQuery();
        this.cn.close();
        return true;
    }
    
    /**
     * Method to run custom SQL Sentence
     * @param queryWithOutWhereSentence Update sentence<br>
     * <h3>Example:</h3><br>
     * <p>Update table set col_1 = ?, col_2 = ?, col_3 = ?</p><br>
     * <h3>Aclaration!</h3><br>
     * <p>the signs "?" are mandatory, these will be replaced by the 
     * values that will be declared in the "queryValues" parameter</p>
     * @param queryValues All values to be set to previously declared columns
     * @param whereSentence Where sentence<br>
     * <h3>Example:</h3><br>
     * <p>Where col_1 = ?, col_2 = ?, col_3 = ?</p><br>
     * <h3>Aclaration!</h3><br>
     * <p>the signs "?" are mandatory, these will be replaced by the 
     * values that will be declared in the "whereValues" parameter</p>
     * @param whereValues All values to be set to previously declared columns
     * @return true if don't happens any SQL Error
     * @throws SQLException throw this exception if happens any SQL error
     * 
     * @since Dynamic SQL 1.0
     */
    public boolean CustomDynamicUpdateMethod(String queryWithOutWhereSentence, Object[] queryValues, String whereSentence, Object[] whereValues) throws SQLException{
        String query = queryWithOutWhereSentence + " " + whereSentence;
        PreparedStatement pst = this.cn.prepareStatement(query);
        Differentiate(pst, queryValues, whereValues);
        pst.executeQuery();
        return true;
    }

    /**
     * Method to run custom SQL Sentence
     * @param queryWithWhereSentence Update sentence<br>
     * <h3>Example:</h3><br>
     * <p>Delete from table where col_1 = ?, col_2 = ?</p><br>
     * <h3>Aclaration!</h3><br>
     * <p>the signs "?" are mandatory, these will be replaced by the 
     * values that will be declared in the "whereValues" parameter</p>
     * @param whereValues All values to be set to previously declared columns
     * @return true if don't happens any SQL Error
     * @throws SQLException throw this exception if happens any SQL error
     * 
     * @since Dynamic SQL 1.0
     */
    public boolean CustomDynamicDeleteMethod(String queryWithWhereSentence, Object[] whereValues) throws SQLException{
        PreparedStatement pst = this.cn.prepareStatement(queryWithWhereSentence);
        Differentiate(pst, whereValues);
        pst.executeQuery();
        return true;
    }
    
}
