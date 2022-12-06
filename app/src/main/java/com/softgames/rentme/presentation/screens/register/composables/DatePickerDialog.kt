package com.softgames.rentme.presentation.screens.register.composables

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.softgames.rentme.R
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun DatePickerDialog(
    onDissMiss: () -> Unit,
    onDateSelected: (String) -> Unit,
) {

    val calendar = Calendar.getInstance()
    val context = LocalContext.current

    DatePickerDialog(
        context,
        R.style.DatePickerDialogTheme,
        { _, year: Int, month: Int, day: Int ->
            onDateSelected(formatDate(day, month.plus(1), year))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply { setOnCancelListener { onDissMiss() }; show() }

}

private fun formatDate(day: Int, month: Int, year: Int): String {
    val date: StringBuilder = StringBuilder("")
    if (day < 10) {date.append("0")}; date.append(day)
    if (month < 10) {date.append("0")}; date.append(month)
    date.append(year); return date.toString()
}

@Preview(showBackground = true)
@Composable
private fun DatePickerDialogPreview() {
    RentMeTheme {
        DatePickerDialog({}, {})
    }
}