package abstractions;

public interface IBackup {
    void backupToFile(String filePath);

    void restoreFromFile(String filePath);
}
