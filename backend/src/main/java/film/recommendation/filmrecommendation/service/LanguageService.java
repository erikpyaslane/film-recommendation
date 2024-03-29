package film.recommendation.filmrecommendation.service;

import film.recommendation.filmrecommendation.enums.Language;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    public Set<String> getAllLanguages(){
        return Arrays.stream(Language.values())
                .map(Language::getName)
                .collect(Collectors.toSet());
    }

}
