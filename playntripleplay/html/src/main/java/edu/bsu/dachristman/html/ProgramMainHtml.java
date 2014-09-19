package edu.bsu.dachristman.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import edu.bsu.dachristman.core.ProgramMain;

public class ProgramMainHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform platform = HtmlPlatform.register(config);
    platform.assets().setPathPrefix("playntripleplay/");
    PlayN.run(new ProgramMain());
  }
}
