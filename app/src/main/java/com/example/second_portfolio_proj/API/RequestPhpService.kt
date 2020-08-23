package com.example.second_portfolio_proj.API

import com.example.second_portfolio_proj.ScriptPOJO
import retrofit2.http.GET

interface RequestPhpService {
    @GET("cloaka.php?id=2ottk6qvq3n63jec38t8")
    suspend fun getScriptInfo(): ScriptPOJO

}



