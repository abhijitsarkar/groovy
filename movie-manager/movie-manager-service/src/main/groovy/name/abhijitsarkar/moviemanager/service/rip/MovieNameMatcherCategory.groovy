package name.abhijitsarkar.moviemanager.service.rip
import java.util.regex.Matcher
import java.util.regex.Pattern

import static name.abhijitsarkar.moviemanager.service.rip.MovieRipParser.fileExtension
/**
 * @author Abhijit Sarkar
 */
@Category(Matcher)
class MovieNameMatcherCategory {
    /*
     * The following regex matches file names with release year in parentheses,
     * something like Titanic (1997).mkv Each part of the regex is explained
     * further:
     *
     * ([-',!\\[\\]\\.\\w\\s]++) -> Matches one or more occurrences of any
     * alphabet, number or the following special characters in the movie name:
     * dash (-), apostrophe ('), comma (,), exclamation sign (!), square braces
     * ([]), full stop (.)
     *
     * (?:\\((\\d{4})\\)) -> Matches 4 digit release year within parentheses.
     *
     * (.++) -> Matches one or more occurrences of any character.
     */
    static final Pattern MOVIE_NAME_WITH_RELEASE_YEAR_REGEX =
            ~/(?<title>[-',!\[\]\.\w\s]++)(?:\((?<year>\d{4})\))?+(?<lastPart>.++)/

    String getTitle() {
        final String fileExtension = fileExtension(this.group())
        final String qualifier = getQualifier(fileExtension)
        String title = this.group('title')

        title += (qualifier == fileExtension ? '' : qualifier)

        title.trim()
    }

    int getYear() {
        final String group2 = this.group('year')

        group2 ? Integer.parseInt(group2) : 0
    }

    String getQualifier(final String fileExtension) {
        this.group('lastPart').minus(fileExtension)
    }
}
