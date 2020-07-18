package model.fileTransfer;

import java.io.File;
import java.util.ArrayList;

public class FileToShow {
    public static ArrayList<FileToShow> fileToShows = new ArrayList<>();
    public String seller;
    public File file;

    public FileToShow() {
        fileToShows.add(this);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getSeller() {
        return seller;
    }

    public static ArrayList<FileToShow> getFileToShows() {
        return fileToShows;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }


    public static void setFileToShows(ArrayList<FileToShow> fileToShows) {
        FileToShow.fileToShows = fileToShows;
    }

}
