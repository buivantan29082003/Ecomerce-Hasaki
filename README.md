🎶 **BeautyHub Marketplace Website**

BeautyHub is a full-featured eCommerce backend built with Spring Framework and MySQL.  
It allows users to place orders, manage orders, carts, search, manage accounts, and pay online.

---

## 📌 **Features**

### 🔐 Secure user authentication and authorization with Spring Security.

---

## 👤 **User Features**
- 🔍 Search for products using variant attributes aligned with categories, including keyword filtering and product sorting.  
- 🛒 Manage the shopping cart, place orders, and process payments (COD or online payment).  
- 📦 Manage purchase orders (search, cancel, pay), view transaction history, and view cancellation reasons.  
- 👤 Manage account information.  
- 📝 Sign up and sign in.  
- 🎟️ Store and apply voucher codes to orders to reduce payment amounts.  

---

## 🛍️ **Saler Features**
- 🧾 Manage products: add new products, update status and information, and delete products.  
- 🔄 Manage orders: cancel orders and update orders through different statuses.  
- 🎫 Manage vouchers and promotions with multiple types and configurations.  
- ⭐ Manage product reviews submitted by users.  
- 📊 View statistical reports (sales, revenue, orders, products, etc.).  

---

## 🗃️ **Project Structure**

### **Backend – BeautyHub-BE**
```
├── Bean/                # Contains bean configurations for business logic and stores request-related data.
├── Config/              # Security settings, bean configurations, and payment configurations.
├── Controllers/         # Defines APIs and interacts with the services defined in the Services folder.
├── DTOs/                # Request/response data structures.
├── Exceptions/          # Handles exceptions thrown from controllers, services, etc.
├── Models/              # Entity models for EF Core.
├── Repositories/        # Base repository models used to query the database.
├── Services/            # Business logic and recommendation logic implementations.
├── ResponseData/        # Sample or standardized response data structures.
├── Util/                # Common utility functions.
```

### **Frontend – BeautyHub-FE**
```
├── Assets/              # Contains images, icons, and other static assets.
├── Components/          # Reusable UI components used across the application.
├── Config/              # Project configurations (Redux store, API settings, etc.).
├── Layout/              # Layout definitions for different roles within the project.
├── Pages/               # User-facing interface pages.
├── Routes/              # Route configuration for the application.
├── Services/            # Business logic, including API calls and reusable helper functions.
├── Others folders and files...
```

---

## 🛠️ **Tech Stack**
**Backend API:** Java Spring Framework  
**ORM:** Spring Data JPA  
**Database:** MySQL  
**Frontend:** ReactJS, TailwindCSS, Redux  

---

## 🎥 **DEMO**

### 👤 USER – 
### Home Page
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/aba9ae7ea86ae4e5b44c28560dbc2ffa8ed4c55b/home.png)

### Searching Page
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/main/searchPage.png)

### Cart Page
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/05924bc99b859ffa508828f4f2e4b28d5a6673bd/cart.png)

### Place and Order Page
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/05924bc99b859ffa508828f4f2e4b28d5a6673bd/order.png)

### Manage and payment page
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/main/ManageOrderUser.png)
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/main/ChoosingPaymentMethos.png)
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/main/PaymentPage.png)

### And others pages.


### 👤 SHOPER – 
### Product Management
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/05924bc99b859ffa508828f4f2e4b28d5a6673bd/manage_product.png)

###  Promotion
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/05924bc99b859ffa508828f4f2e4b28d5a6673bd/manage_promotion.png)

### Manage Voucher
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/05924bc99b859ffa508828f4f2e4b28d5a6673bd/mana_voucher.png)

### Manage Order
![alt](https://github.com/buivantan29082003/Ecomerce-Hasaki/blob/main/ManageOrderSaler.png)

### And others pages.

📫 Contact
Author: Van Tan Email: buivantan29082003@gmail.com.
GitHub: github.com/buivantan29082003

