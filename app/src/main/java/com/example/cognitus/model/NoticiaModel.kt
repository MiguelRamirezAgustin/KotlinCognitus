package com.example.cognitus.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//Modelo para el servicio
class NoticiaModel (@SerializedName("not_id") @Expose val notID: String?,
                    @SerializedName("not_img") @Expose val notImg: String?,
                    @SerializedName("not_url") @Expose val notUrl: String?,
                    @SerializedName("not_titulo") @Expose val notTitulo: String?,
                    @SerializedName("not_fecha") @Expose val notFecha: String?,
                    @SerializedName("not_desc") @Expose val notDesc: String?
)