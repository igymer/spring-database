package config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import dao.SingerDao;
import entities.Singer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class EmbeddedDbConfigTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedDbConfigTest.class);

  @Test
  public void dataSource() {
    GenericApplicationContext context = new AnnotationConfigApplicationContext(
        EmbeddedDbConfig.class);
    DataSource dataSource = context.getBean("dataSource", DataSource.class);
    assertNotNull(dataSource);
    testDataSource(dataSource);
    SingerDao dao = context.getBean(SingerDao.class);
    testDao(dao);
    testFindAllDao(dao);
  }

  private void testDao(SingerDao dao) {
    assertNotNull(dao);
    String lastName = dao.findLastNameById(2L);
    assertEquals("Clapton", lastName);
  }

  private void testFindAllDao(SingerDao dao) {
    assertNotNull(dao);
    List<Singer> singerList = dao.findAll();
    assertEquals(3, singerList.size());
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