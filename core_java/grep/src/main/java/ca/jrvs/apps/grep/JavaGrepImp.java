package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class JavaGrepImp implements JavaGrep{

  private static final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  private String regex;
  private String rootPath;
  private String outFile;
  private Pattern pattern;

  public static void main(String[] args) {

    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    BasicConfigurator.configure();

    JavaGrepImp app = new JavaGrepImp();
    app.setRegex(args[0]);
    app.setRootPath(args[1]);
    app.setOutFile(args[2]);

    try {
      app.process();
    } catch (IOException ex) {
      logger.error("Error: Unable to process", ex);
    }
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
    this.pattern = Pattern.compile(regex);
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  @Override
  public void process() throws IOException {
    List<File> files = listFiles(getRootPath());
    List<String> matchedLines = new ArrayList<>();
    for (File file : files) {
      List<String> lines = readLines(file);
      for (String line : lines) {
        if (containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    }
    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    File root = new File(rootDir);
    List<File> files = new ArrayList<>();
    if (root.isFile()) {
      files.add(root);
      return files;
    }
    File[] leaves = root.listFiles();
    if (leaves != null) {
      for (File leaf:leaves) {
        if (leaf.isFile()) {
          files.add(leaf);
        } else if (leaf.isDirectory()){
          files.addAll(listFiles(leaf.getAbsolutePath()));
        }
      }
    }
    return files;
  }

  @Override
  public List<String> readLines(File inputFile) {
    if (inputFile == null || !inputFile.isFile()) {
      throw new IllegalArgumentException("Invalid file");
    }
    List<String> lines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
      String line;
      while ((line = br.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException ex) {
      logger.error("Error reading file", ex);
    }
    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    if (line == null){
      return false;
    } else {
      return pattern.matcher(line).find();
    }
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    if (lines == null) {
      throw new IllegalArgumentException("No lines");
    }
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(getOutFile()))){
      for (String line : lines) {
        bw.write(line);
        bw.newLine();
      }
    }
  }
}
