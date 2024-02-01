package validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogExtractor {
    public static void main(String[] args) {
        String filePath = "G:\\Research\\Software\\Prototype\\src\\main\\java\\validator\\files\\logTemplate.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                extractInformation(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extractInformation(String logLine) {
        if (logLine.contains("Sent to agent")) {
            extractSentMessage(logLine);
        } else if (logLine.contains("agent") && logLine.contains("event")) {
            extractAgentEvent(logLine);
        }
    }

    private static void extractSentMessage(String logLine) {
        // Regex to match the message sent pattern
        Pattern pattern = Pattern.compile("\\[ websocket-server\\s+] Sent to agent: \\[(\\{.*?\\})]");
        Matcher matcher = pattern.matcher(logLine);

        if (matcher.find()) {
            String message = matcher.group(1);
            System.out.println("Sent Message: " + message);
        }
    }

    private static void extractAgentEvent(String logLine) {
        // Regex to match the agent event pattern
        Pattern pattern = Pattern.compile("> \\[ testing-(.*?) >>>> ] agent \\[(.*?)] event: \\[(\\{.*?\\})]");
        Matcher matcher = pattern.matcher(logLine);

        if (matcher.find()) {
            String agentType = matcher.group(1);
            String agentName = matcher.group(2);
            String event = matcher.group(3);

            System.out.println("Agent Type: " + agentType + ", Agent Name: " + agentName + ", Event: " + event);
        }
    }
}
