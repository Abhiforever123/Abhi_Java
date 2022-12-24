package com.ibsplc.common;

import autoitx4java.AutoItX;
import com.jacob.com.LibraryLoader;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import java.io.File;
import java.io.IOException;

/**
 * Created by a-7681 on 5/29/2018.
 */
public class HHT {

    final private String JACOB_DLL_TO_USE = "jacob-1.18-x64.dll";
    protected AutoItX control;
    protected Screen screen;
    final int DELAY = 500;
    final String resourcePath = System.getProperty("user.dir") + "\\resources\\HHT\\HHTImages\\";
    final String hhtKiller = System.getProperty("user.dir") + "\\resources\\HHT\\HHTKill.bat";

    public HHT(){

        File file = new File(System.getProperty("user.dir") + "\\lib", JACOB_DLL_TO_USE);
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
        control = new AutoItX();
        screen = new Screen();
    }

    protected void openHHT(String pathToHHT){
        control.run(pathToHHT);
        return;
    }
    
    protected void focusWindow(String winTitle){
        control.winActivate(winTitle);
        control.winWaitActive(winTitle);
        control.winMove(winTitle,"",0,0);
        control.winActive(winTitle);
        return;
    }

    protected void click(String winTitle, String controlID){

        focusWindow(winTitle);
        delay();
        control.controlClick(winTitle,"",controlID);
        return;
    }

    protected String getText(String winTitle, String controlID){
        focusWindow(winTitle);
        delay();
        return control.controlGetText(winTitle,"",controlID);
    }

    protected void setText(String winTitle, String controlID, String text){
        focusWindow(winTitle);
        delay();
        control.ControlSetText(winTitle,"",controlID,text);
        return;
    }

    protected void sendKey(String winTitle, String controlID, String key){
        focusWindow(winTitle);
        click(winTitle, controlID);
        delay();
        control.send(key,false);
        return;
    }

    protected void selectByIndex(String winTitle, String controlID, int index){
        focusWindow(winTitle);
        click(winTitle, controlID);
        delay();
        control.send("{DOWN " + String.valueOf(index) + "}",false);
        return;
    }

    protected void closeWindow(String winTitle){

        focusWindow(winTitle);
        control.winClose(winTitle);
    }

    protected void imageWait(String imagePath, int timeOut) throws FindFailed {
        screen.wait(resourcePath + imagePath,timeOut);
        return;
    }

    protected void imageClick(String imagePath) throws FindFailed {
        screen.click(resourcePath + "blank.PNG");
        screen.click(resourcePath + imagePath);
        return;
    }

    protected void killHHT() throws IOException {
        Runtime.getRuntime().exec(hhtKiller);
    }

    protected void minWait() {
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return;
    }


    private void delay() {
        try{
            Thread.sleep(DELAY);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return;
    }


}
