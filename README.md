# MCP PhoneBook Application

A Model Context Protocol (MCP) server implementation in Java that manages a simple in-memory phonebook.

## Overview

This server provides tools to add, view, and remove phone numbers, operating over STDIO transport. It uses the official [Java MCP SDK](https://github.com/modelcontextprotocol/java-sdk).

## Tools

The server exposes the following tools:

- **`add_phone_number`**: Add or update a person's phone number.
  - Arguments: `name` (string), `phoneNumber` (string)
- **`remove_phone_number`**: Remove a person's phone number.
  - Arguments: `name` (string)
- **`view_all_phone_number`**: List all stored phone numbers.
  - Arguments: None

## Prerequisites

- Java 17 or higher
- Maven

## Building only the Server

To build the executable JAR:

```bash
mvn clean package
```

This will create `target/MCPPhoneBookApplication-1.0-SNAPSHOT.jar`.

## Configuration

To use this with Claude Desktop, add the following to your configuration file:

**macOS:** `~/Library/Application Support/Claude/claude_desktop_config.json`  
**Windows:** `%APPDATA%\Claude\claude_desktop_config.json`

```json
{
  "mcpServers": {
    "phonebook": {
      "command": "java",
      "args": [
        "-jar",
        "/ABSOLUTE/PATH/TO/MCPPhoneBookApplication/target/MCPPhoneBookApplication-1.0-SNAPSHOT.jar"
      ]
    }
  }
}
```

> **Note:** Replace `/ABSOLUTE/PATH/TO/` with the actual full path to your project directory.

## Usage

Once configured, you can ask Claude to:
- "Add John Doe's number as 555-0123"
- "What is John Doe's phone number?" (Claude will look it up via `view_all_phone_number` or you can implement a specific lookup if you extend it)
- "Delete John Doe from the phonebook"
