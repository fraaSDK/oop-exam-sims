package a02b.e1;

import java.util.HashSet;
import java.util.Set;

import a02b.e1.UniversityProgram.Sector;

/*
 * Total time: 45 minutes.
 * In 29 minutes: first three methods.
 * Time left: 16 minutes and 41 seconds.
 */
public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    private class Course {

        private String name;
        private Sector sector;
        private int credits;

        public Course(String name, Sector sector, int credits) {
            this.name = name;
            this.sector = sector;
            this.credits = credits;
        }

        public String getName() {
            return name;
        }

        public Sector getSector() {
            return sector;
        }

        public int getCredits() {
            return credits;
        }

    }

    @Override
    public UniversityProgram flexible() {
        return new UniversityProgram() {

            private Set<Course> courses = new HashSet<>();

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                courses.add(new Course(name, sector, credits));
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                var credits = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                System.out.println(credits);
                return credits == 60;
            }
            
        };
    }

    @Override
    public UniversityProgram scientific() {
        return new UniversityProgram() {

            private Set<Course> courses = new HashSet<>();

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                courses.add(new Course(name, sector, credits));
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                var creditsMath = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.MATHEMATICS))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                var creditsCS = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.COMPUTER_SCIENCE))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                var creditsPhysics = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.PHYSICS))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                return creditsMath >= 12 && creditsCS >= 12 && creditsPhysics >= 12;
            }

        };
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return new UniversityProgram() {

            private Set<Course> courses = new HashSet<>();

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                courses.add(new Course(name, sector, credits));
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                var credits = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                var creditsCS = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.COMPUTER_SCIENCE))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                var creditsCE = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.COMPUTER_ENGINEERING))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                return credits >= 48 && creditsCS + creditsCE >= 30;
            }

        };
    }

    @Override
    public UniversityProgram realistic() {
        return new UniversityProgram() {

            private Set<Course> courses = new HashSet<>();

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                courses.add(new Course(name, sector, credits));
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                var credits = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                var creditsCS = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.COMPUTER_SCIENCE))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                var creditsCE = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.COMPUTER_ENGINEERING))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                var creditsMath = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.MATHEMATICS))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                var creditsPhysics = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.PHYSICS))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                var creditsThesis = courses.stream()
                        .filter(c -> courseNames.contains(c.getName()) && c.getSector().equals(Sector.THESIS))
                        .mapToInt(c -> c.getCredits())
                        .sum();
                return credits == 120 && creditsCS + creditsCE >= 60 && creditsMath + creditsPhysics <= 18 && creditsThesis == 24;
            }

        };
    }

}
