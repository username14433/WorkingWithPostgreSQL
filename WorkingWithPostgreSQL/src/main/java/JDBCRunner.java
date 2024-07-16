package main.java;

import java.sql.*;

public class JDBCRunner {

    private static final String PROTOCOL = "jdbc:postgresql://";        // URL-prefix
    private static final String DRIVER = "org.postgresql.Driver";       // Driver name
    private static final String URL_LOCALE_NAME = "localhost/";         // ваш компьютер + порт по умолчанию

    private static final String DATABASE_NAME = "football_club";          // FIXME имя базы

    public static final String DATABASE_URL = PROTOCOL + URL_LOCALE_NAME + DATABASE_NAME;
    public static final String USER_NAME = "postgres";                  // FIXME имя пользователя
    public static final String DATABASE_PASS = "postgres123";              // FIXME пароль базы данных

    public static void main(String[] args) {

        // проверка возможности подключения
        checkDriver();
        checkDB();
        System.out.println("Подключение к базе данных | " + DATABASE_URL + "\n");

        // попытка открыть соединение с базой данных, которое java-закроет перед выходом из try-with-resources
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, DATABASE_PASS)) {
//            getAllFootballers(connection);
//            getAllClubs(connection);
//            getAllChampionships(connection);
//            getFootballersFromClub(connection);
//            getClubFromChampionship(connection);
//            getFootballersWithPlayingPosition(connection, "Нападающий");
//            addFootballer(connection, "Игорь", "Акинфеев", 22, "Вратарь", 35, 12000000, 1);
//            deleteFootballer(connection, "Игорь", "Акинфеев");
//            getFootballersWithSalary(connection, 25000000, 50000000);
//            correctFootballer(connection, "Игорь", "Акинфеев", 23, "Нападающий", 10, 15000000, 2);
//            getClubsWithFootballersQuantity(connection, 23);
        } catch (SQLException e) {
            // При открытии соединения, выполнении запросов могут возникать различные ошибки
            // Согласно стандарту SQL:2008 в ситуациях нарушения ограничений уникальности (в т.ч. дублирования данных) возникают ошибки соответствующие статусу (или дочерние ему): SQLState 23000 - Integrity Constraint Violation
            if (e.getSQLState().startsWith("23")){
                System.out.println("Произошло дублирование данных");
            } else throw new RuntimeException(e);
        }
    }

    // region // Проверка окружения и доступа к базе данных

    public static void checkDriver () {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Нет JDBC-драйвера! Подключите JDBC-драйвер к проекту согласно инструкции.");
            throw new RuntimeException(e);
        }
    }

    public static void checkDB () {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, DATABASE_PASS);
        } catch (SQLException e) {
            System.out.println("Нет базы данных! Проверьте имя базы, путь к базе или разверните локально резервную копию согласно инструкции");
            throw new RuntimeException(e);
        }
    }

    // endregion

    // region // SELECT-запросы без параметров в одной таблице

    private static void getAllFootballers(Connection connection) throws SQLException {
        String cName0 = "id";
        String cName1 = "name";
        String cName2 = "surname";
        String cName3 = "age";
        String cName4 = "playing_position";
        String cName5 = "game_number";
        String cName6 = "salary";
        String cName7 = "club_id";

        int param0 = -1, param3 = -1, param5 = -1, param6 = -1, param7 = -1;
        String param1 = null, param2 = null, param4 = null;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM footballer;");

        System.out.println("id | name | surname | age | playing position | game number | salary | club_id");
        while (rs.next()){
            param0 = rs.getInt(cName0);
            param1 = rs.getString(cName1);
            param2 = rs.getString(cName2);
            param3 = rs.getInt(cName3);
            param4 = rs.getString(cName4);
            param5 = rs.getInt(cName5);
            param6 = rs.getInt(cName6);
            param7 = rs.getInt(cName7);

            System.out.println(param0 + " | " + param1 + " | " + param2 + " | "
                    + param3 + " | " + param4 + " | " + param5 + " | " + param6 + " | " + param7);
        }
    }
    private static void getAllClubs(Connection connection) throws SQLException {
        String cName0 = "id";
        String cName1 = "club_name";
        String cName2 = "year_of_foundation";
        String cName3 = "championship";
        String cName4 = "sponsor";
        String cName5 = "place";
        String cName6 = "championship_id";
        String cName7 = "trophy_count";

        int param0 = -1, param2 = -1, param5 = -1, param6 = -1, param7 = -1;
        String param1 = null, param3 = null, param4 = null;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM club;");

        System.out.println("id | club name | year of foundation | championship | sponsor | place | championship id | trophy count");
        while (rs.next()){
            param0 = rs.getInt(cName0);
            param1 = rs.getString(cName1);
            param2 = rs.getInt(cName2);
            param3 = rs.getString(cName3);
            param4 = rs.getString(cName4);
            param5 = rs.getInt(cName5);
            param6 = rs.getInt(cName6);
            param7 = rs.getInt(cName7);

            System.out.println(param0 + " | " + param1 + " | " + param2 + " | "
                    + param3 + " | " + param4 + " | " + param5 + " | " + param6 + " | " + param7);
        }
    }

    private static void getAllChampionships(Connection connection) throws SQLException {
        String cName0 = "id";
        String cName1 = "country";
        String cName2 = "number_of_teams";
        String cName3 = "duration";
        String cName4 = "champion_of_last_year";
        String cName5 = "name";


        int param0 = -1, param2 = -1;
        String param1 = null, param3 = null, param4 = null, param5 = null;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM championship;");

        System.out.println("id | country | number of teams | duration | champion of last year | name");
        while (rs.next()){
            param0 = rs.getInt(cName0);
            param1 = rs.getString(cName1);
            param2 = rs.getInt(cName2);
            param3 = rs.getString(cName3);
            param4 = rs.getString(cName4);
            param5 = rs.getString(cName5);


            System.out.println(param0 + " | " + param1 + " | " + param2 + " | "
                    + param3 + " | " + param4 + " | " + param5);
        }
    }
    private static void getFootballersFromClub(Connection connection) throws SQLException {
        String cName0 = "name";
        String cName1 = "surname";
        String cName2 = "club_name";

        String param0 = null, param1 = null, param2 = null;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT footballer.name, footballer.surname, club.club_name FROM footballer JOIN club ON footballer.club_id = club.id;"
        );

        while (rs.next()){
            param0 = rs.getString(cName0);
            param1 = rs.getString(cName1);
            param2 = rs.getString(cName2);

            System.out.println(param0 + " " + param1 + " играет в " + param2);
        }

    }

    private static void  getClubFromChampionship(Connection connection) throws SQLException {
        String cName0 = "club_name";
        String cName1 = "name";

        String param0 = null;
        String param1 = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT championship.name, club.club_name FROM club JOIN championship ON club.championship_id = championship.id;");


        while (rs.next()){
            param0 = rs.getString(cName0);
            param1 = rs.getString(cName1);

            System.out.println("Клуб " + param0 + " играет в чемпионате " + param1);
        }
    }


    private static void getFootballersWithPlayingPosition(Connection connection, String playingPosition) throws SQLException {
        if (playingPosition == null || playingPosition.isBlank()) {
            return;
        }

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT footballer.name, footballer.surname, footballer.playing_position FROM footballer WHERE footballer.playing_position = ? ORDER BY footballer.salary;");
        preparedStatement.setString(1, playingPosition);
        ResultSet rs = preparedStatement.executeQuery();

        String cName0 = "name";
        String cName1 = "surname";
        String cName2 = "playing_position";

        while (rs.next()){
            String param0 = rs.getString(cName0);
            String param1 = rs.getString(cName1);
            String param2 = rs.getString(cName2);

            System.out.println("Игрок " + param1 + " " + param0 + " играет на позиции " + param2);
        }

    }

    private static void deleteFootballer(Connection connection, String name, String surname) throws SQLException {
        if (name == null || name.isBlank() || surname == null || surname.isBlank()){
            return;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM footballer WHERE name = ? AND surname = ?;"
        );
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);

        int count = preparedStatement.executeUpdate();
        System.out.println("Deleted " + count + " footballers");

        getAllFootballers(connection);

    }

    private static void addFootballer(Connection connection, String name, String surname, int age,
                                      String playingPosition, int gameNumber, int salary, int clubId) throws SQLException {
        if (name == null || name.isBlank() || surname == null || surname.isBlank()
        || age < 0 || playingPosition == null || playingPosition.isBlank() || gameNumber < 0 ||
        salary < 0 || clubId < 0){
            return;
        }
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO footballer (name, surname, age, playing_position, game_number, salary, club_id) VALUES(?, ?, ?, ?, ?, ?, ?);"
        );
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setInt(3, age);
        preparedStatement.setString(4, playingPosition);
        preparedStatement.setInt(5, gameNumber);
        preparedStatement.setInt(6, salary);
        preparedStatement.setInt(7, clubId);

        int count = preparedStatement.executeUpdate();
        System.out.println("Added " + count + " footballers");
        getAllFootballers(connection);
    }

    private static void correctFootballer(Connection connection, String name, String surname, int age,
                                          String playingPosition, int gameNumber, int salary, int clubId) throws SQLException {
        if (name == null || name.isBlank() || surname == null || surname.isBlank()
                || age < 0 || playingPosition == null || playingPosition.isBlank() || gameNumber < 0 ||
                salary < 0 || clubId < 0){
            return;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE footballer SET age = ?, playing_position = ? , game_number = ?, salary = ?, club_id = ? WHERE footballer.name = ? AND footballer.surname = ?;"
        );
        preparedStatement.setInt(1, age);
        preparedStatement.setString(2, playingPosition);
        preparedStatement.setInt(3, gameNumber);
        preparedStatement.setInt(4, salary);
        preparedStatement.setInt(5, clubId);
        preparedStatement.setString(6, name);
        preparedStatement.setString(7, surname);

        preparedStatement.executeUpdate();

        System.out.println(name + surname + "'s player data has been changed");
        getAllFootballers(connection);
    }

    private static void getFootballersWithSalary(Connection connection, int minSalary, int maxSalary) throws SQLException {
        if (minSalary < 0 || maxSalary < 0){
            return;
        }
        String cName0 = "id";
        String cName1 = "name";
        String cName2 = "surname";
        String cName3 = "age";
        String cName4 = "playing_position";
        String cName5 = "game_number";
        String cName6 = "salary";
        String cName7 = "club_id";

        int param0 = -1, param3 = -1, param5 = -1, param6 = -1, param7 = -1;
        String param1 = null, param2 = null, param4 = null;

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM footballer WHERE salary BETWEEN ? AND ?"
        );
        preparedStatement.setInt(1, minSalary);
        preparedStatement.setInt(2, maxSalary);

        ResultSet rs = preparedStatement.executeQuery();
        System.out.println("Игроки с зарпллатой от " + minSalary + " до " + maxSalary);
        System.out.println("id | name | surname | age | playing position | game number | salary | club_id");
        while (rs.next()){
            param0 = rs.getInt(cName0);
            param1 = rs.getString(cName1);
            param2 = rs.getString(cName2);
            param3 = rs.getInt(cName3);
            param4 = rs.getString(cName4);
            param5 = rs.getInt(cName5);
            param6 = rs.getInt(cName6);
            param7 = rs.getInt(cName7);

            System.out.println(param0 + " | " + param1 + " | " + param2 + " | "
                    + param3 + " | " + param4 + " | " + param5 + " | " + param6 + " | " + param7);
        }
    }

    private static void getClubsWithFootballersQuantity(Connection connection, int quantity) throws SQLException {
        if (quantity < 0){
            return;
        }
        String cName0 = "id";
        String cName1 = "country";
        String cName2 = "number_of_teams";
        String cName3 = "duration";
        String cName4 = "champion_of_last_year";
        String cName5 = "name";


        int param0 = -1, param2 = -1;
        String param1 = null, param3 = null, param4 = null, param5 = null;

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM championship WHERE number_of_teams > ?;"
        );
        preparedStatement.setInt(1, quantity);

        ResultSet rs = preparedStatement.executeQuery();
        System.out.println("Championships with number of teams more than " + quantity);
        System.out.println("id | country | number of teams | duration | champion of last year | name");
        while (rs.next()){
            param0 = rs.getInt(cName0);
            param1 = rs.getString(cName1);
            param2 = rs.getInt(cName2);
            param3 = rs.getString(cName3);
            param4 = rs.getString(cName4);
            param5 = rs.getString(cName5);

            System.out.println(param0 + " | " + param1 + " | " + param2 + " | "
                    + param3 + " | " + param4 + " | " + param5);
        }
    }
}
