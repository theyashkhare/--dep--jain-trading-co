# JainTradingCo Android App (Deprecated)

## Overview

**JainTradingCo** is a legacy Android application developed for **Jain Trading Company** to streamline business operations and enhance inventory tracking efficiency. The app was designed to replace manual processes with a digital solution, providing real-time data access and facilitating better decision-making within the company.

Originally implemented to manage complex trade transactions and inventory management, the application offers features tailored to the specific needs of a trading company, including order processing, stock management, and reporting tools.

## Project Status

⚠️ **DEPRECATED** - This project is no longer maintained and may not be compatible with current Android versions. The codebase is archived and provided for historical reference. Users are advised to exercise caution if attempting to run or modify the application, as it may contain outdated dependencies and security vulnerabilities.

## Technical Details

- **Platform:** Android
- **Development Environment:** Android Studio 3.0
- **Target SDK Version:** API Level 23 (Android 6.0 Marshmallow)
- **Minimum SDK Version:** API Level 16 (Android 4.1 Jelly Bean)
- **Language:** Java
- **Database:** SQLite
- **Build System:** Gradle 3.0

## Features

### Business Operations Management

- **Order Processing:**
    - Create, edit, and manage sales and purchase orders.
    - Track order status from initiation to fulfillment.
- **Client Management:**
    - Maintain a database of clients with contact information.
    - View client order history and outstanding invoices.
- **Supplier Integration:**
    - Manage supplier information and purchase orders.
    - Monitor supplier performance and delivery schedules.

### Inventory Tracking System

- **Real-Time Stock Management:**
    - View current stock levels with automatic updates.
    - Set minimum stock thresholds to trigger restock alerts.
- **Product Categorization:**
    - Organize inventory by categories, SKUs, and barcodes.
    - Support for batch numbers and expiration dates.
- **Stock Movement History:**
    - Record all inventory inflow and outflow transactions.
    - Generate reports on stock adjustments and transfers.

### Reporting and Analytics

- **Financial Reports:**
    - Generate profit and loss statements.
    - Analyze sales trends over customizable periods.
- **Inventory Reports:**
    - View slow-moving and fast-selling products.
    - Monitor inventory turnover rates.
- **Custom Reports:**
    - Create custom reports based on specific data criteria.
    - Export reports to PDF or Excel formats.

### Trading Company Specific Functionalities

- **Pricing Management:**
    - Support for multiple pricing tiers and discounts.
    - Automate price adjustments based on market factors.
- **Regulatory Compliance:**
    - Include necessary documentation for trade compliance.
    - Automated tax calculations for transactions.
- **Multi-Currency Support:**
    - Handle transactions in different currencies.
    - Real-time currency conversion rates integration.

## Architecture

The application employs a modular architecture for scalability and maintainability:

- **Presentation Layer:**
    - XML-based UI layouts optimized for various screen sizes.
    - Interactive components with Material Design principles.
- **Business Logic Layer:**
    - Java classes handling core functionalities and processes.
    - Implemented design patterns such as Singleton and Observer.
- **Data Access Layer:**
    - SQLite database with Content Providers.
    - Data persistence and retrieval mechanisms.

## Dependencies

- **Third-Party Libraries:**
    - **Butter Knife:** For view binding.
    - **Retrofit:** Although not fully utilized, some API handling is included.
    - **Gson:** For JSON parsing.

## Requirements

- **Android Studio:** Version 3.0 or compatible.
- **Java Development Kit (JDK):** Version 8.
- **Android SDK:** Platforms and tools for API Level 16 to 23.
- **Emulator or Device:** Android device running Android 4.1 Jelly Bean to Android 6.0 Marshmallow.

## Installation

1. **Clone the Repository:**
     ```bash
     git clone https://github.com/YourUsername/JainTradingCo.git
     ```
2. **Open in Android Studio:**
     - Launch Android Studio.
     - Navigate to **File > Open...** and select the cloned repository folder.
3. **Install Dependencies:**
     - Allow Android Studio to install any missing SDK components or plugins.
4. **Sync Gradle Files:**
     - Click on **Sync Now** when prompted or navigate to **File > Sync Project with Gradle Files**.
5. **Build Project:**
     - Go to **Build > Make Project** or press **Ctrl+F9**.
6. **Run the App:**
     - Connect an Android device via USB with USB debugging enabled or start an emulator.
     - Click on **Run > Run 'app'** or press **Shift+F10**.

## Usage

- **Login Screen:**
    - Authenticate using the default credentials (if any are set) or consult the admin to create a user account.
- **Dashboard:**
    - Access summaries of inventory levels, recent orders, and alerts.
- **Inventory Module:**
    - Add new products, update stock levels, and view inventory reports.
- **Orders Module:**
    - Create new sales or purchase orders and track their statuses.
- **Clients and Suppliers:**
    - Manage contact information and view transaction histories.
- **Reports:**
    - Generate and export various financial and inventory reports.

## Known Issues

- **Compatibility:**
    - May not run on devices with Android versions higher than 6.0.
- **Security:**
    - Lacks modern encryption and security practices.
- **Dependencies:**
    - Uses outdated libraries that may no longer be supported.

## Planned Improvements (Not Implemented)

- **Migration to newer Android versions and SDKs.**
- **Refactoring codebase to Kotlin for improved performance.**
- **Implementation of modern security standards, including encryption and secure authentication methods.**
- **Addition of cloud synchronization features to support multi-device data access.**

## License

This project is released under the **MIT License**. See the [LICENSE](LICENSE) file for details.

## Contact

For historical inquiries about this project, please open an issue in the repository or contact the former development team:

- **Email:** legacy-support@jaintradingco.com
- **LinkedIn:** [Jain Trading Company](https://www.linkedin.com/company/jaintradingco)

---

*Disclaimer: This application was tailored for the specific needs of Jain Trading Company and may not be suitable for other use cases without significant modifications. The code is provided "as is" without warranty of any kind.*
