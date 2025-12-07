package com.mybatisplus;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.mybatisplus.entity.Person;
import com.mybatisplus.mapper.PersonMapper;

public class Main {
    private static SqlSessionFactory sqlSessionFactory = initSqlSessionFactory();

    public static void main(String[] args) {
       try(SqlSession session = sqlSessionFactory.openSession(true)){
        PersonMapper mapper = session.getMapper(PersonMapper.class);
        Person person = new Person().setName("Công");
        mapper.insert(person);
        Person person1 = new Person().setName("Jayce");
        mapper.insert(person1);
        List<Person> lst = mapper.selectList(null);
        lst.stream().forEach(System.out::println);

       }
    }

    public static SqlSessionFactory initSqlSessionFactory(){
        DataSource dataSource = dataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Production", transactionFactory, dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration(environment);
        configuration.addMapper(PersonMapper.class);
        configuration.setLogImpl(StdOutImpl.class);
        return new MybatisSqlSessionFactoryBuilder().build(configuration);
    }


    public static DataSource dataSource(){
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(org.h2.Driver.class.getName());
        dataSource.setUrl("jdbc:h2:mem:test");
        dataSource.setUsername("root");
        dataSource.setPassword("test");
        try {
            Connection connection = dataSource.getConnection();

            Statement statement = connection.createStatement();
            statement.execute("create table person(" +
                "id BIGINT NOT NULL,"+
                "name VARCHAR(30) NULL,"+
                "age INT NULL,"+
                "PRIMARY KEY(id)"+")"
            );
            // statement.close();
            // connection.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return dataSource;
    }
}