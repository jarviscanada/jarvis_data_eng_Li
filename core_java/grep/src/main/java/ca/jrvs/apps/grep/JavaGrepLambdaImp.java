package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp extends JavaGrepImp {

  private static final Logger logger = LoggerFactory.getLogger(JavaGrepLambdaImp.class);

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
  public void process() throws IOException {
    List<String> matchedLines = listFiles(getRootPath()).stream().
        flatMap(file -> readLines(file).stream()).filter(this::containsPattern).
        collect(Collectors.toList());
    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    File root = new File(rootDir);
    if (root.isFile()) {
      return Collections.singletonList(root);
    }
    File[] leaves = root.listFiles();
    if (leaves == null) {
      return new ArrayList<>();
    }
    return Arrays.stream(leaves).
        flatMap(file -> {
          if (file.isDirectory()){
          return listFiles(file.getAbsolutePath()).stream();
          } else {
            return Stream.of(file);
          }
    }).collect(Collectors.toList());
  }

  @Override
  public List<String> readLines(File inputFile) {
    if (inputFile == null || !inputFile.isFile()) {
      throw new IllegalArgumentException("Invalid file");
    }
    try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
      return br.lines().collect(Collectors.toList());
    } catch (IOException ex) {
      logger.error("Error reading file", ex);
    }
    return new ArrayList<>();
  }

}

