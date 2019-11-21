package com.example.cognitus.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AcercaDeModel (@SerializedName("nosotros_id") @Expose val nosotrosId:String?,
                     @SerializedName("nosotros_img") @Expose val nosotrosImg:String?,
                     @SerializedName("nosotros_status") @Expose val nosotrosStatus:String?,
                     @SerializedName("nosotros_desc") @Expose val nosotrosDesc:String?
)

