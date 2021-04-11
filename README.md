# LearningApp


# Lecture 11 Android Codelab

In this code lab we will build an android app from scratch that saves records into a database using ViewModel, Coroutines and Livedata with the help of Room library

| Task | Video Link | Estimated Time |
| --- | --- | --- |
| 1 Creating a project | [https://www.youtube.com/watch?v=rZI6wmDlXF4](https://www.youtube.com/watch?v=rZI6wmDlXF4)| 10 Mins |
| 2 Adding a record activity | [https://youtu.be/pLUTbADnB38](https://youtu.be/pLUTbADnB38)| 10 Mins |
| 3 Adding layout for record activity | [https://www.youtube.com/watch?v=9rTTwWkVC5U](https://www.youtube.com/watch?v=9rTTwWkVC5U)| 30 Mins |
| 4 Adding view binding and TextWatcher for Record activity | [https://www.youtube.com/watch?v=7t0aKnB9-kI](https://www.youtube.com/watch?v=7t0aKnB9-kI)| 15 Mins |

| Task | Video Link | Estimated Time |
| --- | --- | --- |
| 5. Adding Database | [https://www.youtube.com/watch?v=gp-J6GGHxsI](https://www.youtube.com/watch?v=gp-J6GGHxsI) | 20 mins |

Following coding steps will be performed in the video link for this task.

Steps

**1 . Add plugin kotlin-kapt in app gradle**

**2. Add dependencies in app gradle**

```
def room\_version = &quot;2.2.6&quot;

implementation &quot;androidx.room:room-runtime:$room\_version&quot;

kapt &quot;androidx.room:room-compiler:$room\_version&quot;

implementation &quot;androidx.room:room-ktx:$room\_version&quot;
```
**3. Add DB Classes**

=================

Create a &#39;datastorage&#39; package datastorage

Create a RaceDb.kt file in it.

**3.1 Define Table entity class in RaceDb.kt**

=============================
```
@Entity

data class RecordEntity(

@ColumnInfo(name = &quot;name&quot;) var name: String,

@ColumnInfo(name = &quot;distance&quot;) var distance: Int,

@ColumnInfo(name = &quot;time&quot;) var time: Int)

{

@PrimaryKey(autoGenerate = true) var priId: Int? = null

}
```
**3.2 Create DAO interface in RaceDb.kt**

========================
```
@Dao

interface RaceDao {

@Insert

fun insertRecord(vararg item: RecordEntity)

@Update

fun updateRecord(vararg item: RecordEntity)

@Query(&quot;SELECT \* from RecordEntity&quot;)

fun getAllRecords() : List\&lt;RecordEntity\&gt;

}
```
**3.3 Abstract database class in RaceDb.kt**

===========================
```
@Database(

entities = [RecordEntity::class], version = 1, exportSchema = false

)

abstract class RaceDatabase : RoomDatabase() {

//this declaration of abtract method associate DAO class with the database class RaceDatabase

abstract fun getDao(): RaceDao

companion object {

var DB\_FILENAME = &quot;myracedb&quot;

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

DB\_FILENAME)

.fallbackToDestructiveMigration()

.build()

INSTANCE = instance

return instance

}

}

}

}
```
**4. Adding record to Database from RecordActivity &amp; Crashing the app**
```
var entity = RecordEntity(

binding.editName.text.toString(),

binding.editLength.text.toString().toInt(),

binding.editTime.text.toString().toInt()

)

//Accessing database directly from UI thread is bad. because it can block the UI

// DO NOT DO THIS IN EXAM :)

RaceDatabase.get(this).getDao().insertRecord(entity)

//_Todo NOTE: We will be accessing database with ViewModel and Coroutine later_

**4.1 HOW to fix the crash**

In RaceDb.kt file allow ui thread quering while building RaceDatabase

Add allowMainThreadQueries call to databaseBuilder

val instance = Room.databaseBuilder(

context.applicationContext,

RaceDatabase::class.java,

DB\_FILENAME)

.allowMainThreadQueries() //\&lt;------- here

.fallbackToDestructiveMigration()

.build()
```
**5. Override toString for RecordEntity class**
```
override fun toString(): String {

return &quot;$name ran distance of $distance meters in $time seconds&quot;

}
```
**6. override fun onResume() in MainActivity class**
```
override fun onResume() {

super.onResume()

//Querying database table records

RaceDatabase.get(this).getDao().getAllRecords().map { entity-\&gt;

Log.d(this.javaClass.simpleName, entity.toString())

//So far so good

}

}
```
**7-- Run the app**

| Task | Video Link | Estimated Time |
| --- | --- | --- |
| 6. Adding ViewModel for Record Activity | [https://youtu.be/M7iNZ0RZFUY](https://youtu.be/M7iNZ0RZFUY)| 25 mins |

Following coding steps will be performed in the video link for this task.

Adding ViewModel, Coroutine into a project

==========================

Steps

**1. Add dependencies in to app gradle**

// Coroutines view-model and live-data.
```
implementation &#39;org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1&#39;

implementation &#39;androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0&#39;

implementation &quot;androidx.lifecycle:lifecycle-livedata-ktx:2.3.0&quot;
```
**2. Add a package viewmodel**

**2.1 Add RecordViewModel kotlin class in the package**
```
class RecordViewModel(context: Context) : ViewModel() {

}
```
**2.2 Add raceDao member into the class using context parameter passed in constructor**
```
class RecordViewModel(context: Context) : ViewModel()

{

private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

}
```
**2.3 Add addRaceRecord in the class**
```
class RecordViewModel(context: Context) : ViewModel()

{

private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

fun addRaceRecord (entity : RecordEntity) {

}

}
```
**2.4 Use viewModelScope.launch call for async call**
```
class RecordViewModel(context: Context) : ViewModel() {

private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

fun addRaceRecord (entity : RecordEntity) {

viewModelScope.launch {

}

}

}
```
**2.5 Call withContext inside call launch method and specify the execution Dispatchers.IO**
```
class RecordViewModel(context: Context) : ViewModel() {

private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

fun addRaceRecord (entity : RecordEntity) {

viewModelScope.launch {

withContext(Dispatchers.IO){

}

}

}

}
```
**2.6 Call database inside the withContext so that call is executed in the UI Thread**
```
class RecordViewModel(context: Context) : ViewModel() {

private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

fun addRaceRecord (entity : RecordEntity) {

viewModelScope.launch {

withContext(Dispatchers.IO) {

//This is a call in a thread

raceDao.insertRecord(entity)

}

}

}

}
```
**2.7 Now add class member of type RecordViewModel in RecordActivity and**
```
private lateinit var viewModel : RecordViewModel
```
**2.8 Initialise viewModel instance in the onCreate of the RecordActivity**
```
viewModel = RecordViewModel(this)
```
**3.7 Change the binding.buttonSave.setOnClickListener by replacing database call**
```
binding.buttonSave.setOnClickListener {

var entity = RecordEntity (

binding.editName.text.toString(),

binding.editLength.text.toString().toInt(),

binding.editTime.text.toString().toInt()

)

//This is the non blocking way of accessing database

viewModel.addRaceRecord(entity)

finish()

}
```
**4. Run the app and add race records**

| Task | Video Link | Estimated Time |
| --- | --- | --- |
| 7. Adding MainViewModel and Observing Livedata | [https://youtu.be/TCsG0JiOmoQ](https://youtu.be/TCsG0JiOmoQ)| 25 mins |

Following coding steps will be performed in the video link for this task.

**Adding ViewModel for MainActivity**

==========================

**1 Add MainViewModel kotlin class in the viewmodel package**
```
class MainViewModel(context: Context) : ViewModel() {

}
```
**2.2 Add raceDao member into the class using context parameter passed in constructor**
```
class MainViewModel(context: Context) : ViewModel() {

private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

}
```
**2.3 Add liveRecords**
```
class MainViewModel(context: Context) : ViewModel() {

private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

private var liveRecords = MutableLiveData\&lt;List\&lt;RecordEntity\&gt;\&gt;()

}
```
**2.4 Add getRecords function that returns liveRecords**
```
class MainViewModel(context: Context) : ViewModel() {

private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

private var liveRecords = MutableLiveData\&lt;List\&lt;RecordEntity\&gt;\&gt;()

fun getRecords (): MutableLiveData\&lt;List\&lt;RecordEntity\&gt;\&gt; {

return liveRecords

}

}
```
**2.5 Now call database using DAO and change the value wrapped inside live records**
```
class MainViewModel(context: Context) : ViewModel() {

private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

private var liveRecords = MutableLiveData\&lt;List\&lt;RecordEntity\&gt;\&gt;()

fun getRecords (): MutableLiveData\&lt;List\&lt;RecordEntity\&gt;\&gt; {

viewModelScope.launch {

var result = withContext(Dispatchers.IO){

raceDao.getAllRecords()

}

liveRecords.value = result // updating the value

}

return liveRecords

}

}
```
**3.1 Goto MaiActivity class and add a member**
```
private lateinit var viewModel : MainViewModel

**3.2 Create an instance of MainViewModel in onCreate of MainActivity**

viewModel = MainViewModel(this)

**3.3 Replace onResume with following**

This will start observing the livedata values changes

override fun onResume() {

super.onResume()

viewModel.getRecords().observe(this, { list-\&gt;

list.map {entity-\&gt;

Log.d(this.javaClass.simpleName, entity.toString())

}

})

}
```
**4. Remove .allowMainThreadQueries() from RaceDatabase builder because now we are calling it in non blocking way**
```
val instance = Room.databaseBuilder(

context.applicationContext,

RaceDatabase::class.java,

DB\_FILENAME)

.fallbackToDestructiveMigration()

.build()
```
**5-- Run the app**
