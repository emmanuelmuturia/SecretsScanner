package emmanuelmuturia.secretsscanner.home.source.local.entity

import emmanuelmuturia.secretsscanner.home.source.local.extras.SecretMatchType

data class ScanResultEntity(
    val fileName: String,
    val matchedValue: String,
    val matchType: SecretMatchType,
    val lineNumber: Int,
    val lineContent: String,
)
