require("dotenv").config();

const express = require("express");
const cors = require("cors");

const chatRoutes = require("./routes/chat");

const app = express();

app.use(cors());

app.use(express.json());

app.get("/", (req, res) => {
    res.json({
        success: true,
        app: "GrihaCare",
        message: "GrihaCare Backend is Running 🚀"
    });
});

app.get("/api/health", (req, res) => {
    res.json({
        success: true,
        status: "healthy",
        ai: process.env.ANTHROPIC_API_KEY
            ? "Claude enabled"
            : "Demo AI mode"
    });
});

app.use("/api/chat", chatRoutes);

const PORT = process.env.PORT || 5000;

app.listen(PORT, "0.0.0.0", () => {
    console.log(
        ` GrihaCare Backend running on port ${PORT}`
    );
});