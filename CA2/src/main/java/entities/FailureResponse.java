package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FailureResponse {
    private final boolean success = false;
    private final String error_message;
    public FailureResponse(String error_message) {
        this.error_message = error_message;
    }

    public ObjectNode raise_exception() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode exception_node = mapper.createObjectNode();
        exception_node.put("success", success);
        exception_node.put("data", error_message);
        return exception_node;
    }

}

