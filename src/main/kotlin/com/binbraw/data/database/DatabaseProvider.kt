package com.binbraw.data.database

import com.binbraw.util.Config
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.newFixedThreadPoolContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class DatabaseProvider: KoinComponent{
    private val config by inject<Config>()

    init{
        Database.connect(hikari(config))
        transaction {
//            SchemaUtils.create(Users)
            /*TODO Specify all table schemes here*/
        }
    }

    private fun hikari(mainConfig: Config): HikariDataSource {
        HikariConfig().run {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = mainConfig.jdbc_url
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
            return HikariDataSource(this)
        }
    }
}