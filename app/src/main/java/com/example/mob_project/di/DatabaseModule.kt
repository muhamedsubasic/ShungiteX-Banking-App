package com.example.mob_project.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mob_project.dao.*
import com.example.mob_project.database.AppDatabase
import com.example.mob_project.model.*
import com.example.mob_project.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        lateinit var appDatabase: AppDatabase

        appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "banking_app.db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Log.d("DatabaseModule", "Room database created — seeding initial data")

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            prepopulateDatabase(appDatabase)
                        } catch (e: Exception) {
                            Log.e("DatabaseModule", "Error prepopulating database", e)
                        }
                    }
                }
            })
            .build()

        return appDatabase
    }

    private suspend fun prepopulateDatabase(database: AppDatabase) {
        Log.d("DatabaseModule", "Prepopulating database...")

        val account = Account(                      // Dummy random account
            firstName = "John",
            lastName = "Doe",
            accountNumber = "123555123",
            iban = "GB33BUKB20201555555555",
            address = "Francuska Revolucija bb",
            phoneNumber = "+38761123123",
            bankName = "ShungiteX"
        )
        val accountId = database.accountDao().insert(account).toInt()
        Log.d("DatabaseModule", "Inserted account with ID $accountId")

        val user = User(
            username = "admin",
            password = "admin123",
            email = "admin@example.com",
            createdAt = Date(),
            lastLogin = null,
            accountId = accountId
        )
        database.userDao().insert(user)

        val expiryDate = Calendar.getInstance().apply {
            add(Calendar.YEAR, 3)
        }.time

        val card = Card(
            accountId = accountId,
            cardNumber = "4111111111111111",
            cardType = "Debit",
            cardNetwork = "VISA",
            status = "ACTIVE",
            expiryDate = expiryDate
        )
        database.cardDao().insert(card)

        val payment = Payment(
            accountId = accountId,
            receiverAccountNumber = "987654321",
            amount = 100.0,
            description = "Initial deposit",
            status = "COMPLETED"
        )
        val paymentId = database.paymentDao().insert(payment).toInt()

        val transaction = Transaction(
            transactionType = "DEPOSIT",
            amount = 100.0,
            referenceNumber = "INIT001",
            status = "COMPLETED",
            date = Date(),
            currency = "GBP",
            paymentId = paymentId
        )
        database.transactionDao().insert(transaction)

        Log.d("DatabaseModule", "Database seeding completed.")
    }

    @Provides fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
    @Provides fun provideAccountDao(db: AppDatabase): AccountDao = db.accountDao()
    @Provides fun provideCardDao(db: AppDatabase): CardDao = db.cardDao()
    @Provides fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()
    @Provides fun providePaymentDao(db: AppDatabase): PaymentDao = db.paymentDao()

    @Provides @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository = UserRepositoryImpl(userDao)

    @Provides @Singleton
    fun provideAccountRepository(accountDao: AccountDao): AccountRepository =
        AccountRepositoryImpl(accountDao)

    @Provides @Singleton
    fun provideCardRepository(cardDao: CardDao): CardRepository =
        CardRepositoryImpl(cardDao)

    @Provides @Singleton
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository =
        TransactionRepositoryImpl(transactionDao)

    @Provides @Singleton
    fun providePaymentRepository(paymentDao: PaymentDao): PaymentRepository =
        PaymentRepositoryImpl(paymentDao)
}
