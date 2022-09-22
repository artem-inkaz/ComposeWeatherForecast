package ui.smartpro.weatherforecast.presentation.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import ui.smartpro.common.Constants.TOP_APPBAR_HEIGHT
import ui.smartpro.weatherforecast.ui.theme.secondYellowDawn
import ui.smartpro.weatherforecast.R

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    searchedTextState: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APPBAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = secondYellowDawn
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchedTextState,
            onValueChange = { text -> onTextChange(text) },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    color = Color.White,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = { onSearchClicked(searchedTextState) },
                    modifier = Modifier.alpha(ContentAlpha.disabled)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { onTextChange("") }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = Color.White
                    )
                }
            },
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked(searchedTextState)
                keyboardController?.hide()
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                capitalization = KeyboardCapitalization.Sentences
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}