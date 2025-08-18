package interfaces;

/**
 * Interface for items that can be played in the music streaming application
 */
public interface Playable {
    /**
     * Play the item
     * @return true if playback started successfully, false otherwise
     */
    boolean play();
    
    /**
     * Pause the item
     * @return true if paused successfully, false otherwise
     */
    boolean pause();
    
    /**
     * Stop the item
     * @return true if stopped successfully, false otherwise
     */
    boolean stop();
    
    /**
     * Get the duration of the item in seconds
     * @return duration in seconds
     */
    int getDurationSeconds();
    
    /**
     * Get the current playback position in seconds
     * @return current position in seconds
     */
    int getCurrentPositionSeconds();
    
    /**
     * Seek to a specific position
     * @param positionSeconds the position to seek to in seconds
     * @return true if seek was successful, false otherwise
     */
    boolean seekTo(int positionSeconds);
    
    /**
     * Check if the item is currently playing
     * @return true if playing, false otherwise
     */
    boolean isPlaying();
    
    /**
     * Check if the item is currently paused
     * @return true if paused, false otherwise
     */
    boolean isPaused();
}