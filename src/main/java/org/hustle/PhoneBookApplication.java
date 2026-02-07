package org.hustle;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

public class PhoneBookApplication {
    public static void main(String[] args) {

        StdioServerTransportProvider transportProvider = new StdioServerTransportProvider(new ObjectMapper());

        McpSyncServer syncServer = McpServer.sync(transportProvider)
                .serverInfo("my-server", "1.0.0")
                .capabilities(McpSchema.ServerCapabilities.builder()
                        .tools(true)
                        .build())
                .build();

        MCPTools tools = new MCPTools();
        syncServer.addTool(tools.addPhoneNumber());
        syncServer.addTool(tools.removePhoneNumber());
        syncServer.addTool(tools.viewAllPhoneNumber());
    }
}