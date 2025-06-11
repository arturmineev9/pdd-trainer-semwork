package com.example.autoschool11.core.data.repositories

import android.content.Context
import com.example.autoschool11.core.data.local.DataBaseHelper
import com.example.autoschool11.core.data.remote.api.UserStatsApi
import com.example.autoschool11.core.data.remote.dto.UpdateUserStatsRequest
import com.example.autoschool11.core.data.remote.dto.UserStatsDto
import com.example.autoschool11.core.domain.repositories.UserStatsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserStatsRepositoryImpl @Inject constructor(
    private val api: UserStatsApi,
    @ApplicationContext private val context: Context
) : UserStatsRepository {

    private val dbHelper = DataBaseHelper(context)

    override suspend fun saveStatsToServer() {
        val resultsCursor = dbHelper.readableDatabase.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_NAME_4, null)
        val statsList = mutableListOf<UserStatsDto>()
        if (resultsCursor.moveToFirst()) {
            do {
                val ticketNumber = resultsCursor.getInt(resultsCursor.getColumnIndexOrThrow(
                    DataBaseHelper.COLUMN_TICKET_4))
                val result = resultsCursor.getInt(resultsCursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_RESULT_4))
                statsList.add(UserStatsDto(variantNumber = ticketNumber, correctAnswers = result))
            } while (resultsCursor.moveToNext())
        }
        resultsCursor.close()
        api.saveStats(UpdateUserStatsRequest(statsList))
    }

    override suspend fun loadStatsFromServer() {
        val statsList = api.getStats()
        statsList.forEach {
            dbHelper.insertResults(it.variantNumber, it.correctAnswers)
        }
    }
}
