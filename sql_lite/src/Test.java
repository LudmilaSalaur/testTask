import java.io.*;
import java.sql.*;
import java.util.StringTokenizer;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        /*
        Sample to create db taken from https://bitbucket.org/xerial/sqlite-jdbc/wiki/Usage%20
         */
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        File f = null;
        File fb = null;
        try {
            //find the source file and read data
            f = new File("C:\\Program Files\\Java\\jdk1.8.0_45\\Interview-task-data-osh (2).csv");
            Timestamp t = new Timestamp(System.currentTimeMillis());
//            fb = new File("C:\\Program Files\\Java\\jdk1.8.0_45\\bad-data-" + t + ".csv");
//            fb.createNewFile();
            System.out.println("Hi   " + f.exists());

            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
//            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("create table person (a string, b string, c string, d string, e string, f string, g string, h string, i string, j string)");

            BufferedReader fIn = new BufferedReader(new FileReader(f));
//            PrintWriter fOut = new PrintWriter(new BufferedWriter(new FileWriter(fb)));

            String s = fIn.readLine();
            System.out.println(s);
            String[] s_arr = null;
            StringTokenizer tok = null;
            while ((s = fIn.readLine()) != null) {
                tok = new StringTokenizer(s, ";");
                if (tok.countTokens() < 10) System.out.println("Error! " + tok.countTokens());
                    //fOut.println(tok.toString());
                else {
                    s_arr = new String[10];
                    int i = 0;
                    while (tok.hasMoreTokens()) {
                        s_arr[i] = tok.nextToken();
                        i++;
                    }

                    statement.executeUpdate("insert into person values('" + s_arr[0] +
                            "', '" + s_arr[1] + "', '" + s_arr[2] + "', '" + s_arr[3] +
                            "', '" + s_arr[4] + "', '" + s_arr[5] + "', '" + s_arr[6] +
                            "', '" + s_arr[7] + "', '" + s_arr[8] + "', '" + s_arr[9] + "')");
                    System.out.println("Tok written!");
                }
            }

            ResultSet rs = statement.executeQuery("select * from person");
            System.out.println("Hello!");
            while (rs.next()) {
                // read the result set
                System.out.println("a = " + rs.getString("a"));
                System.out.println("b = " + rs.getString("b"));
                System.out.println("c = " + rs.getString("c"));
                System.out.println("d = " + rs.getString("d"));
                System.out.println("e = " + rs.getString("e"));
                System.out.println("f = " + rs.getString("f"));
                System.out.println("g = " + rs.getString("g"));
                System.out.println("h = " + rs.getString("h"));
                System.out.println("i = " + rs.getString("i"));
                System.out.println("j = " + rs.getString("j"));
                System.out.println("---------------------------------------------");
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e1) {
            System.out.println("File not found!: " + f.getAbsolutePath());
        } catch (IOException ioe){
            System.out.println("IOException! Error reading file!");
        }finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}

