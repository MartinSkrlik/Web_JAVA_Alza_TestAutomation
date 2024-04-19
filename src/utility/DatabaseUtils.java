package utility;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import config.Config.Database;

public class DatabaseUtils extends Exceptions {

    private Database db;
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Connection connection;
    private List<Object> result = new ArrayList<Object>();

    
    public DatabaseUtils(Database db) {
        this.db = db;
    }

    @SuppressWarnings("deprecation")
	public Connection connectToDatabase() throws Exception {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        Class.forName(db.getDriver()).newInstance();
        connection = DriverManager.getConnection(db.getConnectionString(), db.getUser(), db.getPWD());
        Log.info(CONNECTED_MSG);

        return connection;  
    }

    public Object selectValue(String query, String column) {
        
        try {
            Object queryResult = null;
            executeQuery(query);
        
            while (resultSet.next()) {
                queryResult = resultSet.getObject(column);
            }   

            logResult(queryResult);
            result.add(queryResult);   
        }
        catch (Exception e) {
            processException(e);
        }
        finally {
            closeConnection();
        }
        return result.get(0) != null ? result.get(0):"";
    }
       
    public List<String> selectMultipleRowsForSingleColumn(String query, String column) {
        
        List<String> queryResult = new ArrayList<String>();
        
        try {
            executeQuery(query);
        
            while (resultSet.next()) {
                queryResult.add(resultSet.getString(column));
            }   
            logResult(queryResult);
        }
        catch (Exception e) {
            processException(e);
        }
        finally {
            closeConnection();
        }       
        return queryResult;
    }
        
    public List<Object> selectMultipleColumsForSingleRow(String query) {      
        List<Object> queryResult = new ArrayList<Object>();     
        try {
            executeQuery(query);
            
            while (resultSet.next()) {
                for (int i = 1; i < resultSet.getMetaData().getColumnCount() + 1; i++) {
                    queryResult.add(resultSet.getObject(i));
                }
            }     
            logResult(queryResult);
        }
        catch(Exception e) {
            processException(e);
        }
        finally {
            closeConnection();
        }       
        return queryResult;
    }
    
    public List<Object> selectMultipleRowsAndColums(String query) {        
        List<Object> queryResult = new ArrayList<Object>();
        
        try {
            executeQuery(query);
            
            while (resultSet.next()) {
                List<Object> rowResult = new ArrayList<Object>();
                for (int i = 1; i < resultSet.getMetaData().getColumnCount() + 1; i++) {
                    rowResult.add(resultSet.getObject(i));
                }
                queryResult.add(rowResult);
            }     
            
            logResult(queryResult);
        }
        catch(Exception e) {
            processException(e);
        }
        finally {
            closeConnection();
        }       
        return queryResult;
    }
    
    public void insertQuery(String query) { 
        try {
            executeQuery(query);
        }
        catch (Exception e) {
            processException(e);
        }
        finally {
            closeConnection();
        }
    }
        
    public void insertHtmlBlob(String query, String runId, String filePath) {
        try {
            Connection connection = connectToDatabase();
            statement = connection.prepareStatement(query);
            File htmlReport = new File(filePath);
            FileInputStream input = new FileInputStream(htmlReport);
            
            statement.setBinaryStream(1, input, (int) htmlReport.length());
            statement.setString(2, runId);
            statement.execute();
        }
        catch (Exception e) {
            processException(e);
        }
        finally {
            closeConnection();
        }
    }
       
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                Log.info(CONNECTION_CLOSED_MSG);
            }
        }
        catch (Exception e) {
            Log.error(String.format(ERROR_MSG, e.getMessage()));
        }
    }
   
    private void executeQuery(String query) throws Exception {
        Log.info(String.format(QUERY_MSG, query));
        
        Connection connection = connectToDatabase();
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery(query);
    }
       
    private void logResult(Object result) {
        String msg = "";
        if (result instanceof String) {
            msg = (String) result;
        }
        else if (result instanceof List<?>) {
            msg = Arrays.toString(((List<?>) result).toArray());
        }
        
        Log.info(String.format(QUERY_RESULT_MSG, msg));
    }
    
    private final String CONNECTED_MSG = "Connected to database.";
    private final String CONNECTION_CLOSED_MSG = "Database connection closed.";
    private final String ERROR_MSG = "Error when closing databse connection: %s";
    private final String QUERY_MSG = "Executing SQL Query: %s";
    private final String QUERY_RESULT_MSG = "SQL Query result is: %s";
}