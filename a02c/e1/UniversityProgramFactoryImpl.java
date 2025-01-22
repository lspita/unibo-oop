package a02c.e1;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a02c.e1.UniversityProgram.Group;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    private UniversityProgram general(
        final Predicate<Integer> overallPredicate,
        final Set<Pair<Set<Group>, Predicate<Integer>>> groupsPredicates
    ) {
        return new UniversityProgram() {
            Map<String, Pair<Set<Group>, Integer>> courses;
            @Override
            public void setCourses(final Map<String, Pair<Set<Group>, Integer>> courses) {
                this.courses = Collections.unmodifiableMap(courses);
            }

            private Stream<Pair<Set<Group>, Integer>> matchingCourses(final Set<String> courseNames) {
                return courses.keySet().stream()
                    .filter(courseNames::contains)
                    .map(courses::get);
            }

            @Override
            public boolean isValid(final Set<String> courseNames) {
                return overallPredicate.test(
                    matchingCourses(courseNames)
                        .collect(Collectors.summingInt(Pair::get2))
                ) && groupsPredicates.stream()
                    .allMatch(pair -> pair.get2().test(
                        matchingCourses(courseNames)
                            .filter(p -> p.get1().stream().anyMatch(pair.get1()::contains))
                            .collect(Collectors.summingInt(Pair::get2))
                    ));
            }
            
        };
    }

    @Override
    public UniversityProgram flexible() {
        return general(c -> c == 48, Collections.emptySet());
    }

    @Override
    public UniversityProgram fixed() {
        return general(c -> c == 60, Set.of(
            new Pair<>(Set.of(Group.MANDATORY), c -> c == 12),
            new Pair<>(Set.of(Group.OPTIONAL), c -> c == 36),
            new Pair<>(Set.of(Group.THESIS), c -> c == 12)
        ));
    }

    @Override
    public UniversityProgram balanced() {
        return general(c -> c == 60, Set.of(
            new Pair<>(Set.of(Group.MANDATORY), c -> c == 24),
            new Pair<>(Set.of(Group.OPTIONAL), c -> c >= 24),
            new Pair<>(Set.of(Group.FREE), c -> c <= 12),
            new Pair<>(Set.of(Group.THESIS), c -> c <= 12)
        ));
    }

    @Override
    public UniversityProgram structured() {
        return general(c -> c == 60, Set.of(
            new Pair<>(Set.of(Group.MANDATORY), c -> c == 12),
            new Pair<>(Set.of(Group.OPTIONAL_A), c -> c >= 6),
            new Pair<>(Set.of(Group.OPTIONAL_B), c -> c >= 6),
            new Pair<>(Set.of(Group.OPTIONAL_A, Group.OPTIONAL_B), c -> c == 30),
            new Pair<>(Set.of(Group.FREE, Group.THESIS), c -> c == 18)
        ));
    }

}
