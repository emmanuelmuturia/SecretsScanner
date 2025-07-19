package emmanuelmuturia.secretsscanner.home.source.local.extras

enum class SecretMatchType(val label: String) {
    AWS(label = "AWS Access Key"),
    GOOGLE(label = "Google API Key"),
    STRIPE(label = "Stripe Key"),
    PASSWORD(label = "Password"),
    API_KEY(label = "API Key"),
    UNKNOWN(label = "Unknown")
}