const express = require("express");
const axios = require("axios");

const router = express.Router();

/* =========================================================
   GRIHACARE DEMO PROPERTY DATA
   Indian PG / ROOM / 1RK focused
   ========================================================= */

const demoProperties = [
    {
        id: "P001",
        name: "Green Nest PG",
        type: "PG",
        location: "Indore - Vijay Nagar",
        city: "Indore",
        rent: 7500,
        rentText: "₹7,500/month",
        match: 96,
        furnished: "Fully Furnished",
        food: "Breakfast + Dinner",
        occupancy: "Single / Double",
        parking: false,
        wifi: true,
        attachedBathroom: true,
        suitableFor: ["students", "professionals"],
        deposit: "₹7,500"
    },
    {
        id: "P002",
        name: "Cozy Private Room",
        type: "Private Room",
        location: "Gwalior - City Centre",
        city: "Gwalior",
        rent: 8500,
        rentText: "₹8,500/month",
        match: 95,
        furnished: "Furnished",
        food: "Optional",
        occupancy: "Single",
        parking: true,
        wifi: true,
        attachedBathroom: true,
        suitableFor: ["professionals", "students"],
        deposit: "₹8,500"
    },
    {
        id: "P003",
        name: "WorkNest PG",
        type: "PG",
        location: "Pune - Hinjawadi",
        city: "Pune",
        rent: 9500,
        rentText: "₹9,500/month",
        match: 98,
        furnished: "Fully Furnished",
        food: "Breakfast + Dinner",
        occupancy: "Single / Double",
        parking: true,
        wifi: true,
        attachedBathroom: true,
        suitableFor: ["professionals"],
        deposit: "₹10,000"
    },
    {
        id: "P004",
        name: "Metro Stay Room",
        type: "Private Room",
        location: "Delhi - Laxmi Nagar",
        city: "Delhi",
        rent: 10500,
        rentText: "₹10,500/month",
        match: 93,
        furnished: "Furnished",
        food: "Optional",
        occupancy: "Single",
        parking: false,
        wifi: true,
        attachedBathroom: true,
        suitableFor: ["students", "professionals"],
        deposit: "₹10,000"
    },
    {
        id: "P005",
        name: "Budget PG Corner",
        type: "PG",
        location: "Bengaluru - Marathahalli",
        city: "Bengaluru",
        rent: 9000,
        rentText: "₹9,000/month",
        match: 97,
        furnished: "Fully Furnished",
        food: "Breakfast + Dinner",
        occupancy: "Double / Triple",
        parking: false,
        wifi: true,
        attachedBathroom: true,
        suitableFor: ["students", "professionals"],
        deposit: "₹9,000"
    },
    {
        id: "P006",
        name: "Compact 1RK",
        type: "1RK",
        location: "Bengaluru - HSR Layout",
        city: "Bengaluru",
        rent: 15500,
        rentText: "₹15,500/month",
        match: 98,
        furnished: "Semi Furnished",
        food: "Not Included",
        occupancy: "Single / Couple",
        parking: true,
        wifi: false,
        attachedBathroom: true,
        suitableFor: ["professionals", "couples"],
        deposit: "₹25,000"
    },
    {
        id: "P007",
        name: "Urban Living PG",
        type: "PG",
        location: "Hyderabad - Madhapur",
        city: "Hyderabad",
        rent: 12500,
        rentText: "₹12,500/month",
        match: 95,
        furnished: "Fully Furnished",
        food: "Breakfast + Dinner",
        occupancy: "Single / Double",
        parking: true,
        wifi: true,
        attachedBathroom: true,
        suitableFor: ["professionals"],
        deposit: "₹12,000"
    },
    {
        id: "P008",
        name: "Smart 1RK",
        type: "1RK",
        location: "Noida - Sector 62",
        city: "Noida",
        rent: 13500,
        rentText: "₹13,500/month",
        match: 94,
        furnished: "Semi Furnished",
        food: "Not Included",
        occupancy: "Single",
        parking: true,
        wifi: false,
        attachedBathroom: true,
        suitableFor: ["professionals"],
        deposit: "₹20,000"
    },
    {
        id: "P009",
        name: "Malviya Private Room",
        type: "Private Room",
        location: "Jaipur - Malviya Nagar",
        city: "Jaipur",
        rent: 9000,
        rentText: "₹9,000/month",
        match: 92,
        furnished: "Furnished",
        food: "Optional",
        occupancy: "Single",
        parking: true,
        wifi: true,
        attachedBathroom: true,
        suitableFor: ["students", "professionals"],
        deposit: "₹9,000"
    },
    {
        id: "P010",
        name: "Andheri Private Room",
        type: "Private Room",
        location: "Mumbai - Andheri East",
        city: "Mumbai",
        rent: 17500,
        rentText: "₹17,500/month",
        match: 91,
        furnished: "Furnished",
        food: "Optional",
        occupancy: "Single",
        parking: false,
        wifi: true,
        attachedBathroom: true,
        suitableFor: ["professionals"],
        deposit: "₹20,000"
    },
    {
        id: "P011",
        name: "Student Hub PG",
        type: "PG",
        location: "Gwalior - University Area",
        city: "Gwalior",
        rent: 7000,
        rentText: "₹7,000/month",
        match: 97,
        furnished: "Semi Furnished",
        food: "Breakfast + Dinner",
        occupancy: "Double / Triple",
        parking: true,
        wifi: true,
        attachedBathroom: false,
        suitableFor: ["students"],
        deposit: "₹5,000"
    },
    {
        id: "P012",
        name: "Metro View PG",
        type: "PG",
        location: "Delhi - Noida Link Road",
        city: "Delhi",
        rent: 11500,
        rentText: "₹11,500/month",
        match: 90,
        furnished: "Fully Furnished",
        food: "Breakfast + Dinner",
        occupancy: "Single / Double",
        parking: false,
        wifi: true,
        attachedBathroom: true,
        suitableFor: ["professionals", "students"],
        deposit: "₹12,000"
    }
];

/* =========================================================
   WORKERS
   ========================================================= */

const demoWorkers = [
    {
        id: "W001",
        name: "Priya Sharma",
        role: "Professional Maid",
        location: "Andheri",
        city: "Mumbai",
        price: 8000,
        priceText: "₹8,000/month",
        rating: 4.9,
        trust: 94,
        experience: "6 years",
        languages: ["Hindi", "English"],
        services: [
            "cleaning",
            "utensils",
            "dusting",
            "laundry"
        ],
        verified: true
    },
    {
        id: "W002",
        name: "Anita Verma",
        role: "Home Cook",
        location: "Powai",
        city: "Mumbai",
        price: 9500,
        priceText: "₹9,500/month",
        rating: 4.8,
        trust: 91,
        experience: "5 years",
        languages: ["Hindi", "English", "Marathi"],
        services: [
            "north indian food",
            "south indian food",
            "vegetarian food",
            "daily cooking"
        ],
        verified: true
    },
    {
        id: "W003",
        name: "Raj Kumar",
        role: "Driver",
        location: "Bandra",
        city: "Mumbai",
        price: 18000,
        priceText: "₹18,000/month",
        rating: 4.9,
        trust: 96,
        experience: "8 years",
        languages: ["Hindi", "English"],
        services: [
            "city driving",
            "office commute",
            "family driving"
        ],
        verified: true
    },
    {
        id: "W004",
        name: "Mohit Singh",
        role: "Electrician",
        location: "Andheri",
        city: "Mumbai",
        price: 399,
        priceText: "From ₹399",
        rating: 4.7,
        trust: 89,
        experience: "4 years",
        languages: ["Hindi", "English"],
        services: [
            "fan repair",
            "switch repair",
            "wiring",
            "light installation"
        ],
        verified: true
    },
    {
        id: "W005",
        name: "Sonal Gupta",
        role: "Home Cleaner",
        location: "Noida Sector 62",
        city: "Noida",
        price: 6500,
        priceText: "₹6,500/month",
        rating: 4.8,
        trust: 92,
        experience: "5 years",
        languages: ["Hindi"],
        services: [
            "deep cleaning",
            "kitchen cleaning",
            "bathroom cleaning"
        ],
        verified: true
    },
    {
        id: "W006",
        name: "Amit Verma",
        role: "Plumber",
        location: "Vijay Nagar",
        city: "Indore",
        price: 299,
        priceText: "From ₹299",
        rating: 4.7,
        trust: 88,
        experience: "7 years",
        languages: ["Hindi"],
        services: [
            "tap repair",
            "pipe repair",
            "leakage",
            "bathroom fitting"
        ],
        verified: true
    }
];

/* =========================================================
   HELPERS
   ========================================================= */

function money(value) {
    return new Intl.NumberFormat("en-IN", {
        style: "currency",
        currency: "INR",
        maximumFractionDigits: 0
    }).format(value);
}

function findCity(text) {

    const cities = [
        "bengaluru",
        "bangalore",
        "gwalior",
        "indore",
        "delhi",
        "pune",
        "hyderabad",
        "jaipur",
        "noida",
        "mumbai"
    ];

    return cities.find(city =>
        text.includes(city)
    );
}

function getCityMatches(text) {

    const city = findCity(text);

    if (!city) {
        return demoProperties;
    }

    const normalizedCity =
        city === "bangalore"
            ? "Bengaluru"
            : city.charAt(0).toUpperCase() +
              city.slice(1);

    return demoProperties.filter(item =>
        item.city.toLowerCase()
            .includes(normalizedCity.toLowerCase())
    );
}

function extractBudget(text) {

    const patterns = [
        /(?:under|below|less than|max|maximum|budget)\s*(?:₹|rs\.?|inr)?\s*([0-9]+)\s*k?/i,
        /([0-9]+)\s*k\s*(?:per month|monthly|\/month)?/i,
        /₹\s*([0-9,]+)/i
    ];

    for (const pattern of patterns) {

        const match =
            text.match(pattern);

        if (!match) {
            continue;
        }

        let value =
            Number(
                match[1]
                    .replace(/,/g, "")
            );

        if (value < 1000) {
            value *= 1000;
        }

        return value;
    }

    return null;
}

function formatProperty(property) {

    return [
        `🏠 ${property.name}`,
        `📍 ${property.location}`,
        `💰 ${property.rentText}`,
        `✨ ${property.type}`,
        `🛏 ${property.occupancy}`,
        `🪑 ${property.furnished}`,
        `📶 Wi-Fi: ${property.wifi ? "Yes" : "No"}`,
        `🚿 Attached Bathroom: ${property.attachedBathroom ? "Yes" : "No"}`,
        `🅿️ Parking: ${property.parking ? "Yes" : "No"}`,
        `⭐ AI Match: ${property.match}%`
    ].join("\n");
}

function formatWorker(worker) {

    return [
        `👤 ${worker.name}`,
        `🧰 ${worker.role}`,
        `📍 ${worker.location}`,
        `💰 ${worker.priceText}`,
        `⭐ ${worker.rating}/5`,
        `🛡 TrustScore: ${worker.trust}/100`,
        `💼 Experience: ${worker.experience}`,
        `✓ Demo KYC verified`
    ].join("\n");
}

/* =========================================================
   DEMO AI FALLBACK
   ========================================================= */

function demoAI(message) {

    const text =
        message
            .toLowerCase()
            .trim();

    /* -----------------------------------------
       WORKERS
       ----------------------------------------- */

    if (
        text.includes("maid") ||
        text.includes("house help") ||
        text.includes("cleaner") ||
        text.includes("cleaning") ||
        text.includes("domestic help")
    ) {

        const worker =
            demoWorkers.find(
                item =>
                    item.role ===
                    "Professional Maid"
            );

        return `
I found a highly trusted home helper for you 👩‍💼

${formatWorker(worker)}

She is a strong choice for regular home cleaning and household support.

Before hiring, GrihaCare asks you to complete the Demo KYC flow.
`;
    }

    if (
        text.includes("cook") ||
        text.includes("cooking") ||
        text.includes("food")
    ) {

        const worker =
            demoWorkers.find(
                item =>
                    item.role ===
                    "Home Cook"
            );

        return `
Here's a good home-cook match 🍳

${formatWorker(worker)}

She supports regular daily cooking and both North/South Indian style meals.

Next step: open the worker profile and complete Demo KYC before hiring.
`;
    }

    if (
        text.includes("driver") ||
        text.includes("driving")
    ) {

        const worker =
            demoWorkers.find(
                item =>
                    item.role === "Driver"
            );

        return `
I found a highly rated driver 🚗

${formatWorker(worker)}

TrustScore is ${worker.trust}/100 with ${worker.experience} of experience.
`;
    }

    if (
        text.includes("electrician") ||
        text.includes("electric") ||
        text.includes("switch") ||
        text.includes("fan repair")
    ) {

        const worker =
            demoWorkers.find(
                item =>
                    item.role ===
                    "Electrician"
            );

        return `
Here's a verified electrician ⚡

${formatWorker(worker)}

Starting service charge is ${worker.priceText}.
`;
    }

    if (
        text.includes("plumber") ||
        text.includes("tap") ||
        text.includes("pipe") ||
        text.includes("leakage")
    ) {

        const worker =
            demoWorkers.find(
                item =>
                    item.role === "Plumber"
            );

        return `
I found a plumber for you 🔧

${formatWorker(worker)}

Starting service charge is ${worker.priceText}.
`;
    }

    /* -----------------------------------------
       PG
       ----------------------------------------- */

    if (
        text.includes("pg") ||
        text.includes("paying guest")
    ) {

        const cityMatches =
            getCityMatches(text)
                .filter(
                    item =>
                        item.type === "PG"
                );

        const budget =
            extractBudget(text);

        let matches =
            cityMatches.length
                ? cityMatches
                : demoProperties.filter(
                    item =>
                        item.type === "PG"
                );

        if (budget) {

            const underBudget =
                matches.filter(
                    item =>
                        item.rent <= budget
                );

            if (underBudget.length) {
                matches = underBudget;
            }
        }

        matches =
            matches
                .sort(
                    (a, b) =>
                        b.match - a.match
                )
                .slice(0, 4);

        return `
I found these PG options for you 🏠

${matches
    .map(
        (item, index) =>
            `${index + 1}. ${formatProperty(item)}`
    )
    .join("\n\n")}

Tell me your city + monthly budget and I can narrow this to 2–3 best matches.
`;
    }

    /* -----------------------------------------
       ROOM / 1RK
       ----------------------------------------- */

    if (
        text.includes("room") ||
        text.includes("1rk") ||
        text.includes("private room") ||
        text.includes("single room")
    ) {

        const cityMatches =
            getCityMatches(text).filter(
                item =>
                    item.type ===
                        "Private Room" ||
                    item.type === "1RK"
            );

        const budget =
            extractBudget(text);

        let matches =
            cityMatches.length
                ? cityMatches
                : demoProperties.filter(
                    item =>
                        item.type ===
                            "Private Room" ||
                        item.type === "1RK"
                );

        if (budget) {

            const affordable =
                matches.filter(
                    item =>
                        item.rent <= budget
                );

            if (affordable.length) {
                matches = affordable;
            }
        }

        matches =
            matches
                .sort(
                    (a, b) =>
                        b.match - a.match
                )
                .slice(0, 4);

        return `
Here are some room / 1RK options 👇

${matches
    .map(
        (item, index) =>
            `${index + 1}. ${formatProperty(item)}`
    )
    .join("\n\n")}

I can also filter by furnished, parking, Wi-Fi or single occupancy.
`;
    }

    /* -----------------------------------------
       GENERIC HOME / RENT
       ----------------------------------------- */

    if (
        text.includes("home") ||
        text.includes("house") ||
        text.includes("rent") ||
        text.includes("stay") ||
        text.includes("accommodation")
    ) {

        const cityMatches =
            getCityMatches(text);

        const budget =
            extractBudget(text);

        let matches =
            cityMatches.length
                ? cityMatches
                : demoProperties;

        if (budget) {

            const affordable =
                matches.filter(
                    item =>
                        item.rent <= budget
                );

            if (affordable.length) {
                matches = affordable;
            }
        }

        matches =
            matches
                .sort(
                    (a, b) =>
                        b.match - a.match
                )
                .slice(0, 4);

        return `
Here are the best GrihaCare accommodation matches 🏠

${matches
    .map(
        (item, index) =>
            `${index + 1}. ${formatProperty(item)}`
    )
    .join("\n\n")}

GrihaCare is focused on PGs, rooms, 1RKs and affordable rentals — not hotels.
`;
    }

    /* -----------------------------------------
       TRUST / KYC
       ----------------------------------------- */

    if (
        text.includes("trust") ||
        text.includes("verified") ||
        text.includes("kyc") ||
        text.includes("aadhaar") ||
        text.includes("identity")
    ) {

        return `
🛡 GrihaCare Trust Layer

Our prototype uses a TrustScore concept built from:

✓ Identity verification
✓ Mobile verification
✓ Experience
✓ Reviews
✓ Service history
✓ Location signals

For the prototype, worker Aadhaar-style verification is a **Demo KYC flow only**.

It does not perform real UIDAI verification.
`;
    }

    /* -----------------------------------------
       SAFETY
       ----------------------------------------- */

    if (
        text.includes("safe") ||
        text.includes("safety") ||
        text.includes("visit")
    ) {

        return `
🛡 First Visit Mode

For a property visit, GrihaCare's prototype can show:

✓ Meeting location
✓ Visit time
✓ Emergency contact
✓ Check-in status
✓ "I'm Safe" confirmation

This is a prototype safety workflow and does not replace emergency services.
`;
    }

    /* -----------------------------------------
       GREETING
       ----------------------------------------- */

    if (
        text === "hi" ||
        text === "hello" ||
        text.includes("hey")
    ) {

        return `
Hey! 👋 Welcome to GrihaCare AI.

I can help you find:

🏠 PGs
🛏 Private Rooms
🏢 1RKs
👩 Maid / Cleaner
🍳 Home Cook
🚗 Driver
⚡ Electrician
🔧 Plumber
🛡 Verified workers
📅 Property visits

Try:

"PG under ₹10k in Bengaluru"

or

"Find me a room in Gwalior under ₹9k"
`;
    }

    /* -----------------------------------------
       DEFAULT
       ----------------------------------------- */

    return `
I'm your GrihaCare AI assistant 🤖

Tell me any of these:

🏠 "PG under ₹10k in Pune"

🛏 "Private room in Gwalior"

🏠 "1RK in Bengaluru under ₹16k"

👩 "I need a verified maid"

🍳 "Find a home cook"

🚗 "I need a driver"

⚡ "Need an electrician"

🛡 "How does TrustScore work?"
`;
}

/* =========================================================
   CLAUDE SYSTEM PROMPT
   ========================================================= */

function buildSystemPrompt() {

    return `
You are GrihaCare AI, the intelligent assistant inside the GrihaCare mobile application.

ABOUT GRIHACARE:

GrihaCare is an India-focused housing and home-services platform.

Primary accommodation categories:
- PG
- Private Room
- Shared Room
- 1RK
- Affordable rental rooms

Do NOT position GrihaCare as a hotel booking platform.
Do NOT recommend hotels unless the user explicitly asks about hotels.

CORE SERVICES:

1. PG and room discovery
2. Affordable monthly rentals
3. Worker discovery
4. Maids
5. Home cooks
6. Drivers
7. Electricians
8. Plumbers
9. Cleaners
10. Property visits
11. TrustScore
12. Demo KYC
13. Safety workflows

IMPORTANT RESPONSE RULES:

- Be friendly and natural.
- Use Indian English.
- Use INR.
- Prefer monthly prices.
- Keep answers concise but useful.
- Ask for location if missing.
- Ask for budget if missing.
- Ask whether the user wants PG, private room or 1RK when useful.
- Consider student/professional/family requirements.
- Recommend only from the GrihaCare demo inventory below.
- Never invent another real person as a verified GrihaCare worker.
- Never claim demo KYC is real Aadhaar or UIDAI verification.
- Never claim that a worker is legally background-verified unless data explicitly says so.
- When user asks for recommendation, explain WHY.
- For accommodation recommendations, mention rent, location, type and at least one useful amenity.
- For workers, mention rating and TrustScore.

DEMO ACCOMMODATION INVENTORY:

1. Green Nest PG
Indore - Vijay Nagar
₹7,500/month
Fully Furnished
Breakfast + Dinner
Wi-Fi
Attached Bathroom
Students + Professionals
Match 96%

2. Cozy Private Room
Gwalior - City Centre
₹8,500/month
Furnished
Wi-Fi
Parking
Attached Bathroom
Match 95%

3. WorkNest PG
Pune - Hinjawadi
₹9,500/month
Fully Furnished
Breakfast + Dinner
Wi-Fi
Parking
Attached Bathroom
Professionals
Match 98%

4. Metro Stay Room
Delhi - Laxmi Nagar
₹10,500/month
Furnished
Wi-Fi
Attached Bathroom
Match 93%

5. Budget PG Corner
Bengaluru - Marathahalli
₹9,000/month
Fully Furnished
Breakfast + Dinner
Wi-Fi
Attached Bathroom
Match 97%

6. Compact 1RK
Bengaluru - HSR Layout
₹15,500/month
Semi Furnished
Parking
Attached Bathroom
Single/Couple
Match 98%

7. Urban Living PG
Hyderabad - Madhapur
₹12,500/month
Fully Furnished
Breakfast + Dinner
Wi-Fi
Parking
Match 95%

8. Smart 1RK
Noida - Sector 62
₹13,500/month
Semi Furnished
Parking
Attached Bathroom
Match 94%

9. Malviya Private Room
Jaipur - Malviya Nagar
₹9,000/month
Furnished
Parking
Wi-Fi
Match 92%

10. Andheri Private Room
Mumbai - Andheri East
₹17,500/month
Furnished
Wi-Fi
Attached Bathroom
Match 91%

11. Student Hub PG
Gwalior - University Area
₹7,000/month
Semi Furnished
Breakfast + Dinner
Wi-Fi
Parking
Students
Match 97%

12. Metro View PG
Delhi - Noida Link Road
₹11,500/month
Fully Furnished
Breakfast + Dinner
Wi-Fi
Match 90%

DEMO WORKER INVENTORY:

Priya Sharma
Professional Maid
Andheri, Mumbai
₹8,000/month
4.9/5
TrustScore 94/100
6 years experience
Demo KYC verified

Anita Verma
Home Cook
Powai, Mumbai
₹9,500/month
4.8/5
TrustScore 91/100
5 years experience
Demo KYC verified

Raj Kumar
Driver
Bandra, Mumbai
₹18,000/month
4.9/5
TrustScore 96/100
8 years experience
Demo KYC verified

Mohit Singh
Electrician
Andheri, Mumbai
From ₹399
4.7/5
TrustScore 89/100
4 years experience
Demo KYC verified

Sonal Gupta
Home Cleaner
Noida Sector 62
₹6,500/month
4.8/5
TrustScore 92/100
5 years experience
Demo KYC verified

Amit Verma
Plumber
Vijay Nagar, Indore
From ₹299
4.7/5
TrustScore 88/100
7 years experience
Demo KYC verified

TRUST / KYC:

"Demo KYC" means simulated verification for this prototype.
Never describe it as real UIDAI or Aadhaar verification.

SAFETY:

First Visit Mode is a prototype workflow for property visits.
It can contain meeting location, emergency contact, check-in and safe confirmation.

RECOMMENDATION STYLE:

When possible:

1. Understand the user's requirement.
2. Select the best inventory match.
3. Give 2-3 alternatives.
4. Explain why the top match is best.
5. Ask one useful follow-up question.

Example:

User:
"PG under 10k in Pune"

Assistant:
"Your strongest match is WorkNest PG in Hinjawadi at ₹9,500/month because it stays within budget and includes Wi-Fi, food and parking.

Alternatives: ...

Do you prefer single or double occupancy?"

Do not fabricate availability beyond the demo inventory.
`;
}

/* =========================================================
   POST /api/chat
   ========================================================= */

router.post("/", async (req, res) => {

    try {

        const {
            message,
            history = []
        } = req.body;

        /* -----------------------------------------
           VALIDATION
           ----------------------------------------- */

        if (
            typeof message !== "string" ||
            !message.trim()
        ) {

            return res.status(400).json({
                success: false,
                message:
                    "Message is required"
            });
        }

        const cleanMessage =
            message.trim();

        /* -----------------------------------------
           DEMO MODE
           ----------------------------------------- */

        if (
            !process.env.ANTHROPIC_API_KEY ||
            process.env.ANTHROPIC_API_KEY.trim()
                .length < 10
        ) {

            return res.json({
                success: true,
                mode: "demo",
                reply: demoAI(
                    cleanMessage
                )
            });
        }

        /* -----------------------------------------
           SAFE HISTORY
           ----------------------------------------- */

        const safeHistory =
            Array.isArray(history)
                ? history
                    .filter(item =>
                        item &&
                        (
                            item.role ===
                                "user" ||
                            item.role ===
                                "assistant"
                        ) &&
                        typeof item.content ===
                            "string"
                    )
                    .slice(-10)
                    .map(item => ({
                        role: item.role,
                        content:
                            item.content.trim()
                    }))
                : [];

        /* -----------------------------------------
           CURRENT USER MESSAGE
           ----------------------------------------- */

        safeHistory.push({
            role: "user",
            content: cleanMessage
        });

        /* -----------------------------------------
           CLAUDE API REQUEST
           ----------------------------------------- */

        const response =
            await axios.post(
                "https://api.anthropic.com/v1/messages",
                {
                    model: "claude-sonnet-5",

                    max_tokens: 1200,

                    system:
                        buildSystemPrompt(),

                    messages:
                        safeHistory
                },
                {
                    timeout: 60000,

                    headers: {
                        "x-api-key":
                            process.env
                                .ANTHROPIC_API_KEY,

                        "anthropic-version":
                            "2023-06-01",

                        "content-type":
                            "application/json"
                    }
                }
            );

        /* -----------------------------------------
           CLAUDE RESPONSE PARSING
           ----------------------------------------- */

        const reply =
            Array.isArray(
                response.data?.content
            )
                ? response.data.content
                    .filter(
                        block =>
                            block.type ===
                            "text"
                    )
                    .map(
                        block =>
                            block.text
                    )
                    .join("\n")
                    .trim()
                : "";

        if (!reply) {

            throw new Error(
                "Claude returned an empty response"
            );
        }

        /* -----------------------------------------
           SUCCESS
           ----------------------------------------- */

        return res.json({
            success: true,
            mode: "claude",
            model: "claude-sonnet-5",
            reply
        });

    } catch (error) {

        const status =
            error.response?.status;

        const apiError =
            error.response?.data;

        console.error(
            "\n========== GRIHACARE AI ERROR =========="
        );

        console.error(
            "Status:",
            status || "Unknown"
        );

        console.error(
            "Message:",
            error.message
        );

        if (apiError) {

            console.error(
                "API Response:",
                JSON.stringify(
                    apiError,
                    null,
                    2
                )
            );
        }

        console.error(
            "=========================================\n"
        );

        /* -----------------------------------------
           NEVER BREAK DEMO
           ----------------------------------------- */

        try {

            const fallback =
                demoAI(
                    req.body?.message || ""
                );

            return res.json({
                success: true,
                mode: "fallback",
                reply: fallback
            });

        } catch (fallbackError) {

            console.error(
                "Fallback error:",
                fallbackError
            );

            return res.status(500).json({
                success: false,
                message:
                    "GrihaCare AI is temporarily unavailable."
            });
        }
    }
});

/* =========================================================
   EXPORT
   ========================================================= */

module.exports = router;