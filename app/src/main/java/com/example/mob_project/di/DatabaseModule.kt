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
            .fallbackToDestructiveMigration(true)
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

        suspend fun insertAccountWithCards(
            firstName: String,
            lastName: String,
            accountNumber: String,
            iban: String,
            address: String,
            phoneNumber: String,
            bankName: String
        ): Int {
            val account = Account(
                firstName = firstName,
                lastName = lastName,
                accountNumber = accountNumber,
                iban = iban,
                address = address,
                phoneNumber = phoneNumber,
                bankName = bankName
            )
            val accountId = database.accountDao().insert(account).toInt()
            Log.d("DatabaseModule", "Inserted account with ID $accountId")

            val expiryDate = Calendar.getInstance().apply {
                add(Calendar.YEAR, 3)
            }.time

            val debitCard = Card(
                accountId = accountId,
                cardNumber = "411111111111${(1000..9999).random()}",
                cardType = "Debit",
                cardNetwork = "VISA",
                status = "ACTIVE",
                balance = 12500.50,
                expiryDate = expiryDate
            )
            database.cardDao().insert(debitCard)

            val creditCard = Card(
                accountId = accountId,
                cardNumber = "511111111111${(1000..9999).random()}",
                cardType = "Credit",
                cardNetwork = "MasterCard",
                status = "ACTIVE",
                balance = 1644.52,
                expiryDate = expiryDate
            )
            database.cardDao().insert(creditCard)

            Log.d("DatabaseModule", "Inserted debit and credit cards for account ID $accountId")

            return accountId
        }

        val adminAccountId = insertAccountWithCards(
            firstName = "John",
            lastName = "Doe",
            accountNumber = "123555123",
            iban = "GB33BUKB20201555555555",
            address = "Francuska Revolucija bb",
            phoneNumber = "+38761123123",
            bankName = "ShungiteX"
        )
        val adminUser = User(
            username = "admin",
            password = "admin123",
            email = "admin@example.com",
            createdAt = Date(),
            lastLogin = null,
            accountId = adminAccountId
        )
        database.userDao().insert(adminUser)
        Log.d("DatabaseModule", "Inserted user: admin")

        // Bob account + user
        val bobAccountId = insertAccountWithCards(
            firstName = "Bob",
            lastName = "Johnson",
            accountNumber = "555666777",
            iban = "GB11BUKB20201598765432",
            address = "789 Maple Avenue, London",
            phoneNumber = "+441112223334",
            bankName = "Super Bank"
        )
        val bobUser = User(
            username = "bob",
            password = "bobpass",
            email = "bob@example.com",
            createdAt = Date(),
            lastLogin = null,
            accountId = bobAccountId
        )
        database.userDao().insert(bobUser)
        Log.d("DatabaseModule", "Inserted user: bob")

        val transactionDao = database.transactionDao()

        val sampleTransactions = listOf(
            Transaction(
                transactionType = "DEPOSIT",
                amount = 100.0,
                referenceNumber = "REF123",
                status = "COMPLETED",
                date = Date(),
                currency = "USD",
                paymentId = null
            ),
            Transaction(
                transactionType = "WITHDRAWAL",
                amount = 25.5,
                referenceNumber = "REF456",
                status = "COMPLETED",
                date = Date(),
                currency = "USD",
                paymentId = null
            ),
            Transaction(
                transactionType = "PAYMENT",
                amount = 50.0,
                referenceNumber = "REF789",
                status = "PENDING",
                date = Date(),
                currency = "USD",
                paymentId = null
            ),
            Transaction(
                transactionType = "TRANSFER",
                amount = 75.25,
                referenceNumber = "REF101",
                status = "FAILED",
                date = Date(),
                currency = "USD",
                paymentId = null
            )
        )

        sampleTransactions.forEach { transactionDao.insert(it) }
        Log.d("DatabaseModule", "Inserted 4 sample transactions.")

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
