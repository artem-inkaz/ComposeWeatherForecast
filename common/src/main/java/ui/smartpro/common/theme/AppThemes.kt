package ui.smartpro.common.theme

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import ui.smartpro.common.R

/**
 *  Добавить темы UI при желании, в зависимости от погоды
 */
sealed class AppThemes(
    @DrawableRes val backgroundRes: Int, //  фон темы в зависимости от погоды
    val primaryColor: Color,
    val textColor: Color,
    val iconsTint: Color,
    val useDarkNavigationIcons: Boolean,
) {
    class DefaultTheme(
        @DrawableRes backgroundRes: Int = R.drawable.ic_launcher_background,
        primaryColor: Color = Color.White,
        textColor: Color = firstGreenBlue,
        iconsTint: Color = firstGreenBlue,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        primaryColor = primaryColor,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )
}