package com.boriworld.boriPaw.testContainer.testcontainer;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import java.util.Set;

@Component
@ActiveProfiles("test")
@Slf4j
public class TestDataCleaner {
    private static final int OFF = 0;
    private static final int ON = 1;
    @PersistenceContext
    private EntityManager em;
    private final Set<String> deleteDMLs = new HashSet<>();
    private final Set<String> alterDMLs = new HashSet<>();

    @Transactional
    public void clean() {
        em.clear();
        if (deleteDMLs.isEmpty()) {
            init();
        }

        setForeignKeyEnable(OFF);
        truncateAllTables();
        setForeignKeyEnable(ON);
        resetAutoIncrement();
    }

    private void resetAutoIncrement() {
        alterDMLs.stream()
                .map(em::createNativeQuery)
                .forEach(Query::executeUpdate);
    }

    private void truncateAllTables() {
        deleteDMLs.stream()
                .map(em::createNativeQuery)
                .forEach(Query::executeUpdate);
    }

    private void setForeignKeyEnable(final int enable) {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS =" + enable)
                .executeUpdate();
    }

    private void init() {
        try {
            em.createNativeQuery("SHOW TABLES ", String.class)
                    .getResultList()
                    .stream().filter(table -> !table.equals("flyway"))
                    .forEach(name -> {
                        deleteDMLs.add("TRUNCATE TABLE " + name);
                        alterDMLs.add("ALTER TABLE " + name + " AUTO_INCREMENT = " + 1);
                    });
        } catch (Exception e) {
            log.error("TestDataCleaner initialize error {}", e.getStackTrace());

        }
    }
}