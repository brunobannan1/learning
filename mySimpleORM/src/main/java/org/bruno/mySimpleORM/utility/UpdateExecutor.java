package org.bruno.mySimpleORM.utility;

import java.sql.Connection;

public class UpdateExecutor extends Executor {

    public UpdateExecutor(Connection connection) {
        super(connection);
    }
}
