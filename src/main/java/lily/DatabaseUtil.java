package lily;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatabaseUtil {

    static Logger log = LogManager.getLogger("MAIN");
    static Connection c;
    private static boolean init;

    private static void initDatabase() throws SQLException {
        Statement stmt = c.createStatement();

        stmt.execute("CREATE TABLE version(version INT)");
        stmt.execute("INSERT INTO version VALUES(1)");
        log.info("Created table: version");
    }

    private static void initDatabaseUtil() throws SQLException {
        log.info("Initializing database...");
        c = DriverManager.getConnection("jdbc:sqlite:target/test.db");

        Statement statement = c.createStatement();

        init = true;
    }

    public static void ensureInit() throws SQLException {
        if (!init) {
            initDatabaseUtil();
        }
    }

    public static int getDatabaseVersion() throws SQLException {
        ensureInit();

        Statement stmt = c.createStatement();
        ResultSet result = null;

        try {
            c.beginRequest();
            result = stmt.executeQuery("SELECT * FROM version;");
        } catch (SQLException e) {
            // If database does not exist yet, initialize and re-run
            log.error("Database not initialized yet! Deferring...");
            initDatabase();
            return getDatabaseVersion();
        }

        return result.getInt("version");
    }

    public static String checksum(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(file);

        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        fis.close();

        byte[] bytes = digest.digest();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

}
