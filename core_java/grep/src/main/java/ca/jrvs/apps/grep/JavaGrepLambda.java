package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public interface JavaGrepLambda {

  /**
   * Top level search workflow
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse directory and return all files
   * @param rootDir input directory
   * @return files under the rootDir
   */
  Stream<File> listFiles(String rootDir);

  /**
   * Read a file and return all the lines
   * Explain FileReader, BufferedReader, and character encoding
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if given inputFile is not a file
   */
  Stream<String> readLines(File inputFile) throws IllegalArgumentException;

  /**
   * Check if a line contains the regex pattern (passed by user)
   * @param line input string
   * @return true if there's a match
   */
  boolean containsPattern(String line);

  /**
   * Writes lines to a file
   * @param lines matched line
   * @throws IOException if write failed
   */
  void writeToFile(Stream<String> lines) throws IOException;

  String getRootPath();
  void setRootPath(String rootPath);

  String getRegex();
  void setRegex(String regex);

  String getOutFile();
  void setOutFile(String outFile);
}

