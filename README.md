
# Mall & Brand Management System

A JavaFX desktop application for managing shopping mall operations, including stores, brands, managers, customers, products, and purchases with an intuitive TableView interface.

## Installation

### Prerequisites
- **Java JDK 25** - [Download](https://www.oracle.com/java/technologies/downloads/#java25)
- **JavaFX SDK 25** - [Download](https://gluonhq.com/products/javafx/)
- **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/mysql/)
- **MySQL Connector/J** - [Download](https://dev.mysql.com/downloads/connector/j/)

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Abdullatlf/Brand-Mall-Management-System.git
   cd Brand-Mall-Management-System
   ```

2. **Configure JavaFX** (IntelliJ IDEA)
   - File → Project Structure → Libraries → Add JavaFX SDK lib folder
   - Run → Edit Configurations → Add VM options:
     ```
     --module-path "path/to/javafx-sdk-25/lib" --add-modules javafx.controls,javafx.fxml
     ```

3. **Add MySQL Connector**
   - File → Project Structure → Libraries → Add `mysql-connector-j-9.1.0.jar`

4. **Set up database**
   ```sql
   mysql -u root -p
   CREATE DATABASE mydb;
   USE mydb;
   source database/schema.sql;
   ```

5. **Configure database connection**
   
   Edit `src/database/DatabaseConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/mydb";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password";
   ```

6. **Run the project**
   - Open `src/Main.java`
   - Click Run (or right-click → Run 'Main.main()')

## Usage

### Basic Operations
1. Launch the application
2. Select a module (Manager, Customer, Mall, Brand Stores, Products, or Purchases)
3. Choose an operation:
   - **Add** - Create new records
   - **Remove** - Delete records (checks dependencies)
   - **Update** - Edit existing records
   - **Show** - View all records
   - **Search** - Find records by ID

### Working with Tables
- **Click any table row** to auto-fill the edit form
- **Click "Refresh"** to reload data
- Use **ComboBoxes** for selecting related entities (managers, malls, etc.)

### Example: Adding a Store
1. Click "BRAND STORES OPERATIONS"
2. Click "Add Brand Store"
3. Enter Store Number and Address
4. Select Brand, Manager, and Mall from dropdown menus
5. Click "Add Brand Store"

## Features

- ✅ Complete CRUD operations for all entities
- ✅ Interactive TableView with click-to-edit
- ✅ Real-time input validation
- ✅ Foreign key relationship management with ComboBoxes
- ✅ Data integrity checks (prevents invalid deletions)
- ✅ Search by primary key
- ✅ One-click table refresh

### Modules
- **Managers** - Store manager information
- **Customers** - Customer profiles with phone validation
- **Malls** - Shopping mall locations
- **Brand Stores** - Store management with relationships
- **Products** - Inventory across stores
- **Purchases** - Transaction tracking

## Project Structure

```
mall-management-system/
├── src/
│   ├── controllers/        # UI logic
│   ├── models/            # Entity classes
│   ├── database/          # DAO pattern + connection
│   ├── Exceptions/        # Custom exceptions
│   └── Main.java
├── resources/             # FXML files
│   ├── MANAGER/
│   ├── CUSTOMER/
│   ├── MALL/
│   ├── BRANDSTORES/
│   ├── PRODUCTS/
│   └── BUYS/
├── database/
│   └── schema.sql         # Database setup
└── lib/
    └── mysql-connector-j-9.1.0.jar
```

---

**Tech Stack:** JavaFX 25 • JDK 25 • MySQL 8.0 • JDBC
