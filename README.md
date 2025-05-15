<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
</head>
<body>

<h1>My_Bank</h1>
<p>A Java-based banking system that demonstrates core banking operations with Oracle database integration. This project is designed to showcase object-oriented programming, JDBC connectivity, and basic transaction management in a simple, console-based application.</p>

<hr>

<h2>Table of Contents</h2>
<ul>
<li><a href="#overview">Overview</a></li>
<li><a href="#features">Features</a></li>
<li><a href="#project-structure">Project Structure</a></li>
<li><a href="#database-schema">Database Schema</a></li>
<li><a href="#key-classes--functionality">Key Classes &amp; Functionality</a></li>
<li><a href="#getting-started">Getting Started</a></li>
<li><a href="#usage">Usage</a></li>
<li><a href="#dependencies">Dependencies</a></li>
<li><a href="#contributing">Contributing</a></li>
<li><a href="#license">License</a></li>
</ul>

<hr>

<h2 id="overview">Overview</h2>
<p><strong>My_Bank</strong> simulates a basic banking environment where users can register using their Aadhaar number, create accounts, perform deposits and withdrawals, and track transactions. The system uses an Oracle database for persistent storage and demonstrates robust exception handling for banking operations.</p>

<hr>

<h2 id="features">Features</h2>
<ul>
<li>User registration with Aadhaar verification</li>
<li>Account creation and management</li>
<li>Deposit and withdrawal operations with minimum balance checks</li>
<li>Transaction recording and tracking</li>
<li>Exception handling for common banking errors (e.g., insufficient funds, account not found)</li>
<li>Console-based user interaction</li>
</ul>

<hr>

<h2 id="project-structure">Project Structure</h2>
<pre><code>My_Bank/
├── database/
│   └── Table.sql           # SQL schema for required database tables
├── lib/
│   └── ojdbc8.jar          # Oracle JDBC driver
├── src/
│   ├── Aadhar.java         # Handles Aadhaar-based user registration
│   ├── Account.java        # Core account operations and logic
│   ├── MyBank.java         # Main entry point and application logic
│   └── Transaction.java    # Transaction management and custom exceptions
└── README.md               # Project documentation
</code></pre>

<hr>

<h2 id="database-schema">Database Schema</h2>
<p>The project uses an Oracle database. The <code>Table.sql</code> script in the <code>database/</code> directory defines the necessary tables and constraints. It includes:</p>
<ul>
<li>Use of <code>CHECK</code> constraints and <code>REGEXP_LIKE</code> for data validation</li>
<li>Structure for storing user, account, and transaction details</li>
</ul>

<hr>

<h2 id="key-classes--functionality">Key Classes &amp; Functionality</h2>
<h3>Aadhar.java</h3>
<ul>
<li>Handles user registration via Aadhaar number</li>
<li>Connects to the database and inserts new user records</li>
</ul>
<h3>Account.java</h3>
<ul>
<li>Manages core account functionalities: creation, deposit, withdrawal</li>
<li>Utilizes JDBC for database interaction</li>
<li>Implements logic to ensure unique account numbers and maintain account integrity</li>
</ul>
<h3>Transaction.java</h3>
<ul>
<li>Manages transaction records for deposits and withdrawals</li>
<li>Includes custom exceptions:</li>
<ul>
<li><code>MinimumBalanceException</code></li>
<li><code>AccountNotFoundException</code></li>
</ul>
<li>Ensures robust error handling for transaction failures</li>
</ul>
<h3>MyBank.java</h3>
<ul>
<li>Main entry point for the application</li>
<li>Handles user input via console</li>
<li>Manages the application flow: login, registration, and banking operations</li>
<li>Establishes database connection using JDBC (Oracle)</li>
</ul>

<hr>

<h2 id="getting-started">Getting Started</h2>
<p><strong>Prerequisites:</strong></p>
<ul>
<li>Java 8 or above</li>
<li>Oracle Database (XE or similar)</li>
<li>Oracle JDBC driver (<code>ojdbc8.jar</code> included in <code>lib/</code>)</li>
</ul>
<p><strong>Setup Steps:</strong></p>
<ol>
<li><strong>Clone the repository:</strong>
<pre><code>git clone https://github.com/user23052036/My_Bank.git
cd My_Bank
</code></pre>
</li>
<li><strong>Set up the database:</strong>
<ul>
<li>Run the SQL script:
<pre><code>sqlplus HR/hr@localhost/XE @database/Table.sql
</code></pre>
</li>
<li>Adjust connection details in <code>MyBank.java</code> if needed.</li>
</ul>
</li>
<li><strong>Compile the Java source files:</strong>
<pre><code>javac -cp lib/ojdbc8.jar -d bin src/*.java
</code></pre>
</li>
<li><strong>Run the application:</strong>
<pre><code>java -cp "bin:lib/ojdbc8.jar" MyBank
</code></pre>
</li>
</ol>

<hr>

<h2 id="usage">Usage</h2>
<ul>
<li>Launch the application from the command line.</li>
<li>Follow the prompts to register, create an account, deposit or withdraw funds, and view account details.</li>
<li>All data is stored in the connected Oracle database.</li>
</ul>

<hr>

<h2 id="dependencies">Dependencies</h2>
<ul>
<li><a href="https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html">Oracle JDBC Driver (ojdbc8.jar)</a></li>
<li>Oracle Database</li>
</ul>

<hr>

<h2 id="contributing">Contributing</h2>
<p>Contributions are welcome! Please fork the repository and submit a pull request for review.</p>

<hr>

<h2 id="license">License</h2>
<p>This project is open source and available under the MIT License.</p>

<hr>

<p><em>For more details, review the code in the <code>src/</code> directory and the database schema in <code>database/Table.sql</code>.</em></p>

</body>
</html>
