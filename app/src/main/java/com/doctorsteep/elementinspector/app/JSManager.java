package com.doctorsteep.elementinspector.app;

import java.io.InputStream;

public class JSManager {

    private static String CONTENTEDITABLE_ON = "document.getElementsByTagName('body')[0].setAttribute('contenteditable', 'true'); contentEditableBooleanEI = true;";
    private static String CONTENTEDITABLE_OFF = "document.getElementsByTagName('body')[0].setAttribute('contenteditable', 'null'); contentEditableBooleanEI = false;";
    public static String CONTENTEDITABLE = "if (contentEditableBooleanEI == null) {var contentEditableBooleanEI = false;}" +
            "if (contentEditableBooleanEI == false) {" + CONTENTEDITABLE_ON + "} else {" + CONTENTEDITABLE_OFF + "}";








    private static String HIGHLIGHT_BORDER_ON = "function setSourceElementOnEI() {\n" +
            "\tvar bodyInnerElement = document.getElementsByTagName('body')[0].innerHTML;\n" +
            "\tdocument.getElementsByTagName('body')[0].innerHTML = bodyInnerElement +\n" +
            "\t'<style type=\"text/css\">.eiTagBorderDS {box-shadow: 0 1px 2px rgba(0,0,0,.2);}.eiTagBorderDS:active {cursor: pointer; box-shadow: 0 2px 7px rgba(0,0,0,.4);}</style>' +\n" +
            "\t'';\n" +
            "\n" +
            "\tvar elmEIDS = document.getElementsByTagName(\"*\");\n" +
            "\tfor(var i = 0, all = elmEIDS.length; i < all; i++){\n" +
            "\t    elmEIDS[i].classList.add('eiTagBorderDS');\n" +
            "\t    highlightBorderEnable = true;\n" +
            "\t}\n" +
            "}";
    private static String HIGHLIGHT_BORDER_OFF = "function setSourceElementOffEI() {\n" +
            "\tvar elmEIDS = document.getElementsByTagName(\"*\");\n" +
            "\tfor(var i = 0, all = elmEIDS.length; i < all; i++){\n" +
            "\t    elmEIDS[i].classList.remove('eiTagBorderDS');\n" +
            "\t    highlightBorderEnable = false;\n" +
            "\t}\n" +
            "}";
    public static String HIGHLIGHT_BORDER = HIGHLIGHT_BORDER_ON + "\n\n" + HIGHLIGHT_BORDER_OFF + "\n\n if (highlightBorderEnable == null) {var highlightBorderEnable = false;}" +
            "if (highlightBorderEnable == false) {setSourceElementOnEI();} else {setSourceElementOffEI();}";







    public static String SOURCE = "(function(){var bodyInner = document.getElementsByTagName('body')[0].innerHTML;\n" +
            "var htmlInner = document.getElementsByTagName('html')[0].innerHTML;\n" +
            "document.getElementsByTagName('body')[0].innerHTML = bodyInner +\n" +
            "'<style id=\"styleEIDS1\" type=\"text/css\">div.eiAlertDS {display: flex;position: fixed;top: 0;bottom: 0;left: 0;right: 0;background-color: rgba(0,0,0,.3); z-index: 999;align-items: center;justify-content: center;}</style>' +\n" +
            "'<style id=\"styleEIDS2\" type=\"text/css\">div.eiAlertContent {display: block;background-color: rgba(255,255,255,1);width: 100%;height: auto;max-height: 100vh;z-index: 1000;box-shadow: 0 2px 4px rgba(0,0,0,.6);}</style>' +\n" +
            "'<style id=\"styleEIDS3\" type=\"text/css\">textarea.eiTextareaDS {display: block;width: 97%;height: 80vh;resize: none;color: rgba(0,0,0,.9);font-size: 13px;outline: none;border: none;margin: 0;margin-left: 10px;padding: 0;}</style>' +\n" +
            "'<style id=\"styleEIDS4\" type=\"text/css\">h2.eiAlertTitle {font-family: sans-serif;font-weight: 600;font-size: 15px;color: rgba(0,0,0,1);outline: none;border: none;background-color: rgba(0,0,0,.1);margin: 0;padding: 10px;user-select: none;}</style>' +\n" +
            "'<style id=\"styleEIDS5\" type=\"text/css\">div.eiBottomContent {width: 90%;padding: 0;margin: 0;display: flex;align-items: center;}</style>' +\n" +
            "'<style id=\"styleEIDS6\" type=\"text/css\">button.buttonEIAlert {background-color: rgba(0,0,0,.1);color: rgba(0,0,0,.8);border-radius: 2px;padding: 7px;margin: 8px;user-select: none;outline: unset;border: 1px solid rgba(0,0,0,.15);}button.buttonEIAlert:active {box-shadow: 0 2px 3px rgba(0,0,0,.3);}</style>' +\n" +
            "'<div class=\"eiAlertDS\" id=\"eiAlertDS\" contenteditable=\"false\">' +\n" +
            "    '<div class=\"eiAlertContent\">' +\n" +
            "        '<h2 class=\"eiAlertTitle\">Source code page</h2>' +\n" +
            "        '<textarea class=\"eiTextareaDS\" id=\"eiTextareaDS\">' + htmlInner + '</textarea>' +\n" +
            "        '<div class=\"eiBottomContent\">' +\n" +
            "            '<button class=\"buttonEIAlert\" id=\"saveEIDS\" onclick=\"document.getElementsByTagName(\\'html\\')[0].innerHTML = document.getElementById(\\'eiTextareaDS\\').value\">Save</button>' +\n" +
            "            '<button class=\"buttonEIAlert\" id=\"closeEIDS\" onclick=\"document.getElementById(\\'eiAlertDS\\').remove()\">Close</button>' +\n" +
            "        '</div>' +\n" +
            "    '</div>' +\n" +
            "'</div>' +\n" +
            "'';})()";

}
