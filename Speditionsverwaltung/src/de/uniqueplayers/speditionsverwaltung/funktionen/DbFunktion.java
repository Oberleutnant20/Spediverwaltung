package de.uniqueplayers.speditionsverwaltung.funktionen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Erzeugung von HTML-Konstrukten für den Dialog mit Datenbanken.
 *
 * @author Oberleutnant20
 */
public class DbFunktion {

    /**
     * Verbindung zu einer Datenbank aufbauen. Beim Auftreten von Fehlern
     * erfolgt kein Auslösen einer Exception. Es wird eine Fehlermeldung auf
     * die Konsole ausgegeben.
     *
     * @param url Adresse und Namen der DB
     * @param usr DB-Anwender-Login
     * @param passwd Passwort
     * @return Connection zur Datenbank oder null, wenn ein Fehler auftritt
     */
    public static Connection connect(String url, String usr, String passwd) {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Keine Treiber-Klasse!");
        }

        Connection con = null;
        try {
            con = DriverManager.getConnection(url, usr, passwd);
        } catch (SQLException ex) {
            Logger.getLogger(DbFunktion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    // DbHtml.disconnect(con);

    /**
     * Erzeugt html-Tabellenzeilen mit den Werten der Attribute einer
     * Tabellenzeilen.
     *
     * @param rs
     * @param args
     * @return
     * @throws java.sql.SQLException
     */
    public static String htmlRows(ResultSet rs, String... args) throws SQLException {
        String table = "";

        while (rs.next()) { // Alle Ergebniszeilen bearbeiten
            table += "<tr>";
            for (String arg : args) { // alle gesuchten Attribute
                String zelle = rs.getString(arg);
                table += "<td>" + zelle + "</td>";
            }
            table += "</tr>\n";
        }
        return table;
    }

    public static String htmlRowsDelete(ResultSet rs, String... args) throws SQLException {
        String table = "";
        String zaehlen = "";

        Connection con = connect("jdbc:hsqldb:hsql://localhost/", "SA", "");
        Statement stm = con.createStatement();

        while (rs.next()) { // Alle Ergebniszeilen bearbeiten
            table += "<tr>";
            table += "<td><input type='radio' name='check' value='" + rs.getString("ID") + "'/></td>";
            for (String arg : args) { // alle gesuchten Attribute
                String zelle = rs.getString(arg);
                table += "<td>" + zelle + "</td>";
            }
            table += "</tr>\n";
        }

        return table;
    }

    /**
     * Erzeugt den Kopf einer HTML-Tabelle. z.B. htmlTableHead( "firstname",
     * "lastname") =>
     * <table><row><data>firstname</data><data>lastname</data></row>
     *
     * @param args Feld für Zeichenketten mit variabler Länge
     * @return
     */
    public static String htmlTableHead(String... args) {
        String table = "<table border='1'><tr>";
        for (String arg : args) {
            table += "<th>" + arg + "</th>";
        }
        return table + "</tr>";
    }

    public static String htmlTableFoot() {
        return "</table>\n";
    }

    public static ResultSet getResult(Connection con, String table, String idName, String[] ids) {
        if (con == null || table == null || idName == null || ids == null || ids.length == 0) {
            return null;
        }

        String sql = "SELECT * FROM " + table + " WHERE " + idName + " IN( ";
        for (String id : ids) {
            sql += id + ", ";
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += ")";
        ResultSet rs = null;
        try (Statement stm = con.createStatement()) {
            rs = stm.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("getResult: ");
            System.err.println("SQL: " + sql);
        }

        return rs;
    }
}
