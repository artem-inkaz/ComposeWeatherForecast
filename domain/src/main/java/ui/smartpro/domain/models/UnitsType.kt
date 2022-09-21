package ui.smartpro.domain.models

enum class UnitsType {
    METRIC {
        override val next: UnitsType
            get() = IMPERIAL
    },
    IMPERIAL {
        override val next: UnitsType
            get() = METRIC
    };

    abstract val next: UnitsType
}
