package com.budgetpartner.APP.service.AiService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeepseekAgentServiceTest {

    private static final String SAMPLE_RESPONSE = """
    {
      "id": "2382348e-311c-4071-b7b0-8d0486f8617f",
      "object": "chat.completion",
      "created": 1753564544,
      "model": "deepseek-chat",
      "choices": [
        {
          "index": 0,
          "message": {
            "role": "assistant",
            "content": "```json\\n{\\n  \\\"toolName\\\": \\\"none\\\",\\n  \\\"arguments\\\": [],\\n  \\\"finished\\\": true,\\n  \\\"finalResponse\\\": \\\"Hola mundo\\\"\\n}\\n```"
          },
          "logprobs": null,
          "finish_reason": "stop"
        }
      ],
      "usage": {
        "prompt_tokens": 122,
        "completion_tokens": 35,
        "total_tokens": 157
      }
    }
    """;

    @Autowired
    private DeepseekAgentService deepseekAgentService;

    @BeforeAll
    static void init() {

    }

        @Test
        void testExtractContentFromResponse() throws Exception {
            String content = DeepseekAgentService.extractContentFromResponse(SAMPLE_RESPONSE);
            assertTrue(content.startsWith("```json"));
            assertTrue(content.contains("\"toolName\": \"none\""));
        }

        @Test
        void testStripMarkdownCodeBlock() {
            String markdownContent = "```json\n{\n  \"toolName\": \"none\",\n  \"arguments\": [],\n  \"finished\": true,\n  \"finalResponse\": \"Hola mundo\"\n}\n```";
            String expectedJson = "{\n  \"toolName\": \"none\",\n  \"arguments\": [],\n  \"finished\": true,\n  \"finalResponse\": \"Hola mundo\"\n}";

            String cleaned = DeepseekAgentService.stripMarkdownCodeBlock(markdownContent);
            assertEquals(expectedJson, cleaned);
        }
    }

