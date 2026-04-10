package vastraveda.features.feature8_compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Debate threads linked to garment names (in-memory).
 */
public class Feature8Service {

    public static final class Post {
        public final String author;
        public final String text;
        public final boolean moderator;

        public Post(String author, String text, boolean moderator) {
            this.author = author;
            this.text = text;
            this.moderator = moderator;
        }
    }

    public static final class Thread {
        public final int id;
        public final String garmentName;
        public final String title;
        public final List<Post> posts;
        public boolean closed;
        public String resolutionNote;

        public Thread(int id, String garmentName, String title) {
            this.id = id;
            this.garmentName = garmentName;
            this.title = title;
            this.posts = new ArrayList<>();
            this.closed = false;
            this.resolutionNote = "";
        }
    }

    private final List<Thread> threads = new ArrayList<>();
    private int nextId = 1;

    public Feature8Service() {
        Thread t = new Thread(nextId++, "Banarasi Saree", "Origin of zari motifs — Mughal vs local?");
        t.posts.add(new Post("Historian_A", "Temple records suggest parallel development.", false));
        t.posts.add(new Post("ModTeam", "Please cite sources for claims.", true));
        threads.add(t);
    }

    public List<Thread> getThreads() {
        return Collections.unmodifiableList(threads);
    }

    public void addThread(String garment, String title, String opener) {
        Thread t = new Thread(nextId++, garment, title);
        t.posts.add(new Post("User", opener, false));
        threads.add(0, t);
    }

    public void addReply(Thread t, String author, String text, boolean mod) {
        if (t.closed) {
            return;
        }
        t.posts.add(new Post(author, text, mod));
    }

    public void closeThread(Thread t, String resolution) {
        t.closed = true;
        t.resolutionNote = resolution != null ? resolution : "";
        t.posts.add(new Post("Moderator", "Thread closed. Resolution: " + t.resolutionNote, true));
    }
}
