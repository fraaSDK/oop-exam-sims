package a02b.e1;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

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

        @Override
        public int hashCode() {
            return Objects.hash(name, sector, credits);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Course other = (Course) obj;
            return Objects.equals(name, other.name) &&
                    Objects.equals(sector, other.sector) &&
                    Objects.equals(credits, other.credits);
        }

    }

    private int calculateCredits(Set<Course> courses, Set<String> courseNames, Predicate<Sector> sector) {
        return courses.stream()
                .filter(c -> courseNames.contains(c.getName()) && sector.test(c.getSector()))
                .mapToInt(c -> c.getCredits())
                .sum();
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
                var credits = calculateCredits(courses, courseNames, s -> true);
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
                var creditsMath = calculateCredits(courses, courseNames, s -> s.equals(Sector.MATHEMATICS));
                var creditsCS = calculateCredits(courses, courseNames, s -> s.equals(Sector.COMPUTER_SCIENCE));
                var creditsPhysics = calculateCredits(courses, courseNames, s -> s.equals(Sector.PHYSICS));
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
                var credits = calculateCredits(courses, courseNames, s -> true);
                var creditsCS = calculateCredits(courses, courseNames, s -> s.equals(Sector.COMPUTER_SCIENCE));
                var creditsCE = calculateCredits(courses, courseNames, s -> s.equals(Sector.COMPUTER_ENGINEERING));
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
                var credits = calculateCredits(courses, courseNames, s -> true);
                var creditsCS = calculateCredits(courses, courseNames, s -> s.equals(Sector.COMPUTER_SCIENCE));
                var creditsCE = calculateCredits(courses, courseNames, s -> s.equals(Sector.COMPUTER_ENGINEERING));
                var creditsMath = calculateCredits(courses, courseNames, s -> s.equals(Sector.MATHEMATICS));
                var creditsPhysics = calculateCredits(courses, courseNames, s -> s.equals(Sector.PHYSICS));
                var creditsThesis = calculateCredits(courses, courseNames, s -> s.equals(Sector.THESIS));
                return credits == 120 && creditsCS + creditsCE >= 60 && creditsMath + creditsPhysics <= 18 && creditsThesis == 24;
            }

        };
    }

}
