package interfaces;

import java.util.List;

/**
 * Interface for searchable items in the application
 */
public interface Searchable<T> {
    /**
     * Search by name/title
     * @param name the name to search for
     * @return list of matching items
     */
    List<T> searchByName(String name);
    
    /**
     * Search by rating
     * @param minRating minimum rating (inclusive)
     * @param maxRating maximum rating (inclusive)
     * @return list of matching items
     */
    List<T> searchByRating(int minRating, int maxRating);
}