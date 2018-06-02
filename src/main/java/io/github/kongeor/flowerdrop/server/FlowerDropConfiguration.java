package io.github.kongeor.flowerdrop.server;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class FlowerDropConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @Valid
    @NotNull
    private DataSourceFactory logDatabase = new DataSourceFactory();

    @JsonProperty("logDatabase")
    public void setLogDatabase(DataSourceFactory factory) {
        this.logDatabase = factory;
    }

    @JsonProperty("logDatabase")
    public DataSourceFactory getLogDatabase() {
        return this.logDatabase;
    }
}
