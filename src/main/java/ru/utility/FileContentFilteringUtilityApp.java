package ru.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import ru.utility.statistics.FullStatisticsFloatsNumber;
import ru.utility.statistics.FullStatisticsIntegersNumber;
import ru.utility.statistics.FullStatisticsStrings;

public class FileContentFilteringUtilityApp {

  public static void main(String[] args) {
    String fileNameForOutputStrings = "strings.txt";
    String fileNameForOutputIntegersNumber = "integers.txt";
    String fileNameForOutputFloatsNumber = "floats.txt";

    final ArgsCMD argsCMD = new ArgsCMD();

    int countIntegersElem = 0;
    int countFloatsElem = 0;
    int countStringsElem = 0;

    final FullStatisticsStrings fullStatisticsStrings = new FullStatisticsStrings();
    final FullStatisticsIntegersNumber fullStatisticsIntegersNumber = new FullStatisticsIntegersNumber();
    final FullStatisticsFloatsNumber fullStatisticsFloatsNumber = new FullStatisticsFloatsNumber();

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-o":
        case "-O":
          i++;
          if (args[i].startsWith("-")) {
            System.err.println(
                "Вы не указали путь для вывода результатов работы утилиты в аргумент -o.\n"
                    + "Вывод результатов работы утилиты будет осуществлен в текущую директорию.");
            i--;
          } else {
            Path path = Paths.get(args[i]).toAbsolutePath();
            if (Files.isDirectory(path)) {
              argsCMD.setOutputPath(path + File.separator);
            } else {
              System.err.println(
                  "Переданный путь в аргумент -o не существует на диске или не является директорией.\n"
                      + "Вывод результатов работы утилиты будет осуществлен в текущую директорию.");
            }
          }
          break;
        case "-p":
        case "-P":
          i++;
          if (args[i].startsWith("-")) {
            System.err.println(
                "При указании опции -p не был задан префикс имен результирующих файлов.");
            i--;
          } else {
            argsCMD.setPrefixNameFile(args[i]);
          }
          break;
        case "-a":
        case "-A":
          argsCMD.setModeOfAddingToExistingFiles(true);
          break;
        case "-s":
        case "-S":
          argsCMD.setShortStatistics(true);
          break;
        case "-f":
        case "-F":
          argsCMD.setFullStatistics(true);
          break;
        default: {
          if (args[i].startsWith("-")) {
            System.err.println("Указан не верный аргумент командной строки.");
            i--;
          } else {
            File file = new File(args[i]);
            if (file.isFile() && file.canRead()) {
              argsCMD.addFilesForFiltering(file);
            } else {
              System.err.println(
                  "Файл переданный в командной строке для обработки не найден на диске "
                      + "или защищен от чтения: " + args[i]);
            }
          }
        }
      }
    }

    if (argsCMD.getFilesForFiltering().isEmpty()) {
      System.err.println("Отсутствуют файлы для обработки.");
      System.exit(0);
    }

    BufferedReader[] bufferedReaderList = new BufferedReader[argsCMD.getFilesForFiltering().size()];
    int item = 0;
    for (File file : argsCMD.getFilesForFiltering()) {
      try {
        bufferedReaderList[item++] = new BufferedReader(
            new FileReader(file));
      } catch (FileNotFoundException e) {
        System.err.printf(
            "Ошибка открытия файла %s на чтение. Возможно файл защищен от чтения или поврежден на диске.",
            file);
      }
    }

    boolean isAllFilesForFilteringNotFinished = true;

    while (isAllFilesForFilteringNotFinished) {

      isAllFilesForFilteringNotFinished = false;

      String line;

      for (BufferedReader bufferedReader : bufferedReaderList) {

        try {
          if ((line = bufferedReader.readLine()) != null) {

            isAllFilesForFilteringNotFinished = true;

            if (isIntegerNumber(line)) {
              String pathToOutputFile = argsCMD.getOutputPath() + argsCMD.getPrefixNameFile()
                  + fileNameForOutputIntegersNumber;
              try {
                if (countIntegersElem == 0) {
                  writeToOutputFiles(pathToOutputFile, line,
                      argsCMD.isModeOfAddingToExistingFiles());
                } else {
                  writeToOutputFiles(pathToOutputFile, line, true);
                }
                countIntegersElem++;

                if (argsCMD.isFullStatistics()) {
                  fullStatisticsIntegersNumber.add(Long.parseLong(line));
                }

              } catch (IOException ex) {
                System.err.printf("Не удалось записать информацию в файл %s \n",
                    pathToOutputFile);
              }
              continue;
            }

            if (isFloatsNumber(line)) {
              String pathToOutputFile = argsCMD.getOutputPath() + argsCMD.getPrefixNameFile()
                  + fileNameForOutputFloatsNumber;
              try {
                if (countFloatsElem == 0) {
                  writeToOutputFiles(pathToOutputFile, line,
                      argsCMD.isModeOfAddingToExistingFiles());
                } else {
                  writeToOutputFiles(pathToOutputFile, line, true);
                }

                countFloatsElem++;

                if (argsCMD.isFullStatistics()) {
                  fullStatisticsFloatsNumber.add(Double.parseDouble(line));
                }

              } catch (IOException ex) {
                System.err.printf("Не удалось записать информацию в файл %s \n",
                    pathToOutputFile);
              }
              continue;
            }

            String pathToOutputFile =
                argsCMD.getOutputPath() + argsCMD.getPrefixNameFile() + fileNameForOutputStrings;
            try {
              if (countStringsElem == 0) {
                writeToOutputFiles(pathToOutputFile, line, argsCMD.isModeOfAddingToExistingFiles());
              } else {
                writeToOutputFiles(pathToOutputFile, line, true);
              }

              countStringsElem++;

              if (argsCMD.isFullStatistics()) {
                fullStatisticsStrings.add(line);
              }
            } catch (IOException ex) {
              System.err.printf("Не удалось записать информацию в файл %s \n",
                  pathToOutputFile);
            }
          }
        } catch (IOException e) {
          System.err.println(
              "Не удалось прочитать строку из файла.");
        }
      }
    }

    for (BufferedReader bufferedReader : bufferedReaderList) {
      try {
        bufferedReader.close();
      } catch (IOException e) {
        System.err.println(
            "Произошла ошибка при закрытии файловых потоков... работа программы продолжена...\n");
      }
    }

    if (argsCMD.isShortStatistics() || argsCMD.isFullStatistics()) {
      System.out.println("------Вывод статистики по результирующим файлам------");
      if (countStringsElem != 0) {
        System.out.println(
            "-----Файл " + argsCMD.getPrefixNameFile() + fileNameForOutputStrings + "-----\n"
                + "кол-во строк в файле " + countStringsElem +
                (argsCMD.isFullStatistics() ? "\nразмер самой короткой строки равен "
                    + fullStatisticsStrings.getShortString()
                    + "\nразмер самой длинной строки равен "
                    + fullStatisticsStrings.getLongString()
                    + "\n------------------------------------------------\n" : ""));
      }
      if (countIntegersElem != 0) {
        System.out.println(
            "-----Файл " + argsCMD.getPrefixNameFile() + fileNameForOutputIntegersNumber + "-----\n"
                + "кол-во элементов в файле " + countIntegersElem +
                (argsCMD.isFullStatistics() ? "\nmin = "
                    + fullStatisticsIntegersNumber.getMin()
                    + "\nmax = "
                    + fullStatisticsIntegersNumber.getMax()
                    + "\namount = "
                    + fullStatisticsIntegersNumber.getAmount()
                    + "\naverage = "
                    + fullStatisticsIntegersNumber.getAverage()
                    + "\n------------------------------------------------\n" : ""));
      }
      if (countFloatsElem != 0) {
        System.out.println(
            "-----Файл " + argsCMD.getPrefixNameFile() + fileNameForOutputFloatsNumber + "-----\n"
                + "кол-во элементов в файле " + countFloatsElem +
                (argsCMD.isFullStatistics() ? "\nmin = "
                    + fullStatisticsFloatsNumber.getMin()
                    + "\nmax = "
                    + fullStatisticsFloatsNumber.getMax()
                    + "\namount = "
                    + fullStatisticsFloatsNumber.getAmount()
                    + "\naverage = "
                    + fullStatisticsFloatsNumber.getAverage()
                    + "\n------------------------------------------------\n" : ""));
      }
    }
  }

  private static boolean isIntegerNumber(String line) {
    try {
      Long.parseLong(line);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  private static boolean isFloatsNumber(String line) {
    try {
      Double.parseDouble(line);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  private static void writeToOutputFiles(String path, String line, boolean mode)
      throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, mode))) {
      bw.write(line + "\n");
    } catch (IOException e) {
      throw new IOException();
    }
  }
}
