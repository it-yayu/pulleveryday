package com.test.testmysql;

import com.test.testmysql.utils.DBTableNameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestmysqlApplicationTests {

    @Test
    public void contextLoads() throws SQLException, ClassNotFoundException {
//        DBTableNameUtils.creatNewTable("");
    }

}
