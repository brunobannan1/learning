package org.bruno.mySimpleORM.dao;

import org.bruno.mySimpleORM.core.MyORM;

public class PersonDao {
    private MyORM myORM;

    public PersonDao(MyORM myORM) {
        this.myORM = myORM;
    }

}