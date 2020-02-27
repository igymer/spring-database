package dao.impl;

import dao.SingerDao;
import entities.Singer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcSingerDao implements SingerDao {

  private DataSource dataSource;
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<Singer> findAll() {
    return null;
  }

  @Override
  public List<Singer> findByFirstName(String firstName) {
    return null;
  }

  @Override
  public String findLastNameById(Long id) {
    String sql = "select last_name from singer where id = :singerId";
    Map<String, Object> params = new HashMap<>();
    params.put("singerId", id);
    return namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
  }

  @Override
  public String findFirstNameById(Long id) {
    return null;
  }

  @Override
  public void insert(Singer singer) {

  }

  @Override
  public void update(Singer singer) {

  }

  @Override
  public void delete(Long singerId) {

  }

  @Override
  public List<Singer> findAllWithDetail() {
    return null;
  }

  @Override
  public void insertWithDetail(Singer singer) {

  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Autowired
  public void setNamedParameterJdbcTemplate(
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }
}
