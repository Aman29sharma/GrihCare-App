package com.example.grihacare.data

import com.example.grihacare.model.Listing
import com.example.grihacare.model.ServiceType
import com.example.grihacare.model.Worker

object MockRepository {

    // ========================================
    // PROPERTY & STAY DATA
    // ========================================

    val listings = listOf(

        Listing(
            id = "H001",
            title = "2 BHK Modern Apartment",
            location = "HSR Layout, Bengaluru",
            price = "₹28,000/month",
            rating = 4.8,
            type = ServiceType.HOME,
            verified = true,
            aiMatch = 98,
            bedrooms = 2,
            bathrooms = 2,
            description =
                "Modern 2 BHK apartment with parking, WiFi and security."
        ),

        Listing(
            id = "H002",
            title = "Fully Furnished 1 BHK",
            location = "Koramangala, Bengaluru",
            price = "₹22,000/month",
            rating = 4.7,
            type = ServiceType.HOME,
            verified = true,
            aiMatch = 94,
            bedrooms = 1,
            bathrooms = 1,
            description =
                "Fully furnished apartment near major offices and cafes."
        ),

        Listing(
            id = "S001",
            title = "Premium Business Stay",
            location = "Indiranagar, Bengaluru",
            price = "₹2,999/night",
            rating = 4.9,
            type = ServiceType.STAY,
            verified = true,
            aiMatch = 96,
            description =
                "Premium short stay with modern rooms and high-speed WiFi."
        ),

        Listing(
            id = "S002",
            title = "Cozy Studio Stay",
            location = "Andheri, Mumbai",
            price = "₹1,799/night",
            rating = 4.6,
            type = ServiceType.STAY,
            verified = true,
            aiMatch = 91,
            description =
                "Comfortable studio for short business or personal stays."
        )
    )


    // ========================================
    // WORKER DATA
    // ========================================

    val workers = listOf(

        Worker(
            id = "W001",
            name = "Anita Sharma",
            role = "Professional Cook",
            location = "Andheri, Mumbai",
            rating = 4.9,
            verified = true,
            price = "₹8,500/month",
            availability = "Morning",
            experience = "6 years",
            skills = listOf(
                "North Indian",
                "South Indian",
                "Breakfast",
                "Elderly Care"
            )
        ),

        Worker(
            id = "W002",
            name = "Sunita Verma",
            role = "Maid",
            location = "Andheri, Mumbai",
            rating = 4.8,
            verified = true,
            price = "₹7,000/month",
            availability = "Morning + Evening",
            experience = "5 years",
            skills = listOf(
                "Cleaning",
                "Kitchen Help",
                "Laundry"
            )
        ),

        Worker(
            id = "W003",
            name = "Rajesh Kumar",
            role = "Electrician",
            location = "Andheri, Mumbai",
            rating = 4.7,
            verified = true,
            price = "From ₹399",
            availability = "Available today",
            experience = "8 years",
            skills = listOf(
                "Electrical Repair",
                "Wiring",
                "Installation"
            )
        ),

        Worker(
            id = "W004",
            name = "Imran Khan",
            role = "Driver",
            location = "Powai, Mumbai",
            rating = 4.9,
            verified = true,
            price = "₹18,000/month",
            availability = "Full time",
            experience = "10 years",
            skills = listOf(
                "City Driving",
                "Long Distance",
                "Corporate Driver"
            )
        )
    )
}