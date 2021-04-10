package com.imran.appname.datastorage

import android.content.Context
import androidx.room.*

@Entity
data class RecordEntity(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "distance") var distance: Int,
    @ColumnInfo(name = "time") var time: Int)
{
    @PrimaryKey(autoGenerate = true) var priId: Int? = null

    override fun toString(): String {
        return "$name ran distance of $distance meters in $time seconds"
    }
}


@Dao
interface RaceDao {

    @Insert
    fun insertRecord(vararg item: RecordEntity)

    @Update
    fun updateRecord(vararg item: RecordEntity)

    @Query("SELECT * from RecordEntity")
    fun getAllRecords() : List<RecordEntity>
}


@Database(
    entities = [RecordEntity::class], version = 1, exportSchema = false
)
abstract class RaceDatabase : RoomDatabase() {

    //this declaration of abtract method associate DAO class with the database class RaceDatabase
    abstract fun getDao(): RaceDao

    companion object {

        var DB_FILENAME  = "myracedatabasefile"

        @Volatile
        private var INSTANCE: RaceDatabase? = null

        fun get(context: Context): RaceDatabase {
            val tmp =
                INSTANCE
            if (tmp != null) {
                return tmp
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RaceDatabase::class.java,
                    DB_FILENAME)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
