package ca.jrvs.apps.practice;

import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc{

  @Override
  public boolean matchJpeg(String filename){
    return filename != null && Pattern.matches("^.+\\.(jpg|jpeg)$", filename);
  }
  @Override
  public boolean matchIP(String ip){
    return ip != null && Pattern.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$", ip);
  }
  @Override
  public boolean isEmptyLine(String line){
    return line != null && Pattern.matches("^\\s*$", line);
  }
}
