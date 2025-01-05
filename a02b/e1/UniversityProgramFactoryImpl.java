package a02b.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import a02b.e1.UniversityProgram.Sector;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    record CourseSectorCredits(Sector sector, Integer credits) {}
    record SectorsRequirement(Set<Sector> sectors, Predicate<Integer> requirement) {}
    
    private UniversityProgram ofRequirements(final Set<SectorsRequirement> requirements) {
        return new UniversityProgram() {
            private final Map<String, CourseSectorCredits> courses = new HashMap<>();

            @Override
            public void addCourse(final String name, final Sector sector, final int credits) {
                courses.put(name, new CourseSectorCredits(sector, credits));
            }
            
            @Override
            public boolean isValid(final Set<String> courseNames) {                
                final var sectorsCredits = courseNames.stream()
                    .map(courses::get)
                    .collect(Collectors.groupingBy(CourseSectorCredits::sector, Collectors.summingInt(CourseSectorCredits::credits)));
                return requirements.stream()
                    .allMatch(sr -> sr.requirement().test(
                        sr.sectors.stream()
                            .filter(sectorsCredits::containsKey)
                            .collect(Collectors.summingInt(sectorsCredits::get))
                    ));
            }
            
        };
    }

    private SectorsRequirement overallRequirement() {
        return overallRequirement(c -> c == 60);
    }

    private SectorsRequirement overallRequirement(final Predicate<Integer> requirement) {
        return new SectorsRequirement(Set.of(Sector.values()), requirement);
    }

    @Override
    public UniversityProgram flexible() {
        return ofRequirements(Set.of(overallRequirement()));
    }

    @Override
    public UniversityProgram scientific() {
        return ofRequirements(Set.of(
            overallRequirement(),
            new SectorsRequirement(Set.of(Sector.MATHEMATICS), c -> c >= 12),
            new SectorsRequirement(Set.of(Sector.COMPUTER_SCIENCE), c -> c >= 12),
            new SectorsRequirement(Set.of(Sector.PHYSICS), c -> c >= 12)
        ));
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return ofRequirements(Set.of(
            overallRequirement(c -> c >= 48),
            new SectorsRequirement(Set.of(Sector.COMPUTER_SCIENCE, Sector.COMPUTER_ENGINEERING), c -> c >= 30)
        ));
    }

    @Override
    public UniversityProgram realistic() {
        return ofRequirements(Set.of(
            overallRequirement(c -> c == 120),
            new SectorsRequirement(Set.of(Sector.COMPUTER_SCIENCE, Sector.COMPUTER_ENGINEERING), c -> c >= 60),
            new SectorsRequirement(Set.of(Sector.MATHEMATICS, Sector.PHYSICS), c -> c <= 18),
            new SectorsRequirement(Set.of(Sector.THESIS), c -> c == 24)
        ));
    }

}
