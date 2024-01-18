package com.practicum.searchcompose.models

data class SecurityAndAnalysis(
    val advanced_security: AdvancedSecurity,
    val secret_scanning: SecretScanning,
    val secret_scanning_push_protection: SecretScanningPushProtection
)