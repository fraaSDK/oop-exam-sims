package a02c.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import a02c.e1.UniversityProgram.Group;

import static a02c.e1.UniversityProgram.Group.*;

/*
 * Total time: 45 minutes.
 * In 30 minutes: first three methods.
 * Remaining time: 15 minutes
 */
public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    private int getCredits(Map<String, Pair<Set<Group>, Integer>> courses, Set<String> courseNames, Predicate<Set<Group>> group) {
        return courses.entrySet().stream()
                .filter(e -> courseNames.contains(e.getKey()) && group.test(e.getValue().get1()))
                .mapToInt(c -> c.getValue().get2())
                .sum();
    }

    @Override
    public UniversityProgram flexible() {
        return new UniversityProgram() {

            private Map<String, Pair<Set<Group>, Integer>> courses = new HashMap<>();

            @Override
            public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
                this.courses = courses;
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                var credits = getCredits(courses, courseNames, g -> true);
                return credits == 48;
            }
            
        };
    }

    @Override
    public UniversityProgram fixed() {
        return new UniversityProgram() {

            private Map<String, Pair<Set<Group>, Integer>> courses = new HashMap<>();

            @Override
            public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
                this.courses = courses;
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                var credits = getCredits(courses, courseNames, g -> true);
                var creditsMandatory = getCredits(courses, courseNames, g -> g.equals(Set.of(MANDATORY)));
                var creditsOptionalA = getCredits(courses, courseNames, g -> g.equals(Set.of(OPTIONAL, OPTIONAL_A)));
                var creditsOptionalB = getCredits(courses, courseNames, g -> g.equals(Set.of(OPTIONAL, OPTIONAL_B)));
                var creditsOptionals = getCredits(courses, courseNames, g -> g.equals(Set.of(OPTIONAL, OPTIONAL_A, OPTIONAL_B)));
                var creditsThesis = getCredits(courses, courseNames, g -> g.equals(Set.of(THESIS)));
                var creditsFree = getCredits(courses, courseNames, g -> g.equals(Set.of(FREE)));
                return credits + creditsFree == 60 && creditsMandatory == 12 &&
                        creditsOptionals + creditsOptionalA + creditsOptionalB == 36 &&
                        creditsThesis == 12 && creditsFree == 0;
            }

        };
    }

    @Override
    public UniversityProgram balanced() {
        return new UniversityProgram() {

            private Map<String, Pair<Set<Group>, Integer>> courses = new HashMap<>();

            @Override
            public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
                this.courses = courses;
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                var credits = getCredits(courses, courseNames, g -> true);
                var creditsMandatory = getCredits(courses, courseNames, g -> g.equals(Set.of(MANDATORY)));
                var creditsOptionalA = getCredits(courses, courseNames, g -> g.equals(Set.of(OPTIONAL, OPTIONAL_A)));
                var creditsOptionalB = getCredits(courses, courseNames, g -> g.equals(Set.of(OPTIONAL, OPTIONAL_B)));
                var creditsOptionals = getCredits(courses, courseNames, g -> g.equals(Set.of(OPTIONAL, OPTIONAL_A, OPTIONAL_B)));
                var creditsThesis = getCredits(courses, courseNames, g -> g.equals(Set.of(THESIS)));
                var creditsFree = getCredits(courses, courseNames, g -> g.equals(Set.of(FREE)));
                return credits == 60 && creditsMandatory == 24 &&
                        creditsOptionals + creditsOptionalA + creditsOptionalB >= 24 &&
                        creditsThesis + creditsFree <= 12;
            }

        };
    }

    @Override
    public UniversityProgram structured() {
        return new UniversityProgram() {

            private Map<String, Pair<Set<Group>, Integer>> courses = new HashMap<>();

            @Override
            public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
                this.courses = courses;
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                var credits = getCredits(courses, courseNames, g -> true);
                var creditsMandatory = getCredits(courses, courseNames, g -> g.equals(Set.of(MANDATORY)));
                var creditsOptionalA = getCredits(courses, courseNames, g -> g.equals(Set.of(OPTIONAL, OPTIONAL_A)));
                var creditsOptionalB = getCredits(courses, courseNames, g -> g.equals(Set.of(OPTIONAL, OPTIONAL_B)));
                var creditsOptionals = getCredits(courses, courseNames, g -> g.equals(Set.of(OPTIONAL, OPTIONAL_A, OPTIONAL_B)));
                var creditsThesis = getCredits(courses, courseNames, g -> g.equals(Set.of(THESIS)));
                var creditsFree = getCredits(courses, courseNames, g -> g.equals(Set.of(FREE)));
                return credits == 60 && creditsMandatory == 12 && creditsOptionalA >= 6 && creditsOptionalB >= 6 &&
                        creditsOptionalA + creditsOptionalB + creditsOptionals == 30 && creditsThesis + creditsFree == 18;
            }

        };
    }

}
