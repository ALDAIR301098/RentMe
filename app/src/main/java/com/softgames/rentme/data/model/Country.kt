package com.softgames.rentme.data.model

import androidx.annotation.DrawableRes
import com.softgames.rentme.R

sealed class Country(
    val name: String,
    val code: String,
    @DrawableRes val flag: Int,
){

    object MEXICO : Country("México", "52", R.drawable.flag_mexico)
    object UNITED_STATES : Country("Estados Unidos", "1", R.drawable.flag_united_states)
    object CANADA : Country("Canadá", "1", R.drawable.flag_canada)
    object ARGENTINA : Country("Argentina", "54", R.drawable.flag_argentina)
    object BRAZIL : Country("Brasil", "55", R.drawable.flag_brazil)
    object COLOMBIA : Country("Colombia", "57", R.drawable.flag_colombia)
    object COSTA_RICA : Country("Costa Rica", "506", R.drawable.flag_costa_rica)
    object CHILE : Country("Chile", "56", R.drawable.flag_chile)
    object ECUADOOR : Country("Ecuador", "593", R.drawable.flag_ecuador)
    object GUATEMALA : Country("Guatemala", "502", R.drawable.flag_guatemala)
    object HONDURAS : Country("Honduras", "504", R.drawable.flag_honduras)
    object PANAMA : Country("Panamá", "507", R.drawable.flag_panama)
    object PARAGUAY : Country("Paraguay", "597", R.drawable.flag_paraguay)
    object PERU : Country("Perú", "51", R.drawable.flag_peru)
    object URUGUAY : Country("Uruguay", "598", R.drawable.flag_uruguay)
    object VENEZUELA : Country("Venezuela", "58", R.drawable.flag_venezuela)

    object BELGIUM : Country("Bélgica", "32", R.drawable.flag_belgium)
    object ENGLAND : Country("Inglaterra", "44", R.drawable.flag_england)
    object FRANCE : Country("Francia", "33", R.drawable.flag_france)
    object GERMANY : Country("Alemania", "49", R.drawable.flag_germany)
    object GREECE : Country("Grecia", "30", R.drawable.flag_greece)
    object ITALY : Country("Italia", "39", R.drawable.flag_italy)
    object NETHERLANDS : Country("Países bajos", "31", R.drawable.flag_netherlands)
    object POLAND : Country("Polonia", "48", R.drawable.flag_poland)
    object PORTUGAL : Country("Portugal", "351", R.drawable.flag_portugal)
    object SPAIN : Country("España", "34", R.drawable.flag_spain)
    object SWITZERLAND : Country("Suiza", "41", R.drawable.flag_switzerland)
    object UKRAINE : Country("Ucrania", "380", R.drawable.flag_ukraine)
    object UNITED_KINGDOM : Country("Reino unido", "44", R.drawable.flag_united_kingdom)

}
