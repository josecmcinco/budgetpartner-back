package com.budgetpartner.APP.util;

public class MessageAi {
    private String role;
    private String content;
    private String tool_call_id;

    public MessageAi(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public MessageAi(String role, String content, String tool_call_id) {
        this.role = role;
        this.content = content;
        this.tool_call_id = tool_call_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTool_call_id() {
        return tool_call_id;
    }

    public void setTool_call_id(String tool_call_id) {
        this.tool_call_id = tool_call_id;
    }
}