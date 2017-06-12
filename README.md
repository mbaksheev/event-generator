# event-generator
Produces and consumes events.

Build jar with dependencies:
`mvn clean compile assembly:single`

Run jar:
`java -jar target/event-generator-app-1.0-SNAPSHOT-jar-with-dependencies.jar N R`


`N` - Number of interactions to generate events for.

`R` - Rate of generation “start” events per second (how many new interactions must be generated per every second).

Produced events wil be stored in `results.json` file placed in working directory