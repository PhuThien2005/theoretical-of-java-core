package compositepatterndesign;

import java.util.ArrayList;
import java.util.List;

public class CompositePatternDesignSolution {

    public interface FileSystemComponent {
        String getName();
        long getSize();
        void print(String indent);
    }

    public static class File implements FileSystemComponent {
        
        private final String name;
        private final long size;

        public File(String name, long size) {
            if (name == null) {
                throw new IllegalArgumentException("File name cannot be null");
            }
            if (size < 0) {
                throw new IllegalArgumentException("File size cannot be negative");
            }
            this.name = name;
            this.size = size;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public long getSize() {
            return size;
        }

        @Override
        public void print(String indent) {
            System.out.println(indent + "- File: " + getName() + " (" + getSize() + " bytes)");
        }
    }

    public static class Directory implements FileSystemComponent {
        
        private final String name;
        private final List<FileSystemComponent> children = new ArrayList<>();

        public Directory(String name) {
            if (name == null) {
                throw new IllegalArgumentException("Directory name cannot be null");
            }
            this.name = name;
        }

        public void add(FileSystemComponent component) {
            if (component == null) {
                throw new IllegalArgumentException("Cannot add null component");
            }
            children.add(component);
        }

        public void remove(FileSystemComponent component) {
            children.remove(component);
        }

        public List<FileSystemComponent> getChildren() {
            return List.copyOf(children);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public long getSize() {
            // Aggregates sizes of all children recursively.
            // Works transparently for both File (leaf) and Directory (composite) instances.
            long totalSize = 0;
            for (FileSystemComponent child : children) {
                totalSize += child.getSize();
            }
            return totalSize;
        }

        @Override
        public void print(String indent) {
            System.out.println(indent + "+ Directory: " + getName());
            // Delegate the printing to all children recursively.
            for (FileSystemComponent child : children) {
                child.print(indent + "  ");
            }
        }
    }
}
