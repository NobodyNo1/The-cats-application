package com.bekarys.tech_assignment.thecats.features.breedlist.data.model.network

import com.google.gson.annotations.SerializedName

private val EMPTY_WEIGHT = EmptyHolder.EMPTY_WEIGHT
private const val EMPTY_ID = ""
private const val EMPTY_NAME = ""
private const val EMPTY_CFAURL = ""
private const val EMPTY_VETSTREETURL = ""
private const val EMPTY_VCAHOSPITALSURL = ""
private const val EMPTY_TEMPERAMENT = ""
private const val EMPTY_ORIGIN = ""
private const val EMPTY_COUNTRYCODES = ""
private const val EMPTY_COUNTRYCODE = ""
private const val EMPTY_DESCRIPTION = ""
private const val EMPTY_LIFESPAN = ""
private const val EMPTY_INDOOR = 0
private const val EMPTY_LAP = 0
private const val EMPTY_ALTNAMES = ""
private const val EMPTY_ADAPTABILITY = 0
private const val EMPTY_AFFECTIONLEVEL = 0
private const val EMPTY_CHILDFRIENDLY = 0
private const val EMPTY_DOGFRIENDLY = 0
private const val EMPTY_ENERGYLEVEL = 0
private const val EMPTY_GROOMING = 0
private const val EMPTY_HEALTHISSUES = 0
private const val EMPTY_INTELLIGENCE = 0
private const val EMPTY_SHEDDINGLEVEL = 0
private const val EMPTY_SOCIALNEEDS = 0
private const val EMPTY_STRANGERFRIENDLY = 0
private const val EMPTY_VOCALISATION = 0
private const val EMPTY_EXPERIMENTAL = 0
private const val EMPTY_HAIRLESS = 0
private const val EMPTY_NATURAL = 0
private const val EMPTY_RARE = 0
private const val EMPTY_REX = 0
private const val EMPTY_SUPPRESSEDTAIL = 0
private const val EMPTY_SHORTLEGS = 0
private const val EMPTY_WIKIPEDIAURL = ""
private const val EMPTY_HYPOALLERGENIC = 0

data class BreedModel(
    @SerializedName("weight")
    var weight: WeightModel = EMPTY_WEIGHT,
    @SerializedName("id")
    var id: String = EMPTY_ID,
    @SerializedName("name")
    var name: String = EMPTY_NAME,
    @SerializedName("cfa_url")
    var cfaUrl: String = EMPTY_CFAURL,
    @SerializedName("vetstreet_url")
    var vetstreetUrl: String = EMPTY_VETSTREETURL,
    @SerializedName("vcahospitals_url")
    var vcahospitalsUrl: String = EMPTY_VCAHOSPITALSURL,
    @SerializedName("temperament")
    var temperament: String = EMPTY_TEMPERAMENT,
    @SerializedName("origin")
    var origin: String = EMPTY_ORIGIN,
    @SerializedName("country_codes")
    var countryCodes: String = EMPTY_COUNTRYCODES,
    @SerializedName("country_code")
    var countryCode: String = EMPTY_COUNTRYCODE,
    @SerializedName("description")
    var description: String = EMPTY_DESCRIPTION,
    @SerializedName("life_span")
    var lifeSpan: String = EMPTY_LIFESPAN,
    @SerializedName("indoor")
    var indoor: Int = EMPTY_INDOOR,
    @SerializedName("lap")
    var lap: Int = EMPTY_LAP,
    @SerializedName("alt_names")
    var altNames: String = EMPTY_ALTNAMES,
    @SerializedName("adaptability")
    var adaptability: Int = EMPTY_ADAPTABILITY,
    @SerializedName("affection_level")
    var affectionLevel: Int = EMPTY_AFFECTIONLEVEL,
    @SerializedName("child_friendly")
    var childFriendly: Int = EMPTY_CHILDFRIENDLY,
    @SerializedName("dog_friendly")
    var dogFriendly: Int = EMPTY_DOGFRIENDLY,
    @SerializedName("energy_level")
    var energyLevel: Int = EMPTY_ENERGYLEVEL,
    @SerializedName("grooming")
    var grooming: Int = EMPTY_GROOMING,
    @SerializedName("health_issues")
    var healthIssues: Int = EMPTY_HEALTHISSUES,
    @SerializedName("intelligence")
    var intelligence: Int = EMPTY_INTELLIGENCE,
    @SerializedName("shedding_level")
    var sheddingLevel: Int = EMPTY_SHEDDINGLEVEL,
    @SerializedName("social_needs")
    var socialNeeds: Int = EMPTY_SOCIALNEEDS,
    @SerializedName("stranger_friendly")
    var strangerFriendly: Int = EMPTY_STRANGERFRIENDLY,
    @SerializedName("vocalisation")
    var vocalisation: Int = EMPTY_VOCALISATION,
    @SerializedName("experimental")
    var experimental: Int = EMPTY_EXPERIMENTAL,
    @SerializedName("hairless")
    var hairless: Int = EMPTY_HAIRLESS,
    @SerializedName("natural")
    var natural: Int = EMPTY_NATURAL,
    @SerializedName("rare")
    var rare: Int = EMPTY_RARE,
    @SerializedName("rex")
    var rex: Int = EMPTY_REX,
    @SerializedName("suppressed_tail")
    var suppressedTail: Int = EMPTY_SUPPRESSEDTAIL,
    @SerializedName("short_legs")
    var shortLegs: Int = EMPTY_SHORTLEGS,
    @SerializedName("wikipedia_url")
    var wikipediaUrl: String = EMPTY_WIKIPEDIAURL,
    @SerializedName("hypoallergenic")
    var hypoallergenic: Int = EMPTY_HYPOALLERGENIC
)