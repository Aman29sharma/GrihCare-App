//package com.example.grihacare.data
//
//data class Property(
//    val name: String,
//    val location: String,
//    val rent: String,
//    val type: String,
//    val match: Int,
//    val furnished: String,
//    val parking: Boolean,
//    val familyFriendly: Boolean
//)
//
//data class Worker(
//    val name: String,
//    val role: String,
//    val location: String,
//    val price: String,
//    val rating: Double,
//    val trustScore: Int,
//    val experience: String,
//    val verified: Boolean = true
//)
//
//val properties = listOf(
//
//    Property(
//        "Modern 2 BHK",
//        "HSR Layout",
//        "₹28,000",
//        "2 BHK",
//        98,
//        "Semi Furnished",
//        true,
//        true
//    ),
//
//    Property(
//        "Premium 2 BHK",
//        "Koramangala",
//        "₹32,000",
//        "2 BHK",
//        94,
//        "Fully Furnished",
//        true,
//        true
//    ),
//
//    Property(
//        "Family 3 BHK",
//        "Whitefield",
//        "₹38,000",
//        "3 BHK",
//        91,
//        "Semi Furnished",
//        true,
//        true
//    ),
//
//    Property(
//        "Smart 1 BHK",
//        "Indiranagar",
//        "₹22,000",
//        "1 BHK",
//        96,
//        "Fully Furnished",
//        true,
//        false
//    ),
//
//    Property(
//        "Furnished 2 BHK",
//        "Marathahalli",
//        "₹26,000",
//        "2 BHK",
//        93,
//        "Fully Furnished",
//        true,
//        true
//    )
//)
//
//val workers = listOf(
//
//    Worker(
//        "Priya Sharma",
//        "Professional Maid",
//        "Andheri",
//        "₹8,000/month",
//        4.9,
//        94,
//        "6 years"
//    ),
//
//    Worker(
//        "Anita Verma",
//        "Home Cook",
//        "Powai",
//        "₹9,500/month",
//        4.8,
//        91,
//        "5 years"
//    ),
//
//    Worker(
//        "Raj Kumar",
//        "Driver",
//        "Bandra",
//        "₹18,000/month",
//        4.9,
//        96,
//        "8 years"
//    ),
//
//    Worker(
//        "Mohit Singh",
//        "Electrician",
//        "Andheri",
//        "From ₹399",
//        4.7,
//        89,
//        "4 years"
//    )
//)
//
//val pgList = listOf(
//    "Urban Nest PG • Koramangala • ₹11,500",
//    "Green View PG • HSR Layout • ₹9,500",
//    "Metro Living PG • Indiranagar • ₹13,000",
//    "Student Hub • Whitefield • ₹8,500"
//)






package com.example.grihacare.data

data class Property(
    val name: String,
    val location: String,
    val rent: String,
    val type: String,
    val match: Int,
    val furnished: String,
    val parking: Boolean,
    val familyFriendly: Boolean
)

data class Worker(
    val name: String,
    val role: String,
    val location: String,
    val price: String,
    val rating: Double,
    val trustScore: Int,
    val experience: String,
    val verified: Boolean = true
)

/*
 * GrihaCare focuses on Indian:
 *
 * PG
 * Private Rooms
 * 1RK
 * Affordable 1BHK
 *
 * NOT hotels.
 *
 * Prices are intentionally kept in a
 * realistic prototype range of roughly
 * ₹7k–₹20k per month.
 */

val properties = listOf(

    Property(
        name = "Cozy Private Room",
        location = "Gwalior • City Centre",
        rent = "₹8,500/mo",
        type = "Private Room",
        match = 96,
        furnished = "Furnished",
        parking = true,
        familyFriendly = false
    ),

    Property(
        name = "Green Nest PG",
        location = "Indore • Vijay Nagar",
        rent = "₹7,500/mo",
        type = "PG",
        match = 94,
        furnished = "Semi Furnished",
        parking = false,
        familyFriendly = false
    ),

    Property(
        name = "Metro Stay Room",
        location = "Delhi • Laxmi Nagar",
        rent = "₹10,500/mo",
        type = "Private Room",
        match = 92,
        furnished = "Furnished",
        parking = false,
        familyFriendly = false
    ),

    Property(
        name = "WorkNest PG",
        location = "Pune • Hinjawadi",
        rent = "₹9,500/mo",
        type = "PG",
        match = 97,
        furnished = "Fully Furnished",
        parking = true,
        familyFriendly = false
    ),

    Property(
        name = "Urban Living PG",
        location = "Hyderabad • Madhapur",
        rent = "₹12,500/mo",
        type = "PG",
        match = 95,
        furnished = "Fully Furnished",
        parking = true,
        familyFriendly = false
    ),

    Property(
        name = "Compact 1RK",
        location = "Bengaluru • HSR Layout",
        rent = "₹15,500/mo",
        type = "1RK",
        match = 98,
        furnished = "Semi Furnished",
        parking = true,
        familyFriendly = true
    ),

    Property(
        name = "Budget PG Corner",
        location = "Bengaluru • Marathahalli",
        rent = "₹9,000/mo",
        type = "PG",
        match = 96,
        furnished = "Fully Furnished",
        parking = false,
        familyFriendly = false
    ),

    Property(
        name = "Smart 1RK",
        location = "Noida • Sector 62",
        rent = "₹13,500/mo",
        type = "1RK",
        match = 93,
        furnished = "Semi Furnished",
        parking = true,
        familyFriendly = true
    ),

    Property(
        name = "Malviya Room",
        location = "Jaipur • Malviya Nagar",
        rent = "₹9,000/mo",
        type = "Private Room",
        match = 91,
        furnished = "Furnished",
        parking = true,
        familyFriendly = false
    ),

    Property(
        name = "Andheri Private Room",
        location = "Mumbai • Andheri East",
        rent = "₹17,500/mo",
        type = "Private Room",
        match = 90,
        furnished = "Furnished",
        parking = false,
        familyFriendly = false
    )
)

val workers = listOf(

    Worker(
        name = "Priya Sharma",
        role = "Professional Maid",
        location = "Andheri",
        price = "₹8,000/mo",
        rating = 4.9,
        trustScore = 94,
        experience = "6 years"
    ),

    Worker(
        name = "Anita Verma",
        role = "Home Cook",
        location = "Powai",
        price = "₹9,500/mo",
        rating = 4.8,
        trustScore = 91,
        experience = "5 years"
    ),

    Worker(
        name = "Raj Kumar",
        role = "Driver",
        location = "Bandra",
        price = "₹18,000/mo",
        rating = 4.9,
        trustScore = 96,
        experience = "8 years"
    ),

    Worker(
        name = "Mohit Singh",
        role = "Electrician",
        location = "Andheri",
        price = "From ₹399",
        rating = 4.7,
        trustScore = 89,
        experience = "4 years"
    ),

    Worker(
        name = "Sonal Gupta",
        role = "Home Cleaner",
        location = "Noida",
        price = "₹6,500/mo",
        rating = 4.8,
        trustScore = 92,
        experience = "5 years"
    ),

    Worker(
        name = "Amit Verma",
        role = "Plumber",
        location = "Indore",
        price = "From ₹299",
        rating = 4.7,
        trustScore = 88,
        experience = "7 years"
    )
)

val pgList = listOf(
    "Green Nest PG • Indore • ₹7,500/mo",
    "WorkNest PG • Pune • ₹9,500/mo",
    "Budget PG Corner • Bengaluru • ₹9,000/mo",
    "Urban Living PG • Hyderabad • ₹12,500/mo"
)