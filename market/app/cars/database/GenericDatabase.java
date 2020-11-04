package cars.database;

import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public abstract class GenericDatabase {
	private SqlSessionFactory sqlSessionFactory;
	
	public void open(String dbDefaultUrl) {
		try {
			PooledDataSource dataSource = new PooledDataSource("org.h2.Driver", dbDefaultUrl, "", "");
			Environment environment = new Environment("dev", new JdbcTransactionFactory(), dataSource);
			Configuration configuration = new Configuration(environment);
			configuration.addMappers("cars.database.mappers");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected synchronized void doInTransaction(Consumer<SqlSession> consumer) {
		SqlSession session = sqlSessionFactory.openSession();
        
        try {
            consumer.accept(session);
            session.commit();
        } catch (Exception e) {
        	e.printStackTrace();
        	try {
        		session.rollback();
        	}catch(Exception e1) {
        		e.printStackTrace();
        	}
        } finally {
            session.close();
        }
    }
	
	protected synchronized  <T> T doInTransactionWithResult(Function<SqlSession, T> consumer) {
    	SqlSession session = sqlSessionFactory.openSession();
        
        try {
            T result = consumer.apply(session);
            session.commit();
            return result;
        } catch (Exception e) {
        	e.printStackTrace();
        	try {
        		session.rollback();
        	}catch(Exception e1) {
        		e.printStackTrace();
        	}
        } finally {
            session.close();
        }
        return  null;
    }
}
