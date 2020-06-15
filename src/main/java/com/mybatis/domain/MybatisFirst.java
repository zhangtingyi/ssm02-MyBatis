package com.mybatis.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.mybatis.po.User;

public class MybatisFirst {
    //会话工厂
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        //配置文件
        String resource = "SqlMapConfig.xml";
        //把配置文件加载到输入流中
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void TestfindUserById() {
        //利用SqlSessionFactory创建Sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //操作数据库
        User user = null;
        try {
            user = sqlSession.selectOne("test.findUserById", 1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
        System.out.println(user);
    }

    @Test
    public void TestfindUserByName() {
        //利用SqlSessionFactory创建Sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //操作数据库
        List<User> list = null;
        try {
            list = sqlSession.selectList("test.findUserByName", "%四%");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
        System.out.println(list);
    }

    @Test
    public void TestFindAllUser(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        List<User> List = null;

        List=sqlSession.selectList("test.findAllUser");

    }

    @Test
    public void TestinsertUser() {
        //利用SqlSessionFactory创建Sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //操作数据库
        User user = new User();
        user.setUsername("王六");
        user.setBirthday(new Date());
        user.setSex("2");
        user.setAddress("天津河东");
        try {
            sqlSession.insert("test.insertUser", user);
            //提交事务
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
        System.out.println("用户的id：" + user.getId());
    }

    @Test
    //删除用户信息
    public void TestdeleteUser() {
        //根据SqlSessionFactory创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //用SqlSession操作数据库
        try {
            sqlSession.delete("test.deleteUser", 12);
            //需要提交事务
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭sqlsession
            sqlSession.close();
        }
    }

    @Test
    //更新用户信息
    public void TestupdateUser() {
        //根据SqlSessionFactory创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //用SqlSession操作数据库
        User user = new User();
        user.setId(13);
        user.setUsername("王六顺");
        user.setBirthday(new Date());
        user.setSex("2");
        user.setAddress("天津河东");
        try {
            sqlSession.update("test.updateUser", user);
            //需要提交事务
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭sqlsession
            sqlSession.close();
        }
        System.out.println("用户的id：" + user.getId());
    }

}
