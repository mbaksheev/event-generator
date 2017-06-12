package org.example.eventgenerator.consumer;

import org.example.eventgenerator.common.event.Event;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Serializes Events to JSON file.
 */
public class JsonFileEventSerializer {
    private final PrintWriter out;
    private int eventCount = 0;

    /**
     * Constructor with file writer.
     * @param fileWriter file writer to write events.
     */
    public JsonFileEventSerializer(final FileWriter fileWriter) {
        this.out = new PrintWriter(new BufferedWriter(fileWriter));
    }

    public void serialize(final Event event) {
        out.println((eventCount >= 1 ? "," : "[") + eventToJsonObject(event).toString());
        eventCount++;
    }

    private JSONObject eventToJsonObject(final Event event) {
        JSONObject jsonObject = new JSONObject();
        jsonObject
                .put("Id", event.getInteractionId().toString())
                .put("EventType", event.getEventType())
                .put("EventTimeStamp", dateToISOFormat(event.getEventTimeStamp()))
                .put("CreateTime", dateToISOFormat(event.getCreateTime()))
                .put("DeliveryTime", dateToISOFormat(event.getDeliveryTime()))
                .put("EndTime", dateToISOFormat(event.getEndTime()))
                .put("ServiceType", valueOrUndefined(event.getServiceType()))
                .put("OriginationPage", valueOrUndefined(event.getOriginationPage()))
                .put("AgentId", valueOrUndefined(event.getAgentId()))
                .put("EndReason", valueOrUndefined(event.getEndReason()))
                .put("OriginationChannel", valueOrUndefined(event.getOriginationChannel()));
        return jsonObject;


    }

    private String dateToISOFormat(final DateTime date) {
        DateTime dateTime = date == null ? new DateTime(0) : date;
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        return fmt.print(dateTime);
    }

    private String valueOrUndefined(final String value) {
        return value != null ? value : "undefined";
    }

    public void close() {
        out.print("]");
        out.close();
    }
}
