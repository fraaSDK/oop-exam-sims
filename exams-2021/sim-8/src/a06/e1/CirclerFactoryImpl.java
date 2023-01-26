package a06.e1;

import java.util.ArrayList;
import java.util.List;

/*
 * Total time: 45 minutes.
 * 35 minutes: all mandatory methods were implemented.
 * After: small changes to previous methods for the
 *        implementation of the remaining two.
 */
public class CirclerFactoryImpl implements CirclerFactory {

    @Override
    public <T> Circler<T> leftToRight() {
        return leftToRightWithSkip(1);
    }

    @Override
    public <T> Circler<T> alternate() {
        return alternateWithSkip(1);
    }

    @Override
    public <T> Circler<T> stayToLast() {
        return stayToLastWithSkip(1);
    }

    @Override
    public <T> Circler<T> leftToRightSkipOne() {
        return leftToRightWithSkip(2);
    }

    @Override
    public <T> Circler<T> alternateSkipOne() {
        return alternateWithSkip(2);
    }

    @Override
    public <T> Circler<T> stayToLastSkipOne() {
        return stayToLastWithSkip(2);
    }

    private <T> Circler<T> leftToRightWithSkip(int skip) {
        return new Circler<T>() {

            private List<T> list;
            private int index;

            @Override
            public void setSource(List<T> elements) {
                list = elements;
                index = 0;
            }

            @Override
            public T produceOne() {
                var result = list.get(index);
                index = (index + skip) % list.size();
                return result;
            }

            @Override
            public List<T> produceMany(int n) {
                List<T> result = new ArrayList<>();
                int i = 0;
                while (i < n) {
                    result.add(produceOne());
                    i++;
                }
                index = 0;
                return result;
            }

        };
    }

    private <T> Circler<T> alternateWithSkip(int skip) {
        return new Circler<T>() {

            private List<T> list;
            private int index;
            private int swapTimes;

            @Override
            public void setSource(List<T> elements) {
                list = elements;
                index = 0;
                swapTimes = 0;
            }

            @Override
            public T produceOne() {
                if (index >= list.size()) {
                    System.out.println("SWAP");
                    list = swapList(list);
                    System.out.println(list);
                    System.out.println("SWP " + swapTimes);
                    if (skip == 1) {
                        index = 0;
                    } else {
                        // FIXME
                        if (swapTimes % 2 == 1) {
                            index = 0;
                            swapTimes++;
                        } else {
                            index = 1;
                            swapTimes++;
                        }
                    }
                }
                var result = list.get(index);
                System.out.print(index + "\t");
                index += skip;
                System.out.println(result);
                return result;
            }

            @Override
            public List<T> produceMany(int n) {
                List<T> result = new ArrayList<>();
                int i = 0;
                while (i < n) {
                    result.add(produceOne());
                    i++;
                }
                index = 0;
                return result;
            }

            private List<T> swapList(List<T> l) {
                List<T> swap = new ArrayList<>();
                for (int i = l.size() - 1; i >= 0; i--) {
                    swap.add(l.get(i));
                }
                return swap;
            }

        };
    }

    private <T> Circler<T> stayToLastWithSkip(int skip) {
        return new Circler<T>() {

            private List<T> list;
            private int index;

            @Override
            public void setSource(List<T> elements) {
                list = elements;
                index = 0;
                System.out.println("index " + index);
                System.out.println("list " + list);
            }

            @Override
            public T produceOne() {
                var result = list.get(index);
                if (index != list.size() - 1) {
                    index += skip;
                    /*
                     * Last element has to be returned,
                     * avoiding out-of-bound access in case
                     * of skip > 1.
                     */
                    if (index == list.size()) {
                        index--;
                    }
                }
                return result;
            }

            @Override
            public List<T> produceMany(int n) {
                List<T> result = new ArrayList<>();
                int i = 0;
                while (i < n) {
                    result.add(produceOne());
                    i++;
                }
                index = 0;
                return result;
            }

        };
    }

}
