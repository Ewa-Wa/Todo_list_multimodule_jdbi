package ewa.learnprogramming;

import ewa.learnprogramming.jdbi.TodoDAO;
import ewa.learnprogramming.resources.TodoResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import javax.annotation.PostConstruct;


public class TodoApplication extends Application<TodoConfiguration> {
    public static void main(final String[] args) throws Exception {
        new TodoApplication().run(args);
    }


    @PostConstruct
    @Override
    public void initialize(Bootstrap<TodoConfiguration> bootstrap) {
        bootstrap.getConfigurationFactoryFactory();

        bootstrap.addBundle(new MigrationsBundle<>() {
            @Override
            public DataSourceFactory getDataSourceFactory(TodoConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }


        @Override
    public void run(final TodoConfiguration configuration,
                    final Environment environment) throws ClassNotFoundException {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "database");
        final TodoDAO todoDAO = jdbi.onDemand(TodoDAO.class);
         environment.jersey().register(new TodoResource(todoDAO));

    }

}
