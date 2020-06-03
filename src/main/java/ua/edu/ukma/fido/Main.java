package ua.edu.ukma.fido;

import ua.edu.ukma.fido.utils.DB;
import ua.edu.ukma.fido.utils.Table;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public final static String dbName = "database.db";
    public final static String tableName = "counter";

    public static void main(String[] args) {
        DB.connect();
        Table.create();

        Table.insert(1, "First");
        Table.insert(2, "Second");
        Table.insert(3, "Sord");

        Table.update(3, "Third");

        ResultSet oneByTitle = Table.selectOneByTitle("Second");
        printResultSet("oneByTitle", oneByTitle);

        ResultSet oneLimitOffset = Table.selectOneLimitOffset(1, 2);
        printResultSet("oneLimitOffset", oneLimitOffset);

        ResultSet all = Table.selectAll();
        printResultSet("all", all);

        Table.delete(2);

        ResultSet allAfterDelete = Table.selectAll();
        printResultSet("allAfterDelete", allAfterDelete);

        Table.truncate();

        DB.close();
    }

    public static void printResultSet(String resultSetName, ResultSet resultSet) {
        System.out.println(resultSetName + ":");
        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") +  "\t" + resultSet.getString("title"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println();
    }
}
