package com.binbraw.data.database

import com.binbraw.util.Config
import org.jetbrains.exposed.sql.Database
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.postgresql.ds.PGSimpleDataSource

class DatabaseProvider: KoinComponent{
    private val config by inject<Config>()

    fun init(){
        val source = PGSimpleDataSource().apply {
            user = config.db_username
            password = config.db_password
            setURL(config.jdbc_url)
        }

        Database.connect(source)
    }
}