package com.example.cvbuilder

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ROLL_NUMBER + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_CGPA + " REAL, " +
                COL_DEGREE + " TEXT, " +
                COL_GENDER + " TEXT, " +
                COL_DATE_OF_BIRTH + " TEXT, " +
                COL_INTERESTS + " TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun insertCV(
        rollNumber: String?,
        name: String?,
        cgpa: Double,
        degree: String?,
        gender: String?,
        dateOfBirth: String?,
        interests: String?
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ROLL_NUMBER, rollNumber)
        values.put(COL_NAME, name)
        values.put(COL_CGPA, cgpa)
        values.put(COL_DEGREE, degree)
        values.put(COL_GENDER, gender)
        values.put(COL_DATE_OF_BIRTH, dateOfBirth)
        values.put(COL_INTERESTS, interests)
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }
    // Function to retrieve all CVs from the database
    @SuppressLint("Range")
    fun getAllCVs(): List<CVModel> {
        val cvList = mutableListOf<CVModel>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val rollNumber = cursor.getString(cursor.getColumnIndex(COL_ROLL_NUMBER))
                val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                val cgpa = cursor.getDouble(cursor.getColumnIndex(COL_CGPA))
                val degree = cursor.getString(cursor.getColumnIndex(COL_DEGREE))
                val gender = cursor.getString(cursor.getColumnIndex(COL_GENDER))
                val interests = cursor.getString(cursor.getColumnIndex(COL_INTERESTS))

                val cv = CVModel(rollNumber, name, cgpa, degree, gender, interests)
                cvList.add(cv)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return cvList
    }
    val allCVs: Cursor
        get() {
            val db = this.readableDatabase
            return db.query(TABLE_NAME, null, null, null, null, null, null)
        }

    companion object {
        private const val DATABASE_NAME = "CVDatabase.db"
        private const val TABLE_NAME = "CVs"
        private const val COL_ID = "id"
        private const val COL_ROLL_NUMBER = "roll_number"
        private const val COL_NAME = "name"
        private const val COL_CGPA = "cgpa"
        private const val COL_DEGREE = "degree"
        private const val COL_GENDER = "gender"
        private const val COL_DATE_OF_BIRTH = "date_of_birth"
        private const val COL_INTERESTS = "interests"
    }
}
