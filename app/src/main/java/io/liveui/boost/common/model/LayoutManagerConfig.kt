package io.liveui.boost.common.model

enum class LayoutManagerConfig(val phone: Boolean, val list: Boolean) {

    PHONE_LIST(true, true),
    PHONE_GRID(true, false),
    TABLET_LIST(false, true),
    TABLET_GRID(false, false);


    companion object {
        fun getType(isPhone: Boolean, isList: Boolean): LayoutManagerConfig {
            return LayoutManagerConfig.values().find { it.phone == isPhone && it.list == isList }!!
        }
    }

}
