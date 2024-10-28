package jp.ac.jec.cm0128.subdisplaylauncherforliberoflip

enum class SortOrder(val label: String) {
    NAME("名前(昇順)"),
    NAME_REVERSE("名前(降順)"),
    INSTALL_DATE("インストール日(昇順)"),
    INSTALL_DATE_REVERSE("インストール日(降順)"),
}