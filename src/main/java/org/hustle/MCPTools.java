package org.hustle;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MCPTools {

    private final Map<String, String> phoneBook = new ConcurrentHashMap<>();

    public McpServerFeatures.SyncToolSpecification addPhoneNumber() {

        String schema = """
                {
                   "type": "object",
                   "properties": {
                     "name": {
                       "type": "string",
                       "description": "Name of the Person"
                     },
                     "phoneNumber": {
                       "type": "string",
                       "description": "Phone Number of the Person"
                     }
                   },
                   "required": [
                     "name",
                     "phoneNumber"
                   ]
                 }
                """;

        return new McpServerFeatures.SyncToolSpecification(
                new McpSchema.Tool("add_phone_number", "Add or update name and phoneNumber", schema),
                (exchange, arguments) -> {
                    String name = (String) arguments.get("name");
                    String phoneNumber = (String) arguments.get("phoneNumber");
                    phoneBook.put(name, phoneNumber);
                    McpSchema.TextContent result = new McpSchema.TextContent("Added: A new Phone Number has been added with the name: =" + name);
                    return new McpSchema.CallToolResult(List.of(result), false);
                }
        );
    }

    public McpServerFeatures.SyncToolSpecification removePhoneNumber() {
        var schema = """
                {
                   "type" : "object",
                   "properties" : {
                     "name" : {
                         "type": "string",
                         "description": "Name of the Person"
                     }
                   },
                   "required": ["name"]
                 }
                """;
        return new McpServerFeatures.SyncToolSpecification(
                new McpSchema.Tool("remove_phone_number", "Remove Phone Number", schema),
                (exchange, arguments) -> {
                    String name = (String) arguments.get("name");

                    if (phoneBook.containsKey(name)) {
                        phoneBook.remove(name);
                        McpSchema.TextContent result = new McpSchema.TextContent("Removed: A Phone Number with the name: =" + name);
                        return new McpSchema.CallToolResult(List.of(result), false);
                    } else {
                        McpSchema.TextContent result = new McpSchema.TextContent("No Phone Number Found with the Name: =" + name);
                        return new McpSchema.CallToolResult(List.of(result), true);
                    }
                }
        );
    }

    public McpServerFeatures.SyncToolSpecification viewAllPhoneNumber() {
        var schema = """
                {
                  "type" : "object",
                  "properties" : {}
                }
                """;
        return new McpServerFeatures.SyncToolSpecification(
                new McpSchema.Tool("view_all_phone_number", "View all Phone Number", schema),
                (exchange, arguments) -> {

                    if (phoneBook.isEmpty()) {
                        McpSchema.TextContent result = new McpSchema.TextContent("No Phone Number Found");
                        return new McpSchema.CallToolResult(List.of(result), false);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("===============================");
                        phoneBook.forEach((n, d) -> {
                            sb.append(n + "  ::  " + d);
                            sb.append("\n");
                        });
                        sb.append("===============================");

                        McpSchema.TextContent result = new McpSchema.TextContent("All Phone Number\n" + sb);
                        return new McpSchema.CallToolResult(List.of(result), true);
                    }
                }
        );
    }

}
