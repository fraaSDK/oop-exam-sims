package a01c.e1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EventHistoryFactoryImpl implements EventHistoryFactory {

    @Override
    public <E> EventHistory<E> fromMap(Map<Double, E> map) {
        return new EventHistory<>() {

            private final List<Pair<Double, E>> events = map.entrySet().stream()
                    .map(e -> new Pair<>(e.getKey(), e.getValue()))
                    .sorted((p1, p2) -> p1.get1().compareTo(p2.get1()))
                    .toList();
            private int counter;

            @Override
            public double getTimeOfEvent() {
                return events.get(counter).get1();
            }

            @Override
            public E getEventContent() {
                return events.get(counter).get2();
            }

            @Override
            public boolean moveToNextEvent() {
                return ++counter < events.size();
            }

        };
    }

    @Override
    public <E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content) {
        return new EventHistory<>() {

            private final List<Pair<Double, E>> events = toList();
            private int counter;

            @Override
            public double getTimeOfEvent() {
                return events.get(counter).get1();
            }

            @Override
            public E getEventContent() {
                return events.get(counter).get2();
            }

            @Override
            public boolean moveToNextEvent() {
                return ++counter < events.size();
            }

            private List<Pair<Double, E>> toList() {
                List<Pair<Double, E>> list = new ArrayList<>();
                while (times.hasNext() && content.hasNext()) {
                    list.add(new Pair<>(times.next(), content.next()));
                }
                return list;
            }

        };
    }

    @Override
    public <E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta) {
        return new EventHistory<>() {

            private int counter;
            private int multiplier;

            @Override
            public double getTimeOfEvent() {
                return initial + (delta * multiplier++);
            }

            @Override
            public E getEventContent() {
                return content.get(counter);
            }

            @Override
            public boolean moveToNextEvent() {
                return ++counter < content.size();
            }

        };
    }

    @Override
    public <E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size) {
        return new EventHistory<>() {

            private int eventNumber;

            @Override
            public double getTimeOfEvent() {
                return Math.random();
            }

            @Override
            public E getEventContent() {
                eventNumber++;
                return content.get();
            }

            @Override
            public boolean moveToNextEvent() {
                return eventNumber < size;
            }

        };
    }

    @Override
    public EventHistory<String> fromFile(String file) throws IOException {
        return new EventHistory<>() {

            final private List<Pair<Double, String>> events = readFile();
            private int counter;

            @Override
            public double getTimeOfEvent() {
                return events.get(counter).get1();
            }

            @Override
            public String getEventContent() {
                return events.get(counter).get2();
            }

            @Override
            public boolean moveToNextEvent() {
                return ++counter < events.size();
            }

            private List<Pair<Double, String>> readFile() throws IOException {
                var f = new File(file).getAbsoluteFile();
                List<Pair<Double, String>> result = new ArrayList<>();
                try (var reader = new BufferedReader(new FileReader(f))) {
                    while (reader.ready()) {
                        var line = reader.readLine().split(":", 2);
                        result.add(new Pair<>(Double.parseDouble(line[0]), line[1]));
                    }
                }
                return result;
            }

        };
    }
    
}
