package com.jairoco.frameora.domain.brand

object BrandResolver {

    fun resolve(make: String?): CameraBrand? {

        if (make.isNullOrBlank()) {
            return null
        }

        val normalizedMake = normalize(make)

        return BrandCatalog.brands.firstOrNull { brand ->
            brand.aliases.any { alias ->
                normalize(alias) == normalizedMake
            }
        }
    }

    private fun normalize(value: String): String {
        return value
            .trim()
            .lowercase()
            .replace(" ", "")
            .replace("-", "")
            .replace("_", "")
    }
}