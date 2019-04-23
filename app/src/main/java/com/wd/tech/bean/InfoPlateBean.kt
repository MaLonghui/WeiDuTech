package com.wd.tech.bean

data class InfoPlateBean(
    var message: String,
    var result: List<InfoPlateResult>,
    var status: String
)

data class InfoPlateResult(
    var id: Int,
    var name: String,
    var pic: String
)