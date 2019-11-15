package com.example.cognitus.utilities

class Utils {
    //companion para poder exportat y usarlo en otras clases
    companion object{
            val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

        //fun para validar formato de correo
        fun isEmailValid(email:String):Boolean{
            return EMAIL_REGEX.toRegex().matches(email)
        }

    }
}