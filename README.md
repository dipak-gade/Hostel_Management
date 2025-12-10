📘 Hostel Management System
A role-based Hostel Management System built with Spring Boot, PostgreSQL, and React, supporting online hostel bed booking through Razorpay.
The project includes Admin, Owner, and Student modules with structured hostel setup and real-time bed availability.
________________________________________
🚀 Current Features (Completed)
👨‍💼 Admin Panel
•	View all organizations
•	Add new organizations
•	Automatic Owner creation
•	Edit/Delete organizations
________________________________________
🏢 Owner Panel
•	View all hostels under their organization
•	Add hostels
•	Add buildings
•	Add floors
•	Add rooms
•	Add beds
•	(Upcoming: View student bookings & manage bed availability)
________________________________________
🧑‍🎓 Student Panel
•	View available hostels
•	View room types
•	View available beds
•	Select booking duration
•	View booking summary (bed details, room, floor, location, cost)
•	Pay using Razorpay (UPI, cards, net banking, wallet, etc.)
•	Booking confirmed after payment
•	Booking stored in PostgreSQL
________________________________________
🛠️ Features in Progress
•	🔄 Authentication flow improvements (JWT refresh, role guards)
•	📌 Owner dashboard → Booking list view
•	📌 Student booking history
•	📌 Email / SMS notifications
•	📌 Room/Bed images upload
•	📌 Admin analytics
________________________________________
🏗 Tech Stack
Backend
•	Spring Boot
•	Spring MVC
•	PostgreSQL
•	Hibernate/JPA
•	Swagger (API docs)
•	JWT Authentication (in progress)
Frontend
•	React (Vite) → http://localhost:5173/
•	Axios
•	React Router DOM
Tools
•	GitHub
•	Postman
•	VS Code / STS
•	Razorpay API
________________________________________
🖼️ Screenshots (Current UI)
1️⃣ Student Dashboard — List of Hostels
2️⃣ Hostel Details — Room Types
3️⃣ Available Beds View
4️⃣ Booking Duration Popup
5️⃣ Booking Summary & Payment Screen
6️⃣ Razorpay Payment UI
7️⃣ Payment Successful
8️⃣ PostgreSQL — Booking Table (After Payment)
9️⃣ Admin Dashboard — Organization List
🔟 Owner Dashboard — Hostels List
________________________________________
🗂 Database Tables (Current)
•	users
•	organizations
•	hostels
•	buildings
•	floors
•	rooms
•	beds
•	booking
•	payment
Already linked using relational mapping.
________________________________________
💳 Payment Flow (Completed)
1.	User selects a bed
2.	Backend creates booking → IN_PROGRESS
3.	Razorpay order is generated
4.	User pays using Razorpay
5.	Razorpay redirects with success
6.	Backend verifies signature
7.	Booking updated → SUCCESS
8.	Bed updated → BOOKED
9.	Stored in PostgreSQL
________________________________________
🔧 How to Run the Project
Backend
git clone https://github.com/dipak-gade/Hostel_Management.git
cd Hostel_Management
mvn spring-boot:run
Frontend
npm install
npm run dev
________________________________________
🌱 Upcoming Enhancements
•	Owner Booking Dashboard
•	User Booking History
•	JWT refresh token
•	Role-based route protection in React
•	Enhanced filtering (price, type, location)
•	Hostel images & gallery
________________________________________
📬 Contact
Dipak Gade
📧 gadedipak@gmail.com
🔗 LinkedIn: https://www.linkedin.com/in/dipak-gade-951495246
🐙 GitHub: https://github.com/dipak-gade

