package dao.impl;

import dao.SingerDao;
import entities.Album;
import entities.Singer;
import java.util.ArrayList;
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
  public List<Singer> findAllWithAlbums() {
    String sql =
        "select s.id, s.first_name, s.last_name, s.birth_date, a.id as album_id, a.title, a.release_date "
            + "from singer s left join album a on s.id = a.singer_id";
    return namedParameterJdbcTemplate.query(sql, rs -> {
      Singer singer;
      Map<Long, Singer> map = new HashMap<>();
      while (rs.next()) {
        long id = rs.getLong("id");
        singer = map.get(id);
        if (singer == null) {
          singer = new Singer();
          singer.setId(id);
          singer.setFirstName(rs.getString("first_name"));
          singer.setLastName(rs.getString("last_name"));
          singer.setBirthDate(rs.getDate("birth_date"));
          singer.setAlbums(new ArrayList<>());
          map.put(id, singer);
        }
        Long albumId = rs.getLong("album_id");
        if (albumId > 0) {
          Album album = new Album();
          album.setId(albumId);
          album.setSingerId(id);
          album.setTitle(rs.getString("title"));
          album.setReleaseDate(rs.getDate("release_date"));
          singer.addAlbum(album);
        }
      }
      return new ArrayList<>(map.values());
    });
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
