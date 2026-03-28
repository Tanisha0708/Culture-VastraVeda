package vastraveda.core.utils;

/**
 * 🔒 CORE INTERFACE — DO NOT MODIFY
 * 
 * All feature UI classes must implement this interface.
 * This enforces a consistent contract across all features.
 * 
 * CONTRIBUTORS: Your FeatureXUI must implement Feature and override render().
 * 
 * Example:
 *   public class Feature1UI extends BaseUI implements Feature {
 *       public void render() { ... }
 *   }
 */
public interface Feature {

    /**
     * Called to display / open this feature's UI window.
     * Implement this to show your JFrame or JPanel.
     */
    void render();
}
