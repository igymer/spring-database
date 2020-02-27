package dao.impl;

import dao.SingerDao;
import entities.Singer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcSingerDao implements SingerDao {

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<Singer> findAll() {
    String sql = "select id, first_name, last_name, birth_date from singer";
    return namedParameterJdbcTemplate.query(sql, (rs, rownum) -> {
      Singer singer = new Singer();
      singer.setId(rs.getLong("id"));
      singer.setFirstName(rs.getString("first_name"));
      singer.setLastName(rs.getString("last_name"));
      singer.setBirthDate(rs.getDate("birth_date"));
      return singer;
    });
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
  public void setNamedParameterJdbcTemplate(
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }
}
