package emmanuelmuturia.secretsscanner.secretscanner.source.local.entity

import emmanuelmuturia.secretsscanner.secretscanner.source.local.extras.SecretMatchType

data class ScanResultEntity(
    val fileName: String,
    val matchedValue: String,
    val matchType: SecretMatchType,
    val lineNumber: Int,
    val lineContent: String,
)
