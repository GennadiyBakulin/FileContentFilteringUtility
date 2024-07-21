package ru.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ArgsCMD {

  private String outputPath;
  private String prefixNameFile;
  private boolean modeOfAddingToExistingFiles;
  private boolean isShortStatistics;
  private boolean isFullStatistics;
  private final LinkedHashSet<File> files;

  public ArgsCMD() {
    outputPath = "";
    prefixNameFile = "";
    files = new LinkedHashSet<>();
  }

  public String getOutputPath() {
    return outputPath;
  }

  public void setOutputPath(String outputPath) {
    this.outputPath = outputPath;
  }

  public String getPrefixNameFile() {
    return prefixNameFile;
  }

  public void setPrefixNameFile(String prefixNameFile) {
    this.prefixNameFile = prefixNameFile;
  }

  public boolean isModeOfAddingToExistingFiles() {
    return modeOfAddingToExistingFiles;
  }

  public void setModeOfAddingToExistingFiles(boolean modeOfAddingToExistingFiles) {
    this.modeOfAddingToExistingFiles = modeOfAddingToExistingFiles;
  }

  public boolean isShortStatistics() {
    return isShortStatistics;
  }

  public void setShortStatistics(boolean shortStatistics) {
    isShortStatistics = shortStatistics;
  }

  public boolean isFullStatistics() {
    return isFullStatistics;
  }

  public void setFullStatistics(boolean fullStatistics) {
    isFullStatistics = fullStatistics;
  }

  public LinkedHashSet<File> getFilesForFiltering() {
    return files;
  }

  public void addFilesForFiltering(File file) {
    files.add(file);
  }
}
