package Hitch.persistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by olafurma on 6.2.2016.
 */
public class DataBase {

  private DriverManagerDataSource getDataSource()
  {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost/companydb");
    dataSource.setUsername("jcg");
    dataSource.setPassword("jcg");

    return dataSource;
  }

  @Autowired
  JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

  public DataBase() {}


  public void test()
  {
    jdbcTemplate.execute("DROP TABLE rides IF EXISTS");
  }
}
