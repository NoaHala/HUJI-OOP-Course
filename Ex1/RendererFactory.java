/**
 * a factory class for creating different renderers that can render the game
 */
public class RendererFactory {

    /**
     * Constructor. similar to default but public.
     */
    public RendererFactory() {
    }

    /**
     * the main factory function.
     * creates the relevant type of renderer according to the given input
     * @param type - string with a name of a renderer type
     * @return a renderer object as requested or null if there is a problem
     */
    public Renderer buildRenderer(String type, int size) {
        return switch (type) {
            case ("console") -> new ConsoleRenderer(size);
            case ("none") -> new VoidRenderer();
            default -> null;
        };
    }
}
