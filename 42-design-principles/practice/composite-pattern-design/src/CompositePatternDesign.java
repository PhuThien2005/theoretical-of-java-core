package compositepatterndesign;

import java.util.ArrayList;
import java.util.List;

public class CompositePatternDesign {

    public interface FileSystemComponent {
        String getName();
        long getSize();
        void print(String indent);
    }

    public static class File implements FileSystemComponent {
        
        // TODO: Declare private fields for name and size.

        public File(String name, long size) {
            // TODO: Initialize fields.
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public void print(String indent) {
            // TODO: Print file information: indent + "- File: " + getName() + " (" + getSize() + " bytes)"
        }
    }

    public static class Directory implements FileSystemComponent {
        
        // TODO: Declare private fields for name and list of children (FileSystemComponent).

        public Directory(String name) {
            // TODO: Initialize fields.
        }

        public void add(FileSystemComponent component) {
            // TODO: Add component to children.
        }

        public void remove(FileSystemComponent component) {
            // TODO: Remove component from children.
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public long getSize() {
            // TODO: Dynamically aggregate sizes of all children.
            return 0;
        }

        @Override
        public void print(String indent) {
            // TODO: Print directory name: indent + "+ Directory: " + getName()
            // TODO: Recursively call print on all children with an increased indentation (indent + "  ").
        }
    }
}
