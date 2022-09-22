package ui.smartpro.weatherforecast.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import ui.smartpro.common.Constants.CITY_CARD_ANIMATION_DURATION
import ui.smartpro.weatherforecast.R
import ui.smartpro.weatherforecast.ui.theme.ralewayFontFamily
import ui.smartpro.weatherforecast.ui.theme.Shapes

/**
 *  Item container для отображения инфо о ппогоде в выбранном городе
 */
@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    title: String,
    descriptionBlock: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    showOptions: Boolean = true,
    titleFontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    textFontWeight: FontWeight = FontWeight.Bold,
    textFontFamily: FontFamily = ralewayFontFamily,
    shape: Shape = Shapes.medium,
    padding: Dp = 12.dp,
    isExpanded: Boolean,
    animationDuration: Int = CITY_CARD_ANIMATION_DURATION.toInt(),
    onCardClick: () -> Unit = {},
    onMoreButtonClicked: () -> Unit = {}
) {
    val rotationState by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = shape,
        onClick = { onCardClick() },
        elevation = 5.dp,
        border = BorderStroke(width = 1.dp, color = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = title,
                    fontWeight = textFontWeight,
                    fontSize = titleFontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = textFontFamily
                )
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .weight(weight = 1f)
                        .rotate(rotationState),
                    onClick = { onCardClick() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.drop_down)
                    )
                }
                if (showOptions)
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .weight(weight = 1f),
                        onClick = { onMoreButtonClicked() }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.options)
                        )
                    }
            }
            if (isExpanded) {
                descriptionBlock()
            }
        }
    }
}