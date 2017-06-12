package org.example.eventgenerator.producer.event;

import com.google.common.collect.ImmutableList;
import org.example.eventgenerator.common.event.Event;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ProducedEventBuilder {

    private final static ImmutableList<String> serviceTypes = ImmutableList.of("new account", "payment", "delivery");
    private final static ImmutableList<String> originationPages = ImmutableList.of("login", "balance", "transfer");
    private final static ImmutableList<String> originationChannels = ImmutableList.of("webchat", "sms", "wechat");
    private final static ImmutableList<String> endReasons = ImmutableList.of("Normal", "Abnormal");

    private final Random rnd = new Random();

    private UUID interactionId;
    private String eventType;
    private DateTime eventTimeStamp;
    private DateTime createTime;
    private DateTime deliveryTime;
    private DateTime endTime;
    private String serviceType;
    private String originationPage;
    private String agentId;
    private String endReason;
    private String originationChannel;

    public ProducedEventBuilder() {
    }

    public ProducedEventBuilder(Event event) {
        this.interactionId = event.getInteractionId();
        this.eventType = event.getEventType();
        this.eventTimeStamp = event.getEventTimeStamp();
        this.createTime = event.getCreateTime();
        this.deliveryTime = event.getDeliveryTime();
        this.endTime = event.getEndTime();
        this.serviceType = event.getServiceType();
        this.originationPage = event.getOriginationPage();
        this.agentId = event.getAgentId();
        this.endReason = event.getEndReason();
        this.originationChannel = event.getOriginationChannel();
    }

    public ProducedEventBuilder setRandomInteractionId() {
        this.interactionId = UUID.randomUUID();
        return this;
    }

    public ProducedEventBuilder setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public ProducedEventBuilder setEventTimeStamp(DateTime eventTimeStamp) {
        this.eventTimeStamp = eventTimeStamp;
        return this;
    }

    public ProducedEventBuilder setCreateTime(DateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public ProducedEventBuilder setDeliveryTime(DateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
        return this;
    }

    public ProducedEventBuilder setEndTime(DateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public ProducedEventBuilder setRandomServiceType() {
        this.serviceType = getRandomItem(serviceTypes);
        return this;
    }

    public ProducedEventBuilder setRandomOriginationPage() {
        this.originationPage = getRandomItem(originationPages);
        return this;
    }

    public ProducedEventBuilder setRandomAgentId() {
        this.agentId = String.format("Agent_%s%s%s", getRandomDigit(), getRandomDigit(), getRandomDigit());
        return this;
    }

    public ProducedEventBuilder setRandomEndReason() {
        this.endReason = getRandomItem(endReasons);
        return this;
    }

    public ProducedEventBuilder setRandomOriginationChannel() {
        this.originationChannel = getRandomItem(originationChannels);
        return this;
    }

    public ProducedEvent build() {
        return new ProducedEvent(
                interactionId,
                eventType,
                eventTimeStamp,
                createTime,
                deliveryTime,
                endTime,
                serviceType,
                originationPage,
                agentId,
                endReason,
                originationChannel
        );
    }

    private <T> T getRandomItem(final List<T> list) {
        int i = rnd.nextInt(list.size());
        return list.get(i);
    }

    private int getRandomDigit() {
        return rnd.nextInt(10);
    }


}
