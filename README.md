# Toy Store Application

A Java Swing desktop e-commerce application for a toy store, developed as part of an HCI (Human-Computer Interaction) principles course project.

## Features

### 🛍️ Product Display
- Grid-based product layout with visual cards
- Category filtering (Building Blocks, Dolls, Vehicles, etc.)
- Search functionality
- Product details including price, age range, and stock information
- Visual product placeholders with emoji icons

### 🛒 Shopping Cart
- Add/remove items with quantity controls
- Real-time cart total calculation
- Stock validation
- Clear cart functionality
- Visual cart summary in footer

### 💳 Checkout Process
- Customer information form with validation
- Order summary with tax calculation
- Input validation for all fields
- Order confirmation dialog

### 🎨 User Interface
- Modern, colorful design with toy store theme
- Intuitive navigation between sections
- Responsive layout with proper spacing
- Error handling with user-friendly messages
- Consistent color scheme and typography

## Technical Implementation

### Architecture
- **Model Layer**: `Toy`, `ShoppingCart`, `CartItem`, `Customer` classes
- **Data Layer**: `ToyInventory` for product management
- **UI Layer**: Separate panels for products, cart, and checkout
- **Main Window**: Central controller with navigation

### HCI Principles Applied
1. **Usability**: Clear navigation, intuitive controls, consistent layout
2. **Accessibility**: Proper labeling, keyboard navigation support
3. **Visual Design**: Color-coded elements, appropriate spacing, readable fonts
4. **Error Prevention**: Input validation, confirmation dialogs
5. **Feedback**: Visual updates, success/error messages

## Project Structure

```
src/main/java/com/toystore/
├── ToyStoreApplication.java          # Main application entry point
├── model/
│   ├── Toy.java                     # Product model
│   ├── ShoppingCart.java            # Cart management
│   ├── CartItem.java                # Individual cart items
│   └── Customer.java                # Customer information
├── data/
│   └── ToyInventory.java            # Product data management
└── ui/
    ├── ToyStoreMainWindow.java      # Main application window
    ├── ProductDisplayPanel.java     # Product browsing interface
    ├── ShoppingCartPanel.java       # Cart management interface
    └── CheckoutPanel.java           # Checkout process interface
```

## Sample Products

The application includes sample toy data:
- LEGO Classic Creative Box
- Barbie Dreamhouse
- Hot Wheels Track Set
- Nerf Blaster
- Educational Puzzle Set
- Remote Control Car
- Art Supplies Kit
- Board Game Collection

## Running the Application

### Prerequisites
- Java 11 or higher
- Maven (for building)

### Build and Run
```bash
# Compile the project
mvn compile

# Run the application
mvn exec:java -Dexec.mainClass="com.toystore.ToyStoreApplication"

# Or build executable JAR
mvn package
java -jar target/toy-store-1.0.0.jar
```

## Development Sessions

This project follows an 8-session development plan:

1. **Session 1**: Project idea and mock-up design ✅
2. **Session 2**: Project setup, packages and classes ✅
3. **Session 3**: Main window and navigation ✅
4. **Session 4**: Product display interface ✅
5. **Session 5**: Shopping cart and total calculation ✅
6. **Session 6**: Checkout and user form validation ✅
7. **Session 7**: Data storage implementation
8. **Session 8**: Interface refinement and ergonomics

## Future Enhancements

- Image support for products
- Database integration for persistent storage
- Order history and customer accounts
- Payment processing integration
- Advanced search and filtering options
- Product reviews and ratings
- Inventory management for administrators

## License

This project is developed for educational purposes as part of an HCI course.
