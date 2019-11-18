//import com.datastax.oss.driver.api.core.CqlSession;

public class CassandraConnector {

        public static void main(String args[]){

            //Query
            String createKeyspaceQuery = "CREATE KEYSPACE capgemini WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};";
            String createTableQuery = "CREATE TABLE emp(emp_id int PRIMARY KEY, "
                    + "emp_name text, "
                    + "emp_city text, "
                    + "emp_sal varint, "
                    + "emp_phone varint );";
            String dataInsertQuery= "INSERT INTO emp (emp_id, emp_name, emp_city, emp_phone,  emp_sal)"
                    + " VALUES(1,'Neelam', 'Pune', 9848022338, 5000000);" ;
/*
            //creating Cluster object
            CqlSession session = CqlSession.builder().build();
         *//*   Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

            //Creating Session object
            Session session = cluster.connect();*//*

            //using the KeySpace
            session.execute("USE capgemini");

            //Executing the query
            session.execute(createKeyspaceQuery);
            session.execute(createTableQuery);
            session.execute(dataInsertQuery);*/

        }
    }

