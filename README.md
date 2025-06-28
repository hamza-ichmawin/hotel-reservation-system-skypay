# Hotel Reservation System

## Overview

This project implements a simplified hotel reservation system in Java. The system allows users to create and manage hotel rooms, register users with balances, and handle room bookings for specific date ranges while ensuring availability and sufficient funds. The application simulates core hotel booking workflows with proper data validation and history tracking.

## Features

- **Room Management:** Create and update hotel rooms with unique IDs, types, and prices per night.
- **User Management:** Register users with unique IDs and account balances.
- **Booking:** Reserve a room for a specific period if the user has enough balance and the room is available.
- **Booking Validation:** Checks for invalid date ranges (e.g., check-out before check-in) and overlapping bookings.
- **Historical Data:** Tracks all bookings and displays the room and user details as they were at the time of booking, even if rooms are updated later.
- **Print Functions:**
    - `printAllUsers()` shows all registered users in reverse chronological order.
    - `printAll()` shows all rooms and bookings in reverse chronological order.
- **Input Validation:** Ensures data integrity, prevents invalid operations, and handles exceptions gracefully.

## Technical Details

- Developed using **Java JDK 24**.
