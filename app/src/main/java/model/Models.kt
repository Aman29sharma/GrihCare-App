

package com.example.grihacare.model

// ========================================
// SERVICE TYPES
// ========================================

enum class ServiceType(
    val title: String,
    val subtitle: String
) {

    HOME(
        title = "Rent a Home",
        subtitle = "Verified rental homes"
    ),

    STAY(
        title = "Short Stay",
        subtitle = "Hotels & short stays"
    ),

    WORKER(
        title = "Home Workers",
        subtitle = "Maid, cook, driver & more"
    )
}


// ========================================
// USER ROLES
// ========================================

enum class UserRole {

    CUSTOMER,

    OWNER,

    WORKER,

    ADMIN
}


// ========================================
// PROPERTY / STAY LISTING
// ========================================

data class Listing(

    val id: String,

    val title: String,

    val location: String,

    val price: String,

    val rating: Double,

    val type: ServiceType,

    val verified: Boolean = true,

    val aiMatch: Int = 90,

    val bedrooms: Int = 0,

    val bathrooms: Int = 0,

    val description: String = ""

)


// ========================================
// HOME WORKER
// ========================================

data class Worker(

    val id: String,

    val name: String,

    val role: String,

    val location: String,

    val rating: Double,

    val verified: Boolean,

    val price: String,

    val availability: String,

    val experience: String = "",

    val skills: List<String> = emptyList()

)


// ========================================
// BOOKING
// ========================================

data class Booking(

    val id: String,

    val title: String,

    val location: String,

    val price: String,

    val status: String,

    val date: String = ""

)


// ========================================
// CHAT MESSAGE
// ========================================

data class ChatMessage(

    val id: String,

    val senderId: String,

    val message: String,

    val timestamp: String,

    val isMine: Boolean

)


// ========================================
// REVIEW
// ========================================

data class Review(

    val id: String,

    val userName: String,

    val rating: Double,

    val comment: String,

    val date: String

)


// ========================================
// USER
// ========================================

data class User(

    val id: String,

    val name: String,

    val phone: String,

    val role: UserRole,

    val verified: Boolean = false

)