package cn.roboteco;

import cn.roboteco.mybatis.model.Country;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;

public class CountryMapperTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init(){
        try {
            //通过Resources工具类将配置文件读入Reader
            Reader reader=Resources.getResourceAsReader("mybatis-config.xml");

            //通过sqlSessionFactoryBuilder使用Reader创建sqlSessionFactory工厂对象
            //创建的过程：
            //        1.首先解析mybatis-config.xml配置文件，读取配置文件中的mappers配置后
            //          会读取全部的Mapper.xml进行具体方法的解析，解析完成后，SqlSessionFactory就
            //          包含了所有的属性配置和执行sql的信息
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException ignore) {
            // TODO: handle exception
            ignore.printStackTrace();
        }

    }
    @Test
    public void testSelectAll() {
        //使用SqlSessionFactory对象获取一个SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //1. 通过SqlSession的selectlist方法查找到CountryMapper.xml中的id="selectAll"的方法
            //    执行sql查询
            //2. MyBatis底层使用JDBC执行SQL,获得查询结果集ResultSet后，根据resultType的配置
            //   将结果映射为Country类型的集合，返回结果
            List<Country> countryList = sqlSession.selectList("selectAll");
            printCountryList(countryList);
        } finally{
            //不要忘记关闭sqlSession
            sqlSession.close();
        }
    }
    private void printCountryList(List<Country> countryList) {
        for(Country country : countryList){
            System.out.printf("%-4d%4s%4s\n",country.getId(), country.getCountryname(), country.getCountrycode());
        }
    }
}
