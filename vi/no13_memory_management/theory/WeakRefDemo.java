package vi.no13_memory_management.theory;

import java.lang.ref.WeakReference;

public class WeakRefDemo {
    public static void main(String[] args) {
        String strongRef = new String("MetaData");
        WeakReference<String> weakRef = new WeakReference<String>(strongRef);
        System.out.println("Before:");
        System.out.println(strongRef);
        System.out.println(weakRef.get());
        strongRef = null;
        System.out.println("after");
        System.out.println(weakRef.get());
        System.out.println(strongRef);
        System.gc();
        System.out.println("gc:");
        System.out.println(weakRef.get());
        System.out.println(strongRef);
    }
}
