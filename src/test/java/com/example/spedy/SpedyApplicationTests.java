package com.example.spedy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class SpedyApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testUUID() {
        UUID id1 = UUID.randomUUID();
        String id1AsText = id1.toString();

        UUID id2 = UUID.fromString(id1AsText);

        Assertions.assertEquals(id1, id2);
    }

    @Test
    void testSQLDate() {
        Date date1 = Date.valueOf(LocalDate.now());
        String str1 = date1.toString();

        Date date2 = Date.valueOf(str1);

        Assertions.assertEquals(date1, date2);

    }
}
