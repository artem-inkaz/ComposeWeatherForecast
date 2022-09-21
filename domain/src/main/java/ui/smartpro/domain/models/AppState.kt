package ui.smartpro.domain.models

sealed class AppState<T>(val data: T? = null, val message: ErrorState? = null) {
    class Success<T>(data: T) : AppState<T>(data)
    class Error<T>(error: ErrorState, data: T? = null) : AppState<T>(data, error)
    class Loading<T>(data: T? = null) : AppState<T>(data)
}

enum class ErrorState {
    NO_INTERNET_CONNECTION,
    NO_FORECAST_LOADED,
    NO_LOCATION_AVAILABLE,
    EMPTY_RESULT,
    NO_SAVED_CITIES,
    BAD_REQUEST
}
