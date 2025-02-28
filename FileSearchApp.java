import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileSystemItem {
    String name;
    boolean isDirectory;
    List<FileSystemItem> children;

    FileSystemItem(String name, boolean isDirectory) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.children = new ArrayList<>();
    }

    void addChild(FileSystemItem child) {
        if (this.isDirectory) {
            this.children.add(child);
        }
    }

    void searchFiles(String extension, String path, FileWriter writer) throws IOException {
        String fullPath = path + "/" + name;
        if (!isDirectory && name.endsWith(extension)) {
            writer.write(fullPath + "\n");
            System.out.println("File found: " + fullPath);
        }
        for (FileSystemItem child : children) {
            child.searchFiles(extension, fullPath, writer);
        }
    }
}

public class FileSearchApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter directory path: ");
        String directoryPath = scanner.nextLine();
        System.out.print("Enter file extension to search for: ");
        String fileExtension = scanner.nextLine();
        scanner.close();

        FileSystemItem root = new FileSystemItem(directoryPath, true);
        FileSystemItem subfolder = new FileSystemItem("subfolder", true);
        root.addChild(new FileSystemItem("notes.txt", false));
        subfolder.addChild(new FileSystemItem("todo.txt", false));
        root.addChild(subfolder);

        try (FileWriter writer = new FileWriter("search_results.txt")) {
            System.out.println("Searching...");
            root.searchFiles(fileExtension, directoryPath, writer);
            System.out.println("Search completed. Results saved to search_results.txt.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
}
