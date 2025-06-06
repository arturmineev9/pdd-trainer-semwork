package com.example.autoschool11.core.data.local.mapper

import android.content.Context
import com.example.autoschool11.core.data.local.entities.RoadSignRecognitionEntity
import com.example.autoschool11.core.domain.models.RoadSignModel

fun RoadSignRecognitionEntity.toRoadSignModel(context: Context): RoadSignModel {
    val resId = context.resources.getIdentifier(
        "sign_${this.code}",
        "drawable",
        context.packageName
    )

    return RoadSignModel(
        imageResId = if (resId != 0) resId else null,
        name = this.name,
        description = this.description
    )
}
