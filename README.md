# GrihaCare

### Housing, Home Services and Trust — in one place.

GrihaCare is a full-stack platform designed around a simple problem: finding an affordable place to stay and finding reliable help for that place are often treated as completely separate tasks.

A student may be searching for a PG. A working professional may need a private room. A family may need a maid, cook, electrician, plumber or driver. In each case, the user has to search, compare options, ask questions, verify people and finally make a decision.

GrihaCare brings these everyday needs into one connected experience.

The project combines an Android application, backend services and a web interface, with a focus on affordability, trust, convenience and safety.

---

## Demo Video

[Watch the GrihaCare Demo](https://drive.google.com/file/d/1iKqM3wUbcU2KdbbH8Z72mMf7JCBCh-Pa/view?usp=drivesdk)

---

## The Problem

Finding accommodation is not only about finding a room.

A user may find a listing but still want to know:

- Is the rent within my budget?
- Is the location suitable?
- What facilities are available?
- Is the listing genuine?
- Who is managing the property?
- Can I trust the person providing the service?

The same problem appears when looking for home-service professionals.

Finding a maid, cook, driver, electrician or plumber often depends on personal references, scattered listings or informal recommendations.

This creates a fragmented experience where users have to move between different sources for:

**Discovery → Comparison → Verification → Communication → Booking → Review**

GrihaCare is built to connect these steps.

---

## What is GrihaCare?

GrihaCare is designed as a single platform where users can:

1. Discover affordable accommodation
2. Find home-service professionals
3. Compare profiles and options
4. Check trust-related information
5. Communicate through chat
6. Book a property or service
7. Manage bookings and profile information
8. Use safety-focused features during new visits

The goal is not to create another listing application.

The goal is to make the complete decision-making process easier.

---

# Main Features

## 1. Accommodation Discovery

Users can explore practical housing options such as:

- PGs
- Private rooms
- 1RKs
- Student accommodation
- Budget-friendly monthly rentals

Search and discovery are designed around common user requirements such as:

- Location
- Monthly budget
- Type of stay
- Furnishing
- Parking
- Family-friendly options

The project focuses primarily on affordable and realistic rental scenarios rather than premium luxury properties.

---

## 2. Home Services

GrihaCare also connects users with everyday service professionals.

Supported service categories include:

- Maid
- Home Cook
- Driver
- Electrician
- Plumber
- Home Cleaner

Worker profiles can include:

- Name
- Service category
- Experience
- Pricing
- Rating
- Trust Score
- Verification status
- Location

This gives the user more context before starting a booking.

---

## 3. Trust and Verification

Trust is one of the central ideas behind GrihaCare.

The application includes a verification-oriented experience with features such as:

- Mobile verification
- Identity verification flow
- Verified profile indicators
- Trust Score
- Ratings and reviews
- Safety-oriented visit features

### Trust Score

The Trust Score is intended to provide an easy-to-understand signal when comparing profiles.

It can consider factors such as:

- Identity verification
- Mobile verification
- Experience
- Ratings
- Reviews
- Profile and location information

The Trust Score is a supporting signal and should not be treated as a guarantee of a person's safety or reliability.

### Important Note

The identity verification shown in the current application is a **demo KYC flow for the project**.

It is not connected to the real UIDAI Aadhaar verification infrastructure.

---

## 4. AI Assistant

GrihaCare includes an AI-assisted conversational interface for housing and home-service related questions.

Instead of navigating through multiple screens, users can describe what they need in normal language.

For example:

> "I am a student looking for a private room in Gwalior under ₹10,000 per month."

Other examples:

> "Show me affordable PGs in Gwalior."

> "Find a maid near Andheri."

> "What should I check before booking a room?"

> "Which option would be better for a student?"

The Android application communicates with the GrihaCare backend, and the backend handles the AI request.

The API key is kept on the server side rather than being placed inside the Android application.

For development environments where an AI API key is not configured, the backend also supports demo/fallback responses.

---

# How GrihaCare Works

A typical user journey looks like this:

```text
                    GrihaCare
                        |
                     Login
                        |
                  OTP Verification
                        |
                      Home
               _________|_________
              |                   |
          Accommodation       Home Services
              |                   |
        Search / Filter       Worker Search
              |                   |
        Property Details      Worker Profile
              |                   |
             Chat             Trust Score
              |                   |
              |              Verification
              |                   |
              |_________   _______|
                        \ /
                      Booking
                        |
                    Confirmation
                        |
                      Review
