-----------
### Portfolio https://mohamedmzd0.github.io/portfolio/

# 📦 Android Order Tracker App

An Android app built with **Jetpack Compose** using **MVI architecture**, **Clean Architecture**, **SOLID principles**, and **modular design**.  
It fetches and caches data via **Retrofit** (with a MockInterceptor), stores it in **Room DB**, and listens for **real-time updates using Socket.IO**.

---

## 🚀 Features

- 📃 **Order List Screen**  
  Fetches a list of mocked orders from a REST API and caches them in Room DB.

- 🔁 **Order Details Screen**  
  Displays order details and listens for real-time order status updates via Socket.IO.

- 💡 **Reactive MVI Pattern**  
  UI built with unidirectional data flow and clear state/event/effect separation.

---

## 🧱 Tech Stack

| Layer        | Tech Used                           |
|--------------|-------------------------------------|
| UI           | Jetpack Compose, Material3          |
| Architecture | MVI, Clean Architecture             |
| DI           | Hilt                                |
| Network      | Retrofit + MockInterceptor          |
| Database     | Room                                |
| Realtime     | Socket.IO                           |
| Principles   | SOLID, Clean Code                   |
| Modularity   | Feature-based multi-module project  |

---

## 🗂️ Project Structure (Clean Architecture)

---

## 🛠️ How It Works

1. **Home Screen**
   - On launch, orders are fetched from a **mocked API** using Retrofit + MockInterceptor.
   - Data is cached into **Room DB**.
   - Displays list from DB once available.

2. **Details Screen**
   - On item click, navigates to a **details screen**.
   - Displays full order info.
   - Subscribes to **real-time updates** for the selected order via **Socket.IO**.


------

https://github.com/user-attachments/assets/7e57146f-645a-4365-a8b7-ffdd3ecdefdd


