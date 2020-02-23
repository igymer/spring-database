package config;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class EmbeddedDbConfigTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedDbConfigTest.class);

    @Test
    public void dataSource() {
        GenericApplicationContext context = new AnnotationConfigApplicationContext(EmbeddedDbConfig.class);
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);
    }

    private void testDataSource(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from singer");
            ResultSet resultSet = statement.executeQuery();
            resultSet.last();
            assertEquals(3, resultSet.getRow());
            statement.close();
        } catch (SQLException e) {
            LOGGER.warn("Something unexpected happened.", e);
        }
    }
}