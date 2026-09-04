# GrihaCare

GrihaCare is a practical housing and home-services platform built around a simple idea: finding a place to live and finding reliable help for that place should not feel like two completely different problems.

The project brings rental/PG discovery, verified service workers, bookings, user profiles, safety features and an AI-assisted chat experience into one platform.

It is being developed as a full-stack project with an Android application, backend services and a web interface.

---

## Why GrihaCare?

Finding a room is usually easy to start and difficult to finish.

You may find a listing but still have questions about the area, price, facilities, safety or the person managing it. The same problem exists with home services. A user may need a maid, cook, electrician, driver or plumber, but finding someone trustworthy is often based on scattered recommendations.

GrihaCare tries to bring these everyday requirements into one place.

The focus of the project is not just listing services, but making the overall process easier:

**Discover → Compare → Verify → Communicate → Book → Review**

---

## What the Platform Includes

### For Users

- Browse PGs, private rooms and affordable rental options
- Search properties by location and budget
- View property details and basic facilities
- Find domestic and home-service workers
- Check ratings, experience and trust information
- View verified worker profiles
- Chat with the GrihaCare assistant
- Book a property or service
- Maintain a personal profile
- View previous and active bookings
- Use safety-focused features for new visits

### For Service Workers

The platform is designed to support workers such as:

- Maids
- Home cooks
- Drivers
- Electricians
- Plumbers
- Home cleaners

Worker profiles can contain information such as experience, pricing, ratings, verification status and trust score.

### Trust and Safety

Trust is an important part of GrihaCare.

The application includes concepts such as:

- Mobile verification
- Identity verification flow
- Verified worker badges
- Trust Score
- Ratings and reviews
- First Visit / Safety Radar concepts

The identity verification screen currently represents a **demo KYC flow** for the project. It is not connected to the real UIDAI Aadhaar verification system.

---

## AI Assistant

GrihaCare includes a conversational assistant that can help users with common housing and service-related questions.

Examples:

- "Show me affordable PGs in Gwalior"
- "I need a private room under ₹12,000"
- "Find a maid near Andheri"
- "What should I check before booking a room?"
- "Which option is better for a student?"

The Android application sends the conversation to the GrihaCare backend, which handles the AI request.

The backend is designed so that the Anthropic API key is kept on the server instead of being placed directly inside the Android application.

For development without an API key, the backend can also respond using the project's demo/fallback logic.

---

## Project Structure

```text
GrihCare-App/
│
├── android-app/
│   └── Android application
│
├── backend/
│   └── Node.js / Express API
│
├── website/
│   └── Web application
│
└── README.md
