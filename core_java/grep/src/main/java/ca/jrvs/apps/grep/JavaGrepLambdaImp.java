package ca.jrvs.apps.grep;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp implements JavaGrepLambda {

  private static final Logger logger = LoggerFactory.getLogger(JavaGrepLambdaImp.class);

  private String regex;
  private String rootPath;
  private String outFile;
  private Pattern pattern;

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    BasicConfigurator.configure();

    JavaGrepLambdaImp app = new JavaGrepLambdaImp();
    app.setRegex(args[0]);
    app.setRootPath(args[1]);
    app.setOutFile(args[2]);

    try {
      app.process();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public String getRootPath() { return rootPath; }

  @Override
  public void setRootPath(String rootPath) { this.rootPath = rootPath; }

  @Override
  public String getRegex() { return regex; }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
    this.pattern = Pattern.compile(regex);
  }

  @Override
  public String getOutFile() { return outFile; }

  @Override
  public void setOutFile(String outFile) { this.outFile = outFile; }

  @Override
  public void process() throws IOException {
    Stream<String> matchedLines = listFiles(getRootPath())
        .flatMap(this::readLines)
        .filter(this::containsPattern);
    writeToFile(matchedLines);
  }

  @Override
  public void writeToFile(Stream<String> lines) throws IOException {
    if (lines == null) throw new IllegalArgumentException("No lines");
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(getOutFile()))) {
      lines.forEach(line -> {
        try {
          bw.write(line);
          bw.newLine();
        } catch (IOException ex) {
          logger.error("Cannot write to file", ex);
        }
      });
    }
  }

  @Override
  public Stream<File> listFiles(String rootDir) {
    File root = new File(rootDir);
    if (root.isFile()){
      return Stream.of(root);
    }
    File[] leaves = root.listFiles();
    if (leaves == null){
      return Stream.empty();
    }
    return Arrays.stream(leaves)
        .flatMap(file -> {
          if (file.isDirectory()) {
            return listFiles(file.getAbsolutePath());
          } else {
            return Stream.of(file);
          }
        });
  }

  @Override
  public Stream<String> readLines(File inputFile) {
    if (inputFile == null || !inputFile.isFile())
      throw new IllegalArgumentException("Invalid file");
    try {
      BufferedReader br = new BufferedReader(new FileReader(inputFile));
      return br.lines().onClose(() -> {
        try {
          br.close();
        } catch (IOException ex) {
          logger.error("Error closing reader", ex);
        }
      });
    } catch (IOException ex) {
      logger.error("Cannot read file", ex);
      return Stream.empty();
    }
  }

  public boolean containsPattern(String line) {
    return pattern.matcher(line).find();
  }
}