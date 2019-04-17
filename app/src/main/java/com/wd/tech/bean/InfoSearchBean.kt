package com.wd.tech.bean

data class InfoSearchBean(
    var message: String,
    var result: List<SearchResult>,
    var status: String
)

data class SearchResult(
    var id: Int,
    var releaseTime: Long,
    var source: String,
    var title: String
)