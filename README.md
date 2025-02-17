<p align="center">
  <img src="https://staging.testsquad.co/wp-content/uploads/2025/02/testsquad-logo-500-469x100.png" width="400"/>
</p>

# TestSquad - Web Automation Test using Selenium & Cucumber (Boiler-plate)

## About TestSquad

TestSquad is a software testing company specializing in manual and automated testing solutions, ensuring high-quality software for global clients. We provide expert QA services, including mobile automation, to enhance product reliability and performance.

📩 Contact us: info@testsquad.co | 🌐 Website: https://testsquad.co

---

## Overview

A robust Selenium-based test automation framework built with Java, TestNG, and Cucumber.

## Features

- Page Object Model design pattern
- Multi-browser support (Chrome, Firefox, Edge, Safari)
- Parallel test execution
- Headless mode support
- Grid execution capability
- Cloud platform integration (BrowserStack, LambdaTest)
- Extent Reports integration
- Environment-based configuration
- Screenshot capture on test failure

## Prerequisites

- Java JDK 17 or higher
- Maven 3.8.x or higher
- Chrome/Firefox/Edge browser installed
- Selenium Grid (optional, for grid execution)
- BrowserStack/LambdaTest account (optional, for cloud execution)

## Running Tests

### Basic Execution

```bash
mvn clean test -Denv=qa -Dbrowser=chrome -Dbrowser.mode=normal
```

### Headless Mode
```bash
mvn clean test -Denv=qa -Dbrowser=chrome -Dbrowser.mode=headless
```

### Different Environments
```bash
mvn clean test -Denv=staging
mvn clean test -Denv=production
```

### Grid Execution
```bash
mvn clean test -Denv=qa -Dgrid.enabled=true
```

## Reports

Test execution reports can be found in:
- Extent Reports: `test-output/ExtentReport_<timestamp>.html`
- Cucumber Reports: `target/cucumber-reports/`

## Configuration

Environment-specific configurations are stored in `src/test/resources/config/` directory.

## Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details. Mention TestSquad on LinkedIn :)

## Support

Need help implementing this framework or looking for custom automation solutions? Contact TestSquad:

- 📧 Email: info@testsquad.co
- 🌐 Website: https://testsquad.co
- 💼 Services: Mobile Testing, Automation Solutions, QA Consulting


## License

TestSquad is released under the [MIT License](LICENSE).

---

<p align="center">Powered by <a href="https://testsquad.co">TestSquad</a> - Your Quality Assurance Partner</p>

