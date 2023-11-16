package com.boriworld.boriPaw.accountService.command.mediumTest.interfaces;


import com.boriworld.testConfig.MySQLTestContainerRun;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class InteMySQLTest extends MySQLTestContainerRun {

    @Test
    void test1() throws Exception{
        //given
        System.out.println("~~~~~~~~~~~~~~~~~~~~TEST1~~~~~~~~~~~~~~~~~~~~`");
        //when

        //then

    }

    @Test
    void test2() throws Exception{
        //given
        System.out.println("~~~~~~~~~~~~~~~~~~~~TEST2~~~~~~~~~~~~~~~~~~~~`");
        //when

        //then

    }
}
