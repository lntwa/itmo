package org.example.managers;

import org.example.models.City;
import org.example.models.Coordinates;
import org.example.models.Human;
import org.example.models.StandardOfLiving;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

public class DBmanager {
    public static final Logger logger = Logger.getLogger(DBmanager.class.getName());
    private final String url = "jdbc:postgresql://localhost:5433/studs";

    public void createBase() {
        String creationTables = """
                CREATE SEQUENCE IF NOT EXISTS cities_id_seq INCREMENT BY 1 START WITH 1 NO MAXVALUE;
                CREATE SEQUENCE IF NOT EXISTS users_id_seq INCREMENT BY 1 START WITH 1 NO MAXVALUE;
                CREATE SEQUENCE IF NOT EXISTS governors_id_seq INCREMENT BY 1 START WITH 1 NO MAXVALUE;
                
                CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY DEFAULT nextval('users_id_seq'),
                login VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL
                );
                
                CREATE TABLE IF NOT EXISTS governors (
                id INTEGER PRIMARY KEY DEFAULT nextval('governors_id_seq'),
                name TEXT NOT NULL,
                age BIGINT NOT NULL CHECK(age > 0),
                height INTEGER NOT NULL CHECK(height > 0)
                );
                
                CREATE TABLE IF NOT EXISTS cities (
                id INTEGER PRIMARY KEY DEFAULT nextval('cities_id_seq'),
                name TEXT NOT NULL,
                x DOUBLE PRECISION NOT NULL CHECK(x < 330),
                y BIGINT NOT NULL,
                creation_date TIMESTAMP NOT NULL,
                area DOUBLE PRECISION NOT NULL CHECK(area > 0),
                population INTEGER NOT NULL CHECK(population > 0),
                metersabovesealevel BIGINT NOT NULL,
                timezone DOUBLE PRECISION NOT NULL CHECK(timezone > -13 and timezone <= 15),
                populationdensity FLOAT NOT NULL CHECK(populationdensity > 0),
                standardofliving VARCHAR(100) CHECK(standardofliving in ('VERY_HIGH', 'HIGH', 'LOW', 'ULTRA_LOW') OR standardofliving IS NULL),
                owner INTEGER NOT NULL REFERENCES users(id),
                governor INTEGER NOT NULL REFERENCES governors(id)
                );
                """;
        try (Statement statement = getConn().createStatement()) {
            statement.execute(creationTables);
        } catch (SQLException e) {
            logger.info("Ошибка при подключении к бд! ");
        }
    }

    public Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            String propertiesPath = System.getenv("DB_PROPERTIES");
            if (propertiesPath == null) {
                logger.info("Переменная окружения DB_PROPERTIES не найдена");
                System.exit(-1);
            }
            Properties properties = new Properties();
            properties.load(new FileInputStream(propertiesPath));
            String username = properties.getProperty("login");
            String password = properties.getProperty("password");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            logger.info("Драйвер JDBC не найден");
            System.exit(-1);
        } catch (SQLException e) {
            logger.info("Ошибка при подключении к базе данных");
            System.exit(-1);
        } catch (IOException e) {
            logger.info("Не получилось загрузить данные из файла конфигурации");
            System.exit(-1);
        }
        return null;
    }

    public Connection getConn() {
        return connect();
    }

    public int insertCity(City city) {
        try (Connection connection = getConn()) {
            // вставка мэра
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO governors(name, age, height) VALUES (?, ?, ?) RETURNING id"
            );
            stmt.setString(1, city.getGovernor().getName());
            stmt.setLong(2, city.getGovernor().getAge());
            stmt.setInt(3, city.getGovernor().getHeight());

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Не удалось получить id губернатора.");
            }

            int governorId = rs.getInt("id");
            rs.close();
            stmt.close();

            // вставка города
            stmt = connection.prepareStatement(
                    "INSERT INTO cities(name, x, y, creation_date, area, population, metersabovesealevel, timezone, populationdensity, standardofliving, owner, governor) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, city.getName());
            stmt.setDouble(2, city.getCoordinates().getX());
            stmt.setLong(3, city.getCoordinates().getY());
            stmt.setDate(4, java.sql.Date.valueOf(city.getCreationDate()));
            stmt.setDouble(5, city.getArea());
            stmt.setInt(6, city.getPopulation());
            stmt.setLong(7, city.getMetersAboveSeaLevel());
            stmt.setDouble(8, city.getTimezone());
            stmt.setFloat(9, city.getPopulationDensity());
            stmt.setString(10, city.getStandardOfLiving() != null ? city.getStandardOfLiving().toString() : null);
            int ownerId = getIdByOwner(city.getOwner());
            stmt.setInt(11, ownerId);
            stmt.setInt(12, governorId);

            stmt.executeUpdate();
            ResultSet result = stmt.getGeneratedKeys();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка вставки данных!");
            System.out.println(e.getMessage() + " (код ошибки: " + e.getErrorCode() + ")");
        }
        return -1;
    }


    public Integer getIdByOwner(String login) {
        String query = "SELECT id FROM users WHERE login = ?";
        try (Connection connection = getConn();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, login);  // установка параметра запроса

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return null; // пользователь не найден
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении ID пользователя по логину", e);
        }
    }

    synchronized public void loadCollection(ConcurrentLinkedDeque<City> collection) {
        try (Connection connection = getConn()){
            collection.clear();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cities JOIN governors ON cities.governor = governors.id");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int cityid = rs.getInt(1);
                String cityName = rs.getString(2);
                double x = rs.getDouble(3);
                Long y = rs.getLong(4);
                LocalDate creationDate = rs.getDate(5).toLocalDate();
                Double area = rs.getDouble(6);
                Integer population = rs.getInt(7);
                long metersabovesealevel = rs.getLong(8);
                Double timezone = rs.getDouble(9);
                Float populationDensity = rs.getFloat(10);
                StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(rs.getString(11));
                String owner = rs.getString(12);
                String nameGovernor = rs.getString(13);
                Long age = rs.getLong(14);
                Integer height = rs.getInt(15);
                Human governor = new Human(nameGovernor, age, height);
                City city = new City(cityid, cityName, new Coordinates(x,y), creationDate, area, population, metersabovesealevel, timezone, populationDensity, standardOfLiving,owner, governor);
                collection.add(city);
            }

        } catch (SQLException e) {
            collection = new ConcurrentLinkedDeque<>();
        }
    }

    public City getById(int id) {
        try (Connection connection = getConn()){
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cities JOIN governors ON cities.governor = governors.id where cities.id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int cityid = rs.getInt(1);
                String cityName = rs.getString(2);
                double x = rs.getDouble(3);
                Long y = rs.getLong(4);
                LocalDate creationDate = rs.getDate(5).toLocalDate();
                Double area = rs.getDouble(6);
                Integer population = rs.getInt(7);
                long metersabovesealevel = rs.getLong(8);
                Double timezone = rs.getDouble(9);
                Float populationDensity = rs.getFloat(10);
                StandardOfLiving standardOfLiving = StandardOfLiving.valueOf(rs.getString(11));
                String owner = rs.getString(12);
                String nameGovernor = rs.getString(13);
                Long age = rs.getLong(14);
                Integer height = rs.getInt(15);
                Human governor = new Human(nameGovernor, age, height);
                return new City(cityid, cityName, new Coordinates(x,y), creationDate, area, population, metersabovesealevel, timezone, populationDensity, standardOfLiving, owner, governor);
            } else return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean deleteById(int id) {
        try (Connection connection = getConn()){
            PreparedStatement stmt = connection.prepareStatement("delete from cities where id = ? returning governor");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stmt = connection.prepareStatement("delete from governors where id = ?");
                stmt.setLong(1, rs.getLong(1));
                stmt.execute();
                return true;
            } else return false;

        } catch (SQLException e) {
            return false;
        }

    }

    public ResultSet executeQuery(String sql) {
        try (Connection connection = getConn()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }

    public String getCitiesString() {
        try {
            StringBuilder info = new StringBuilder();
            ResultSet rs = executeQuery("SELECT * FROM cities order by name");
            while (rs.next()) {
                info.append(getById(rs.getInt("id")) + "\n\n");
            }
            return info.toString().trim();
        } catch (SQLException e) {
            return "";
        }
    }

    public ConcurrentLinkedDeque<City> getCities() {
        ConcurrentLinkedDeque<City> cities = new ConcurrentLinkedDeque<>();
        try (Connection conn = getConn();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM cities ORDER BY name");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                City city = getById(rs.getInt("id"));
                if (city != null) cities.add(city);
            }
        } catch (SQLException e) {
            logger.warning("Ошибка при получении городов: " + e.getMessage());
        }
        return cities;
    }

    public boolean registerUser(String login, String password) {
        try (Connection connection = getConn()){
            PreparedStatement st = connection.prepareStatement("insert into users(login,password) values(?,?)");
            st.setString(1, login);
            st.setString(2, password);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean exists(String login, String password) {
        try (Connection connection = getConn()){
            PreparedStatement st = connection.prepareStatement("select password from users WHERE login=?");
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                if (password.equals(rs.getString(1))) return true;
                else return false;
            } else return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(int id, City newCity) {
        try (Connection connection = getConn()){
            String query = "update cities set name=?, x=?, y=?,creation_date=?,area=?,population=?,metersabovesealevel=?,timezone=?,populationDensity=?,standardofliving=?,owner=?,governor=? where id=? returning governor";
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, newCity.getName());
            st.setDouble(2, newCity.getCoordinates().getX());
            st.setLong(3, newCity.getCoordinates().getY());
            st.setDate(4, java.sql.Date.valueOf(newCity.getCreationDate()));
            st.setDouble(5, newCity.getArea());
            st.setInt(6, newCity.getPopulation());
            st.setLong(7, newCity.getMetersAboveSeaLevel());
            st.setDouble(8, newCity.getTimezone());
            st.setFloat(9, newCity.getPopulationDensity());
            st.setString(10, newCity.getStandardOfLiving().toString());
            st.setString(11, newCity.getOwner());
            st.setString(12, newCity.getGovernor().toString());
            st.setInt(13, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                query = "update cities set name=?,age=?, height=? where id=?";
                st = connection.prepareStatement(query);
                st.setString(1, newCity.getGovernor().getName());
                st.setLong(2, newCity.getGovernor().getAge());
                st.setInt(3, newCity.getGovernor().getHeight());
                st.setLong(5, rs.getLong("governor"));
                return true;
            } else return false;
        } catch (SQLException e) {
            return false;
        }
    }


    public boolean clear(String login) {
        try (Connection connection = getConn()){
            PreparedStatement stmt = connection.prepareStatement("delete from cities where owner = ?");
            stmt.setString(1, login);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


}
