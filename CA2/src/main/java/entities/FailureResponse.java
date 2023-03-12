package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FailureResponse {
    private final boolean success = false;
    private final String error_message;
    ObjectMapper objectMapper = new ObjectMapper();
    public FailureResponse(String error_message) {
//        super();
        this.error_message = error_message;
    }

//    public ObjectNode getError() {
//        ObjectNode exception_node = objectMapper.createObjectNode();
//        exception_node.put("success", success);
//        exception_node.put("data", error_message);
//        return exception_node;
//    }
    public ObjectNode raise_exception() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode exception_node = mapper.createObjectNode();
        exception_node.put("success", success);
        exception_node.put("data", error_message);
        return exception_node;
    }

}

